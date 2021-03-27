package MIR.IRInst;

import java.io.PrintStream;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.operand;
import MIR.IRType.IRBaseType;
import MIR.IRType.VoidType;

public class ReturnInst extends Inst {
    public IRBaseType retType;
    public operand val;

    public ReturnInst(BasicBlock bb, IRBaseType retType, operand val) {
        super(bb);
        this.retType = retType;
        this.val = val;
    }

    @Override
    public void print(PrintStream out) {
        out.print("\t");
        if(retType instanceof VoidType)
            out.println("ret void");
        else 
            out.println("ret " + retType.toString() + " " + val.toString());
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
