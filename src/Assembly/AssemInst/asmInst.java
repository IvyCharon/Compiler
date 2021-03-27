package Assembly.AssemInst;

import java.util.ArrayList;

import Assembly.Operand.Register;

abstract public class asmInst {
    public asmInst pre, next;
    public ArrayList<Register> UsedVirReg = new ArrayList<>();

    abstract public String toString();
}
