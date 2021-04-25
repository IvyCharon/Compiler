package Optim;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import Assembly.AssemBlock;
import Assembly.AssemFunction;
import Assembly.AssemModule;
import Assembly.AssemInst.asmInst;
import Assembly.AssemInst.branchInst;
import Assembly.AssemInst.jInst;
import Assembly.Operand.Register;

public class LivenessAnalysis {
    public AssemModule module;
    public LinkedHashMap<AssemBlock, LinkedHashSet<Register>> blockUses = new LinkedHashMap<>();
    public LinkedHashMap<AssemBlock, LinkedHashSet<Register>> blockDefs = new LinkedHashMap<>();
    public LinkedHashSet<AssemBlock> blockVisited = new LinkedHashSet<>();
    
    public LivenessAnalysis(AssemModule m) {
        this.module = m;
    }

    private void getCFG(AssemFunction func) {
        AssemBlock b = func.entranBlock;
        while(b != null) {
            b.predecessor = new ArrayList<>();
            b.successor = new ArrayList<>();
            b = b.next;
        }
        b = func.entranBlock;
        while(b != null) {
            asmInst i = b.instHead;
            while(i != null) {
                if(i instanceof branchInst) {
                    if(((branchInst)i).trueBlock != null) {
                        AssemBlock tmp = ((branchInst)i).trueBlock;
                        b.successor.add(tmp); tmp.predecessor.add(b);
                    }
                    if(((branchInst)i).falseBlock != null) {
                        AssemBlock tmp = ((branchInst)i).falseBlock;
                        b.successor.add(tmp); tmp.predecessor.add(b);
                    }
                    if(((branchInst)i).trueBlock != null && ((branchInst)i).falseBlock != null)
                        i.next = null;
                } else if(i instanceof jInst) {
                    AssemBlock tmp = ((jInst)i).dest;
                    b.successor.add(tmp); tmp.predecessor.add(b);
                    i.next = null;
                }
                i = i.next;
            }
            b = b.next;
        }
    }

    public void runFunc(AssemFunction func) {
        getCFG(func);

        AssemBlock b = func.entranBlock;
        while(b != null) {
            LinkedHashSet<Register> u = new LinkedHashSet<>();
            LinkedHashSet<Register> d = new LinkedHashSet<>();
            asmInst i = b.instHead;
            while(i != null) {
                LinkedHashSet<Register> tmp = new LinkedHashSet<>(i.use());
                tmp.removeAll(d);
                u.addAll(tmp);
                d.addAll(i.def());
                i = i.next;
            }
            blockUses.put(b, u);
            blockDefs.put(b, d);

            b = b.next;
        }

        calLiveOut(func.exitBlock);

    }

    private void calLiveOut(AssemBlock b) {
        if(blockVisited.contains(b)) return;
        blockVisited.add(b);

        LinkedHashSet<Register> lo = new LinkedHashSet<>();
        b.successor.forEach(s -> {
            lo.addAll(s.liveIn);
        });
        b.liveOut.addAll(lo);

        LinkedHashSet<Register> li = new LinkedHashSet<>(lo);
        li.removeAll(blockDefs.get(b));
        li.addAll(blockUses.get(b));
        li.removeAll(b.liveIn);

        if(!li.isEmpty()) {
            b.liveIn.addAll(li);
            blockVisited.removeAll(b.predecessor);
        }

        b.predecessor.forEach(t -> calLiveOut(t));
    }

    public void run() {
        module.functions.forEach((name, f) -> runFunc(f));
    }
}
