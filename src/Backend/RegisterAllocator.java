package Backend;

import java.util.HashMap;

import Assembly.AssemBlock;
import Assembly.AssemModule;
import Assembly.AssemInst.asmInst;
import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.Register;
import Assembly.Operand.VirtualRegister;
import Util.error.runtimeError;

public class RegisterAllocator {
    public AssemModule asmModule;
    public HashMap<Register, Register> regMap = new HashMap<>();

    public RegisterAllocator(AssemModule asmM) {
        this.asmModule = asmM;
    }

    ////////////// TO DO //////////////
    public void run() {
        asmModule.functions.forEach((name, func) -> {
            AssemBlock block = func.entranBlock;
            while(block != null) {
                asmInst inst = block.instHead;
                while(inst != null) {
                    inst.UsedVirReg.forEach(vr -> {
                        if(vr instanceof VirtualRegister) {
                            //block.addInstAtFront();
                        } else if(vr instanceof AsmGlobalVar) {

                        } else {
                            throw new runtimeError("[RegAlloc]");
                        }
                    });
                    inst = inst.next;
                }
                block = block.next;
            }
        });

    }

}
