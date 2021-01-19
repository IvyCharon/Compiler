package AST;

import Util.position;
import Util.Type.*;

abstract public class TypeNode extends ASTNode {
    public String identifier;
    public Type type;

    public TypeNode(position pos, String iden) {
        super(pos);
        this.identifier = iden;
    }
}
