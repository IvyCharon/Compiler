// Generated from C:/Users/skysz/Desktop/study/�����ϵͳ/Compiler/Compiler/src/Parser\Mx.g4 by ANTLR 4.9
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MxParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#programSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgramSection(MxParser.ProgramSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#funcDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncDecl(MxParser.FuncDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#paraDeclList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParaDeclList(MxParser.ParaDeclListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#classDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDecl(MxParser.ClassDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(MxParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#varList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarList(MxParser.VarListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#singleVarDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleVarDecl(MxParser.SingleVarDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(MxParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code block}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MxParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code vardefStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardefStmt(MxParser.VardefStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(MxParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code loopstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopstmt(MxParser.LoopstmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jumpstmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpstmt(MxParser.JumpstmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pureExprStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPureExprStmt(MxParser.PureExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyStmt}
	 * labeled alternative in {@link MxParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStmt(MxParser.EmptyStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link MxParser#loopStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(MxParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link MxParser#loopStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(MxParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link MxParser#jumpStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(MxParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link MxParser#jumpStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(MxParser.BreakStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link MxParser#jumpStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStmt(MxParser.ContinueStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MemberAccess}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccess(MxParser.MemberAccessContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpr(MxParser.AtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Subscript}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscript(MxParser.SubscriptContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FunctionCall}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(MxParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PostfixIncDec}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixIncDec(MxParser.PostfixIncDecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UnaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(MxParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(MxParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(MxParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MxParser.LiteralContext ctx);
}