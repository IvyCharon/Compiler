package MIR.IRInst;

import java.io.PrintStream;
import java.util.ArrayList;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.*;

public class PhiInst extends Inst{
    public ArrayList<operand> opers;
    public ArrayList<BasicBlock> blocks;
    public Register result;
    
    public PhiInst(BasicBlock bb, ArrayList<operand> opers, ArrayList<BasicBlock> blocks, Register result) {
        super(bb);
        this.opers = opers;
        this.blocks = blocks;
        this.result = result;
    }

    @Override
    public void print(PrintStream out) {
        String o = "\t";
        o += result.toString() + " = phi " + result.type().toString();
        
        for(int i = 0; i < opers.size(); ++ i) {
            if(i != 0) o += ", ";
            o += "[ " + opers.get(i).toString() + ", " + blocks.get(i).toString() + "]";
        }
        out.println(o);
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
