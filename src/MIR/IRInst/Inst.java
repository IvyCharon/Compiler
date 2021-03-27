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
}
