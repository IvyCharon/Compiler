package MIR.IRInst;

import java.io.PrintStream;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.Register;
import MIR.IROperand.operand;

public class BinaryInst extends Inst {
    public enum binaryInstOp {
        add, sub, 
        mul, sdiv, srem,
        shl, ashr, 
        and, or, xor
    }
    public binaryInstOp op;
    public operand left, right;
    public Register result;

    public BinaryInst(BasicBlock bb, binaryInstOp op, operand left, operand right, Register result) {
        super(bb);
        this.op = op;
        this.left = left;
        this.right = right;
        this.result = result;
    }

    private String toStr(binaryInstOp op) {
        switch (op) {
            case add:
                return "add";
            case sub:
                return "sub";
            case mul:
                return "mul";
            case sdiv:
                return "sdiv";
            case srem:
                return "srem";
            case shl:
                return "shl";
            case ashr: 
                return "ashr";
            case and:
                return "and";
            case or:
                return "or";
            case xor:
                return "xor";
            default:
                return null;
        }
    }

    @Override
    public void print(PrintStream out) {
        out.println("binary " 
                    + toStr(op) + " "
                    + result.toString() + " " 
                    + left.toString() + " " 
                    + right.toString());
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
