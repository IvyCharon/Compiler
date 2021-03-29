package AST;

import MIR.IROperand.operand;
import Util.position;
import Util.Type.*;

public abstract class ExprNode extends ASTNode {
    public Type type;
    public boolean isAssignable = false;
    
    public operand oper, lresult = null;

    public ExprNode(position pos, boolean isA) {
        super(pos);
        this.isAssignable = isA;
    }

    public boolean isAssignable() {
        return isAssignable;
    }
}
