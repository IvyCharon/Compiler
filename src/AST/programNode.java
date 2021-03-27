package AST;

import Util.position;

import java.util.ArrayList;

import Assembly.AssemModule;
import MIR.Module;

public class programNode extends ASTNode {
    public ArrayList<ASTNode> decls;
    public Module module;
    public AssemModule asmModule;

    public programNode(position pos, ArrayList<ASTNode> d) {
        super(pos);
        this.decls = d;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
