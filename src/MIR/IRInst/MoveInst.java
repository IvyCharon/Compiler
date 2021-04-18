package MIR.IRInst;

import java.io.PrintStream;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.Register;
import MIR.IROperand.operand;

public class MoveInst extends Inst {
    public Register rd;
    public operand rs;

    public MoveInst(BasicBlock bb, Register rd, operand rs) {
        super(bb);
        this.rd = rd;
        this.rs = rs;
    }

    @Override
    public void print(PrintStream out) {
        out.println("\t" + rd.toString() + "=" + rs.toString());
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
