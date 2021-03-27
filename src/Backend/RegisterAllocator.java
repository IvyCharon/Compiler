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

    public RegisterAllocator(AssemModule asmM) {
        this.asmModule = asmM;
        t0 = asmM.getPhyReg("t0");
        t1 = asmM.getPhyReg("t1");
        t2 = asmM.getPhyReg("t2");
        sp = asmM.getPhyReg("sp");
    }

    ////////////// TO DO //////////////
    public void run() {
        asmModule.functions.forEach((name, func) -> {
            AssemBlock block = func.entranBlock;
            VirtualRegister tmp;
            while(block != null) {
                asmInst inst = block.instHead;
                while(inst != null) {
                    if(inst instanceof binaryInst) {
                        if(((binaryInst)inst).rs1 instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((binaryInst)inst).rs1;
                            inst.addPreInst(new loadInst(t1, sp, new Imm(tmp.index * 4 + 4), 4));
                            ((binaryInst)inst).rs1 = t1;
                            //use t1
                        }
                        if(((binaryInst)inst).rs2 instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((binaryInst)inst).rs2;
                            inst.addPreInst(new loadInst(t2, sp, new Imm(tmp.index * 4 + 4), 4));
                            ((binaryInst)inst).rs2 = t2;
                            //use t2
                        }
                        if(((binaryInst)inst).rd instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((binaryInst)inst).rd;
                            inst.addNextInst(new storeInst(t0, sp, new Imm(tmp.index * 4 + 4), 4));
                            ((binaryInst)inst).rd = t0;
                            //use t0
                        }
                    } else if(inst instanceof branchInst) {
                        if(((branchInst)inst).rs instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((branchInst)inst).rs;
                            inst.addPreInst(new loadInst(t0, sp, new Imm(tmp.index * 4 + 4), 4));
                            ((branchInst)inst).rs = t0;
                        }
                    } else if(inst instanceof liInst) {
                        if(((liInst)inst).rd instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((liInst)inst).rd;
                            inst.addNextInst(new storeInst(t0, sp, new Imm(tmp.index * 4 + 4), 4));
                            ((liInst)inst).rd = t0;
                        }
                    } else if(inst instanceof loadInst) {
                        if(((loadInst)inst).addr instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((loadInst)inst).addr;
                            inst.addPreInst(new loadInst(t1, sp, new Imm(tmp.index * 4 + 4), 4));
                            ((loadInst)inst).addr = t1;
                        }
                        if(((loadInst)inst).reg instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((loadInst)inst).reg;
                            inst.addNextInst(new storeInst(t0, sp, new Imm(tmp.index * 4 + 4), 4));
                            ((loadInst)inst).reg = t0;
                        }
                    } else if(inst instanceof luiInst) {

                    } else if(inst instanceof mvInst) {

                    } else if(inst instanceof storeInst) {
                        if(((storeInst)inst).addr instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((storeInst)inst).addr;
                            inst.addPreInst(new loadInst(t0, sp, new Imm(tmp.index * 4 + 4), 4));
                            ((storeInst)inst).addr = t0;
                        }
                        if(((storeInst)inst).reg instanceof VirtualRegister) {
                            tmp = (VirtualRegister)((storeInst)inst).reg;
                            inst.addPreInst(new loadInst(t1, sp, new Imm(tmp.index * 4 + 4), 4));
                            ((storeInst)inst).reg = t1;
                        }
                    }
                    inst = inst.next;
                }
                block = block.next;
            }
        });

    }

}
