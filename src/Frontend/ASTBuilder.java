package Frontend;

import AST.*;
import AST.binaryExprNode.binaryOpType;
import AST.postfixExprNode.postfixOpType;
import AST.unaryExprNode.unaryOpType;
import Parser.MxBaseVisitor;
import Parser.MxParser;
import Util.position;
import Util.Type.*;
import Util.error.semanticError;

import java.util.ArrayList;

public class ASTBuilder extends MxBaseVisitor<ASTNode> {

    Type intType, boolType, stringType;

	@Override public ASTNode visitProgram(MxParser.ProgramContext ctx) {
        ArrayList<ASTNode> tmp = new ArrayList<ASTNode>();
        for (var t : ctx.programSection()) {
            tmp.add((ASTNode)visit(t));
        }
        return new programNode(new position(ctx), tmp);
    }
	
	@Override public ASTNode visitProgramSection(MxParser.ProgramSectionContext ctx) {
        if(ctx.funcDecl() != null) {
            return visit(ctx.funcDecl());
        } else if(ctx.classDecl() != null) {
            return visit(ctx.classDecl());
        } else if(ctx.varDecl() != null) {
            return visit(ctx.varDecl());
        } else {
            return null;
        }
    }
	
	@Override public ASTNode visitFuncDecl(MxParser.FuncDeclContext ctx) {
        TypeNode type = (ctx.funcType == null) ? null : (TypeNode) visit(ctx.funcType);
        String iden = ctx.funcName.getText();
        ArrayList<singleVarDeclNode> paras = new ArrayList<singleVarDeclNode>();
        blockStmtNode suite;

        for(int i = 1; i < ctx.type().size(); i ++) {
            singleVarDeclNode tmp = new singleVarDeclNode(new position(ctx), ctx.Identifier(i).getText(), null);
            tmp.type =  (TypeNode) visit(ctx.type(i));
            paras.add(tmp);
        }
        
        suite = (blockStmtNode) visit(ctx.suite());
        
        return new funcDeclNode(new position(ctx), type, iden, paras, suite);
    }

	@Override public ASTNode visitClassDecl(MxParser.ClassDeclContext ctx) {
        String identifier;
        ArrayList<funcDeclNode> funcs = new ArrayList<funcDeclNode>();
        ArrayList<varDeclNode> vars = new ArrayList<varDeclNode>();

        identifier = ctx.Identifier().getText();
        for(var t : ctx.funcDecl()) {
            funcs.add((funcDeclNode) visit(t));
        }
        for(var t : ctx.varDecl()) {
            vars.add((varDeclNode) visit(t));
        }
        
        return new classDeclNode(new position(ctx), identifier, funcs, vars);
    }

	@Override public ASTNode visitVarDecl(MxParser.VarDeclContext ctx) {
        TypeNode type;
        ArrayList<singleVarDeclNode> varList = new ArrayList<singleVarDeclNode>();
        
        type = (TypeNode) visit(ctx.type());
        for(var t : ctx.singleVarDecl()) {
            singleVarDeclNode tmp = (singleVarDeclNode) visit(t);
            tmp.type = type;
            varList.add(tmp);
        }
        return new varDeclNode(new position(ctx), type, varList);
    }

	@Override public ASTNode visitSimpleType(MxParser.SimpleTypeContext ctx) {
        if(ctx.Int() != null) {
            return new simpleTypeNode(new position(ctx), "int");
        } else if(ctx.Bool() != null) {
            return new simpleTypeNode(new position(ctx), "bool");
        } else if(ctx.String() != null) {
            return new simpleTypeNode(new position(ctx), "string");
        } else if(ctx.Identifier() != null) {
            return new simpleTypeNode(new position(ctx), ctx.Identifier().getText());
        } else {
            return null;
        }
    }
	
	@Override public ASTNode visitType(MxParser.TypeContext ctx) {
        int dim;
        if(ctx.LeftBracket() != null) dim = ctx.LeftBracket().size();
        else dim = 0;
        String typeName = ((simpleTypeNode)visit(ctx.simpleType())).identifier;
        return new TypeNode(new position(ctx), typeName, dim);
    }
	
	@Override public ASTNode visitSingleVarDecl(MxParser.SingleVarDeclContext ctx) {
        String identifier;
        ExprNode expr;

        identifier = ctx.Identifier().getText();
        if(ctx.expression() != null)
            expr = (ExprNode) visit(ctx.expression());
        else expr = null;
        return new singleVarDeclNode(new position(ctx), identifier, expr);
    }
	
	@Override public ASTNode visitSuite(MxParser.SuiteContext ctx) {
        ArrayList<StmtNode> stmts = new ArrayList<StmtNode>();

        for (var t : ctx.statement()) {
            stmts.add((StmtNode) visit(t));
        }
        blockStmtNode tmp = new blockStmtNode(new position(ctx));
        tmp.stmts = stmts;

        return tmp;
    }
	
	@Override public ASTNode visitForStmt(MxParser.ForStmtContext ctx) {
        ExprNode ex1, ex2, ex3;
        if(ctx.i != null) ex1 = (ExprNode) visit(ctx.i); else ex1 = null;
        if(ctx.con != null) ex2 = (ExprNode) visit(ctx.con); else ex2 = null;
        if(ctx.step != null) ex3 = (ExprNode) visit(ctx.step); else ex3 = null;
        return new forStmtNode(new position(ctx), ex1, ex2, ex3, (StmtNode)visit(ctx.statement()));
    }

    @Override public ASTNode visitExprStmt(MxParser.ExprStmtContext ctx) {
        return new exprStmtNode((ExprNode) visit(ctx.expression()), new position(ctx));
    }
	
	@Override public ASTNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
        return new whileStmtNode(new position(ctx), (ExprNode) visit(ctx.expression()), (StmtNode) visit(ctx.statement()));
    }

    @Override public ASTNode visitIfstmt(MxParser.IfstmtContext ctx) {
        ExprNode con = (ExprNode) visit(ctx.expression());
        StmtNode tr = (StmtNode) visit(ctx.trueStmt);
        StmtNode fa = (ctx.falseStmt == null) ? null : (StmtNode) visit(ctx.falseStmt);
        return new ifStmtNode(new position(ctx), con, tr, fa); 
    }

	@Override public ASTNode visitBreakStmt(MxParser.BreakStmtContext ctx) {
        return new breakStmtNode(new position(ctx)); 
    }

    @Override public ASTNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) {
        return new emptyStmtNode(new position(ctx)); 
    }

    @Override public ASTNode visitBlock(MxParser.BlockContext ctx) {
        return (blockStmtNode) visit(ctx.suite());
    }

	@Override public ASTNode visitReturnStmt(MxParser.ReturnStmtContext ctx) {
        return new returnStmtNode(new position(ctx), (ExprNode) visit(ctx.expression())); 
    }
	
	@Override public ASTNode visitContinueStmt(MxParser.ContinueStmtContext ctx) {
        return new continueStmtNode(new position(ctx));
    }
	
	@Override public ASTNode visitVardefStmt(MxParser.VardefStmtContext ctx) {
        return visit(ctx.varDecl());
    }
    
	@Override public ASTNode visitNewArray(MxParser.NewArrayContext ctx) {
        ArrayList<ExprNode> expr = new ArrayList<ExprNode>();
        String arrName = ((simpleTypeNode)visit(ctx.simpleType())).identifier;

        for (var t : ctx.expression()) {
            expr.add((ExprNode)visit(t));
        }

        TypeNode tmp = new TypeNode(new position(ctx), arrName, expr.size());
        return new newArrayExprNode(new position(ctx), tmp, expr);
    }

    @Override public ASTNode visitNewInitObject(MxParser.NewInitObjectContext ctx) {
        ArrayList<ExprNode> expr = new ArrayList<ExprNode>();
        for (var t : ctx.expression()) {
            expr.add((ExprNode) visit(t));
        }
        return new newInitObjectExprNode(new position(ctx), (simpleTypeNode) visit(ctx.simpleType()), expr);
    }
	
	@Override public ASTNode visitNewObject(MxParser.NewObjectContext ctx) {
        return new newObjectExprNode(new position(ctx), (simpleTypeNode) visit(ctx.simpleType()));
    }
	
	@Override public ASTNode visitMemberAccess(MxParser.MemberAccessContext ctx) {
        return new memberAccessExprNode(new position(ctx), (ExprNode) visit(ctx.expression()), ctx.Identifier().getText());
    }
    
	@Override public ASTNode visitAtomExpr(MxParser.AtomExprContext ctx) {
        if(ctx.expression() != null) return visit(ctx.expression());
        else if(ctx.Identifier() != null) return new varNode(new position(ctx), ctx.Identifier().getText());
        else if(ctx.literal() != null) return visit(ctx.literal());
        else if(ctx.This() != null) return new thisExprNode(new position(ctx));
        else return null;
    }
	
	@Override public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
        ExprNode ex1 = (ExprNode) visit(ctx.expression(0));
        ExprNode ex2 = (ExprNode) visit(ctx.expression(1));
        if(ctx.Mul() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.mul, ex1, ex2);
        else if(ctx.Div() != null) 
            return new binaryExprNode(new position(ctx), binaryOpType.div, ex1, ex2);
        else if(ctx.Mod() != null) 
            return new binaryExprNode(new position(ctx), binaryOpType.mod, ex1, ex2);
        else if(ctx.Add() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.add, ex1, ex2);
        else if(ctx.Sub() != null) 
            return new binaryExprNode(new position(ctx), binaryOpType.sub, ex1, ex2);
        else if(ctx.Smallersmaller() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.smallersmaller, ex1, ex2);
        else if(ctx.Biggerbigger() != null) 
            return new binaryExprNode(new position(ctx), binaryOpType.biggerbigger, ex1, ex2);
        else if(ctx.Smaller() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.smaller, ex1, ex2);
        else if(ctx.Bigger() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.bigger, ex1, ex2);
        else if(ctx.Smaller_equal() != null) 
            return new binaryExprNode(new position(ctx), binaryOpType.smaller_equal, ex1, ex2);
        else if(ctx.Bigger_equal() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.bigger_equal, ex1, ex2);
        else if(ctx.Equal() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.equal, ex1, ex2);
        else if(ctx.Not_equal() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.not_equal, ex1, ex2);
        else if(ctx.And() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.and, ex1, ex2);
        else if(ctx.Xor() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.xor, ex1, ex2);
        else if(ctx.Or() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.or, ex1, ex2);
        else if(ctx.Andand() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.andand, ex1, ex2);
        else if(ctx.Oror() != null)
            return new binaryExprNode(new position(ctx), binaryOpType.oror, ex1, ex2);
        else return null;
    }
	
	@Override public ASTNode visitSubscript(MxParser.SubscriptContext ctx) {
        return new subscriptExprNode(new position(ctx), (ExprNode)visit(ctx.array), (ExprNode)visit(ctx.index));
    }
	
	@Override public ASTNode visitFunctionCall(MxParser.FunctionCallContext ctx) {
        ExprNode tmp;
        ArrayList<ExprNode> paras = new ArrayList<ExprNode>();

        tmp = (ExprNode)visit(ctx.funcName);

        for(int i = 1;i < ctx.expression().size();++ i){
            paras.add((ExprNode)visit(ctx.expression(i)));
        }

        if(tmp instanceof varNode) {
            funcNode r = new funcNode(tmp.pos, ((varNode)tmp).name);
            return new funcCallExprNode(new position(ctx), r, paras);
        } else if(tmp instanceof memberAccessExprNode) {
            methodNode r = new methodNode(tmp.pos, tmp, ((memberAccessExprNode)tmp).iden);
            return new funcCallExprNode(new position(ctx), r, paras);
        } else {
            throw new semanticError("sth wrong with function call!", new position(ctx));
        }
    }
    
	@Override public ASTNode visitPostfixIncDec(MxParser.PostfixIncDecContext ctx) {
        if(ctx.Plusplus() != null)
            return new postfixExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), postfixOpType.plusplus);
        else if(ctx.Subsub() != null)
            return new postfixExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), postfixOpType.subsub);
        else return null;
    }
    
	@Override public ASTNode visitUnaryExpr(MxParser.UnaryExprContext ctx) {
        if(ctx.Plusplus() != null)
            return new unaryExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), unaryOpType.plusplus);
        else if(ctx.Subsub() != null)
            return new unaryExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), unaryOpType.subsub);
        else if(ctx.Add() != null)
            return new unaryExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), unaryOpType.posi);
        else if(ctx.Sub() != null)
            return new unaryExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), unaryOpType.neg);
        else if(ctx.Not() != null)
            return new unaryExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), unaryOpType.not);
        else if(ctx.Bit_opposite() != null)
            return new unaryExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), unaryOpType.bit_opposite);
        else return null; 
    }
    
	@Override public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {
        ExprNode tmp = (ExprNode)visit(ctx.expression(0));
        ExprNode t = (ExprNode)visit(ctx.expression(1));
        return new assignExprNode(new position(ctx), t.isAssignable(), tmp, t);
    }
    
	@Override public ASTNode visitLiteral(MxParser.LiteralContext ctx) {
        if(ctx.IntegerConstant() != null) {
            return new intConstNode(new position(ctx), Integer.parseInt(ctx.IntegerConstant().getText()));
        } else if(ctx.True() != null) {
            return new boolConstNode(new position(ctx), true);
        }else if(ctx.False() != null) {
            return new boolConstNode(new position(ctx), false);
        }else if(ctx.NullConstant() != null) {
            return new nullConstNode(new position(ctx));
        } else if(ctx.StringConstant() != null) {
            return new stringConstNode(new position(ctx), ctx.StringConstant().getText());
        } else {
            return null;
        }
    }

}