package AST;

import MIR.IROperand.operand;
import Util.position;
import Util.Type.*;

public abstract class ExprNode extends ASTNode {
    public Type type;
    public boolean isAssignable = false;
    
    public operand oper, lresult = null;

    public boolean isConst = false;

    public ExprNode(position pos, boolean isA) {
        super(pos);
        this.isAssignable = isA;
    }

    public boolean isAssignable() {
        return isAssignable;
    }

    public int getInt() {
        if(this instanceof intConstNode)
            return ((intConstNode)this).value;
        else return 0;
    }

    public boolean getBool() {
        if(this instanceof boolConstNode)
            return ((boolConstNode)this).value;
        else return false;
    }

    public String getString() {
        if(this instanceof stringConstNode)
            return ((stringConstNode)this).value;
        else return "";
    }
}
