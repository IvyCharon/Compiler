package AST;

import Util.position;

public class newObjectExprNode extends ExprNode {
    public simpleTypeNode typeNode;

    public newObjectExprNode(position pos, simpleTypeNode type) {
        super(pos, true);
        this.typeNode = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
