package Optim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Assembly.AssemBlock;
import Assembly.AssemFunction;
import Assembly.AssemInst.asmInst;
import Assembly.AssemInst.branchInst;
import Assembly.AssemInst.jInst;
import Assembly.Operand.Register;

public class LivenessAnalysis {
    public AssemFunction func;
    public HashMap<AssemBlock, HashSet<Register>> blockUses = new HashMap<>();
    public HashMap<AssemBlock, HashSet<Register>> blockDefs = new HashMap<>();
    public HashSet<AssemBlock> blockVisited = new HashSet<>();
    
    public LivenessAnalysis(AssemFunction f) {
        func = f;
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

    public void run() {
        getCFG(func);

        AssemBlock b = func.entranBlock;
        while(b != null) {
            HashSet<Register> u = new HashSet<>();
            HashSet<Register> d = new HashSet<>();
            asmInst i = b.instHead;
            while(i != null) {
                HashSet<Register> tmp = new HashSet<>(i.use());
                tmp.removeAll(d);
                u.addAll(tmp);
                d.addAll(i.def());
                i = i.next;
            }
            blockUses.put(b, u);
            blockDefs.put(b, d);
            b.liveIn.clear();
            b.liveOut.clear();

            b = b.next;
        }

        calLiveOut(func.exitBlock);

    }

    private void calLiveOut(AssemBlock b) {
        if(blockVisited.contains(b)) return;
        blockVisited.add(b);

        HashSet<Register> lo = new HashSet<>();
        b.successor.forEach(s -> {
            lo.addAll(s.liveIn);
        });
        b.liveOut.addAll(lo);

        HashSet<Register> li = new HashSet<>(lo);
        li.removeAll(blockDefs.get(b));
        li.addAll(blockUses.get(b));
        li.removeAll(b.liveIn);

        if(!li.isEmpty()) {
            b.liveIn.addAll(li);
            blockVisited.removeAll(b.predecessor);
        }

        b.predecessor.forEach(t -> calLiveOut(t));
    }
}
