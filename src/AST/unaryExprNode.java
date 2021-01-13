package AST;

import Util.position;

public class unaryExprNode extends ExprNode {
    public enum unaryOpType {
        plusplus, subsub,
        posi, neg,
        not, bit_opposite
    }

    public ExprNode node;
    public unaryOpType op;

    public unaryExprNode(position pos, ExprNode node, unaryOpType op) {
        super(pos);
        this.node = node;
        this.op = op;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
