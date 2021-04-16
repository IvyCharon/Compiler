package Backend;

import java.util.HashMap;

import Assembly.AssemBlock;
import Assembly.AssemModule;
import Assembly.AssemInst.*;
import Assembly.Operand.Imm;
import Assembly.Operand.PhysicalRegister;
import Assembly.Operand.Register;
import Assembly.Operand.VirtualRegister;

public class RegisterAllocator {
    public AssemModule asmModule;
    public HashMap<Register, Register> regMap = new HashMap<>();
    public PhysicalRegister t0, t1, t2, sp;
    public int maxStack = 0;

    public RegisterAllocator(AssemModule asmM) {
        this.asmModule = asmM;
        t0 = asmM.getPhyReg("t0");
        t1 = asmM.getPhyReg("t1");
        t2 = asmM.getPhyReg("t2");
        sp = asmM.getPhyReg("sp");
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    ////////////// TO DO //////////////
    public void run() {
        int maxMax = 0;
        AssemBlock block;
        for(var func : asmModule.functions.values()) {
            maxStack = 0;
            block = func.entranBlock;
            VirtualRegister tmp;
            while(block != null) {
                asmInst inst = block.instHead;
                while(inst != null) {
                    if(inst instanceof binaryInst) {
                        if(((binaryInst)inst).rs1 instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((binaryInst)inst).rs1;
                            inst.addPreInst(new loadInst(t1, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((binaryInst)inst).rs1 = t1;
                            //use t1
                        }
                        if(((binaryInst)inst).rs2 instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((binaryInst)inst).rs2;
                            inst.addPreInst(new loadInst(t2, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((binaryInst)inst).rs2 = t2;
                            //use t2
                        }
                        if(((binaryInst)inst).rd instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((binaryInst)inst).rd;
                            inst.addNextInst(new storeInst(t0, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((binaryInst)inst).rd = t0;
                            //use t0
                        }
                    } else if(inst instanceof branchInst) {
                        if(((branchInst)inst).rs instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((branchInst)inst).rs;
                            inst.addPreInst(new loadInst(t0, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((branchInst)inst).rs = t0;
                        }
                    } else if(inst instanceof liInst) {
                        if(((liInst)inst).rd instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((liInst)inst).rd;
                            inst.addNextInst(new storeInst(t0, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((liInst)inst).rd = t0;
                        }
                    } else if(inst instanceof loadInst) {
                        if(((loadInst)inst).addr instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((loadInst)inst).addr;
                            inst.addPreInst(new loadInst(t1, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((loadInst)inst).addr = t1;
                        }
                        if(((loadInst)inst).reg instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((loadInst)inst).reg;
                            inst.addNextInst(new storeInst(t0, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((loadInst)inst).reg = t0;
                        }
                    } else if(inst instanceof luiInst) {
                        if(((luiInst)inst).reg instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((luiInst)inst).reg;
                            inst.addNextInst(new storeInst(t0, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((luiInst)inst).reg = t0;
                        }
                    } else if(inst instanceof mvInst) {
                        if(((mvInst)inst).rs instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((mvInst)inst).rs;
                            inst.addPreInst(new loadInst(t1, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((mvInst)inst).rs = t1;
                        }
                        if(((mvInst)inst).rd instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((mvInst)inst).rd;
                            inst.addNextInst(new storeInst(t0, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((mvInst)inst).rd = t0;
                        }
                    } else if(inst instanceof setzInst) {
                        if(((setzInst)inst).rs instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((setzInst)inst).rs;
                            inst.addPreInst(new loadInst(t1, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((setzInst)inst).rs = t1;
                        }
                        if(((setzInst)inst).rd instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((setzInst)inst).rd;
                            inst.addNextInst(new storeInst(t0, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((setzInst)inst).rd = t0;
                        }
                    } else if(inst instanceof storeInst) {
                        if(((storeInst)inst).addr instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((storeInst)inst).addr;
                            inst.addPreInst(new loadInst(t0, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((storeInst)inst).addr = t0;
                        }
                        if(((storeInst)inst).reg instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((storeInst)inst).reg;
                            inst.addPreInst(new loadInst(t1, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((storeInst)inst).reg = t1;
                        }
                    } else if(inst instanceof laInst) {
                        if(((laInst)inst).rd instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((laInst)inst).rd;
                            inst.addNextInst(new storeInst(t0, sp, new Imm(tmp.index * 4), inst.block));
                            maxStack = max(maxStack, tmp.index * 4 + 4);
                            ((laInst)inst).rd = t0;
                        }
                    }
                    inst = inst.next;
                }
                block = block.next;
            }

            maxStack = maxStack + 12 * 4;

            block = func.entranBlock;

            while(block != null) {
                asmInst inst = block.instHead;
                while(inst != null) {
                    inst.setStackImm(maxStack);
                    inst = inst.next;
                }
                block = block.next;
            }
            block = func.entranBlock;
            block.addInstAtFront(new binaryInst("addi", asmModule.getPhyReg("sp"), asmModule.getPhyReg("sp"), new Imm(- maxStack - 4), block));
            if(maxStack + 4 >= maxMax) maxMax = maxStack + 4;
            func.maxStack = maxStack + 4;
        }

        //if(true) return;
        //System.out.println("qwq");
        if(maxMax <= 2048) return;

        //int off = maxMax;
        //int n = Math.floorDiv(off, 2048);

        for(var func : asmModule.functions.values()) {
            block = func.entranBlock;
            asmInst i;
            int off = func.maxStack;
            
            ////////////////////////TO DO////////////////////////
            int o;
            while(block != null) {
                i = block.instHead;
                while(i != null) {
                    if(i instanceof loadInst && ((loadInst)i).addr == asmModule.getPhyReg("sp")) {
                        o = ((loadInst)i).imm.val;
                        int rc = Math.floorDiv(off - o, 2048);
                        if(off - o == 2048 * rc) rc -= 1;
                        Register r = asmModule.getPhyReg("s" + rc);
                        ((loadInst)i).addr = r;
                        ((loadInst)i).imm.val = o - off + 2048 * (rc + 1);

                    } else if(i instanceof storeInst && ((storeInst)i).addr == asmModule.getPhyReg("sp")) {
                        o = ((storeInst)i).imm.val;
                        int rc = Math.floorDiv(off - o, 2048);
                        if(off - o == 2048 * rc) rc -= 1;
                        Register r = asmModule.getPhyReg("s" + rc);
                        ((storeInst)i).addr = r;
                        ((storeInst)i).imm.val = o - off + 2048 * (rc + 1); 
                    }
                    i = i.next;
                }
                block = block.next;
            }
            //////////////////////

            block = func.entranBlock;
            i = block.instHead;

            int n = Math.floorDiv(off, 2048);
            for(int s = 0;s <= 11; ++ s) {
                i.addNextInst(new storeInst(asmModule.getPhyReg("s" + s), asmModule.getPhyReg("sp"), new Imm(-4 * s - 4), func.entranBlock));
                i = i.next;
            }
            for(int c = 0; c <= n; ++ c) {
                i.addNextInst(new binaryInst("addi", asmModule.getPhyReg("sp"), asmModule.getPhyReg("sp"), new Imm(-2048), func.entranBlock));
                i = i.next;
                i.addNextInst(new mvInst(asmModule.getPhyReg("s" + c), asmModule.getPhyReg("sp"), func.entranBlock));
                i = i.next;
            }

            i = block.instHead;
            func.entranBlock.deleteInst(i);

            ///////////////////////////////////////////
            block = func.exitBlock;
            i = func.exitBlock.instTail.pre;
            asmInst j = i;
            
            for(int q = 0; q <= n; ++ q) {
                i.addNextInst(new binaryInst("addi", asmModule.getPhyReg("sp"), asmModule.getPhyReg("sp"), new Imm(1024), func.exitBlock));
                i = i.next;
                i.addNextInst(new binaryInst("addi", asmModule.getPhyReg("sp"), asmModule.getPhyReg("sp"), new Imm(1024), func.exitBlock));
                i = i.next;
            }

            for(int s = 0; s <= 11; ++ s) {
                i.addNextInst(new loadInst(asmModule.getPhyReg("s" + s), asmModule.getPhyReg("sp"), new Imm(-4 * s - 4), func.entranBlock));
                i = i.next;
            }
        

            func.exitBlock.deleteInst(j);

            //if(func.name.equals("main")) {
            //    func.entranBlock.addInstAtFront(new binaryInst("addi", asmModule.getPhyReg("sp"), asmModule.getPhyReg("sp"), new Imm(-2048), func.entranBlock));
            //}
            
        }
    }

}
