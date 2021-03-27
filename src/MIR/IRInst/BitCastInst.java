package MIR.IRInst;

import java.io.PrintStream;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.Register;
import MIR.IROperand.operand;
import MIR.IRType.IRBaseType;

public class BitCastInst extends Inst {
    public operand oper;
    public IRBaseType type;
    public Register result;

    public BitCastInst(BasicBlock bb, operand oper, IRBaseType type, Register result) {
        super(bb);
        this.oper = oper;
        this.type = type;
        this.result = result;
    }

    @Override
    public void print(PrintStream out) {
        out.println("bitcast " + result.toString() + " " + oper.toString());
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
