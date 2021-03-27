package Assembly.Operand;

public class VirtualRegister extends Register {
    public String name;
    public int index;

    public VirtualRegister(int n) {
        this.name = "%" + n;
        this.index = n;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
