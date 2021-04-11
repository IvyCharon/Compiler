package Assembly;

import Assembly.AssemInst.asmInst;
import MIR.BasicBlock;

public class AssemBlock {
    public String name;
    public BasicBlock irBlock;

    public AssemBlock pre, next;

    public asmInst instHead = null, instTail = null;

    public AssemBlock(String name, BasicBlock bb) {
        this.name = name;
        this.irBlock = bb;
    }

    public void addInst(asmInst i) {
        if(instHead == null) {
            instHead = i;
        } else if(instTail == null) {
            instTail = i;
            instHead.next = instTail;
            instTail.pre = instHead;
        } else {
            i.pre = instTail;
            instTail.next = i;
            instTail = i;
        }
    }

    public void addInstAtFront(asmInst i) {
        if(instHead == null) {
            instHead = i;
        } else if(instTail == null) {
            instTail = instHead;
            instHead = i;
            instHead.next = instTail;
            instTail.pre = instHead;
        } else {
            i.next = instHead;
            instHead.pre = i;
            instHead = i;
        }
    }

    public void deleteInst(asmInst i) {
        if(i.pre == null && i.next == null) {
            instHead = null;
            instTail = null;
        } else if(i.pre == null) {
            if(i.next == instTail) {
                instHead = i.next;
                instHead.pre = null; instHead.next = null;
                instTail = null;
            } else {
                instHead = i.next;
                instHead.pre = null;
            }
        } else if(i.next == null) {
            if(i.pre == instHead) {
                instHead = i.pre;
                instHead.pre = null; instHead.next = null;
                instTail = null;
            } else {
                instTail = i.pre;
                instTail.next = null;
            }
        } else {
            i.pre.next = i.next;
            i.next.pre = i.pre;
        }
    }
}
