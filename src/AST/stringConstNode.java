package AST;

import Util.position;

public class stringConstNode extends constExprNode {
    public String value;

    public stringConstNode(position pos, String v) {
        super(pos);
        this.value = v;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
