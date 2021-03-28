package Assembly.AssemInst;

import Assembly.AssemBlock;
import Assembly.AssemFunction;

public class callInst extends asmInst {
    public AssemFunction func;

    public callInst(AssemFunction func, AssemBlock b) {
        this.func = func;
        this.block = b;
    }

    @Override
    public String toString() {
        return "call " + func.toString();
    }

    @Override
    public void setStackImm(int s) {
        
    }
}
