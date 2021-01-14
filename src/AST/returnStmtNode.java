package AST;

import Util.position;

public class returnStmtNode extends StmtNode {
    public ExprNode val;

    public returnStmtNode(position pos, ExprNode v) {
        super(pos);
        this.val = v;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
