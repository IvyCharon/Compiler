package AST;

import Util.position;

public class newObjectExprNode extends ExprNode {
    public simpleTypeNode type;

    public newObjectExprNode(position pos, simpleTypeNode type) {
        super(pos);
        this.type = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
