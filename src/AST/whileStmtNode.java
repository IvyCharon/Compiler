package AST;

import Util.position;

public class whileStmtNode extends StmtNode {
    public ExprNode con;

    public whileStmtNode(ExprNode co, position pos) {
        super(pos);
        this.con = co;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
