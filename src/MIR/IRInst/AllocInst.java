package MIR.IRInst;

import java.io.PrintStream;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.Register;
import MIR.IRType.IRBaseType;

public class AllocInst extends Inst {
    public Register addr;
    public IRBaseType type;

    public AllocInst(BasicBlock bb, Register addr, IRBaseType type) {
        super(bb);
        this.addr = addr;
        this.type = type;
    }

    @Override
    public void print(PrintStream out) {
        out.println("alloc " + addr.toString());
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
