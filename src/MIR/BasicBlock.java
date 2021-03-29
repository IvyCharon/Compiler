package MIR;

import java.util.ArrayList;

import MIR.IRInst.BranchInst;
import MIR.IRInst.Inst;

public class BasicBlock {
    public ArrayList<BasicBlock> precursors = new ArrayList<>();
    public ArrayList<BasicBlock> successors = new ArrayList<>();

    public Inst instHead, instTail;

    public String name;
    public Function function;

    public BasicBlock pre, next;

    public boolean end = false;

    public BasicBlock(String name, Function function) {
        this.name = name;
        this.function = function;
        instHead = null; instTail = null;
    }

    public void addInst(Inst i) {
        if(instHead == null) {
            instHead = i;
            i.pre = null; i.next = null;
        } else if(instTail == null) {
            instTail = i;
            instHead.next = instTail;
            instTail.pre = instHead;
        } else {
            instTail.next = i;
            i.pre = instTail;
            instTail = i;
        }
    }

    public void addInstAtFront(Inst i) {
        if(instHead == null) {
            instHead = i;
            instTail = instHead;
            i.pre = null; i.next = null;
        } else {
            instHead.pre = i;
            i.next = instHead;
            instHead = i;
        }
    }

    public void addEndInst(Inst i) {
        addInst(i);
        end = true;
        BasicBlock dest = ((BranchInst)i).trueBlock;
        successors.add(dest);
        dest.precursors.add(this);
        if(((BranchInst)i).falseBlock != null) {
            dest = ((BranchInst)i).falseBlock;
            successors.add(dest);
            dest.precursors.add(this);
        }
    }

    public String toString() {
        return "%" + name;
    }
}
