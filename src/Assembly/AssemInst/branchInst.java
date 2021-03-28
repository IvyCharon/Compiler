package Assembly.AssemInst;

import Assembly.AssemBlock;
import Assembly.Operand.*;

public class branchInst extends asmInst {
    public Register rs;
    public AssemBlock trueBlock, falseBlock;

    public branchInst(Register rs, AssemBlock trueB, AssemBlock falseB, AssemBlock b) {
        this.rs = rs;
        this.trueBlock = trueB;
        this.falseBlock = falseB;
        this.block = b;
    }

    @Override
    public String toString() {
        return "bnez " + rs.toString() + ", " + trueBlock.name + "\n\t" + "j " + falseBlock.name;
    }

    @Override
    public void setStackImm(int s) {
        
    }
}
