package AST;

import Util.position;

public class newObjectExprNode extends ExprNode {
    public simpleTypeNode typeNode;

    public newObjectExprNode(position pos, simpleTypeNode type) {
        super(pos);
        this.typeNode = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
