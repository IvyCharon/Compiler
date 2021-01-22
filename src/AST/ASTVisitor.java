package AST;

public interface ASTVisitor {
    void visit(programNode it);

    void visit(funcDeclNode it);
    void visit(classDeclNode it);
    void visit(singleVarDeclNode it);
    void visit(varDeclNode it);

    void visit(blockStmtNode it);
    void visit(breakStmtNode it);
    void visit(continueStmtNode it);
    void visit(exprStmtNode it);
    void visit(forStmtNode it);
    void visit(ifStmtNode it);
    void visit(returnStmtNode it);
    void visit(whileStmtNode it);
    void visit(emptyStmtNode it);

    void visit(binaryExprNode it);
    void visit(unaryExprNode it);
    void visit(newArrayExprNode it);
    void visit(newInitObjectExprNode it);
    void visit(newObjectExprNode it);
    void visit(postfixExprNode it);
    void visit(funcCallExprNode it);
    void visit(assignExprNode it);
    void visit(memberAccessExprNode it);
    void visit(subscriptExprNode it);
    void visit(thisExprNode it);

    void visit(intConstNode it);
    void visit(boolConstNode it);
    void visit(nullConstNode it);
    void visit(stringConstNode it);

    void visit(TypeNode it);

    void visit(varNode it);
    void visit(funcNode it);
    void visit(methodNode it);
}
