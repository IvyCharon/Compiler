package Assembly.AssemInst;

import Assembly.AssemBlock;

public class jInst extends asmInst {
    public AssemBlock dest;

    public jInst(AssemBlock dest, AssemBlock b) {
        this.dest = dest;
        this.block = b;
    }
    
    @Override
    public String toString() {
        return "j " + dest.name;
    }

    @Override
    public void setStackImm(int s) {
        
    }
}
