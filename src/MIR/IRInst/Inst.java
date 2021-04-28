package MIR.IRInst;

import java.io.PrintStream;

import Backend.IRVisitor;
import MIR.BasicBlock;

abstract public class Inst {
    private BasicBlock basic_block;

    public Inst pre, next;

    public Inst(BasicBlock bb) {
        this.basic_block = bb;
        pre = null;
        next = null;
    }

    public BasicBlock basic_block() {
        return basic_block;
    }

    public abstract void print(PrintStream out);

    public abstract void accept(IRVisitor it);

    public void replaceSelf(Inst i) {
        if(this.pre != null) {
            this.pre.next = i;
            i.pre = this.pre;
        } else {
            basic_block.instHead = i;
            i.pre = null;
        }
        if(this.next != null) {
            this.next.pre = i;
            i.next = this.next;
        } else {
            basic_block.instTail = i;
            i.next = null;
        }
    }
}
