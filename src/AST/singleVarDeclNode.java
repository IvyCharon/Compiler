package AST;

import Util.position;
import Util.Entity.varEntity;

public class singleVarDeclNode extends ASTNode {
    public TypeNode type;
    public String identifier;
    public ExprNode expr;
    public varEntity var;

    public singleVarDeclNode(position pos, String iden, ExprNode expr) {
        super(pos);
        this.identifier = iden;
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
