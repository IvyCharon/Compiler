package AST;

import MIR.IRType.*;
import Util.position;

public class TypeNode extends ASTNode {
    public String identifier;
    //public Type type;
    public int dim;

    public TypeNode(position pos, String iden, int dim) {
        super(pos);
        this.identifier = iden;
        this.dim = dim;
    }

    public IRBaseType toIRType() {
        IRBaseType tmp;
        switch (identifier) {
            case "int":
                tmp = new IntType(32);
                break;
            case "bool":
                tmp = new BoolType(1);
                break;
            case "string":
                tmp = new PointerType(new IntType(32));
                break;
            case "void":
                tmp = new VoidType();
                break;
            case "null":
                tmp = new NullType();
                break;
            default:
                tmp = new ClassType();
                break;
        }
        for(int i = 0;i < dim;++ i)
            tmp = new PointerType(tmp);
        return tmp;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
