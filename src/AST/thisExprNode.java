package AST;

import Util.position;
import Util.Type.*;

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
