package AST;

import Util.position;

public class assignExprNode extends ExprNode{
    public ExprNode left, right;
    
    public assignExprNode(position pos, ExprNode left, ExprNode right) {
        super(pos);
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
