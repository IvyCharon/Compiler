package AST;

public interface ASTVisitor {
    void visit(blockStmtNode it);
    void visit(breakStmtNode it);
    void visit(continueStmtNode it);
    void visit(exprStmtNode it);
    void visit(forStmtNode it);
    void visit(ifStmtNode it);
    void visit(returnStmtNode it);
    void visit(varDeclStmtNode it);
    void visit(whileStmtNode it);
}
