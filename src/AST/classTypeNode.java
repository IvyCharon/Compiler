package AST;

import Util.position;

public class classTypeNode extends TypeNode {
    public classTypeNode(position pos, String iden) {
        super(pos, iden);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    
}
