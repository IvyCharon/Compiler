package MIR.IRInst;

import java.io.PrintStream;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.Register;
import MIR.IROperand.operand;
import MIR.IRType.IRBaseType;

public class CompareInst extends Inst{
    public enum compareInstOp {
        eq, //equal
        ne, //not equal
        sgt, //greater
        sge, //greater_equal
        slt, //smaller
        sle //smaller_equal
    }

    public compareInstOp op;
    public operand left, right;
    public Register result;
    public IRBaseType type;

    public CompareInst(BasicBlock bb, compareInstOp op, operand left, operand right, Register result, IRBaseType type) {
        super(bb);
        this.op = op;
        this.left = left;
        this.right = right;
        this.result = result;
        this.type = type;
    }

    private String toStr(compareInstOp op) {
        switch (op) {
            case eq:
                return "eq";
            case ne:
                return "ne";
            case sgt:
                return "sgt";
            case sge:
                return "sge";
            case slt:
                return "slt";
            case sle:
                return "sle";
            default:
                return null;
        }
    }

    @Override
    public void print(PrintStream out) {
        out.println("\t" + result.toString() +
                     " = icmp " + toStr(op) +
                     " " + type.toString() + 
                     " " + left.toString() + 
                     ", " + right.toString()
                );
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
    
}
