package Assembly.Operand;

public class VirtualRegister extends Register {
    public String name;

    public VirtualRegister(int n) {
        this.name = "%" + n;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
