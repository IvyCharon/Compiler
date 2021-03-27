package Assembly.Operand;

public class RelocationImm extends Imm{
    public String name;

    public RelocationImm(int val, String name) {
        super(val);
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
}
