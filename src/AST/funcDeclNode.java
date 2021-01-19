package AST;

import java.util.ArrayList;

import Util.Type.*;
import Util.position;

public class funcDeclNode extends programSectionNode {
    public TypeNode type;
    public String identifier;
    public ArrayList<singleVarDeclNode> paras;
    public blockStmtNode suite;
    public funcType func;

    public funcDeclNode(position pos, TypeNode type, String iden, ArrayList<singleVarDeclNode> paras, blockStmtNode suite) {
        super(pos);
        this.type = type;
        this.identifier = iden;
        this.paras = paras;
        this.suite = suite;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
