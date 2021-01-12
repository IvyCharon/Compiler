package AST;

import Util.position;

public class forStmtNode extends StmtNode {
    public ExprNode ex1, ex2, ex3;

    public forStmtNode(ExprNode e1, ExprNode e2, ExprNode e3, position pos) {
        super(pos);
        this.ex1 = e1;
        this.ex2 = e2;
        this.ex3 = e3;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
