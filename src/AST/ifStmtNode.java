package AST;

import Util.position;

public class ifStmtNode extends StmtNode {
    public ExprNode condition;
    public StmtNode thenstmt, elsestmt;

    public ifStmtNode(ExprNode con, StmtNode ths, StmtNode els, position pos) {
        super(pos);
        this.condition = con;
        this.thenstmt = ths;
        this.elsestmt = els;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
