package Backend;

import java.util.ArrayList;
import java.util.Stack;

import AST.*;
import AST.binaryExprNode.binaryOpType;
import AST.postfixExprNode.postfixOpType;
import MIR.BasicBlock;
import MIR.Function;
import MIR.Module;
import MIR.IRInst.*;
import MIR.IRInst.BinaryInst.binaryInstOp;
import MIR.IRInst.CompareInst.compareInstOp;
import MIR.IROperand.*;
import MIR.IRType.*;
import Util.Scope.*;
import Util.Type.Type;
import Util.Type.classType;
import Util.error.runtimeError;

public class IRBuilder implements ASTVisitor {
    private Module module;

    private globalScope gScope;

    private classType current_class;
    private BasicBlock current_block;
    private Function current_function;

    private boolean in_class = false;
    private boolean isParam = false;

    private Stack<BasicBlock> BreakBlock = new Stack<>();
    private Stack<BasicBlock> ContinueBlock = new Stack<>();

    public IRBuilder(globalScope gScope) {
        this.gScope = gScope;
        this.current_block = null;
        this.current_class = null;

        this.module = new Module();

    }

    public Module module() {
        return module;
    }

    @Override
    public void visit(programNode it) {             //TO DO
        it.decls.forEach(t -> t.accept(this));

        it.module = this.module;
    }

    @Override
    public void visit(funcDeclNode it) {
        String funcN;
        if(in_class) {
            String class_name = current_class.name();
            String funcName = it.identifier;
            funcN = class_name + "." + funcName;
        } else {
            funcN = it.identifier;
        }
        
        isParam = true;
        it.paras.forEach(t -> t.accept(this));
        isParam = false;
        ArrayList<parameter> paras = new ArrayList<>();
        it.paras.forEach(t -> {
            paras.add((parameter)(t.var.oper));
        });
        IRBaseType retType = it.func.retType() == null ? new VoidType() : it.func.retType().toIRType();
        Function func = new Function(funcN, retType, paras);
        func.retVal = new Register(new PointerType(retType), funcN + "_retVal");
        for(var t : it.paras) {
            if(t.type.type == null) System.exit(0);
            func.symbolAdd(t.identifier, new Register(new PointerType(t.type.type.toIRType()), t.identifier));
        }
        module.functions.put(funcN, func);

        current_function = func;
        current_block = func.entranceBlock;

        if(it.identifier.equals("main")) {
            Register t = new Register(new VoidType(), "call_init");
            Function f = module.functions.get("__init__");
            current_block.addInst(new CallInst(current_block, f, new ArrayList<>(), t));
        }

        it.suite.accept(this);

        current_block.addInst(new BranchInst(current_block, null, current_function.retBlock, null));
        current_block = current_function.retBlock;

        current_block.addInst(new ReturnInst(current_block, retType, current_function.retVal));
        
        current_function = null;
        current_block = null;
    }
    @Override
    public void visit(classDeclNode it) {
        current_class = (classType)gScope.getType(it.identifier, it.pos);
        in_class = true;
        it.funcs.forEach(i -> i.accept(this));
        in_class = false;
        current_class = null;
    }
    @Override
    public void visit(singleVarDeclNode it) {
        Type type = gScope.generateType(it.type);
        IRBaseType irType = type.toIRType();
        String name = it.identifier;
        if(it.var.isGlobal) {
            globalVariable gVar = new globalVariable(new PointerType(irType), name, null);
            operand init;
            if(it.expr != null) {
                it.expr.accept(this);
                init = it.expr.oper;
                if(!init.isConst()) {
                    current_block.addInst(new StoreInst(current_block, init, gVar));
                }
            } else 
                init = irType.toOper();
            gVar.init = init;
            module.globalVars.put(gVar.name, gVar);
            it.var.oper = gVar;
        } else if(isParam) {
            it.var.oper = new parameter(irType, name);
        } else if(current_function != null) {
            Register addr = new Register(new PointerType(irType), name + "_addr");
            current_function.symbolAdd(addr.name, addr);
            it.var.oper = addr;

            if(it.expr != null) {
                it.expr.accept(this);
                operand init = it.expr.oper;
                current_block.addInst(new StoreInst(current_block, init, addr));
            }
        } else if(in_class) {
            Register addr = new Register(new PointerType(irType), name + "_addr");
            it.var.oper = addr;
        } else {
            throw new runtimeError("[IRBuilder][visit single var]: var pos is wrong!", it.pos);
        }
    }
    @Override
    public void visit(varDeclNode it) {
        it.varList.forEach(t -> t.accept(this));
    }

    @Override
    public void visit(blockStmtNode it) {
        it.stmts.forEach(t -> t.accept(this));
    }
    @Override
    public void visit(breakStmtNode it) {
        current_block.addInst(new BranchInst(current_block, null, BreakBlock.peek(), null));
    }
    @Override
    public void visit(continueStmtNode it) {
        current_block.addInst(new BranchInst(current_block, null, ContinueBlock.peek(), null));
    }
    @Override
    public void visit(exprStmtNode it) {
        it.expr.accept(this);
    }
    @Override
    public void visit(forStmtNode it) {
        BasicBlock Cond = it.condition != null ? new BasicBlock("for_condition", current_function) : null;
        BasicBlock Incr = it.incr != null ? new BasicBlock("for_incr", current_function) : null;
        BasicBlock Body = new BasicBlock("for_body", current_function);
        BasicBlock AfterFor = new BasicBlock("after_for", current_function);

        if(it.init != null)
            it.init.accept(this);

        if(it.condition != null) {
            current_block.addInst(new BranchInst(current_block, null, Cond, null));

            current_block = Cond;
            it.condition.accept(this);
            operand condResult = it.condition.oper;
            current_block.addInst(new BranchInst(current_block, condResult, Body, AfterFor));
            current_function.addBasicBlock(Cond);
            BreakBlock.push(AfterFor);
            ContinueBlock.push(it.incr != null ? Incr : Cond);

            current_block = Body;
            it.stmts.accept(this);
            if(it.incr != null) 
                current_block.addInst(new BranchInst(current_block, null, Incr, null));
            else
                current_block.addInst(new BranchInst(current_block, null, Cond, null));
            current_function.addBasicBlock(Body);
            BreakBlock.pop(); ContinueBlock.pop();

            if(it.incr != null) {
                current_block = Incr;
                it.incr.accept(this);
                current_block.addInst(new BranchInst(current_block, null, Cond, null));
                current_function.addBasicBlock(Incr);
            }
        } else {
            current_block.addInst(new BranchInst(current_block, null, Body, null));
            BreakBlock.push(AfterFor);
            ContinueBlock.push(it.incr != null ? Incr : Cond);

            current_block = Body;
            it.stmts.accept(this);
            if(it.incr != null) 
                current_block.addInst(new BranchInst(current_block, null, Incr, null));
            else
                current_block.addInst(new BranchInst(current_block, null, Body, null));
            current_function.addBasicBlock(Body);
            BreakBlock.pop(); ContinueBlock.pop();

            if(it.incr != null) {
                current_block = Incr;
                it.incr.accept(this);
                current_block.addInst(new BranchInst(current_block, null, Body, null));
                current_function.addBasicBlock(Incr);
            }
        }

        current_block = AfterFor;
        current_function.addBasicBlock(AfterFor);

        /* if(it.condition != null)
            current_function.BasicBlockAdd(Cond.name, Cond);
        if(it.incr != null)
            current_function.BasicBlockAdd(Incr.name, Incr);
        current_function.BasicBlockAdd(Body.name, Body);
        current_function.BasicBlockAdd(AfterFor.name, AfterFor); */
    }
    @Override
    public void visit(ifStmtNode it) {
        BasicBlock trueBlock = new BasicBlock("if_true", current_function);
        BasicBlock falseBlock = it.elsestmt == null ? null : new BasicBlock("if_false", current_function);
        BasicBlock AfterIf = new BasicBlock("after_if", current_function);

        it.condition.accept(this);
        operand Cond = it.condition.oper;

        if(it.elsestmt != null) 
            current_block.addInst(new BranchInst(current_block, Cond, trueBlock, falseBlock));
        else
            current_block.addInst(new BranchInst(current_block, Cond, trueBlock, AfterIf));
        
        current_block = trueBlock;
        it.thenstmt.accept(this);
        current_block.addInst(new BranchInst(current_block, null, AfterIf, null));
        current_function.addBasicBlock(trueBlock);

        if(it.elsestmt != null) {
            current_block = falseBlock;
            it.elsestmt.accept(this);
            current_block.addInst(new BranchInst(current_block, null, AfterIf, null));
            current_function.addBasicBlock(falseBlock);
        }

        current_block = AfterIf;
        current_function.addBasicBlock(AfterIf);

        /* current_function.BasicBlockAdd(trueBlock.name, trueBlock);
        if(it.elsestmt != null)
            current_function.BasicBlockAdd(falseBlock.name, falseBlock);
        current_function.BasicBlockAdd(AfterIf.name, AfterIf); */
    }
    @Override
    public void visit(returnStmtNode it) {
        //current_block = current_function.retBlock;
        if(it.val != null) {
            it.val.accept(this);
            operand result = it.val.oper;
            current_block.addInst(new StoreInst(current_block, result, current_function.retVal));
        }
        current_block.addInst(new BranchInst(current_block, null, current_function.exitBlock, null));
        current_block = current_function.exitBlock;
    }
    @Override
    public void visit(whileStmtNode it) {
        BasicBlock Cond = new BasicBlock("while_condition", current_function);
        BasicBlock Body = new BasicBlock("while_body", current_function);
        BasicBlock AfterWhile = new BasicBlock("after_while", current_function);

        current_block.addInst(new BranchInst(current_block, null, Cond, null));

        current_block = Cond;
        it.con.accept(this);
        operand conResult = it.con.oper;
        current_block.addInst(new BranchInst(current_block, conResult, Body, AfterWhile));
        current_function.addBasicBlock(Cond);
        BreakBlock.push(AfterWhile); ContinueBlock.push(AfterWhile);
        
        current_block = Body;
        it.stmts.accept(this);
        current_block.addInst(new BranchInst(current_block, null, Cond, null));
        current_function.addBasicBlock(Body);
        BreakBlock.pop(); ContinueBlock.pop();

        current_block = AfterWhile;
        current_function.addBasicBlock(AfterWhile);

        /* current_function.BasicBlockAdd(Cond.name, Cond);
        current_function.BasicBlockAdd(Body.name, Body);
        current_function.BasicBlockAdd(AfterWhile.name, AfterWhile); */
    }
    @Override
    public void visit(emptyStmtNode it) {}

    @Override
    public void visit(binaryExprNode it) {
        operand left, right;
        if(it.op != binaryOpType.andand && it.op != binaryOpType.oror) {
            it.left.accept(this);
            it.right.accept(this);
            left = it.left.oper;
            right = it.right.oper;
            Register result = null;
            Type le = it.left.type, ri = it.right.type;
            switch(it.op) {
                case add:
                    if(it.left.type.isInt()) {
                        result = new Register(new IntType(32), "add");
                        current_block.addInst(new BinaryInst(current_block, binaryInstOp.add, left, right, result));
                        break;
                    } else {
                        Function func = module.builtinFunctions.get("string_add");
                        ArrayList<operand> paras = new ArrayList<>();
                        paras.add(left); paras.add(right);
                        result = new Register(new PointerType(new IntType(32)), "string_add");
                        current_block.addInst(new CallInst(current_block, func, paras, result));
                        break;
                    }
                case sub:
                    result = new Register(new IntType(32), "sub");
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.sub, left, right, result));
                    break;
                case equal:
                    if(le.isInt() && ri.isInt()) {
                        result = new Register(new BoolType(1), "equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.eq, left, right, result, new IntType(32)));
                    } else if(le.isBool() && ri.isBool()) {
                        result = new Register(new BoolType(1), "equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.eq, left, right, result, new BoolType(1)));
                    } else if(le.isArray() && ri.isNull()) {
                        result = new Register(new BoolType(1), "equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.eq, left, right, result, left.type()));
                    } else if(le.isNull() && ri.isArray()) {
                        result = new Register(new BoolType(1), "equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.eq, left, right, result, right.type()));
                    } else if(le.isString() && ri.isString()) {
                        Function func = module.builtinFunctions.get("string_equal");
                        ArrayList<operand> paras = new ArrayList<>();
                        paras.add(left); paras.add(right);

                        result = new Register(new BoolType(1), "equal_string");
                        current_block.addInst(new CallInst(current_block, func, paras, result));
                    } else if(le.isNull() && ri.isClass()) {
                        result = new Register(new BoolType(1), "equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.eq, left, right, result, right.type()));
                    } else if(le.isClass() && ri.isNull()) {
                        result = new Register(new BoolType(1), "equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.eq, left, right, result, left.type()));
                    } else if(le.isNull() && ri.isNull()) {

                    } else {
                        throw new runtimeError("[IRBuilder][visit binaryExpr Node] wrong type of equal", it.pos);
                    }
                    break;
                case not_equal:
                    if(le.isInt() && ri.isInt()) {
                        result = new Register(new BoolType(1), "not_equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.ne, left, right, result, new IntType(32)));
                    } else if(le.isBool() && ri.isBool()) {
                        result = new Register(new BoolType(1), "not_equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.ne, left, right, result, new BoolType(1)));
                    } else if(le.isArray() && ri.isNull()) {
                        result = new Register(new BoolType(1), "not_equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.ne, left, right, result, left.type()));
                    } else if(le.isNull() && ri.isArray()) {
                        result = new Register(new BoolType(1), "not_equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.ne, left, right, result, right.type()));
                    } else if(le.isString() && ri.isString()) {
                        Function func = module.builtinFunctions.get("string_not_equal");
                        ArrayList<operand> paras = new ArrayList<>();
                        paras.add(left); paras.add(right);

                        result = new Register(new BoolType(1), "not_equal_string");
                        current_block.addInst(new CallInst(current_block, func, paras, result));
                    } else if(le.isNull() && ri.isClass()) {
                        result = new Register(new BoolType(1), "not_equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.ne, left, right, result, right.type()));
                    } else if(le.isClass() && ri.isNull()) {
                        result = new Register(new BoolType(1), "not_equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.ne, left, right, result, left.type()));
                    } else if(le.isNull() && ri.isNull()) {

                    } else {    
                        throw new runtimeError("[IRBuilder][visit binaryExpr Node] wrong type of not equal", it.pos);
                    }
                    break;
                case mul:
                    result = new Register(new IntType(32), "mul");
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.mul, left, right, result));
                    break;
                case div:
                    result = new Register(new IntType(32), "div");
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.sdiv, left, right, result));
                    break;
                case mod:
                    result = new Register(new IntType(32), "mod");
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.srem, left, right, result));
                    break;
                case smallersmaller:
                    result = new Register(new IntType(32), "smallersmaller");
                    if(current_block == null) System.exit(0);
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.shl, left, right, result));
                    break;
                case biggerbigger:
                    result = new Register(new IntType(32), "biggerbigger");
                    if(current_block == null) System.exit(0);
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.ashr, left, right, result));
                    break;
                case smaller:
                    if(le.isInt() && ri.isInt()) {
                        result = new Register(new BoolType(1), "smaller");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.slt, left, right, result, new IntType(32)));
                    } else {
                        Function func = module.builtinFunctions.get("string_smaller");
                        ArrayList<operand> paras = new ArrayList<>();

                        paras.add(left); paras.add(right);
                        result = new Register(new BoolType(1), "string_smaller");
                        current_block.addInst(new CallInst(current_block, func, paras, result));
                    }
                    break;
                case bigger:
                    if(le.isInt() && ri.isInt()) {
                        result = new Register(new BoolType(1), "bigger");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.sgt, left, right, result, new IntType(32)));
                    } else {
                        Function func = module.builtinFunctions.get("string_bigger");
                        ArrayList<operand> paras = new ArrayList<>();

                        paras.add(left); paras.add(right);
                        result = new Register(new BoolType(1), "string_bigger");
                        current_block.addInst(new CallInst(current_block, func, paras, result));
                    }
                    break;
                case smaller_equal:
                    if(le.isInt() && ri.isInt()) {
                        result = new Register(new BoolType(1), "smaller_equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.sle, left, right, result, new IntType(32)));
                    } else {
                        Function func = module.builtinFunctions.get("string_smaller_equal");
                        ArrayList<operand> paras = new ArrayList<>();

                        paras.add(left); paras.add(right);
                        result = new Register(new BoolType(1), "string_smaller_equal");
                        current_block.addInst(new CallInst(current_block, func, paras, result));

                    }
                    break;
                case bigger_equal:
                    if(le.isInt() && ri.isInt()) {
                        result = new Register(new BoolType(1), "bigger_equal");
                        current_block.addInst(new CompareInst(current_block, compareInstOp.sge, left, right, result, new IntType(32)));
                    } else {
                        Function func = module.builtinFunctions.get("string_bigger_equal");
                        ArrayList<operand> paras = new ArrayList<>();

                        paras.add(left); paras.add(right);
                        result = new Register(new BoolType(1), "string_bigger_equal");
                        current_block.addInst(new CallInst(current_block, func, paras, result));

                    }
                    break;
                case and:
                    result = new Register(new IntType(32), "and");
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.and, left, right, result));
                    break;
                case xor:
                    result = new Register(new IntType(32), "xor");
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.xor, left, right, result));
                    break;
                case or:
                    result = new Register(new IntType(32), "or");
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.or, left, right, result));
                    break;
                default:
                    throw new runtimeError("[IRBuilder][visit binaryExpr Node] wrong type of op", it.pos);
            }
            switch(it.op) {
                case sub:
                case mul:
                case div:
                case mod:
                case smallersmaller:
                case biggerbigger:
                case and:
                case xor:
                case or:
                case add:
                case smaller:
                case bigger:
                case smaller_equal:
                case bigger_equal:
                    it.oper = result;
                    it.addr = null;
                    current_function.symbolAdd(result.name, result);
                    break;
                case equal:
                    if(it.left.type.isNull() && it.right.type.isNull()) {
                        it.oper = new ConstBool(1, true);
                        it.addr = null;
                    } else {
                        it.oper = result;
                        it.addr = null;
                        current_function.symbolAdd(result.name, result);
                    }
                    break;
                case not_equal:
                    if(it.left.type.isNull() && it.right.type.isNull()) {
                        it.oper = new ConstBool(1, false);
                        it.addr = null;
                    } else {
                        it.oper = result;
                        it.addr = null;
                        current_function.symbolAdd(result.name, result);
                    }
                    break;
                
                default:
                    throw new runtimeError("[IRBuilder][visit binaryExpr Node] wrong type of op", it.pos);
            }
        } else {
            BasicBlock branchBlock, afterBlock, phi1, phi2;
            ArrayList<operand> opers = new ArrayList<>();
            ArrayList<BasicBlock> blocks = new ArrayList<>();
            switch(it.op) {
                case andand:
                    branchBlock = new BasicBlock("andand", current_function);
                    afterBlock = new BasicBlock("afterAndand", current_function);

                    it.left.accept(this);
                    left = it.left.oper;
                    current_block.addInst(new BranchInst(current_block, left, branchBlock, afterBlock));
                    phi1 = current_block;

                    current_block = branchBlock;
                    it.right.accept(this);
                    right = it.right.oper;
                    current_block.addInst(new BranchInst(current_block, null, afterBlock, null));
                    current_function.addBasicBlock(branchBlock);
                    phi2 = current_block;

                    current_block = afterBlock;
                    Register result = new Register(new BoolType(1), "andand");
                    opers.add(new ConstBool(1, false)); blocks.add(phi1);
                    opers.add(right); blocks.add(phi2);
                    current_block.addInst(new PhiInst(current_block, opers, blocks, result));
                    current_function.addBasicBlock(afterBlock);
                    
                    it.oper = result; it.addr = null;
                    current_function.symbolAdd(result.name, result);
                    /* current_function.BasicBlockAdd(branchBlock.name, branchBlock);
                    current_function.BasicBlockAdd(afterBlock.name, afterBlock); */
                    break;
                case oror:
                    branchBlock = new BasicBlock("andand", current_function);
                    afterBlock = new BasicBlock("afterAndand", current_function);

                    it.left.accept(this);
                    left = it.left.oper;
                    current_block.addInst(new BranchInst(current_block, left, afterBlock, branchBlock));
                    phi1 = current_block;

                    current_block = branchBlock;
                    it.right.accept(this);
                    right = it.right.oper;
                    current_block.addInst(new BranchInst(current_block, null, afterBlock, null));
                    current_function.addBasicBlock(branchBlock);
                    phi2 = current_block;

                    current_block = afterBlock;
                    result = new Register(new BoolType(1), "oror");
                    opers.add(new ConstBool(1, true)); blocks.add(phi1);
                    opers.add(right); blocks.add(phi2);
                    current_block.addInst(new PhiInst(current_block, opers, blocks, result));
                    current_function.addBasicBlock(afterBlock);

                    it.oper = result;
                    it.addr = null;
                    current_function.symbolAdd(result.name, result);
                    /* current_function.BasicBlockAdd(branchBlock.name, branchBlock);
                    current_function.BasicBlockAdd(afterBlock.name, afterBlock); */
                    break;
                default:
                    throw new runtimeError("[IRBuilder][visit binaryExpr Node] wrong type of op", it.pos);
            }
        }
    }
    @Override
    public void visit(unaryExprNode it) {
        it.node.accept(this);
        unaryExprNode.unaryOpType op = it.op;
        operand left = it.node.oper;
        operand right = new ConstInt(32, 1);
        operand addr = it.node.addr;
        Register result;
        switch(op) {
            case plusplus:  //++x
                result = new Register(new IntType(32), "unary_plusplus");
                current_block.addInst(new BinaryInst(current_block, binaryInstOp.add, left, right, result));
                current_block.addInst(new StoreInst(current_block, result, addr));
                it.oper = result;
                it.addr = addr;
                current_function.symbolAdd(result.name, result);
                break;
            case subsub:    //--x
                result = new Register(new IntType(32), "unary_subsub");
                current_block.addInst(new BinaryInst(current_block, binaryInstOp.sub, left, right, result));
                current_block.addInst(new StoreInst(current_block, result, addr));
                it.oper = result;
                it.addr = addr;
                current_function.symbolAdd(result.name, result);
                break;
            case posi:      //+x
                it.oper = left;
                it.addr = addr;
                break;
            case neg:       //-x
                if(left.isConst()) {
                    it.oper = new ConstInt(32, -((ConstInt)left).value());
                    it.addr = null;
                } else {
                    result = new Register(new IntType(32), "neg");
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.sub, new ConstInt(32, 0), left, result));
                    it.oper = result;
                    it.addr = null;
                    current_function.symbolAdd(result.name, result);
                }
                break;
            case not:       //!x
                result = new Register(new IntType(32), "not");
                current_block.addInst(new BinaryInst(current_block, binaryInstOp.xor, new ConstBool(8, true), left, result));
                it.oper = result;
                it.addr = null;
                current_function.symbolAdd(result.name, result);
                break;
            case bit_opposite:  //~x
                result = new Register(new IntType(32), "bit_opposite");
                current_block.addInst(new BinaryInst(current_block, binaryInstOp.xor, new ConstInt(32, -1), left, result));
                it.oper = result;
                it.addr = null;
                current_function.symbolAdd(result.name, result);
                break;
        }
    }
    @Override
    public void visit(newArrayExprNode it) {        //TO DO
        //TO DO
        System.exit(0);
    }
    @Override
    public void visit(newInitObjectExprNode it) {   //TO DO
        //TO DO
        System.exit(0);
    }
    @Override
    public void visit(newObjectExprNode it) {       //TO DO
        //TO DO
        System.exit(0);
    }
    @Override
    public void visit(postfixExprNode it) {
        it.node.accept(this);
        operand left = it.node.oper;
        operand right = new ConstInt(32, 1);
        Register result;

        if(it.op == postfixOpType.plusplus) {
            result = new Register(new IntType(32), "postfix_plusplus");
            BinaryInst inst = new BinaryInst(current_block, binaryInstOp.add, left, right, result);
            current_block.addInst(inst);
        } else {
            result = new Register(new IntType(32), "postfix_subsub");
            BinaryInst inst = new BinaryInst(current_block, binaryInstOp.sub, left, right, result);
            current_block.addInst(inst);
        }
        current_block.addInst(new StoreInst(current_block, result, it.node.addr));
        it.oper = it.node.oper;
        it.addr = null;

        current_function.symbolAdd(result.name, result);
    }
    @Override
    public void visit(funcCallExprNode it) {
        Function func;
        if(it.funcName instanceof methodNode) {
            System.exit(0);
            memberAccessExprNode funcN = ((memberAccessExprNode)(it.funcName));
            ExprNode body = funcN.bo;
            Type type = body.type;
            body.accept(this);
            if(type.isArray()) {
                Register pointer;
                if(body.oper.type().equals(new PointerType(new IntType(32)))) {
                    pointer = (Register) body.oper;
                } else {
                    pointer = new Register(new PointerType(new IntType(32)), "emmm");
                    current_block.addInst(new BitCastInst(current_block, body.oper, new PointerType(new IntType(32)), pointer));
                    current_function.symbolAdd(pointer.name, pointer);
                }
                ArrayList<operand> index = new ArrayList<>();
                index.add(new ConstInt(32, -1));
                Register result = new Register(pointer.type(), "elementPtr");
                Register size = new Register(new IntType(32), "ArraySize");
                current_block.addInst(new GetElementPtrInst(current_block, pointer, index, result));
                current_block.addInst(new LoadInst(current_block, new IntType(32), result, size));

                it.oper = size;
                it.addr = null;
                current_function.symbolAdd(result.name, result);
                current_function.symbolAdd(size.name, size);
            } else {
                String name = funcN.iden;
                if(type.isString())
                    func = module.builtinFunctions.get("string." + name);
                else
                    func = module.functions.get(((classType)type).name() + "." + name);
                ArrayList<operand> paras = new ArrayList<>();
                IRBaseType RetType = func.retType;
                Register result = RetType instanceof VoidType ? null : new Register(RetType, "funcRet");
                paras.add(body.oper);
                it.paras.forEach( t -> {
                    t.accept(this);
                    paras.add(t.oper);
                });
                current_block.addInst(new CallInst(current_block, func, paras, result));
                if(result != null)
                    current_function.symbolAdd(result.name, result);

                it.oper = result;
                it.addr = null;
            }
        } else if(it.funcName instanceof funcNode) {
            funcNode fn = (funcNode)it.funcName;
            if(gScope.containsFunction(fn.funcName, false)) {
                if(module.functions.containsKey(fn.funcName)) 
                    func = module.functions.get(fn.funcName);
                else func = module.builtinFunctions.get(fn.funcName);
                if(func == null) System.exit(0);
                IRBaseType retType = func.retType;
                Register result = retType instanceof VoidType ? null : new Register(retType, "call");
                ArrayList<operand> paras = new ArrayList<>();
                it.paras.forEach(t -> {
                    t.accept(this);
                    paras.add(t.oper);
                });
                current_block.addInst(new CallInst(current_block, func, paras, result));
                if(result != null)
                    current_function.symbolAdd(result.name, result);
                it.oper = result;
            } else {
                throw new runtimeError("[IRBuilder][visit funcCallExprNode] sth wrong", it.pos);
            }
        } else
            throw new runtimeError("[IRBuilder][visit funcCallExprNode] " + 
                                       "it is not a function",
                                       it.pos);
    }
    @Override
    public void visit(assignExprNode it) {
        it.left.accept(this);
        it.right.accept(this);
        current_block.addInst(new StoreInst(current_block, it.right.oper, it.left.addr));
        it.oper = it.right.oper;
        it.addr = null;
    }
    @Override
    public void visit(memberAccessExprNode it) {    //TO DO
        System.exit(0);
    }
    @Override
    public void visit(subscriptExprNode it) {       //TO DO
        //TO DO
        System.exit(0);
        it.array.accept(this);
        it.index.accept(this);
    }
    @Override
    public void visit(thisExprNode it) {            //TO DO
        //TO DO
        System.exit(0);
    }

    @Override
    public void visit(intConstNode it) {
        it.oper = new ConstInt(32, it.value);
    }
    @Override
    public void visit(boolConstNode it) {
        it.oper = new ConstBool(8, it.value);
        //TO DO
    }
    @Override
    public void visit(nullConstNode it) {
        it.oper = new ConstNull();
    }
    @Override
    public void visit(stringConstNode it) {
        String val = it.value;
        val.replace("\\n", "\n");
        val.replace("\\\\", "\\");
        val.replace("\\\"", "\"");
        val += "\0";

        Register re = new Register(new PointerType(new IntType(32)), "const_string");
        globalVariable s = module.addString(it.value);
        ArrayList<operand> index = new ArrayList<>();
        index.add(new ConstInt(32, 0));
        index.add(new ConstInt(32, 0));
        if(current_block == null) System.exit(0);
        current_block.addInst(new GetElementPtrInst(
            current_block,
            s,
            index,
            re
        ));
        it.oper = re;
    }

    @Override
    public void visit(simpleTypeNode it) {}
    @Override
    public void visit(TypeNode it) {}

    @Override
    public void visit(varNode it) {                 //TO DO
        operand addr = it.var.oper;
        if(addr != null) {
            IRBaseType irType;
            if(it.var.isGlobal) 
                irType = addr.type();
            else 
                irType = ((PointerType) addr.type()).baseType;
            Register result = new Register(irType, it.name);
            current_block.addInst(new LoadInst(current_block, irType, addr, result));

            it.oper = result;
            it.addr = addr;
            current_function.symbolAdd(result.name, result);
        } else {
            System.exit(0);
        }
        
    }
    @Override
    public void visit(funcNode it) {                //TO DO
        //TO DO
        System.exit(0);
    }
    @Override
    public void visit(methodNode it) {              //TO DO
        //TO DO
        System.exit(0);
    }
}
