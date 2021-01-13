package AST;

import Util.position;

public class arrayTypeNode extends TypeNode {
    public TypeNode base;
    public int length;

    public arrayTypeNode(position pos,TypeNode t, int l) {
        super(pos, t.identifier);
        this.base = t;
        this.length = l;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
