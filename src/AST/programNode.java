package AST;

import Util.position;

import java.util.ArrayList;

public class programNode extends ASTNode {
    public ArrayList<programSectionNode> decls;
    public ArrayList<StmtNode> stmts;

    public programNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
