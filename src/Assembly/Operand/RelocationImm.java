package Assembly.Operand;

public class RelocationImm extends Imm{
    public String t;
    public String name;

    public RelocationImm(String t, String name) {
        super(0);
        this.t = t;
        this.name = name;
    }
    
    public String toString() {
        return "%" + t + "(" + name + ")";
    }
}
