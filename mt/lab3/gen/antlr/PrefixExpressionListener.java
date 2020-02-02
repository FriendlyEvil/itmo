// Generated from /home/friendlyevil/IdeaProjects/MT_3Lab/lab3/src/main/java/antlr/PrefixExpression.g4 by ANTLR 4.7.2
package antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PrefixExpressionParser}.
 */
public interface PrefixExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(PrefixExpressionParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(PrefixExpressionParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(PrefixExpressionParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(PrefixExpressionParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#big_expression}.
	 * @param ctx the parse tree
	 */
	void enterBig_expression(PrefixExpressionParser.Big_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#big_expression}.
	 * @param ctx the parse tree
	 */
	void exitBig_expression(PrefixExpressionParser.Big_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#one_expression}.
	 * @param ctx the parse tree
	 */
	void enterOne_expression(PrefixExpressionParser.One_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#one_expression}.
	 * @param ctx the parse tree
	 */
	void exitOne_expression(PrefixExpressionParser.One_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#do_whil}.
	 * @param ctx the parse tree
	 */
	void enterDo_whil(PrefixExpressionParser.Do_whilContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#do_whil}.
	 * @param ctx the parse tree
	 */
	void exitDo_whil(PrefixExpressionParser.Do_whilContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#whil}.
	 * @param ctx the parse tree
	 */
	void enterWhil(PrefixExpressionParser.WhilContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#whil}.
	 * @param ctx the parse tree
	 */
	void exitWhil(PrefixExpressionParser.WhilContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#if_rule}.
	 * @param ctx the parse tree
	 */
	void enterIf_rule(PrefixExpressionParser.If_ruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#if_rule}.
	 * @param ctx the parse tree
	 */
	void exitIf_rule(PrefixExpressionParser.If_ruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#else_rule}.
	 * @param ctx the parse tree
	 */
	void enterElse_rule(PrefixExpressionParser.Else_ruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#else_rule}.
	 * @param ctx the parse tree
	 */
	void exitElse_rule(PrefixExpressionParser.Else_ruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#print}.
	 * @param ctx the parse tree
	 */
	void enterPrint(PrefixExpressionParser.PrintContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#print}.
	 * @param ctx the parse tree
	 */
	void exitPrint(PrefixExpressionParser.PrintContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#define}.
	 * @param ctx the parse tree
	 */
	void enterDefine(PrefixExpressionParser.DefineContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#define}.
	 * @param ctx the parse tree
	 */
	void exitDefine(PrefixExpressionParser.DefineContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#right_variable}.
	 * @param ctx the parse tree
	 */
	void enterRight_variable(PrefixExpressionParser.Right_variableContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#right_variable}.
	 * @param ctx the parse tree
	 */
	void exitRight_variable(PrefixExpressionParser.Right_variableContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#logic}.
	 * @param ctx the parse tree
	 */
	void enterLogic(PrefixExpressionParser.LogicContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#logic}.
	 * @param ctx the parse tree
	 */
	void exitLogic(PrefixExpressionParser.LogicContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#compare_operation}.
	 * @param ctx the parse tree
	 */
	void enterCompare_operation(PrefixExpressionParser.Compare_operationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#compare_operation}.
	 * @param ctx the parse tree
	 */
	void exitCompare_operation(PrefixExpressionParser.Compare_operationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#logic_operation}.
	 * @param ctx the parse tree
	 */
	void enterLogic_operation(PrefixExpressionParser.Logic_operationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#logic_operation}.
	 * @param ctx the parse tree
	 */
	void exitLogic_operation(PrefixExpressionParser.Logic_operationContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic(PrefixExpressionParser.ArithmeticContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#arithmetic}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic(PrefixExpressionParser.ArithmeticContext ctx);
	/**
	 * Enter a parse tree produced by {@link PrefixExpressionParser#arithmetic_operation}.
	 * @param ctx the parse tree
	 */
	void enterArithmetic_operation(PrefixExpressionParser.Arithmetic_operationContext ctx);
	/**
	 * Exit a parse tree produced by {@link PrefixExpressionParser#arithmetic_operation}.
	 * @param ctx the parse tree
	 */
	void exitArithmetic_operation(PrefixExpressionParser.Arithmetic_operationContext ctx);
}