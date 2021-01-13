package AST;

import Util.position;

public class identifierExprNode extends ExprNode{
    public String iden;
    
    public identifierExprNode(position pos, String s) {
        super(pos);
        this.iden = s;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
