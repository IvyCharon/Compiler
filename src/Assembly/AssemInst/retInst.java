package Assembly.AssemInst;

import Assembly.AssemBlock;

public class retInst extends asmInst {
    public retInst(AssemBlock b) {
        this.block = b;
    }
    
    @Override
    public String toString() {
        return "ret";
    }

    @Override
    public void setStackImm(int s) {
        
    }
}
