package Assembly.Operand;

public class Imm extends asmOperand {
    public int val;

    public Imm(int val) {
        this.val = val;
    }

    public String toString() {
        return val + "";
    }
}
