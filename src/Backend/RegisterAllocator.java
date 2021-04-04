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
        asmModule.functions.forEach((name, func) -> {
            maxStack = 0;
            AssemBlock block = func.entranBlock;
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
        });

    }

}
