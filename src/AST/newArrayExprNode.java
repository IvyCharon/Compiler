package AST;

import java.util.ArrayList;

import Util.position;

public class newArrayExprNode extends ExprNode {
    public TypeNode typeNode;
    public ArrayList<ExprNode> expr;

    public newArrayExprNode(position pos, TypeNode type, ArrayList<ExprNode> expr) {
        super(pos, true);
        this.typeNode = type;
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
