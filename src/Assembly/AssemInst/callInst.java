package Assembly.AssemInst;

import Assembly.AssemFunction;

public class callInst extends asmInst {
    public AssemFunction func;

    public callInst(AssemFunction func) {
        this.func = func;
    }

    @Override
    public String toString() {
        return "call " + func.toString();
    }

    @Override
    public void setStackImm(int s) {
        
    }
}
