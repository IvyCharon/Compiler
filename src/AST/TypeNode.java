package AST;

import Util.position;

abstract public class TypeNode extends ASTNode {
    public String identifier;

    public TypeNode(position pos, String iden) {
        super(pos);
        this.identifier = iden;
    }
}
