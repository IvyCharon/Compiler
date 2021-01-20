package Frontend;

import java.util.Stack;

import AST.*;
import AST.unaryExprNode.unaryOpType;
import Util.error.*;
import Util.Scope.*;
import Util.Type.*;
import Util.Entity.*;

public class SemanticChecker implements ASTVisitor {
    private Scope currentScope;
    private globalScope gScope;
    private classType currentClass = null;
    private Type retType = null;
    private Stack<ASTNode> loop = new Stack<ASTNode>();

    public SemanticChecker(globalScope gScope) {
        currentScope = this.gScope = gScope;
    }
    
    @Override
    public void visit(programNode it){
        currentScope = gScope;
        it.decls.forEach(t -> t.accept(this));
        if(!gScope.containsFunction("main", false))
            throw new semanticError("there is no main function!", it.pos);
    }

    @Override
    public void visit(funcDeclNode it){
        if(it.type == null) {
            //it is a constructor
            if(it.identifier != currentClass.name()) {
                throw new semanticError("the name of constructor is wrong!", it.pos);
            }
        }
        retType = null;
        currentScope = it.func.getScope();
        it.suite.accept(this);
        currentScope = currentScope.parentScope();
        if(it.identifier == "main") {
            if(! it.func.retType().isInt()) 
                throw new semanticError("main function should return int!", it.pos);
            if(! it.paras.isEmpty())
                throw new semanticError("main function should not have parameters!", it.pos);
        }
        //check return
        if((retType == null && it.func.retType() == null) || (retType != null && it.func.retType() != null && retType.getType() != it.func.retType().getType()))
            throw new semanticError("wrong return! ", it.pos);
        retType = null;
    }
    @Override
    public void visit(classDeclNode it){
        classType tmp = (classType)gScope.getType(it.identifier, it.pos);
        currentClass = tmp;
        currentScope = tmp.getScope();
        it.vars.forEach(t -> t.accept(this));
        it.funcs.forEach(t -> t.accept(this));
        currentClass = null;
        currentScope = currentScope.parentScope();
    }
    @Override
    public void visit(singleVarDeclNode it){
        varEntity var = new varEntity(it.identifier, gScope.generateType(it.type));
        it.var = var;
        if(it.var.type().isVoid()) throw new semanticError("variable type is void!", it.pos);
        if(it.expr != null) {
            it.expr.accept(this);
            if(it.expr.type.getType() != var.type().getType()) 
                throw new semanticError("variable wrong init!", it.pos);
        }
        currentScope.defineVariable(it.identifier, var, it.pos);
    }
    @Override
    public void visit(varDeclNode it){
        it.type.accept(this);
        it.varList.forEach(t -> t.accept(this));
    }

    @Override
    public void visit(blockStmtNode it){
        if (!it.stmts.isEmpty()) {
            currentScope = new Scope(currentScope);
            for (StmtNode stmt : it.stmts) stmt.accept(this);
            currentScope = currentScope.parentScope();
        }
    }
    @Override
    public void visit(breakStmtNode it){
        if(loop.empty()) throw new semanticError("break not in loop!", it.pos);
    }
    @Override
    public void visit(continueStmtNode it){
        if(loop.empty()) throw new semanticError("continue not in loop!", it.pos);
    }
    @Override
    public void visit(exprStmtNode it){
        it.expr.accept(this);
    }
    @Override
    public void visit(forStmtNode it){
        if(it.condition != null) {
            it.condition.accept(this);
            if(! it.condition.type.isBool()) 
                throw new semanticError("Semantic Error: type not match. It should be bool",
                it.condition.pos);
        }
        if(it.init != null) it.init.accept(this);
        if(it.incr != null) it.incr.accept(this);
        currentScope = new Scope(currentScope);
        loop.push(it);
        it.stmts.accept(this);
        loop.pop();
        currentScope = currentScope.parentScope();
    }
    @Override
    public void visit(ifStmtNode it){
        it.condition.accept(this);
        if (!it.condition.type.isBool())
            throw new semanticError("Semantic Error: type not match. It should be bool",
                    it.condition.pos);
        currentScope = new Scope(currentScope);
        it.thenstmt.accept(this);
        currentScope = currentScope.parentScope();
        if (it.elsestmt != null){
            currentScope = new Scope(currentScope);
            it.elsestmt.accept(this);
            currentScope = currentScope.parentScope();
        }
    }
    @Override
    public void visit(returnStmtNode it){
        if (it.val != null) {
            it.val.accept(this);
            retType = it.val.type;
        }
    }
    @Override
    public void visit(whileStmtNode it){
        it.con.accept(this);
        if(!it.con.type.isBool())
            throw new semanticError("Semantic Error: type not match. It should be bool",
                    it.con.pos);
        loop.push(it);
        currentScope = new Scope(currentScope);
        it.stmts.accept(this);
        loop.pop();
        currentScope = currentScope.parentScope();
    }
    @Override
    public void visit(emptyStmtNode it) {}

    @Override
    public void visit(binaryExprNode it){
        it.left.accept(this);
        it.right.accept(this);
        switch (it.op) {
            case  add, sub, mul, div, mod, 
                  smallersmaller, biggerbigger, and, xor, or :
                if ((!it.left.type.isInt()) 
                  || (!it.right.type.isInt()) 
                  || it.left.type.getType()!= it.right.type.getType()) 
                throw new semanticError("Semantic Error: type not match. " + 
                                          "It should not be Int and the left should be the same as the right.[1] ",
                                          it.pos);
                it.type = it.left.type;
              break;
            case smaller, bigger, smaller_equal, bigger_equal:
                if ((!it.left.type.isInt()) 
                    || (!it.right.type.isInt()) 
                    || it.left.type.getType() != it.right.type.getType()) 
                    throw new semanticError("Semantic Error: type not match. " + 
                                            "It should not be Int and the left should be the same as the right.[2] ",
                                            it.pos);
                it.type = gScope.boolType;
                break;
            case equal, not_equal, andand, oror:
                if (it.right.type.getType() != it.left.type.getType() || (!it.right.type.isInt() && !it.right.type.isBool()))
                    throw new semanticError("Semantic Error: type not match. " + 
                                            "The left should be the same as the right. ",
                                            it.pos);
                it.type = gScope.boolType;
                break;
            default:
                break;
        }
    }
    @Override
    public void visit(unaryExprNode it){
        it.node.accept(this);
        switch (it.op) {
            case plusplus, subsub, posi, neg, bit_opposite :
                if(! it.node.type.isInt())
                    throw new semanticError("wrong type!", it.pos);
                if((it.op == unaryOpType.plusplus || it.op == unaryOpType.subsub) && (!it.node.isAssignable()))
                    throw new semanticError("not assignable!", it.pos);
                it.type = gScope.intType;
                break;
            case not :
                if(! it.node.type.isBool())
                    throw new semanticError("wrong type for not!", it.pos);
                it.type = gScope.boolType;
            default:
                break;
        }
    }
    @Override
    public void visit(newArrayExprNode it){
        it.expr.forEach(t -> {
            t.accept(this);
            if(!t.type.isInt())
                throw new semanticError("wrong new for array!", it.pos);
        });
        it.type = gScope.generateType(it.typeNode);
    }
    @Override
    public void visit(newInitObjectExprNode it){
        it.expr.forEach(t -> t.accept(this));
        it.type = gScope.generateType(it.typeNode);
    }
    @Override
    public void visit(newObjectExprNode it){
        it.type = gScope.generateType(it.typeNode);
    }
    @Override
    public void visit(postfixExprNode it){
        it.node.accept(this);
        if(!it.node.type.isInt())
            throw new semanticError("wrong type for postfix operation!", it.pos);
        if(!it.node.isAssignable())
            throw new semanticError("not assignable!", it.pos);
        it.type = gScope.intType;
    }
    @Override
    public void visit(funcCallExprNode it){
        it.funcName.accept(this);
        if(it.funcName instanceof methodNode) {
            if(it.funcName.type.isArray()) {
                if(((methodNode)it.funcName).name != "size")
                    throw new semanticError("wrong use of method!", it.pos);
                it.type = gScope.intType;
            } else {
                if(!it.funcName.type.isClass())
                    throw new semanticError("it is not a class!", it.pos);
                classType tmp = (classType) it.funcName.type;
                if(!tmp.getScope().containsFunction(((methodNode)it.funcName).name, false))
                    throw new semanticError("no such method!", it.pos);
                funcType t = tmp.getScope().getFunction(((methodNode)it.funcName).name, false);
                it.type = t;
            }
        } else if(it.funcName instanceof funcNode) {
            if(!it.paras.isEmpty())
                it.paras.forEach(t -> t.accept(this));
            funcType func = (funcType)it.funcName.type;
            if(!currentScope.containsFunction(((funcNode)it.funcName).funcName, true))
                throw new semanticError("no such function!", it.pos);
            if(func.getScope().getParas().size() != it.paras.size())
                throw new semanticError("parameters not match!", it.pos);
            for(int i = 0;i < it.paras.size();++ i) {
                if(it.paras.get(i).type != func.getScope().getParas().get(i).type())
                    throw new semanticError("parameter type not match!", it.pos);
            }
            it.type = func.retType();
        } else {
            throw new semanticError("function call wrong!", it.pos);
        }
    }
    @Override
    public void visit(assignExprNode it){
        it.right.accept(this);
        it.left.accept(this);
        if (it.right.type.getType() != it.left.type.getType())
            throw new semanticError("Semantic Error: type not match. " + 
                                    "The left should be the same as the right. ",
                                    it.pos);
        if (!it.left.isAssignable())
            throw new semanticError("Semantic Error: not assignable. ", it.left.pos);
        it.type = it.right.type;
    }
    @Override
    public void visit(memberAccessExprNode it){
        it.bo.accept(this);
        if(!it.type.isClass())
            throw new semanticError("member access fail!", it.pos);
        classType tmp = (classType) it.type;
        if(!tmp.getScope().containsVariable(it.iden, false))
            throw new semanticError("no such member!", it.pos);
        else {
            it.type = tmp.getScope().getMemberType(it.iden, it.pos, false);
            it.var = tmp.getScope().getMember(it.iden, it.pos, false);
        }
    }
    @Override
    public void visit(subscriptExprNode it){
        it.array.accept(this);
        it.index.accept(this);
        if(!it.index.type.isInt())
            throw new semanticError("index is not int!", it.pos);
        if(it.array.type.dim() > 1) {
            it.type = new arrayType(((arrayType)it.array.type).type(), it.array.type.dim() - 1);
        } else if(it.array.type.dim() < 1) {
            throw new semanticError("not an array!", it.pos);
        } else {
            it.type = ((arrayType)it.array.type).type();
        }
    }
    @Override
    public void visit(thisExprNode it){
        if(currentClass == null)
            throw new semanticError("not in a class!", it.pos);
        it.type = currentClass;
    }

    @Override
    public void visit(intConstNode it){
        it.type = gScope.intType;
    }
    @Override
    public void visit(boolConstNode it){
        it.type = gScope.boolType;
    }
    @Override
    public void visit(nullConstNode it){
        it.type = gScope.nullType;
    }
    @Override
    public void visit(stringConstNode it){
        it.type = gScope.getType("string", it.pos);
    }

    @Override
    public void visit(TypeNode it) {}
    @Override
    public void visit(simpleTypeNode it) {}

    @Override
    public void visit(varNode it) {
        it.type = currentScope.getMemberType(it.name, it.pos, true);
        it.var = currentScope.getMember(it.name, it.pos, true);
    }
    @Override
    public void visit(funcNode it) {
        it.type = (funcType)currentScope.getFunction(it.funcName, true);
    }
    @Override 
    public void visit(methodNode it) {}

}
