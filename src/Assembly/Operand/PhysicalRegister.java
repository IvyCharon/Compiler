package Assembly.Operand;

public class PhysicalRegister extends Register {
    public String name;

    public PhysicalRegister(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
