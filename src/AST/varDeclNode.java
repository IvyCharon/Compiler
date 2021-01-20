package AST;

import java.util.ArrayList;

import Util.position;

public class varDeclNode extends StmtNode {
    public TypeNode type;
    public ArrayList<singleVarDeclNode> varList;

    public varDeclNode(position pos, TypeNode type, ArrayList<singleVarDeclNode> varl) {
        super(pos);
        this.type = type;
        this.varList = varl;
        this.varList.forEach(t -> t.type = this.type);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
