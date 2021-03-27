package MIR.IRInst;

import java.io.PrintStream;
import java.util.ArrayList;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.Register;
import MIR.IROperand.operand;
import MIR.IRType.PointerType;

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
        String o = "\t";
        o += result.toString() + " = getelementptr ";
        String baseTypeN, pointerTypeN;
        if(ptr.type() instanceof PointerType) {
            baseTypeN = ((PointerType)(ptr.type())).baseType.toString();
            pointerTypeN = ptr.type().toString();
        } else {
            baseTypeN = ptr.type().toString();
            pointerTypeN = baseTypeN + "*";
        }
        o += baseTypeN + ", " + pointerTypeN + " " + ptr.toString();

        for(int i = 0; i < index.size(); ++ i) {
            o += ", " + index.get(i).type().toString() + " " + index.get(i).toString();
        }
        out.println(o);
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
