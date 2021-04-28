package Optim;

import java.util.HashMap;
import java.util.HashSet;

import Assembly.AssemBlock;
import Assembly.AssemFunction;
import Assembly.AssemModule;
import Assembly.AssemInst.asmInst;
import Assembly.AssemInst.binaryInst;
import Assembly.AssemInst.branchInst;
import Assembly.AssemInst.callInst;
import Assembly.AssemInst.jInst;
import Assembly.Operand.Imm;

public class HumanIntelligence {
    public AssemModule module;

    public HashSet<AssemFunction> uselessFunc = new HashSet<>();

    public HumanIntelligence(AssemModule m) {
        module = m;
    }

    public void run() {
        ReduceUselessBlock();
        ReduceInitFunc();
        ReduceSPAddi();
    }

    private void ReduceUselessBlock() {
        for(var func : module.functions.values()) {
            AssemBlock b = func.entranBlock;
            HashMap<AssemBlock, AssemBlock> uselessBlock = new HashMap<>();
            while(b != null) {
                if(b.instHead == null && b.instTail == null) {
                    uselessBlock.put(b, b.next);
                    b = b.next;
                    continue;
                }
                if(b.instHead instanceof jInst) {
                    uselessBlock.put(b, ((jInst)b.instHead).dest);
                    b = b.next;
                    continue;
                }
                b = b.next;
            }

            if(uselessBlock.isEmpty()) return;

            b = func.entranBlock;
            while(b != null) {
                if(uselessBlock.containsKey(b)) {
                    b = b.next;
                    continue;
                }
                asmInst i = b.instHead;
                while(i != null) {
                    if(i instanceof jInst) {
                        while(uselessBlock.containsKey(((jInst)i).dest))
                            ((jInst)i).dest = uselessBlock.get(((jInst)i).dest);
                    }
                    if(i instanceof branchInst) {
                        while(uselessBlock.containsKey(((branchInst)i).trueBlock))
                            ((branchInst)i).trueBlock = uselessBlock.get(((branchInst)i).trueBlock);
                        while(uselessBlock.containsKey(((branchInst)i).falseBlock))
                            ((branchInst)i).falseBlock = uselessBlock.get(((branchInst)i).falseBlock);
                    }
                    i = i.next;
                }
                b = b.next;
            }

            for(var bb : uselessBlock.keySet()) {
                bb.deleteSelf(func);
            }
        }
    }

    private void ReduceInitFunc() {
        AssemFunction init = module.functions.get("__init__");
        if(init.entranBlock.instHead.next instanceof jInst && ((jInst)init.entranBlock.instHead.next).dest == init.exitBlock) {
            AssemFunction ma = module.functions.get("main");
            asmInst i = ma.entranBlock.instHead;
            while((!(i instanceof callInst)) || ((callInst)i).func != init)
                i = i.next;
            i.deleteSelf();
            module.functions.remove("__init__");
        }
    }

    private void ReduceSPAddi() {
        for(var func : module.functions.values()) {
            asmInst i = func.entranBlock.instHead;
            if(((Imm)((binaryInst)i).rs2).val == 0) {
                i.deleteSelf();
                i = func.exitBlock.instTail.pre;
                i.deleteSelf();
            } else continue;
        }
    }
}
