package Backend;

//import java.util.ArrayList;
import java.util.HashMap;

import Assembly.AssemBlock;
import Assembly.AssemFunction;
import Assembly.AssemModule;
import Assembly.AssemInst.binaryInst;
import Assembly.AssemInst.branchInst;
import Assembly.AssemInst.callInst;
import Assembly.AssemInst.jInst;
import Assembly.AssemInst.liInst;
import Assembly.AssemInst.loadInst;
import Assembly.AssemInst.luiInst;
import Assembly.AssemInst.mvInst;
import Assembly.AssemInst.retInst;
import Assembly.AssemInst.storeInst;
import Assembly.Operand.RelocationImm;
import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.Imm;
import Assembly.Operand.Register;
import Assembly.Operand.VirtualRegister;
import Assembly.Operand.asmOperand;
import MIR.BasicBlock;
import MIR.Function;
import MIR.Module;
import MIR.IRInst.AllocInst;
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
import MIR.IRType.ArrayType;
import MIR.IRType.BoolType;
import MIR.IRType.IntType;
import MIR.IRType.PointerType;
import Util.position;
import Util.error.semanticError;

public class InstSelector implements IRVisitor {
    public Module irModule;
    public AssemModule assemModule;
    public HashMap<Function, AssemFunction> functions = new HashMap<>();
    public HashMap<BasicBlock, AssemBlock> blocks = new HashMap<>();
    public HashMap<operand, Register> regs = new HashMap<>();

    public AssemFunction current_function = null;
    public AssemBlock current_block = null;

    public InstSelector(Module module, AssemModule asmM) {
        this.irModule = module;
        this.assemModule = asmM;
    }

    public void run() {        
        visit(irModule);
    }

    private Register getRegFromOper(operand oper) {
        if(oper instanceof ConstBool) {
            VirtualRegister ret = new VirtualRegister(assemModule.VirRegCnt ++);
            current_block.addInst(new liInst(ret, new Imm(((ConstBool) oper).value() ? 1 : 0)));
            return ret;
        } else if(oper instanceof ConstInt) {
            VirtualRegister ret = new VirtualRegister(assemModule.VirRegCnt ++);
            current_block.addInst(new liInst(ret, new Imm(((ConstInt) oper).value())));
            return ret;
        } else if(oper instanceof ConstNull) {
            VirtualRegister ret = new VirtualRegister(assemModule.VirRegCnt ++);
            current_block.addInst(new liInst(ret, new Imm(0)));
            return ret;
        } else if(oper instanceof ConstString) {
            return null;
        } else if(oper instanceof globalVariable) {
            if(regs.containsKey(oper)) return regs.get(oper);
            AsmGlobalVar ret = new AsmGlobalVar(((globalVariable)oper).name);
            regs.put(oper, ret);
            return ret;
        } else if(oper instanceof parameter) {
            System.exit(0);
            return null;
        } else if(oper instanceof MIR.IROperand.Register) {
            if(regs.containsKey(oper)) 
                return regs.get(oper);
            else {
                VirtualRegister tmp = new VirtualRegister(assemModule.VirRegCnt ++);
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

            if(var.type() instanceof IntType) {
                gV = new AsmGlobalVar(name, ((ConstInt) init).value());
            } else if(var.type() instanceof BoolType) {
                gV = new AsmGlobalVar(name, ((ConstBool) init).value());
            } else if(var.type() instanceof ArrayType) {
                gV = new AsmGlobalVar(name, ((ConstString) init).value());
            } else if(var.type() instanceof PointerType) {
                gV = new AsmGlobalVar(name);
            } else {
                throw new semanticError("qwq", new position(0,0));
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

        irModule.functions.forEach((irfunc, func) -> {
                this.visit(func);
        });
    }

    @Override
    public void visit(Function func) {
        current_function = assemModule.functions.get(func.name);
        current_block = getAsmBlock(func.entranceBlock);
        
/*         ArrayList<VirtualRegister> calleeSavedReg = new ArrayList<>();
        for(int i = 0;i <= 11; ++ i) {
            VirtualRegister r = new VirtualRegister(assemModule.VirRegCnt ++);
            current_block.addInst(new mvInst(r, assemModule.getPhyReg("s" + i)));
            calleeSavedReg.add(r);
        }
        current_block.addInst(new mvInst(new VirtualRegister(assemModule.VirRegCnt ++), assemModule.getPhyReg("ra"))); */

        int min = func.paras.size() <= 8 ? func.paras.size() : 8;
        for(int i = 0; i < min; ++ i) {
            current_block.addInst(new mvInst(assemModule.getPhyReg("a" + i), getRegFromOper(func.paras.get(i))));
        }

        //if paras.size() > 8
        // ----- TO DO -----
        BasicBlock tmp = func.entranceBlock;
        while(tmp != null) {
            this.visit(tmp);
            tmp = tmp.next;
        }
        
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
    public void visit(AllocInst inst) {         //TO DO
        System.exit(0);
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
                if(inst.op == binaryInstOp.add 
                    || inst.op == binaryInstOp.and 
                    || inst.op == binaryInstOp.or 
                    || inst.op == binaryInstOp.xor) {
                    rs1 = getRegFromOper(inst.right);
                    rs2 = new Imm(((ConstInt)inst.left).value());
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
            } else if(inst.right.isConst()) {
                rs1 = getRegFromOper(inst.left);
                rs2 = new Imm(((ConstInt)inst.right).value());
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
        current_block.addInst(new binaryInst(op, rd, rs1, rs2));
    }
    @Override
    public void visit(BitCastInst inst) {
        current_block.addInst(new mvInst(getRegFromOper(inst.result), getRegFromOper(inst.oper)));
    }
    @Override
    public void visit(BranchInst inst) {        //TO DO
        if(inst.condition != null) {
            current_block.addInst(new branchInst(getRegFromOper(inst.condition), getAsmBlock(inst.trueBlock), getAsmBlock(inst.falseBlock)));
        } else {
            current_block.addInst(new jInst(getAsmBlock(inst.trueBlock)));
        }
    }
    @Override
    public void visit(CallInst inst) {          //TO DO
        int min_ = inst.paras.size() < 8 ? inst.paras.size() : 8;
        for(int i = 0; i < min_; ++ i) {
            current_block.addInst(new mvInst(assemModule.getPhyReg("a" + i), getRegFromOper(inst.paras.get(i))));
            //----- para size > 8 -----
            //TO DO
            
        }

        current_block.addInst(new callInst(functions.get(inst.func)));

        if(inst.result != null) {
            current_block.addInst(new mvInst(getRegFromOper(inst.result), assemModule.getPhyReg("a0")));
        }
    }
    @Override
    public void visit(CompareInst inst) {
        VirtualRegister tmp;
        switch(inst.op) {
            case eq: //equal
                tmp = new VirtualRegister(assemModule.VirRegCnt ++);
                current_block.addInst(new binaryInst("xor", tmp, 
                                                    getRegFromOper(inst.left), 
                                                    getRegFromOper(inst.right)));
                current_block.addInst(new binaryInst("sltui", getRegFromOper(inst.result), tmp, new Imm(1)));
                break;
            case ne: //not equal
                tmp = new VirtualRegister(assemModule.VirRegCnt ++);
                current_block.addInst(new binaryInst("xor", tmp,
                                                    getRegFromOper(inst.left),
                                                    getRegFromOper(inst.right)));
                current_block.addInst(new binaryInst("slt", getRegFromOper(inst.result), assemModule.getPhyReg("zero"), tmp));
                break;
            case sgt: //greater
                current_block.addInst(new binaryInst("slt", 
                                                    getRegFromOper(inst.result), 
                                                    getRegFromOper(inst.right), 
                                                    getRegFromOper(inst.left)));
                break;
            case sge: //greater_equal
                tmp = new VirtualRegister(assemModule.VirRegCnt ++);
                current_block.addInst(new binaryInst("slt", tmp, 
                                                    getRegFromOper(inst.left), 
                                                    getRegFromOper(inst.right)));
                current_block.addInst(new binaryInst("xori", getRegFromOper(inst.result), tmp, new Imm(1)));
                break;
            case slt: //smaller
                current_block.addInst(new binaryInst("slt", 
                                                    getRegFromOper(inst.result), 
                                                    getRegFromOper(inst.left), 
                                                    getRegFromOper(inst.right)));
                break;
            case sle: //smaller_equal
                tmp = new VirtualRegister(assemModule.VirRegCnt ++);
                current_block.addInst(new binaryInst("slt", tmp, 
                                                    getRegFromOper(inst.right), 
                                                    getRegFromOper(inst.left)));
                current_block.addInst(new binaryInst("xori", getRegFromOper(inst.result), tmp, new Imm(1)));
                break;
        }
    }
    @Override
    public void visit(GetElementPtrInst inst) { //TO DO
        System.exit(0);
    }
    @Override
    public void visit(LoadInst inst) {
        Register rs = getRegFromOper(inst.result);
        Register rd = getRegFromOper(inst.address);

        if(rs instanceof AsmGlobalVar) {
            VirtualRegister vr = new VirtualRegister(assemModule.VirRegCnt ++);
            current_block.addInst(new luiInst(vr, new RelocationImm(1, ((MIR.IROperand.Register)(inst.address)).name)));
            current_block.addInst(new loadInst(rs, vr, new RelocationImm(0, ((MIR.IROperand.Register)(inst.address)).name), 4));
        } else {
            current_block.addInst(new loadInst(rs, rd, new Imm(0), 4));
        }
    }
    @Override
    public void visit(PhiInst inst) {           //TO DO
        System.exit(0);
    }
    @Override
    public void visit(ReturnInst inst) {
        current_block.addInst(new retInst());
    }
    @Override
    public void visit(StoreInst inst) {         //TO DO
        Register rs = getRegFromOper(inst.val);
        Register rd = getRegFromOper(inst.addr);

        if(rd instanceof AsmGlobalVar) {
            VirtualRegister vr = new VirtualRegister(assemModule.VirRegCnt ++);
            if(!(inst.addr instanceof MIR.IROperand.Register)) System.exit(0);
            current_block.addInst(new luiInst(vr, new RelocationImm(1, ((MIR.IROperand.Register)(inst.addr)).name)));
            current_block.addInst(new storeInst(rs, vr, new RelocationImm(0, ((MIR.IROperand.Register)(inst.addr)).name), 4));
        } else {
            current_block.addInst(new storeInst(rs, rd, new Imm(0), 4));
        }
    }
}
