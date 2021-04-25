package Assembly.Operand;

import java.util.HashSet;

import Assembly.AssemInst.mvInst;

abstract public class Register extends asmOperand {
    abstract public String toString();

    // color graph
    public HashSet<Register> linkList = new HashSet<>();
    public HashSet<mvInst> moveList = new HashSet<>();
    public double weight;
    public int degree;
    public Register alias = null;
    public PhysicalRegister color = null;
    public Imm stackOff = null;

    public Register() {
        super();
        if(this instanceof PhysicalRegister) color = (PhysicalRegister)this;
        else color = null;
    }

    public void init() {
        moveList.clear();
        weight = 0.0;
    }

    public void clear() {
        linkList.clear();
        moveList.clear();
        weight = 0.0;
        degree = 0;
        alias = null;
        color = null;
    }

}
