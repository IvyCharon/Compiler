package AST;

import Util.position;

public class boolConstNode extends constExprNode {
    public boolean value;

    public boolConstNode(position pos, boolean v) {
        super(pos);
        this.value = v;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
