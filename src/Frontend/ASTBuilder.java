package Frontend;

import AST.*;
import AST.binaryExprNode.binaryOpType;
import AST.postfixExprNode.postfixOpType;
import AST.unaryExprNode.unaryOpType;
import Parser.MxBaseVisitor;
import Parser.MxParser;
import Util.position;

import java.util.ArrayList;

import org.antlr.v4.runtime.ParserRuleContext;

public class ASTBuilder extends MxBaseVisitor<ASTNode> {

	@Override public ASTNode visitProgram(MxParser.ProgramContext ctx) {
        ArrayList<programSectionNode> tmp = new ArrayList<programSectionNode>();
        for (var t : ctx.programSection()) {
            tmp.add((programSectionNode)visit(t));
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
        TypeNode type = (TypeNode) visit(ctx.funcType);
        String iden = ctx.funcName.toString();
        ArrayList<paraDeclNode> paras = new ArrayList<paraDeclNode>();
        blockStmtNode suite;

        for(int i = 0; i < ctx.type().size(); i ++) {
            paras.add(new paraDeclNode(new position(ctx), (TypeNode) visit(ctx.type(i)), ctx.Identifier(i).toString()));
        }
        
        suite = (blockStmtNode) visit(ctx.suite());
        
        return new funcDeclNode(new position(ctx), type, iden, paras, suite);
    }

	@Override public ASTNode visitClassDecl(MxParser.ClassDeclContext ctx) {
        String identifier;
        ArrayList<funcDeclNode> funcs = new ArrayList<funcDeclNode>();
        ArrayList<varDeclNode> vars = new ArrayList<varDeclNode>();

        identifier = ctx.Identifier().toString();
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
            varList.add((singleVarDeclNode) visit(t));
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
            return new simpleTypeNode(new position(ctx), ctx.Identifier().toString());
        } else {
            return null;
        }
    }
	
	@Override public ASTNode visitType(MxParser.TypeContext ctx) {
        if(ctx.simpleType() != null) {
            return visit(ctx.simpleType());
        } else if(ctx.type() != null) {
            return new arrayTypeNode(new position(ctx), (TypeNode)visit(ctx.type()));
        } else {
            return null;
        }
    }
	
	@Override public ASTNode visitSingleVarDecl(MxParser.SingleVarDeclContext ctx) {
        String identifier;
        ExprNode expr;

        identifier = ctx.Identifier().toString();
        expr = (ExprNode) visit(ctx.expression());
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
        return new forStmtNode(new position(ctx), ex1, ex2, ex3);
    }

    @Override public ASTNode visitExprStmt(MxParser.ExprStmtContext ctx) {
        return new exprStmtNode((ExprNode) visit(ctx.expression()), new position(ctx));
    }
	
	@Override public ASTNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
        return new whileStmtNode(new position(ctx), (ExprNode) visit(ctx.expression()));
    }

    @Override public ASTNode visitIfstmt(MxParser.IfstmtContext ctx) {
        return new ifStmtNode(new position(ctx), (ExprNode) visit(ctx.expression()), (StmtNode) visit(ctx.trueStmt), (StmtNode) visit(ctx.falseStmt)); 
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

        for (var t : ctx.expression()) {
            expr.add((ExprNode)visit(t));
        }
        return new newArrayExprNode(new position(ctx), (simpleTypeNode) visit(ctx.simpleType()), expr);
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
        return new memberAccessExprNode(new position(ctx), (ExprNode) visit(ctx.expression()), ctx.Identifier().toString());
    }
    
	@Override public ASTNode visitAtomExpr(MxParser.AtomExprContext ctx) {
        if(ctx.expression() != null) return visit(ctx.expression());
        else if(ctx.Identifier() != null) return new identifierExprNode(new position(ctx), ctx.Identifier().toString());
        else if(ctx.literal() != null) return visit(ctx.literal());
        else if(ctx.This() != null) return new thisExprNode(new position(ctx));
        else return null;
    }
	
	@Override public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
        ExprNode ex1 = (ExprNode) visit(ctx.expression(0));
        ExprNode ex2 = (ExprNode) visit(ctx.expression(1));
        switch(ctx.op.toString()) {
            case "+" : 
                return new binaryExprNode(new position(ctx), binaryOpType.add, ex1, ex2);
            case "-" : 
                return new binaryExprNode(new position(ctx), binaryOpType.sub, ex1, ex2);
            case "==" : 
                return new binaryExprNode(new position(ctx), binaryOpType.equal, ex1, ex2);
            case "!=" : 
                return new binaryExprNode(new position(ctx), binaryOpType.not_equal, ex1, ex2);
            case "*" : 
                return new binaryExprNode(new position(ctx), binaryOpType.mul, ex1, ex2);
            case "/" : 
                return new binaryExprNode(new position(ctx), binaryOpType.div, ex1, ex2);
            case "%" : 
                return new binaryExprNode(new position(ctx), binaryOpType.mod, ex1, ex2);            
            case "<<" : 
                return new binaryExprNode(new position(ctx), binaryOpType.smallersmaller, ex1, ex2);
            case ">>" : 
                return new binaryExprNode(new position(ctx), binaryOpType.biggerbigger, ex1, ex2);
            case "<" : 
                return new binaryExprNode(new position(ctx), binaryOpType.smaller, ex1, ex2);
            case ">" : 
                return new binaryExprNode(new position(ctx), binaryOpType.bigger, ex1, ex2);
            case "<=" : 
                return new binaryExprNode(new position(ctx), binaryOpType.smaller_equal, ex1, ex2);
            case ">=" : 
                return new binaryExprNode(new position(ctx), binaryOpType.bigger_equal, ex1, ex2);
            case "&" : 
                return new binaryExprNode(new position(ctx), binaryOpType.and, ex1, ex2);
            case "^" : 
                return new binaryExprNode(new position(ctx), binaryOpType.xor, ex1, ex2);
            case "|" : 
                return new binaryExprNode(new position(ctx), binaryOpType.or, ex1, ex2);
            case "&&" : 
                return new binaryExprNode(new position(ctx), binaryOpType.andand, ex1, ex2);
            case "||" : 
                return new binaryExprNode(new position(ctx), binaryOpType.oror, ex1, ex2);
            default : 
                return null;
        }
    }
	
	@Override public ASTNode visitSubscript(MxParser.SubscriptContext ctx) {
        return new subscriptExprNode(new position(ctx), (ExprNode)visit(ctx.array), (ExprNode)visit(ctx.index));
    }
	
	@Override public ASTNode visitFunctionCall(MxParser.FunctionCallContext ctx) {
        ExprNode funcName;
        ArrayList<ExprNode> paras = new ArrayList<ExprNode>();

        funcName = (ExprNode)visit(ctx.funcName);
        for(var t : ctx.expression()) {
            paras.add((ExprNode)visit(t));
        }
        return new funcCallExprNode(new position(ctx), funcName, paras); 
    }
    
	@Override public ASTNode visitPostfixIncDec(MxParser.PostfixIncDecContext ctx) {
        switch (ctx.op.toString()) {
            case "++" :
                return new postfixExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), postfixOpType.plusplus);
            case "--" :
                return new postfixExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), postfixOpType.subsub);
            default:
                return null;
        }
    }
    
	@Override public ASTNode visitUnaryExpr(MxParser.UnaryExprContext ctx) {
        switch (ctx.op.toString()) {
            case "++" :
                return new unaryExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), unaryOpType.plusplus);
            case "--" :
                return new unaryExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), unaryOpType.subsub);
            case "+" :
                return new unaryExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), unaryOpType.posi);
            case "-" :
                return new unaryExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), unaryOpType.neg);
            case "!" :
                return new unaryExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), unaryOpType.not);
            case "~" :
                return new unaryExprNode(new position(ctx), (ExprNode)visit(ctx.expression()), unaryOpType.bit_opposite);
            default:
                return null;
        } 
    }
    
	@Override public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {
        return new assignExprNode(new position(ctx), (ExprNode)visit(ctx.expression(0)), (ExprNode)visit(ctx.expression(1)));
    }
    
	@Override public ASTNode visitLiteral(MxParser.LiteralContext ctx) {
        if(ctx.IntegerConstant() != null) {
            return new intConstNode(new position(ctx), Integer.parseInt(ctx.IntegerConstant().toString()));
        } else if(ctx.BoolConstant() != null) {
            if(ctx.BoolConstant().toString() == "true")
                return new boolConstNode(new position(ctx), true);
            else if(ctx.BoolConstant().toString() == "false")
                return new boolConstNode(new position(ctx), false);
            else return null;
        } else if(ctx.NullConstant() != null) {
            return new nullConstNode(new position(ctx));
        } else if(ctx.StringConstant() != null) {
            return new stringConstNode(new position(ctx), ctx.StringConstant().toString());
        } else {
            return null;
        }
    }

}