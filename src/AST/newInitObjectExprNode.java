package AST;

import Util.position;

import java.util.ArrayList;

public class newInitObjectExprNode extends ExprNode {
    public simpleTypeNode type;
    public ArrayList<ExprNode> expr;

    public newInitObjectExprNode(position pos, simpleTypeNode type, ArrayList<ExprNode> expr) {
        super(pos);
        this.type = type;
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
