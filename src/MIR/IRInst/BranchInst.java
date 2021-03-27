package MIR.IRInst;

import java.io.PrintStream;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.operand;

public class BranchInst extends Inst {
    public operand condition;
    public BasicBlock trueBlock, falseBlock;

    public BranchInst(BasicBlock bb, operand con, BasicBlock trueb, BasicBlock falb) {
        super(bb);
        this.condition = con;
        this.trueBlock = trueb;
        this.falseBlock = falb;
    }

    @Override
    public void print(PrintStream out) {
        out.print("\t");
        if(condition != null)
            out.println("br i1 " + condition.toString() + ", label " + trueBlock.toString() + ", label " + (falseBlock == null ? "" : falseBlock.toString()));
        else 
            out.println("br label " + trueBlock.toString());
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
