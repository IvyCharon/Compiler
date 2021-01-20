package AST;

import Util.position;

public class postfixExprNode extends ExprNode {
    public enum postfixOpType {
        plusplus, subsub
    }    

    public ExprNode node;
    public postfixOpType op;

    public postfixExprNode(position pos, ExprNode node, postfixOpType op) {
        super(pos, false);
        this.node = node;
        this.op = op;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
