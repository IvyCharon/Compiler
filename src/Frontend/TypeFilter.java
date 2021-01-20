package Frontend;

import AST.*;
import Util.Entity.varEntity;
import Util.Scope.*;
import Util.Type.classType;
import Util.Type.funcType;
import Util.error.semanticError;

public class TypeFilter implements ASTVisitor {
    private globalScope gScope;
    private Scope currentScope = null;

    public TypeFilter(globalScope gScope) {
        this.gScope = gScope;
    }

    @Override
    public void visit(programNode it) {
        currentScope = gScope;
        it.decls.forEach(t -> {
            if(!(t instanceof varDeclNode))
                t.accept(this);
        });
    }

    @Override
    public void visit(funcDeclNode it) {
        funcType tmp = it.func;
        if(it.type != null)
            tmp.setRetType(gScope.generateType(it.type));
        else tmp.setRetType(null);
        currentScope = new funcScope(currentScope);
        it.paras.forEach(t -> t.accept(this));
        tmp.setScope((funcScope)currentScope);
        currentScope = currentScope.parentScope();
    }
    @Override
    public void visit(classDeclNode it) {
        classType cla = gScope.getClass(it.identifier, it.pos);
        currentScope = cla.getScope();
        it.funcs.forEach(t -> t.accept(this));
        currentScope = currentScope.parentScope();
    }
    @Override
    public void visit(singleVarDeclNode it) {
        varEntity tmp = new varEntity(it.identifier, gScope.generateType(it.type));
        //tmp.type().print();
        if(tmp.type().isVoid()) throw new semanticError("parameter type is void!", it.pos);
        if(currentScope instanceof funcScope) {
            it.var = tmp;
            ((funcScope)currentScope).addPara(tmp, it.pos);
        } else throw new semanticError("sth wrong with para visit", it.pos);
    }
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
    public void visit(simpleTypeNode it) {}

    @Override
    public void visit(varNode it) {}
    @Override
    public void visit(funcNode it) {}
    @Override
    public void visit(methodNode it) {}
}
