package AST;

import Util.position;

public class forStmtNode extends StmtNode {
    public ExprNode init, condition, incr;
    public StmtNode stmts;

    public forStmtNode(position pos, ExprNode e1, ExprNode e2, ExprNode e3, StmtNode stmts) {
        super(pos);
        this.init = e1;
        this.condition = e2;
        this.incr = e3;
        this.stmts = stmts;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
