package AST;

import Util.position;

public class funcNode extends ExprNode {
    public String funcName;

    public funcNode(position pos, String name) {
        super(pos);
        this.funcName = name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
