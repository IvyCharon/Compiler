package AST;

import java.util.ArrayList;

import Util.position;

public class classDeclNode extends programSectionNode {
    public String identifier;
    public ArrayList<funcDeclNode> funcs;
    public ArrayList<varDeclNode> vars;

    public classDeclNode(position pos, String iden, ArrayList<funcDeclNode> funcs, ArrayList<varDeclNode> vars) {
        super(pos);
        this.identifier = iden;
        this.funcs = funcs;
        this.vars = vars;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
