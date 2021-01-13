package AST;

import Util.position;

public class varDeclStmtNode extends StmtNode {
    public String name;
    public ExprNode init;

    public varDeclStmtNode(String s, ExprNode e, position pos) {
        super(pos);
        this.name = s;
        this.init = e;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
