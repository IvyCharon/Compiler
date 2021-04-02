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
import Util.Type.arrayType;
import Util.Type.classType;
//import Util.error.runtimeError;
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

    private int BlockNum = 0, RegNum = 0;

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
        current_function = module.functions.get("__init__");
        current_block = current_function.entranceBlock;

        it.decls.forEach(t -> {
            if(t instanceof varDeclNode) {
                t.accept(this);
            }
        });
        current_block.addInst(new BranchInst(current_block, null, current_function.retBlock, null));
        current_block = current_function.retBlock;
        current_block.addInst(new ReturnInst(current_block, new VoidType(), null));

        current_function = null;
        current_block = null;

        it.decls.forEach(t -> {
            if(t instanceof classDeclNode) {
                t.accept(this);
            }
        });

        for(var t : it.decls) {
            if(t instanceof funcDeclNode) {
                String funcN = ((funcDeclNode)t).identifier;
                isParam = true;
                ((funcDeclNode)t).paras.forEach(para -> para.accept(this));
                isParam = false;
                ArrayList<parameter> paras = new ArrayList<>();
                ((funcDeclNode)t).paras.forEach(para -> {
                    paras.add((parameter)(para.var.oper));
                });
                IRBaseType retType = ((funcDeclNode)t).func.retType() == null ? new VoidType() : ((funcDeclNode)t).func.retType().toIRType();
                Function func = new Function(funcN, retType, paras);
                func.retVal = new Register(new PointerType(retType), funcN + "_retVal" + RegNum ++);
                for(var p : ((funcDeclNode)t).paras) {
                    //if(p.type.type == null) {System.out.println("1");System.exit(0);}
                    func.symbolAdd(p.identifier, new Register(new PointerType(p.type.toIRType()), ((funcDeclNode)t).identifier + RegNum ++));
                }
                module.functions.put(funcN, func);
            }
        }

        it.decls.forEach(t -> {
            if(t instanceof funcDeclNode) {
                t.accept(this);
            }
        });

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
        
        current_function = module.functions.get(funcN);
        current_block = current_function.entranceBlock;

        if(it.identifier.equals("main")) {
            Register t = new Register(new VoidType(), "call_init" + RegNum ++);
            Function f = module.functions.get("__init__");
            current_block.addInst(new CallInst(current_block, f, new ArrayList<>(), t));
        }

        it.suite.accept(this);

        current_block.addInst(new BranchInst(current_block, null, current_function.retBlock, null));
        current_block = current_function.retBlock;

        current_block.addInst(new ReturnInst(current_block, current_function.retType, current_function.retVal));
        
        current_function = null;
        current_block = null;
    }
    @Override
    public void visit(classDeclNode it) {
        current_class = (classType)gScope.getType(it.identifier, it.pos);
        in_class = true;
        for(var i : it.funcs) {
            String funcN = it.identifier + "." + i.identifier;
            isParam = true;
            i.paras.forEach(t -> t.accept(this));
            isParam = false;
            ArrayList<parameter> paras = new ArrayList<>();
            i.paras.forEach(t -> {
                paras.add((parameter)(t.var.oper));
            });
            IRBaseType retType = i.func.retType() == null ? new VoidType() : i.func.retType().toIRType();
            Function func = new Function(funcN, retType, paras);
            func.retVal = new Register(new PointerType(retType), funcN + "_retVal" + RegNum ++);
            for(var t : i.paras) {
                //if(t.type.type == null) {System.out.println("1");System.exit(0);}
                func.symbolAdd(t.identifier, new Register(new PointerType(t.type.toIRType()), t.identifier + RegNum ++));
            }
            module.functions.put(funcN, func);
        }
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
                current_block.addInst(new StoreInst(current_block, init, gVar));
            } else 
                init = irType.toOper();
            gVar.init = init;
            module.globalVars.put(gVar.name, gVar);
            it.var.oper = gVar;
        } else if(isParam) {
            it.var.oper = new parameter(irType, name);
        } else if(current_function != null) {
            Register addr = new Register(new PointerType(irType), name + RegNum ++);
            current_function.symbolAdd(name, addr);
            it.var.oper = addr;

            if(it.expr != null) {
                it.expr.accept(this);
                operand init = it.expr.oper;
                current_block.addInst(new StoreInst(current_block, init, addr));
            }
        } else if(in_class) {
            Register addr = new Register(new PointerType(irType), name + RegNum ++);
            it.var.oper = addr;
        } else {
            System.out.println("2");
            System.exit(0);
            //throw new runtimeError("[IRBuilder][visit single var]: var pos is wrong!", it.pos);
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
        BasicBlock Cond = it.condition != null ? new BasicBlock("for_condition" + BlockNum ++, current_function) : null;
        BasicBlock Incr = it.incr != null ? new BasicBlock("for_incr" + BlockNum ++, current_function) : null;
        BasicBlock Body = new BasicBlock("for_body" + BlockNum ++, current_function);
        BasicBlock AfterFor = new BasicBlock("after_for" + BlockNum ++, current_function);

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
        BasicBlock trueBlock = new BasicBlock("if_true" + BlockNum ++, current_function);
        BasicBlock falseBlock = it.elsestmt == null ? null : new BasicBlock("if_false" + BlockNum ++, current_function);
        BasicBlock AfterIf = new BasicBlock("after_if" + BlockNum ++, current_function);

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
        BasicBlock Cond = new BasicBlock("while_condition" + BlockNum ++, current_function);
        BasicBlock Body = new BasicBlock("while_body" + BlockNum ++, current_function);
        BasicBlock AfterWhile = new BasicBlock("after_while" + BlockNum ++, current_function);

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
                        result = new Register(new IntType(32), "add" + RegNum ++);
                        current_block.addInst(new BinaryInst(current_block, binaryInstOp.add, left, right, result));
                        break;
                    } else {
                        Function func = module.builtinFunctions.get("__string_add");
                        ArrayList<operand> paras = new ArrayList<>();
                        paras.add(left); paras.add(right);
                        result = new Register(new PointerType(new IntType(32)), "__string_add" + RegNum ++);
                        current_block.addInst(new CallInst(current_block, func, paras, result));
                        break;
                    }
                case sub:
                    result = new Register(new IntType(32), "sub" + RegNum ++);
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.sub, left, right, result));
                    break;
                case equal:
                    if(le.isInt() && ri.isInt()) {
                        result = new Register(new BoolType(1), "equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.eq, left, right, result, new IntType(32)));
                    } else if(le.isBool() && ri.isBool()) {
                        result = new Register(new BoolType(1), "equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.eq, left, right, result, new BoolType(1)));
                    } else if(le.isArray() && ri.isNull()) {
                        result = new Register(new BoolType(1), "equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.eq, left, right, result, left.type()));
                    } else if(le.isNull() && ri.isArray()) {
                        result = new Register(new BoolType(1), "equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.eq, left, right, result, right.type()));
                    } else if(le.isString() && ri.isString()) {
                        Function func = module.builtinFunctions.get("__string_equal");
                        ArrayList<operand> paras = new ArrayList<>();
                        paras.add(left); paras.add(right);

                        result = new Register(new BoolType(1), "equal_string" + RegNum ++);
                        current_block.addInst(new CallInst(current_block, func, paras, result));
                    } else if(le.isNull() && ri.isClass()) {
                        result = new Register(new BoolType(1), "equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.eq, left, right, result, right.type()));
                    } else if(le.isClass() && ri.isNull()) {
                        result = new Register(new BoolType(1), "equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.eq, left, right, result, left.type()));
                    } else if(le.isNull() && ri.isNull()) {

                    } else {
                        System.out.println("3");
                        System.exit(0);
                        //throw new runtimeError("[IRBuilder][visit binaryExpr Node] wrong type of equal", it.pos);
                    }
                    break;
                case not_equal:
                    if(le.isInt() && ri.isInt()) {
                        result = new Register(new BoolType(1), "not_equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.ne, left, right, result, new IntType(32)));
                    } else if(le.isBool() && ri.isBool()) {
                        result = new Register(new BoolType(1), "not_equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.ne, left, right, result, new BoolType(1)));
                    } else if(le.isArray() && ri.isNull()) {
                        result = new Register(new BoolType(1), "not_equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.ne, left, right, result, left.type()));
                    } else if(le.isNull() && ri.isArray()) {
                        result = new Register(new BoolType(1), "not_equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.ne, left, right, result, right.type()));
                    } else if(le.isString() && ri.isString()) {
                        Function func = module.builtinFunctions.get("__string_not_equal");
                        ArrayList<operand> paras = new ArrayList<>();
                        paras.add(left); paras.add(right);

                        result = new Register(new BoolType(1), "not_equal_string" + RegNum ++);
                        current_block.addInst(new CallInst(current_block, func, paras, result));
                    } else if(le.isNull() && ri.isClass()) {
                        result = new Register(new BoolType(1), "not_equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.ne, left, right, result, right.type()));
                    } else if(le.isClass() && ri.isNull()) {
                        result = new Register(new BoolType(1), "not_equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.ne, left, right, result, left.type()));
                    } else if(le.isNull() && ri.isNull()) {

                    } else { 
                        System.out.println("4");   
                        System.exit(0);
                        //throw new runtimeError("[IRBuilder][visit binaryExpr Node] wrong type of not equal", it.pos);
                    }
                    break;
                case mul:
                    result = new Register(new IntType(32), "mul" + RegNum ++);
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.mul, left, right, result));
                    break;
                case div:
                    result = new Register(new IntType(32), "div" + RegNum ++);
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.sdiv, left, right, result));
                    break;
                case mod:
                    result = new Register(new IntType(32), "mod" + RegNum ++);
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.srem, left, right, result));
                    break;
                case smallersmaller:
                    result = new Register(new IntType(32), "smallersmaller" + RegNum ++);
                    if(current_block == null) {System.out.println("5");System.exit(0);}
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.shl, left, right, result));
                    break;
                case biggerbigger:
                    result = new Register(new IntType(32), "biggerbigger" + RegNum ++);
                    if(current_block == null) {System.out.println("6");System.exit(0);}
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.ashr, left, right, result));
                    break;
                case smaller:
                    if(le.isInt() && ri.isInt()) {
                        result = new Register(new BoolType(1), "smaller" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.slt, left, right, result, new IntType(32)));
                    } else {
                        Function func = module.builtinFunctions.get("__string_smaller");
                        ArrayList<operand> paras = new ArrayList<>();

                        paras.add(left); paras.add(right);
                        result = new Register(new BoolType(1), "__string_smaller" + RegNum ++);
                        current_block.addInst(new CallInst(current_block, func, paras, result));
                    }
                    break;
                case bigger:
                    if(le.isInt() && ri.isInt()) {
                        result = new Register(new BoolType(1), "bigger" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.sgt, left, right, result, new IntType(32)));
                    } else {
                        Function func = module.builtinFunctions.get("__string_bigger");
                        ArrayList<operand> paras = new ArrayList<>();

                        paras.add(left); paras.add(right);
                        result = new Register(new BoolType(1), "__string_bigger" + RegNum ++);
                        current_block.addInst(new CallInst(current_block, func, paras, result));
                    }
                    break;
                case smaller_equal:
                    if(le.isInt() && ri.isInt()) {
                        result = new Register(new BoolType(1), "smaller_equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.sle, left, right, result, new IntType(32)));
                    } else {
                        Function func = module.builtinFunctions.get("__string_smaller_equal");
                        ArrayList<operand> paras = new ArrayList<>();

                        paras.add(left); paras.add(right);
                        result = new Register(new BoolType(1), "__string_smaller_equal" + RegNum ++);
                        current_block.addInst(new CallInst(current_block, func, paras, result));

                    }
                    break;
                case bigger_equal:
                    if(le.isInt() && ri.isInt()) {
                        result = new Register(new BoolType(1), "bigger_equal" + RegNum ++);
                        current_block.addInst(new CompareInst(current_block, compareInstOp.sge, left, right, result, new IntType(32)));
                    } else {
                        Function func = module.builtinFunctions.get("__string_bigger_equal");
                        ArrayList<operand> paras = new ArrayList<>();

                        paras.add(left); paras.add(right);
                        result = new Register(new BoolType(1), "__string_bigger_equal" + RegNum ++);
                        current_block.addInst(new CallInst(current_block, func, paras, result));

                    }
                    break;
                case and:
                    result = new Register(new IntType(32), "and" + RegNum ++);
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.and, left, right, result));
                    break;
                case xor:
                    result = new Register(new IntType(32), "xor" + RegNum ++);
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.xor, left, right, result));
                    break;
                case or:
                    result = new Register(new IntType(32), "or" + RegNum ++);
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.or, left, right, result));
                    break;
                default:
                    System.out.println("7");
                    System.exit(0);
                    //throw new runtimeError("[IRBuilder][visit binaryExpr Node] wrong type of op", it.pos);
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
                    current_function.symbolAdd(result.name, result);
                    break;
                case equal:
                    if(it.left.type.isNull() && it.right.type.isNull()) {
                        it.oper = new ConstBool(1, true);
                    } else {
                        it.oper = result;
                        current_function.symbolAdd(result.name, result);
                    }
                    break;
                case not_equal:
                    if(it.left.type.isNull() && it.right.type.isNull()) {
                        it.oper = new ConstBool(1, false);
                    } else {
                        it.oper = result;
                        current_function.symbolAdd(result.name, result);
                    }
                    break;
                
                default:
                    System.out.println("8");
                    System.exit(0);
                    //throw new runtimeError("[IRBuilder][visit binaryExpr Node] wrong type of op", it.pos);
            }
        } else {
            /* BasicBlock branchBlock, afterBlock, phi1, phi2;
            ArrayList<operand> opers = new ArrayList<>();
            ArrayList<BasicBlock> blocks = new ArrayList<>();
            switch(it.op) {
                case andand:
                    branchBlock = new BasicBlock("andand" + BlockNum ++, current_function);
                    afterBlock = new BasicBlock("afterAndand" + BlockNum ++, current_function);

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
                    Register result = new Register(new BoolType(1), "andand" + RegNum ++);
                    opers.add(new ConstBool(1, false)); blocks.add(phi1);
                    opers.add(right); blocks.add(phi2);
                    current_block.addInst(new PhiInst(current_block, opers, blocks, result));
                    current_function.addBasicBlock(afterBlock);
                    
                    it.oper = result;
                    current_function.symbolAdd(result.name, result);
                    break;
                case oror:
                    branchBlock = new BasicBlock("andand" + BlockNum ++, current_function);
                    afterBlock = new BasicBlock("afterAndand" + BlockNum ++, current_function);

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
                    result = new Register(new BoolType(1), "oror" + RegNum ++);
                    opers.add(new ConstBool(1, true)); blocks.add(phi1);
                    opers.add(right); blocks.add(phi2);
                    current_block.addInst(new PhiInst(current_block, opers, blocks, result));
                    current_function.addBasicBlock(afterBlock);

                    it.oper = result;
                    current_function.symbolAdd(result.name, result);
                    break;
                default:
                    System.out.println("9");
                    System.exit(0);
                    //throw new runtimeError("[IRBuilder][visit binaryExpr Node] wrong type of op", it.pos);
            } */

            BasicBlock brB, afterB;
            Register tmp;
            switch (it.op) {
                case andand:
                    brB = new BasicBlock("andand" + BlockNum ++, current_function);
                    afterB = new BasicBlock("after_andand" + BlockNum ++, current_function);
                    it.left.accept(this);
                    current_block.addInst(new BranchInst(current_block, it.left.oper, brB, afterB));

                    current_block = brB;
                    it.right.accept(this);
                    current_block.addInst(new BranchInst(current_block, null, afterB, null));

                    current_block = afterB;
                    tmp = new Register(new BoolType(1), "andand" + RegNum ++);
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.and, it.left.oper, it.right.oper, tmp));
                    it.oper = tmp;

                    current_function.addBasicBlock(brB);
                    current_function.addBasicBlock(afterB);
                    
                    break;
                case oror:
                    brB = new BasicBlock("oror" + BlockNum ++, current_function);
                    afterB = new BasicBlock("after_oror" + BlockNum ++, current_function);
                    it.left.accept(this);
                    current_block.addInst(new BranchInst(current_block, it.left.oper, afterB, brB));

                    current_block = brB;
                    it.right.accept(this);
                    current_block.addInst(new BranchInst(current_block, null, afterB, null));

                    current_block = afterB;
                    tmp = new Register(new BoolType(1), "oror" + RegNum ++);
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.or, it.left.oper, it.right.oper, tmp));
                    it.oper = tmp;

                    current_function.addBasicBlock(brB);
                    current_function.addBasicBlock(afterB);

                    break;
                default:
                    System.out.println("9");
                    System.exit(0);
            }
        }
    }
    @Override
    public void visit(unaryExprNode it) {
        it.node.accept(this);
        unaryExprNode.unaryOpType op = it.op;
        operand left = it.node.oper;
        operand right = new ConstInt(32, 1);
        operand addr = it.node.lresult;
        Register result;
        switch(op) {
            case plusplus:  //++x
                result = new Register(new IntType(32), "unary_plusplus" + RegNum ++);
                current_block.addInst(new BinaryInst(current_block, binaryInstOp.add, left, right, result));
                current_block.addInst(new StoreInst(current_block, result, addr));
                it.oper = result;
                it.lresult = addr;
                current_function.symbolAdd(result.name, result);
                break;
            case subsub:    //--x
                result = new Register(new IntType(32), "unary_subsub" + RegNum ++);
                current_block.addInst(new BinaryInst(current_block, binaryInstOp.sub, left, right, result));
                current_block.addInst(new StoreInst(current_block, result, addr));
                it.oper = result;
                it.lresult = addr;
                current_function.symbolAdd(result.name, result);
                break;
            case posi:      //+x
                it.oper = left;
                it.lresult = addr;
                break;
            case neg:       //-x
                if(left.isConst()) {
                    it.oper = new ConstInt(32, -((ConstInt)left).value());
                    it.lresult = addr;
                } else {
                    result = new Register(new IntType(32), "neg" + RegNum ++);
                    current_block.addInst(new BinaryInst(current_block, binaryInstOp.sub, new ConstInt(32, 0), left, result));
                    it.oper = result;
                    it.lresult = addr;
                    current_function.symbolAdd(result.name, result);
                }
                break;
            case not:       //!x
                result = new Register(new IntType(32), "not" + RegNum ++);
                current_block.addInst(new BinaryInst(current_block, binaryInstOp.xor, new ConstBool(8, true), left, result));
                it.oper = result;
                it.lresult = addr;
                current_function.symbolAdd(result.name, result);
                break;
            case bit_opposite:  //~x
                result = new Register(new IntType(32), "bit_opposite" + RegNum ++);
                current_block.addInst(new BinaryInst(current_block, binaryInstOp.xor, new ConstInt(32, -1), left, result));
                it.oper = result;
                it.lresult = addr;
                current_function.symbolAdd(result.name, result);
                break;
        }
    }
    
    private Register arrayMalloc(int dim, IRBaseType type, newArrayExprNode it) {
        Register ptr = new Register(type, "arrayMalloc" + RegNum ++);
        if(dim == it.expr.size())
            return ptr;
        
        return null;
    }
    
    @Override
    public void visit(newArrayExprNode it) {        //TO DO
        IRBaseType type = ((arrayType)(it.type)).tran_IRType();
        it.expr.forEach(t -> t.accept(this));
        it.oper = arrayMalloc(0, type, it);

        System.out.println("10");
        System.exit(0);
    }
    @Override
    public void visit(newInitObjectExprNode it) {   //TO DO
        //TO DO
        System.out.println("11");
        System.exit(0);
    }
    @Override
    public void visit(newObjectExprNode it) {       //TO DO
        //TO DO
        System.out.println("12");
        System.exit(0);
    }
    @Override
    public void visit(postfixExprNode it) {
        it.node.accept(this);
        operand left = it.node.oper;
        operand right = new ConstInt(32, 1);
        Register result;

        if(it.op == postfixOpType.plusplus) {
            result = new Register(new IntType(32), "postfix_plusplus" + RegNum ++);
            BinaryInst inst = new BinaryInst(current_block, binaryInstOp.add, left, right, result);
            current_block.addInst(inst);
        } else {
            result = new Register(new IntType(32), "postfix_subsub" + RegNum ++);
            BinaryInst inst = new BinaryInst(current_block, binaryInstOp.sub, left, right, result);
            current_block.addInst(inst);
        }
        current_block.addInst(new StoreInst(current_block, result, it.node.lresult));
        it.oper = it.node.oper;

        current_function.symbolAdd(result.name, result);
    }
    @Override
    public void visit(funcCallExprNode it) {        //TO DO
        Function func = null;
        if(it.funcName instanceof methodNode) {
            //System.out.println("13");
            //System.exit(0);
            methodNode funcN = ((methodNode)(it.funcName));
            ExprNode body = funcN.bo;
            Type type = body.type;
            body.accept(this);
            if(type.isArray()) {
                Register pointer;
                if(body.oper.type().equals(new PointerType(new IntType(32)))) {
                    pointer = (Register) body.oper;
                } else {
                    pointer = new Register(new PointerType(new IntType(32)), "emmm" + RegNum ++);
                    current_block.addInst(new BitCastInst(current_block, body.oper, new PointerType(new IntType(32)), pointer));
                    current_function.symbolAdd(pointer.name, pointer);
                }
                ArrayList<operand> index = new ArrayList<>();
                index.add(new ConstInt(32, -1));
                Register result = new Register(pointer.type(), "elementPtr" + RegNum ++);
                Register size = new Register(new IntType(32), "ArraySize" + RegNum ++);
                current_block.addInst(new GetElementPtrInst(current_block, pointer, index, result));
                current_block.addInst(new LoadInst(current_block, new IntType(32), result, size));

                it.oper = size;
                current_function.symbolAdd(result.name, result);
                current_function.symbolAdd(size.name, size);
            } else {
                String name = funcN.name;
                if(type.isString())
                    func = module.builtinFunctions.get("__string_" + name);
                else
                    func = module.functions.get(((classType)type).name() + "." + name);
                ArrayList<operand> paras = new ArrayList<>();
                IRBaseType RetType = func.retType;
                Register result = RetType instanceof VoidType ? null : new Register(RetType, "funcRet" + RegNum ++);
                paras.add(body.oper);
                it.paras.forEach( t -> {
                    t.accept(this);
                    paras.add(t.oper);
                });
                current_block.addInst(new CallInst(current_block, func, paras, result));
                if(result != null)
                    current_function.symbolAdd(result.name, result);

                it.oper = result;
            }
        } else if(it.funcName instanceof funcNode) {
            funcNode fn = (funcNode)it.funcName;
            String funcN = fn.funcName;
            if(in_class && module.functions.containsKey(current_class.name() + "." + funcN)) {
                func = module.functions.get(current_class.name() + "." + funcN);
                funcN = current_class.name() + "." + funcN;
            } else if(module.functions.containsKey(funcN)) {
                func = module.functions.get(funcN);
            } else if(module.builtinFunctions.containsKey(funcN)) {
                func = module.builtinFunctions.get(funcN);
            }
            
            if(func == null) throw new runtimeError("[IRBuilder][visit func call] no such function!");

            IRBaseType retType = func.retType;
            Register result = retType instanceof VoidType ? null : new Register(retType, "call" + RegNum ++);

            ArrayList<operand> paras = new ArrayList<>();
            if(in_class) {
                //add this
                //add what?!
            }
            it.paras.forEach(t -> {
                t.accept(this);
                paras.add(t.oper);
            });
            current_block.addInst(new CallInst(current_block, func, paras, result));
            if(result != null)
                current_function.symbolAdd(result.name, result);
            it.oper = result;
        } else {
            throw new runtimeError("[IRBuilder][visit funcCallExprNode] it is not a function!");
        }
    }
    @Override
    public void visit(assignExprNode it) {
        it.left.accept(this);
        it.right.accept(this);
        current_block.addInst(new StoreInst(current_block, it.right.oper, it.left.lresult));
        it.oper = it.right.oper;
    }
    @Override
    public void visit(memberAccessExprNode it) {    //TO DO
        System.out.println("17");
        System.exit(0);
    }
    @Override
    public void visit(subscriptExprNode it) {       //TO DO
        //TO DO
        System.out.println("18");
        System.exit(0);
        it.array.accept(this);
        it.index.accept(this);
    }
    @Override
    public void visit(thisExprNode it) {            //TO DO
        //TO DO
        System.out.println("19");
        System.exit(0);
    }

    @Override
    public void visit(intConstNode it) {
        it.oper = new ConstInt(32, it.value);
    }
    @Override
    public void visit(boolConstNode it) {
        it.oper = new ConstBool(1, it.value);
        //TO DO
    }
    @Override
    public void visit(nullConstNode it) {
        it.oper = new ConstNull();
    }
    @Override
    public void visit(stringConstNode it) {         //TO DO
        int si = it.value.length();
        String val = it.value.substring(1, si - 1);
        val.replace("\\n", "\n");
        val.replace("\\\\", "\\");
        val.replace("\\\"", "\"");

        Register re = new Register(new PointerType(new IntType(32)), "const_string" + RegNum ++);
        globalVariable s = module.addString(val);
        ArrayList<operand> index = new ArrayList<>();
        index.add(new ConstInt(32, 0));
        index.add(new ConstInt(32, 0));
        if(current_block == null) {System.out.println("20");System.exit(0);}
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
        if(current_function != null) {
            ArrayList<operand> symbs = current_function.symbols.get(it.name);
            if(symbs == null) {
                //global variable
                operand t = module.globalVars.get(it.name);
                if(t == null) System.exit(0);
                if(t.type() instanceof PointerType) {
                    Register r = new Register(((PointerType)(t.type())).baseType, it.name + RegNum ++);
                    current_block.addInst(new LoadInst(current_block, ((PointerType)(t.type())).baseType, t, r));
                    it.oper = r;
                    it.lresult = t;
                } else 
                    it.oper = t;
                return;            
            }
            operand tmp = symbs.get(symbs.size() - 1);
            if(tmp.type() instanceof PointerType) {
                Register reg = new Register(((PointerType)(tmp.type())).baseType, it.name + RegNum ++);
                current_block.addInst(new LoadInst(current_block, ((PointerType)(tmp.type())).baseType, tmp, reg));
                it.oper = reg;
                it.lresult = tmp;
            } else 
                it.oper = tmp;
        } else {    //in class
            System.out.println("22");
            System.exit(0);
        }        
    }
    @Override
    public void visit(funcNode it) {                //TO DO
        //TO DO
        System.out.println("23");
        System.exit(0);
    }
    @Override
    public void visit(methodNode it) {              //TO DO
        //TO DO
        System.out.println("24");
        System.exit(0);
    }
}
