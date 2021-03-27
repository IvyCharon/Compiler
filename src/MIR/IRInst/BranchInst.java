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
        if(condition != null)
            out.println("branch " + condition.toString() + " " + trueBlock.name + " " + (falseBlock == null ? "" : falseBlock.name));
        else 
            out.println("jump " + trueBlock.name);
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
