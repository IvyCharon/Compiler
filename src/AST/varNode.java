package AST;

import Util.Entity.*;
import Util.position;

public class varNode extends ExprNode {
    public String name;
    public varEntity var;

    public varNode(position pos, String name) {
        super(pos);
        this.name = name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
