package AST;

import Util.position;

public class whileStmtNode extends StmtNode {
    public ExprNode con;
    public StmtNode stmts;

    public whileStmtNode(position pos, ExprNode co, StmtNode stmts) {
        super(pos);
        this.con = co;
        this.stmts = stmts;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
