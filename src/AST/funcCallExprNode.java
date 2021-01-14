package AST;

import Util.position;
import java.util.ArrayList;

public class funcCallExprNode extends ExprNode{
    public ExprNode funcName;
    public ArrayList<ExprNode> paras;

    public funcCallExprNode(position pos, ExprNode funcN, ArrayList<ExprNode> paras) {
        super(pos);
        this.funcName = funcN;
        this.paras = paras;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
