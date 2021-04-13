package MIR.IRInst;

import java.io.PrintStream;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.Register;

public class MoveInst extends Inst {
    public Register rd, rs;

    public MoveInst(BasicBlock bb, Register rd, Register rs) {
        super(bb);
        this.rd = rd;
        this.rs = rs;
    }

    @Override
    public void print(PrintStream out) {
        out.println(rd.toString() + "=" + rs.toString());
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
