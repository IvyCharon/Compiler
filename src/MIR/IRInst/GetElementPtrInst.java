package MIR.IRInst;

import java.io.PrintStream;
import java.util.ArrayList;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.Register;
import MIR.IROperand.operand;

public class GetElementPtrInst extends Inst {
    public operand ptr;
    
    public ArrayList<operand> index;

    public Register result;

    public GetElementPtrInst(BasicBlock bb, operand ptr, ArrayList<operand> index, Register result) {
        super(bb);
        this.ptr = ptr;
        this.index = index;
        this.result = result;
    }

    @Override
    public void print(PrintStream out) {
        out.println("getEle " + " " + result.toString() + " " + ptr.toString());
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
