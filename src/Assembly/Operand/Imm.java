package Assembly.Operand;

public class Imm extends asmOperand {
    public int val;

    public boolean inStack;

    public Imm(int val) {
        this.val = val;
        this.inStack = false;
    }

    public Imm(int val, boolean inStack) {
        this.val = val;
        this.inStack = inStack;
    }

    public String toString() {
        return val + "";
    }

    
}
