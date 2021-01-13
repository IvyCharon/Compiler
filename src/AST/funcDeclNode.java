package AST;

import java.util.ArrayList;

import Util.position;

public class funcDeclNode extends programSectionNode {
    public TypeNode type;
    public String identifier;
    public ArrayList<paraDeclNode> paras;
    public ArrayList<StmtNode> stmts;

    public funcDeclNode(position pos, TypeNode type, String iden, ArrayList<paraDeclNode> paras, ArrayList<StmtNode> stmts) {
        super(pos);
        this.type = type;
        this.identifier = iden;
        this.paras = paras;
        this.stmts = stmts;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
