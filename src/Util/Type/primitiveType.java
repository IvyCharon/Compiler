package Util.Type;

public class primitiveType extends Type {
    private String name;

    public primitiveType(String name) {
        this.name = name;
    }

    @Override
    public int dim() {
        return 0;
    }

    public String name() {
        return name;
    }
}
