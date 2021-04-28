package Assembly;

import MIR.Function;

public class AssemFunction {
    public String name;

    public Function func;

    public AssemBlock entranBlock, exitBlock;

    public int maxStack = 0;

    public int VirReg = 0;

    public int paraOff = 0;

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

    public void deleteEntranceBlock() {
        if(entranBlock.next == null)
            entranBlock = null;
        else if(entranBlock.next == exitBlock) {
            entranBlock = exitBlock;
            entranBlock.pre = null;
            exitBlock = null;
        } else {
            entranBlock = entranBlock.next;
            entranBlock.pre = null;
        }
    }

    public void deleteExitBlock() {
        if(exitBlock.pre == entranBlock) {
            exitBlock = null;
            entranBlock.next = null;
        } else {
            exitBlock.pre.next = null;
            exitBlock = exitBlock.pre;
        }
    }
    
}
