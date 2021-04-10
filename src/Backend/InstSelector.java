package Backend;

import java.util.HashMap;

import Assembly.AssemBlock;
import Assembly.AssemFunction;
import Assembly.AssemModule;
import Assembly.AssemInst.binaryInst;
import Assembly.AssemInst.branchInst;
import Assembly.AssemInst.callInst;
import Assembly.AssemInst.jInst;
import Assembly.AssemInst.laInst;
import Assembly.AssemInst.liInst;
import Assembly.AssemInst.loadInst;
import Assembly.AssemInst.luiInst;
import Assembly.AssemInst.mvInst;
import Assembly.AssemInst.retInst;
import Assembly.AssemInst.setzInst;
import Assembly.AssemInst.storeInst;
import Assembly.Operand.RelocationImm;
import Assembly.Operand.AddrImm;
import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.Imm;
import Assembly.Operand.Register;
import Assembly.Operand.VirtualRegister;
import Assembly.Operand.asmOperand;
import MIR.BasicBlock;
import MIR.Function;
import MIR.Module;
import MIR.IRInst.BinaryInst;
import MIR.IRInst.BitCastInst;
import MIR.IRInst.BranchInst;
import MIR.IRInst.CallInst;
import MIR.IRInst.CompareInst;
import MIR.IRInst.GetElementPtrInst;
import MIR.IRInst.Inst;
import MIR.IRInst.LoadInst;
import MIR.IRInst.PhiInst;
import MIR.IRInst.ReturnInst;
import MIR.IRInst.StoreInst;
import MIR.IRInst.BinaryInst.binaryInstOp;
import MIR.IROperand.*;
import MIR.IRType.ClassType;
import MIR.IRType.IRBaseType;
import MIR.IRType.PointerType;
import Util.error.runtimeError;

public class InstSelector implements IRVisitor {
    public Module irModule;
    public AssemModule assemModule;
    public HashMap<Function, AssemFunction> functions = new HashMap<>();
    public HashMap<BasicBlock, AssemBlock> blocks = new HashMap<>();
    public HashMap<operand, Register> regs = new HashMap<>();
    public HashMap<Register, AddrImm> addrImmMap = new HashMap<>();

    public AssemFunction current_function = null;
    public AssemBlock current_block = null;

    public int max_imm = 2047, min_imm = -2048;

    public int fix = 0;


    public InstSelector(Module module, AssemModule asmM) {
        this.irModule = module;
        this.assemModule = asmM;
    }

    public void run() {        
        visit(irModule);
    }

    private Register getRegFromOper(operand oper) {
        if(oper instanceof ConstBool) {
            VirtualRegister ret = new VirtualRegister(fix + current_function.VirRegCnt ++);
            current_block.addInst(new liInst(ret, new Imm(((ConstBool) oper).value() ? 1 : 0), current_block));
            return ret;
        } else if(oper instanceof ConstInt) {
            VirtualRegister ret = new VirtualRegister(fix + current_function.VirRegCnt ++);
            current_block.addInst(new liInst(ret, new Imm(((ConstInt) oper).value()), current_block));
            return ret;
        } else if(oper instanceof ConstNull) {
            VirtualRegister ret = new VirtualRegister(fix + current_function.VirRegCnt ++);
            current_block.addInst(new liInst(ret, new Imm(0), current_block));
            return ret;
        } else if(oper instanceof ConstString) {
            return null;
        } else if(oper instanceof globalVariable) {
            if(regs.containsKey(oper)) return regs.get(oper);
            AsmGlobalVar ret = new AsmGlobalVar(((globalVariable)oper).name);
            regs.put(oper, ret);
            return ret;
        } else if(oper instanceof parameter) {
            if(regs.containsKey(oper))
                return regs.get(oper);
            else {
                VirtualRegister tmp = new VirtualRegister(fix + current_function.VirRegCnt ++);
                regs.put(oper, tmp);
                return tmp;
            }
        } else if(oper instanceof MIR.IROperand.Register) {
            if(regs.containsKey(oper)) 
                return regs.get(oper);
            else {
                VirtualRegister tmp = new VirtualRegister(fix + current_function.VirRegCnt ++);
                regs.put(oper, tmp);
                return tmp;
            }
        } else 
            return assemModule.getPhyReg("zero");
    }

    private AssemBlock getAsmBlock(BasicBlock bb) {
        if(blocks.containsKey(bb))
            return blocks.get(bb);
        else {
            AssemBlock tmp = new AssemBlock(bb.name, bb);
            blocks.put(bb, tmp);
            return tmp;
        }
    }

    @Override
    public void visit(Module it) {
        irModule.globalVars.forEach((name, var) -> {
            AsmGlobalVar gV;
            operand init = var.init;

            if(init instanceof ConstInt) {
                gV = new AsmGlobalVar(name, ((ConstInt) init).value());
            } else if(init instanceof ConstBool) {
                gV = new AsmGlobalVar(name, ((ConstBool) init).value());
            } else if(init instanceof ConstString) {
                gV = new AsmGlobalVar(name, ((ConstString) init).value());
            } else {
                gV = new AsmGlobalVar(name, 0);
            }

            assemModule.globalVars.put(gV.name, gV);

        });
        irModule.builtinFunctions.forEach((name, func) -> {
            AssemFunction afunc = new AssemFunction(func);
            assemModule.builtinFunctions.put(afunc.name, afunc);
            functions.put(func, afunc);
        });
        irModule.functions.forEach((name, func) -> {
            AssemFunction afunc = new AssemFunction(func);
            assemModule.functions.put(afunc.name, afunc);
            functions.put(func, afunc);
        });

        Function init = irModule.functions.get("__init__");
        this.visit(init);

        Function m = irModule.functions.get("main");
        this.visit(m);

        irModule.functions.forEach((irfunc, func) -> {
            if((!irfunc.equals("__init__")) && (!irfunc.equals("main")))
                this.visit(func);
        });
    }

    @Override
    public void visit(Function func) {
        current_function = assemModule.functions.get(func.name);
        current_block = getAsmBlock(func.entranceBlock);

        //current_block.addInst(new binaryInst("addi", assemModule.getPhyReg("sp"), assemModule.getPhyReg("sp"), new Imm(-4, true)));
        
        /* ArrayList<VirtualRegister> calleeSavedReg = new ArrayList<>();
        for(int i = 0;i <= 11; ++ i) {
            VirtualRegister r = new VirtualRegister(fix + current_function.VirRegCnt ++);
            current_block.addInst(new mvInst(r, assemModule.getPhyReg("s" + i), current_block));
            calleeSavedReg.add(r);
        } */
        VirtualRegister ret = new VirtualRegister(fix + current_function.VirRegCnt ++);
        current_block.addInst(new mvInst(ret, assemModule.getPhyReg("ra"), current_block)); 

        int min = func.paras.size() <= 8 ? func.paras.size() : 8;
        for(int i = 0; i < min; ++ i) {
            current_block.addInst(new mvInst(getRegFromOper(func.paras.get(i)), assemModule.getPhyReg("a" + i), current_block));
        }

        //if paras.size() > 8
        // ----- TO DO -----

        // -----------------

        BasicBlock tmp = func.entranceBlock;
        while(tmp != null) {
            this.visit(tmp);
            tmp = tmp.next;
        }

        current_block = getAsmBlock(func.retBlock);
        
        /* for(int i = 0; i <= 11; ++ i) {
            current_block.addInst(new mvInst(assemModule.getPhyReg("s" + i), calleeSavedReg.get(i), current_block));
        } */
        current_block.addInst(new mvInst(assemModule.getPhyReg("ra"), ret, current_block));
        current_block.addInst(new binaryInst("addi", assemModule.getPhyReg("sp"), assemModule.getPhyReg("sp"), new Imm(4, true), current_block));
        current_block.addInst(new retInst(current_block));

        if(func.name.equals("__init__") || func.name.equals("main")) fix += current_function.VirRegCnt;
        
    }
    @Override
    public void visit(BasicBlock bb) {
        current_block = getAsmBlock(bb);
        current_function.addBlock(current_block);
        Inst inst = bb.instHead;
        while(inst != null) {
            inst.accept(this);
            inst = inst.next;
        }
    }

    @Override
    public void visit(BinaryInst inst) {
        Register rd = getRegFromOper(inst.result);
        Register rs1;
        asmOperand rs2;
        String op;
        //mul, mod, div cannot use imm
        if(inst.op == binaryInstOp.mul || inst.op == binaryInstOp.sdiv || inst.op == binaryInstOp.srem) {
            rs1 = getRegFromOper(inst.left);
            rs2 = getRegFromOper(inst.right);
            op = switch (inst.op) {
                case mul -> "mul";
                case sdiv -> "div";
                case srem -> "rem";
                default -> "wrong";
            };
        } else {
            if(inst.left.isConst()) {
                if((inst.op == binaryInstOp.add 
                    || inst.op == binaryInstOp.and 
                    || inst.op == binaryInstOp.or 
                    || inst.op == binaryInstOp.xor) 
                    && ((ConstInt)inst.left).value() <= max_imm 
                    && ((ConstInt)inst.left).value() >= min_imm) {
                    rs1 = getRegFromOper(inst.right);
                    if(inst.left instanceof ConstInt)
                        rs2 = new Imm(((ConstInt)inst.left).value());
                    else if(inst.left instanceof ConstBool)
                        rs2 = new Imm(((ConstBool)inst.left).value() ? 1 : 0);
                    else throw new runtimeError("[InstSelector][binaryInst] wrong type-1!");
                    op = switch (inst.op) {
                        case add -> "addi";
                        case and -> "andi";
                        case or -> "ori";
                        case xor -> "xori";                    
                        default -> "wrong";
                    };
                } else {
                    rs1 = getRegFromOper(inst.left);
                    rs2 = getRegFromOper(inst.right);
                    op = switch(inst.op) {
                        case sub -> "sub";
                        case shl -> "sll";
                        case ashr -> "sra";
                        default -> "wrong";
                    };
                }
            } else if(inst.right.isConst() && ((ConstInt)inst.right).value() <= max_imm && ((ConstInt)inst.right).value() >= min_imm) {
                rs1 = getRegFromOper(inst.left);
                if(inst.right instanceof ConstInt)
                    rs2 = new Imm(((ConstInt)inst.right).value());
                else if(inst.right instanceof ConstBool)
                    rs2 = new Imm(((ConstBool)inst.right).value() ? 1 : 0);
                else throw new runtimeError("[InstSelector][binaryInst] wrong type-2!");
                if(inst.op == binaryInstOp.sub) 
                    ((Imm)rs2).val = -((Imm)rs2).val;
                op = switch (inst.op) {
                    case add -> "addi";
                    case sub -> "addi"; 
                    case shl -> "slli";
                    case ashr -> "srai"; 
                    case and -> "andi";
                    case or -> "ori";
                    case xor -> "xori";
                    default -> "wrong";
                };
            } else {
                rs1 = getRegFromOper(inst.left);
                rs2 = getRegFromOper(inst.right);
                op = switch (inst.op) {
                    case add -> "add";
                    case sub -> "sub"; 
                    case shl -> "sll";
                    case ashr -> "sra"; 
                    case and -> "and";
                    case or -> "or";
                    case xor -> "xor";
                    default -> "wrong";
                };
            }
        }
        current_block.addInst(new binaryInst(op, rd, rs1, rs2, current_block));
    }
    @Override
    public void visit(BitCastInst inst) {
        current_block.addInst(new mvInst(getRegFromOper(inst.result), getRegFromOper(inst.oper), current_block));
    }
    @Override
    public void visit(BranchInst inst) {
        if(inst.condition != null) {
            current_block.addInst(new branchInst(getRegFromOper(inst.condition), getAsmBlock(inst.trueBlock), getAsmBlock(inst.falseBlock), current_block));
        } else {
            current_block.addInst(new jInst(getAsmBlock(inst.trueBlock), current_block));
        }
    }
    @Override
    public void visit(CallInst inst) {          //TO DO
        int min_ = inst.paras.size() < 8 ? inst.paras.size() : 8;
        for(int i = 0; i < min_; ++ i) {
            current_block.addInst(new mvInst(assemModule.getPhyReg("a" + i), getRegFromOper(inst.paras.get(i)), current_block));
            
            
        }

        //----- para size > 8 -----
        //TO DO

        current_block.addInst(new callInst(functions.get(inst.func), current_block));

        if(inst.func.retVal != null && inst.result != null) {
            current_block.addInst(new mvInst(getRegFromOper(inst.result), assemModule.getPhyReg("a0"), current_block));
        }
    }
    @Override
    public void visit(CompareInst inst) {
        VirtualRegister tmp;
        switch(inst.op) {
            case eq: //equal
                tmp = new VirtualRegister(fix + current_function.VirRegCnt ++);
                current_block.addInst(new binaryInst("xor", tmp, 
                                                    getRegFromOper(inst.left), 
                                                    getRegFromOper(inst.right),
                                                    current_block));
                current_block.addInst(new setzInst("seqz", getRegFromOper(inst.result), tmp, current_block));
                break;
            case ne: //not equal
                tmp = new VirtualRegister(fix + current_function.VirRegCnt ++);
                current_block.addInst(new binaryInst("xor", tmp,
                                                    getRegFromOper(inst.left),
                                                    getRegFromOper(inst.right),
                                                    current_block));
                current_block.addInst(new setzInst("snez", getRegFromOper(inst.result), tmp, current_block));
                break;
            case sgt: //greater
                current_block.addInst(new binaryInst("slt", 
                                                    getRegFromOper(inst.result), 
                                                    getRegFromOper(inst.right), 
                                                    getRegFromOper(inst.left),
                                                    current_block));
                break;
            case sge: //greater_equal
                tmp = new VirtualRegister(fix + current_function.VirRegCnt ++);
                current_block.addInst(new binaryInst("slt", tmp, 
                                                    getRegFromOper(inst.left), 
                                                    getRegFromOper(inst.right),
                                                    current_block));
                current_block.addInst(new binaryInst("xori", getRegFromOper(inst.result), tmp, new Imm(1), current_block));
                break;
            case slt: //smaller
                current_block.addInst(new binaryInst("slt", 
                                                    getRegFromOper(inst.result), 
                                                    getRegFromOper(inst.left), 
                                                    getRegFromOper(inst.right),
                                                    current_block));
                break;
            case sle: //smaller_equal
                tmp = new VirtualRegister(fix + current_function.VirRegCnt ++);
                current_block.addInst(new binaryInst("slt", tmp, 
                                                    getRegFromOper(inst.right), 
                                                    getRegFromOper(inst.left),
                                                    current_block));
                current_block.addInst(new binaryInst("xori", getRegFromOper(inst.result), tmp, new Imm(1), current_block));
                break;
        }
    }
    @Override
    public void visit(GetElementPtrInst inst) { //TO DO
        Register rd = getRegFromOper(inst.result);
        if(inst.ptr.type() instanceof PointerType) {
            Register base = getRegFromOper(inst.ptr);
            if(((PointerType)(inst.ptr.type())).baseType instanceof ClassType) {
                ClassType cla = (ClassType)((PointerType)(inst.ptr.type())).baseType;
                int index = ((ConstInt)(inst.index.get(1))).value();
                int off = cla.sizeOffset(index);
                IRBaseType t = cla.getMemType(index);
                if(t instanceof PointerType) {
                    if(off <= max_imm && off >= min_imm)
                        current_block.addInst(new binaryInst("addi", rd, base, new Imm(off), current_block));
                    else current_block.addInst(new binaryInst("add", rd, base, getRegFromOper(new ConstInt(32, off)), current_block));
                    addrImmMap.put(rd, new AddrImm(rd, 0));
                } else {
                    addrImmMap.put(rd, new AddrImm(base, off));
                }
            } else {
                operand index = inst.index.get(0);

                if(index instanceof ConstInt) {
                    current_block.addInst(new binaryInst("add", rd, base, getRegFromOper(new ConstInt(32, ((ConstInt)index).value() * 4)), current_block));
                } else {
                    Register indexT = getRegFromOper(index);
                    Register tmp = new VirtualRegister(fix + current_function.VirRegCnt ++);
                    current_block.addInst(new binaryInst("slli", tmp, indexT, new Imm(2), current_block));
                    current_block.addInst(new binaryInst("add", rd, base, tmp, current_block));
                }
            }
        } else if(inst.ptr instanceof globalVariable) {
            current_block.addInst(new laInst(rd, getRegFromOper(inst.ptr)));
        } else {
            throw new runtimeError("[InstSelector][visitGetElePtr]wrong ptr!");
        }
    }
    @Override
    public void visit(LoadInst inst) {          //TO DO
        Register rd = getRegFromOper(inst.result);
        Register rs = getRegFromOper(inst.address);

        if(rs instanceof AsmGlobalVar) {
            VirtualRegister vr = new VirtualRegister(fix + current_function.VirRegCnt ++);
            current_block.addInst(new luiInst(vr, new RelocationImm("hi", ((MIR.IROperand.globalVariable)(inst.address)).name), current_block));
            current_block.addInst(new loadInst(rd, vr, new RelocationImm("lo", ((MIR.IROperand.globalVariable)(inst.address)).name), current_block));
        } else if(addrImmMap.containsKey(rs)) {
            AddrImm tmpI = addrImmMap.get(rs);
            current_block.addInst(new loadInst(rd, tmpI.baseReg, tmpI, current_block));
        } else if(inst.address.isArray) {
            AddrImm tmpIm = new AddrImm(rs, 0);
            current_block.addInst(new loadInst(rd, tmpIm.baseReg, tmpIm, current_block));
        } else {
            current_block.addInst(new mvInst(rd, rs, current_block));
        }
    }
    @Override
    public void visit(PhiInst inst) {           //TO DO
        //BasicBlock b1 = inst.blocks.get(0), b2 = inst.blocks.get(1);
        //AssemBlock ab1 = getAsmBlock(b1), ab2 = getAsmBlock(b2);
        //VirtualRegister tmp = new VirtualRegister(fix + current_function.VirRegCnt ++);
        System.out.println("is 4");
        System.exit(0);
    }
    @Override
    public void visit(ReturnInst inst) {
        if(inst.val != null) {
            Register ret = getRegFromOper(inst.val);
            if(ret instanceof AsmGlobalVar) {
                current_block.addInst(new luiInst(assemModule.getPhyReg("a0"), new RelocationImm("hi", ((AsmGlobalVar)ret).name), current_block));
            } else {
                current_block.addInst(new mvInst(assemModule.getPhyReg("a0"), ret, current_block));
            }
        }
    }
    @Override
    public void visit(StoreInst inst) {
        Register rs = getRegFromOper(inst.val);
        Register rd = getRegFromOper(inst.addr);

        if(rd instanceof AsmGlobalVar) {
            VirtualRegister vr = new VirtualRegister(fix + current_function.VirRegCnt ++);
            current_block.addInst(new luiInst(vr, new RelocationImm("hi", ((MIR.IROperand.globalVariable)(inst.addr)).name), current_block));
            current_block.addInst(new storeInst(rs, vr, new RelocationImm("lo", ((MIR.IROperand.globalVariable)(inst.addr)).name), current_block));
        } else if(addrImmMap.containsKey(rd)) {
            AddrImm tmpI = addrImmMap.get(rd);
            current_block.addInst(new storeInst(rs, tmpI.baseReg, tmpI, current_block));
        } else if(inst.addr.isArray) {
            AddrImm tmpIm = new AddrImm(rd, 0);
            current_block.addInst(new storeInst(rs, tmpIm.baseReg, tmpIm, current_block));
        } else {
            current_block.addInst(new mvInst(rd, rs, current_block));
        }
    }
}
