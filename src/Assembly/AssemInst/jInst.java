package Assembly.AssemInst;

import Assembly.AssemBlock;

public class jInst extends asmInst {
    public AssemBlock dest;

    public jInst(AssemBlock dest) {
        this.dest = dest;
    }
    
    @Override
    public String toString() {
        return "j " + dest.name;
    }
}
