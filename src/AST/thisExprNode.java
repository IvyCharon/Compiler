package AST;

import Util.Type.*;
import Util.position;

public class thisExprNode extends ExprNode {
    public classType type;
    
    public thisExprNode(position pos) {
        super(pos);
    }    

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
