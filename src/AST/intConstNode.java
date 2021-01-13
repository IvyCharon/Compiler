package AST;

import Util.position;

public class intConstNode extends constExprNode {
    public int value;

    public intConstNode(position pos, int v) {
        super(pos);
        this.value = v;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
