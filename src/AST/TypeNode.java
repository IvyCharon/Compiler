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

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
