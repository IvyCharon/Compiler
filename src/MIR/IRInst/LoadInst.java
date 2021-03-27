package MIR.IRInst;

import java.io.PrintStream;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.Register;
import MIR.IROperand.operand;
import MIR.IRType.IRBaseType;

public class LoadInst extends Inst {
    public IRBaseType type;
    public operand address;
    public Register result;

    public LoadInst(BasicBlock bb, IRBaseType type, operand address, Register result) {
        super(bb);
        this.type = type;
        this.address = address;
        this.result = result;
    }

    @Override
    public void print(PrintStream out) {
        out.println("\t" + result.toString() + " = load " + result.type().toString() + ", " + address.type().toString() + " " + address.toString());
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
