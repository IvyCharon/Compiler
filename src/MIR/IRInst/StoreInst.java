package MIR.IRInst;

import java.io.PrintStream;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.IROperand.operand;
import MIR.IRType.PointerType;

public class StoreInst extends Inst {
    public operand val, addr;
    public boolean ptr = false;
    
    public StoreInst(BasicBlock bb, operand val, operand addr) {
        super(bb);
        this.val = val;
        this.addr = addr;
    }

    @Override
    public void print(PrintStream out) {
        if(addr.type() instanceof PointerType) {
            out.println("\tstore " + ((PointerType)(addr.type())).baseType.toString() + 
                        " " + val.toString() + ", " + 
                        addr.type().toString() + " " + 
                        addr.toString());
        }
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
