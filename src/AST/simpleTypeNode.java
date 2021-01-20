package AST;

import Util.position;

public class simpleTypeNode extends TypeNode {
    public simpleTypeNode(position pos, String iden) {
        super(pos, iden, 0);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
