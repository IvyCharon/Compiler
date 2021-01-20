package AST;

import Util.position;

public class binaryExprNode extends ExprNode {
    public enum binaryOpType {
        add, sub,
        equal, not_equal,
        mul, div, mod,
        smallersmaller, biggerbigger,
        smaller, bigger,
        smaller_equal, bigger_equal,
        and, xor, or,
        andand, oror
    }

    public ExprNode left, right;
    public binaryOpType op;

    public binaryExprNode(position pos, binaryOpType op, ExprNode le, ExprNode ri) {
        super(pos, false);
        this.op = op;
        this.left = le;
        this.right = ri;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
