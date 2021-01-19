package AST;

import Util.Type.*;
import Util.position;

abstract public class TypeNode extends ASTNode {
    public String identifier;
    public Type type;

    public TypeNode(position pos, String iden) {
        super(pos);
        this.identifier = iden;
    }
}
