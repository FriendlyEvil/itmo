// Generated from /home/friendlyevil/doc/mt/lab4/src/main/java/antlr/Grammer.g4 by ANTLR 4.7.2
package antlr;

import java.util.Arrays;
import utils.Pair;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammerParser}.
 */
public interface GrammerListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammerParser#grammer}.
	 * @param ctx the parse tree
	 */
	void enterGrammer(GrammerParser.GrammerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#grammer}.
	 * @param ctx the parse tree
	 */
	void exitGrammer(GrammerParser.GrammerContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammerParser#expressions}.
	 * @param ctx the parse tree
	 */
	void enterExpressions(GrammerParser.ExpressionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#expressions}.
	 * @param ctx the parse tree
	 */
	void exitExpressions(GrammerParser.ExpressionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammerParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(GrammerParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(GrammerParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammerParser#terminal}.
	 * @param ctx the parse tree
	 */
	void enterTerminal(GrammerParser.TerminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#terminal}.
	 * @param ctx the parse tree
	 */
	void exitTerminal(GrammerParser.TerminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammerParser#non_terminal}.
	 * @param ctx the parse tree
	 */
	void enterNon_terminal(GrammerParser.Non_terminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#non_terminal}.
	 * @param ctx the parse tree
	 */
	void exitNon_terminal(GrammerParser.Non_terminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammerParser#return_type}.
	 * @param ctx the parse tree
	 */
	void enterReturn_type(GrammerParser.Return_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#return_type}.
	 * @param ctx the parse tree
	 */
	void exitReturn_type(GrammerParser.Return_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammerParser#next_token_list}.
	 * @param ctx the parse tree
	 */
	void enterNext_token_list(GrammerParser.Next_token_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#next_token_list}.
	 * @param ctx the parse tree
	 */
	void exitNext_token_list(GrammerParser.Next_token_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammerParser#token_list}.
	 * @param ctx the parse tree
	 */
	void enterToken_list(GrammerParser.Token_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#token_list}.
	 * @param ctx the parse tree
	 */
	void exitToken_list(GrammerParser.Token_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammerParser#token}.
	 * @param ctx the parse tree
	 */
	void enterToken(GrammerParser.TokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#token}.
	 * @param ctx the parse tree
	 */
	void exitToken(GrammerParser.TokenContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammerParser#header}.
	 * @param ctx the parse tree
	 */
	void enterHeader(GrammerParser.HeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#header}.
	 * @param ctx the parse tree
	 */
	void exitHeader(GrammerParser.HeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammerParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(GrammerParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammerParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(GrammerParser.StartContext ctx);
}