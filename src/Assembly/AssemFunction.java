package Assembly;

import MIR.Function;

public class AssemFunction {
    public String name;

    public Function func;

    public AssemBlock entranBlock, exitBlock;

    public AssemFunction(Function func) {
        this.func = func;
        this.name = func.name;
        entranBlock = null;
        exitBlock = null;
    }

    public void addBlock(AssemBlock ab) {
        if(entranBlock == null) {
            entranBlock = ab;
        } else if(exitBlock == null) {
            exitBlock = ab;
            exitBlock.pre = entranBlock;
            entranBlock.next = exitBlock;
        } else {
            exitBlock.next = ab;
            ab.pre = exitBlock;
            exitBlock = ab;
        }
    }

    public String toString() {
        return name;
    }
    
}
