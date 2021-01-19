package AST;

import Util.position;
import Util.Type.*;

public class arrayTypeNode extends TypeNode {
    public TypeNode base;
    public Type type;

    public arrayTypeNode(position pos,TypeNode t) {
        super(pos, t.identifier);
        this.base = t;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
