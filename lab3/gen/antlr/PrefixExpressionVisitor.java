// Generated from /home/friendlyevil/IdeaProjects/MT_3Lab/src/main/java/antlr/PrefixExpression.g4 by ANTLR 4.7.2
package antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PrefixExpressionParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PrefixExpressionVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(PrefixExpressionParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#s_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitS_expression(PrefixExpressionParser.S_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#big_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBig_expression(PrefixExpressionParser.Big_expressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(PrefixExpressionParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#if_rule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_rule(PrefixExpressionParser.If_ruleContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#else_rule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElse_rule(PrefixExpressionParser.Else_ruleContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(PrefixExpressionParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#define}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefine(PrefixExpressionParser.DefineContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#right_variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRight_variable(PrefixExpressionParser.Right_variableContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#arithmetic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic(PrefixExpressionParser.ArithmeticContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#arithmetic_operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArithmetic_operation(PrefixExpressionParser.Arithmetic_operationContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#logic_operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogic_operation(PrefixExpressionParser.Logic_operationContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#logic}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogic(PrefixExpressionParser.LogicContext ctx);
	/**
	 * Visit a parse tree produced by {@link PrefixExpressionParser#compare_operation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompare_operation(PrefixExpressionParser.Compare_operationContext ctx);
}