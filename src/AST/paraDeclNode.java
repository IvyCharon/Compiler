package AST;

import java.util.ArrayList;

import Util.position;

public class paraDeclNode extends ASTNode {
    public TypeNode type;
    public String identifier;

    public paraDeclNode(position pos, TypeNode type, String s) {
        super(pos);
        this.type = type;
        this.identifier = s;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
