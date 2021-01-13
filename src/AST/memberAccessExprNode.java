package AST;

import Util.position;

public class memberAccessExprNode extends ExprNode {
    public ExprNode bo;    
    public String iden;

    public memberAccessExprNode(position pos, ExprNode bo, String iden) {
        super(pos);
        this.bo = bo;
        this.iden = iden;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
