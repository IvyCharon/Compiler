package AST;

import Util.position;

import java.util.ArrayList;

public class programNode extends ASTNode {
    public ArrayList<programSectionNode> decls;

    public programNode(position pos, ArrayList<programSectionNode> d) {
        super(pos);
        this.decls = d;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
