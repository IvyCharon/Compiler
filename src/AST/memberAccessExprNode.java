package AST;

import Util.position;
import Util.Entity.*;

public class memberAccessExprNode extends ExprNode {
    public ExprNode bo;    
    public String iden;
    public varEntity var;

    public memberAccessExprNode(position pos, ExprNode bo, String iden) {
        super(pos, true);
        this.bo = bo;
        this.iden = iden;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
