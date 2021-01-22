package Frontend;

import AST.*;
import Util.Scope.Scope;
import Util.Scope.globalScope;
import Util.Type.classType;
import Util.Type.funcType;
import Util.error.semanticError;

public class SymbolCollector implements ASTVisitor {
    private globalScope gScope;
    private Scope currentScope = null;

    public SymbolCollector(globalScope gScope) {
        this.gScope = gScope;
    }

    @Override
    public void visit(programNode it) {
        currentScope = gScope;
        it.decls.forEach(t -> t.accept(this));
    }

    @Override
    public void visit(funcDeclNode it) {
        funcType func = new funcType(it.identifier);
        if(gScope == currentScope && gScope.hasType(it.identifier))
            throw new semanticError("[SymbolCollector][function declare] " + 
                                    "re-definition or default funcs", it.pos);
        it.func = func;
        currentScope.defineFunction(it.identifier, func, it.pos);
    }
    @Override
    public void visit(classDeclNode it) {
        if(currentScope != gScope)
            throw new semanticError("[SymbolCollector][class declare] class not define globally", it.pos);
        classType cla = new classType(it.identifier);
        Scope claScope = new Scope(currentScope);
        currentScope = claScope;
        it.funcs.forEach(t -> t.accept(this));
        it.vars.forEach(t -> t.accept(this));
        cla.setScope(currentScope);
        currentScope = currentScope.parentScope();
        gScope.defineClass(it.identifier, cla, it.pos);
        if(gScope.containsVariable(it.identifier, false))
            throw new semanticError("[SymbolCollector][class declare] " + 
                                    "class name is the same as variable", it.pos);
        if(gScope.containsFunction(it.identifier, false))
            throw new semanticError("[SymbolCollector][class declare] " + 
                                    "class name is the same as function", it.pos);
    }
    @Override
    public void visit(singleVarDeclNode it) {}
    @Override
    public void visit(varDeclNode it) {}

    @Override
    public void visit(blockStmtNode it) {}
    @Override
    public void visit(breakStmtNode it) {}
    @Override
    public void visit(continueStmtNode it) {}
    @Override
    public void visit(exprStmtNode it) {}
    @Override
    public void visit(forStmtNode it) {}
    @Override
    public void visit(ifStmtNode it) {}
    @Override
    public void visit(returnStmtNode it) {}
    @Override
    public void visit(whileStmtNode it) {}
    @Override
    public void visit(emptyStmtNode it) {}

    @Override
    public void visit(binaryExprNode it) {}
    @Override
    public void visit(unaryExprNode it) {}
    @Override
    public void visit(newArrayExprNode it) {}
    @Override
    public void visit(newInitObjectExprNode it) {}
    @Override
    public void visit(newObjectExprNode it) {}
    @Override
    public void visit(postfixExprNode it) {}
    @Override
    public void visit(funcCallExprNode it) {}
    @Override
    public void visit(assignExprNode it) {}
    @Override
    public void visit(memberAccessExprNode it) {}
    @Override
    public void visit(subscriptExprNode it) {}
    @Override
    public void visit(thisExprNode it) {}

    @Override
    public void visit(intConstNode it) {}
    @Override
    public void visit(boolConstNode it) {}
    @Override
    public void visit(nullConstNode it) {}
    @Override
    public void visit(stringConstNode it) {}

    @Override
    public void visit(TypeNode it) {}

    @Override
    public void visit(varNode it) {}
    @Override
    public void visit(funcNode it) {}
    @Override
    public void visit(methodNode it) {}
}
