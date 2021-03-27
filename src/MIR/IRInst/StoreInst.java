package MIR.IRInst;

import java.io.PrintStream;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.operand;

public class StoreInst extends Inst {
    public operand val, addr;
    
    public StoreInst(BasicBlock bb, operand val, operand addr) {
        super(bb);
        this.val = val;
        this.addr = addr;
    }

    @Override
    public void print(PrintStream out) {
        out.println("store " + addr.toString() + " " + val.toString());
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
