package AST;

import Util.position;

public class subscriptExprNode extends ExprNode {
    public ExprNode array, index;

    public subscriptExprNode(position pos, ExprNode arr, ExprNode ind) {
        super(pos);
        this.array = arr;
        this.index = ind;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
