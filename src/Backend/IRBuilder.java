package Backend;

import AST.*;

public class IRBuilder implements ASTVisitor {

    
    @Override
    public void visit(programNode it) {

    }

    @Override
    public void visit(funcDeclNode it) {

    }
    @Override
    public void visit(classDeclNode it) {

    }
    @Override
    public void visit(singleVarDeclNode it) {

    }
    @Override
    public void visit(varDeclNode it) {

    }

    @Override
    public void visit(blockStmtNode it) {

    }
    @Override
    public void visit(breakStmtNode it) {

    }
    @Override
    public void visit(continueStmtNode it) {

    }
    @Override
    public void visit(exprStmtNode it) {
        it.expr.accept(this);
    }
    @Override
    public void visit(forStmtNode it) {

    }
    @Override
    public void visit(ifStmtNode it) {

    }
    @Override
    public void visit(returnStmtNode it) {

    }
    @Override
    public void visit(whileStmtNode it) {

    }
    @Override
    public void visit(emptyStmtNode it) {}

    @Override
    public void visit(binaryExprNode it) {

    }
    @Override
    public void visit(unaryExprNode it) {

    }
    @Override
    public void visit(newArrayExprNode it) {

    }
    @Override
    public void visit(newInitObjectExprNode it) {

    }
    @Override
    public void visit(newObjectExprNode it) {

    }
    @Override
    public void visit(postfixExprNode it) {

    }
    @Override
    public void visit(funcCallExprNode it) {

    }
    @Override
    public void visit(assignExprNode it) {

    }
    @Override
    public void visit(memberAccessExprNode it) {

    }
    @Override
    public void visit(subscriptExprNode it) {

    }
    @Override
    public void visit(thisExprNode it) {

    }

    @Override
    public void visit(intConstNode it) {

    }
    @Override
    public void visit(boolConstNode it) {

    }
    @Override
    public void visit(nullConstNode it) {

    }
    @Override
    public void visit(stringConstNode it) {

    }

    @Override
    public void visit(simpleTypeNode it) {

    }
    @Override
    public void visit(TypeNode it) {

    }

    @Override
    public void visit(varNode it) {

    }
    @Override
    public void visit(funcNode it) {

    }
    @Override
    public void visit(methodNode it) {

    }
}
