// Generated from C:/Users/skysz/Desktop/study/计算机系统/Compiler/Compiler/src/Parser\Mx.g4 by ANTLR 4.9
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxParser}.
 */
public interface MxListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#varDef}.
	 * @param ctx the parse tree
	 */
	void enterVarDef(MxParser.VarDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#varDef}.
	 * @param ctx the parse tree
	 */
	void exitVarDef(MxParser.VarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#singlevarDef}.
	 * @param ctx the parse tree
	 */
	void enterSinglevarDef(MxParser.SinglevarDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#singlevarDef}.
	 * @param ctx the parse tree
	 */
	void exitSinglevarDef(MxParser.SinglevarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void enterFuncDef(MxParser.FuncDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funcDef}.
	 * @param ctx the parse tree
	 */
	void exitFuncDef(MxParser.FuncDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#funcType}.
	 * @param ctx the parse tree
	 */
	void enterFuncType(MxParser.FuncTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funcType}.
	 * @param ctx the parse tree
	 */
	void exitFuncType(MxParser.FuncTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#parDefList}.
	 * @param ctx the parse tree
	 */
	void enterParDefList(MxParser.ParDefListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#parDefList}.
	 * @param ctx the parse tree
	 */
	void exitParDefList(MxParser.ParDefListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#typedef}.
	 * @param ctx the parse tree
	 */
	void enterTypedef(MxParser.TypedefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#typedef}.
	 * @param ctx the parse tree
	 */
	void exitTypedef(MxParser.TypedefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code narrayTypeInt}
	 * labeled alternative in {@link MxParser#nonarraytypedef}.
	 * @param ctx the parse tree
	 */
	void enterNarrayTypeInt(MxParser.NarrayTypeIntContext ctx);
	/**
	 * Exit a parse tree produced by the {@code narrayTypeInt}
	 * labeled alternative in {@link MxParser#nonarraytypedef}.
	 * @param ctx the parse tree
	 */
	void exitNarrayTypeInt(MxParser.NarrayTypeIntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code narrayTypeBool}
	 * labeled alternative in {@link MxParser#nonarraytypedef}.
	 * @param ctx the parse tree
	 */
	void enterNarrayTypeBool(MxParser.NarrayTypeBoolContext ctx);
	/**
	 * Exit a parse tree produced by the {@code narrayTypeBool}
	 * labeled alternative in {@link MxParser#nonarraytypedef}.
	 * @param ctx the parse tree
	 */
	void exitNarrayTypeBool(MxParser.NarrayTypeBoolContext ctx);
	/**
	 * Enter a parse tree produced by the {@code narrayTypeString}
	 * labeled alternative in {@link MxParser#nonarraytypedef}.
	 * @param ctx the parse tree
	 */
	void enterNarrayTypeString(MxParser.NarrayTypeStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code narrayTypeString}
	 * labeled alternative in {@link MxParser#nonarraytypedef}.
	 * @param ctx the parse tree
	 */
	void exitNarrayTypeString(MxParser.NarrayTypeStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code narrayTypeIdentifier}
	 * labeled alternative in {@link MxParser#nonarraytypedef}.
	 * @param ctx the parse tree
	 */
	void enterNarrayTypeIdentifier(MxParser.NarrayTypeIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code narrayTypeIdentifier}
	 * labeled alternative in {@link MxParser#nonarraytypedef}.
	 * @param ctx the parse tree
	 */
	void exitNarrayTypeIdentifier(MxParser.NarrayTypeIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#suite}.
	 * @param ctx the parse tree
	 */
	void enterSuite(MxParser.SuiteContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#suite}.
	 * @param ctx the parse tree
	 */
	void exitSuite(MxParser.SuiteContext ctx);
	/**
	 * Enter a parse tree produced by the {@code block}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MxParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code block}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MxParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code vardefStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterVardefStmt(MxParser.VardefStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code vardefStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitVardefStmt(MxParser.VardefStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(MxParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(MxParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(MxParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(MxParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStmt(MxParser.ContinueStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStmt(MxParser.ContinueStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(MxParser.BreakStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(MxParser.BreakStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pureExprStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterPureExprStmt(MxParser.PureExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pureExprStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitPureExprStmt(MxParser.PureExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code emptyStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStmt(MxParser.EmptyStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStmt(MxParser.EmptyStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterThisExpr(MxParser.ThisExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitThisExpr(MxParser.ThisExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(MxParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(MxParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funccal}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunccal(MxParser.FunccalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funccal}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunccal(MxParser.FunccalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selfExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSelfExpr(MxParser.SelfExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selfExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSelfExpr(MxParser.SelfExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberAcc}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemberAcc(MxParser.MemberAccContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberAcc}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemberAcc(MxParser.MemberAccContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(MxParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(MxParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arraydef}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArraydef(MxParser.ArraydefContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arraydef}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArraydef(MxParser.ArraydefContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(MxParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(MxParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(MxParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(MxParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonarraynewtype}
	 * labeled alternative in {@link MxParser#newType}.
	 * @param ctx the parse tree
	 */
	void enterNonarraynewtype(MxParser.NonarraynewtypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonarraynewtype}
	 * labeled alternative in {@link MxParser#newType}.
	 * @param ctx the parse tree
	 */
	void exitNonarraynewtype(MxParser.NonarraynewtypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newtypeobject}
	 * labeled alternative in {@link MxParser#newType}.
	 * @param ctx the parse tree
	 */
	void enterNewtypeobject(MxParser.NewtypeobjectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newtypeobject}
	 * labeled alternative in {@link MxParser#newType}.
	 * @param ctx the parse tree
	 */
	void exitNewtypeobject(MxParser.NewtypeobjectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newtypearray}
	 * labeled alternative in {@link MxParser#newType}.
	 * @param ctx the parse tree
	 */
	void enterNewtypearray(MxParser.NewtypearrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newtypearray}
	 * labeled alternative in {@link MxParser#newType}.
	 * @param ctx the parse tree
	 */
	void exitNewtypearray(MxParser.NewtypearrayContext ctx);
}