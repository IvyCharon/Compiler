package AST;

import Util.position;

public class thisExprNode extends ExprNode {
    
    public thisExprNode(position pos) {
        super(pos, false);
    }    

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
