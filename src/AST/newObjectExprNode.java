package AST;

import Util.position;

public class newObjectExprNode extends ExprNode {
    public TypeNode typeNode;

    public newObjectExprNode(position pos, TypeNode type) {
        super(pos, true);
        this.typeNode = type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
