package Assembly.Operand;

public class VirtualRegister extends Register {
    public String name;
    public int index;

    public VirtualRegister(int n, int ind) {
        super();
        this.name = "%" + n;
        this.index = ind;
    }
    
    @Override
    public String toString() {
        if(color == null)
            return name;
        else return color.toString();
    }
}
