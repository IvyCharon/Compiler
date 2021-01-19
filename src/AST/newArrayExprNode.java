package AST;

import java.util.ArrayList;

import Util.position;

public class newArrayExprNode extends ExprNode {
    public simpleTypeNode typeNode;
    public ArrayList<ExprNode> expr;

    public newArrayExprNode(position pos, simpleTypeNode type, ArrayList<ExprNode> expr) {
        super(pos);
        this.typeNode = type;
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
