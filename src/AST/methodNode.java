package AST;

import Util.position;

public class methodNode  extends ExprNode {
    public ExprNode bo;
    public String name;

    public methodNode(position pos, ExprNode e, String n) {
        super(pos, false);
        bo = e;
        name = n;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
