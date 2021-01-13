package AST;

import Util.position;

public class newExprNode extends ExprNode {
    

    public newExprNode(position pos) {
        super(pos);
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
