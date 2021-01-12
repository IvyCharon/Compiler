package AST;

import Util.position;

public class returnStmtNode extends StmtNode {
    public ExprNode val;

    public returnStmtNode(ExprNode v, position pos) {
        super(pos);
        this.val = v;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
