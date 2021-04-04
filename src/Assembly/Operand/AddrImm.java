package Assembly.Operand;

public class AddrImm extends Imm {
    public Register baseReg;

    public AddrImm(Register reg, int val) {
        super(val);
        this.baseReg = reg;
    }

    @Override
    public String toString() {
        return val + "";
    }
}
