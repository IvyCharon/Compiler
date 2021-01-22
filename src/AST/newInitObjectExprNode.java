package AST;

import Util.position;

import java.util.ArrayList;

public class newInitObjectExprNode extends ExprNode {
    public TypeNode typeNode;
    public ArrayList<ExprNode> expr;

    public newInitObjectExprNode(position pos, TypeNode type, ArrayList<ExprNode> expr) {
        super(pos, true);
        this.typeNode = type;
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
