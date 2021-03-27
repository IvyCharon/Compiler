package Util.Type;

import MIR.IRType.IRBaseType;
import MIR.IRType.PointerType;

public class arrayType extends Type {
    private Type type;
    private int dim;

    public arrayType(Type type, int dim) {
        super(Type.types.Array);
        this.type = type;
        this.dim = dim;
    }

    public Type type() {
        return type;
    }

    public IRBaseType tran_IRType() {
        IRBaseType base = type.toIRType();
        for(int i = 0; i < dim; ++i) {
            base = new PointerType(base);
        }
        return base;
    }

    @Override
    public int dim() {
        return dim;
    }
}
