// Generated from /home/friendlyevil/doc/mt/lab4/src/main/java/antlr/Grammer.g4 by ANTLR 4.7.2
package antlr;

import java.util.Arrays;
import utils.Pair;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammerParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammerVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammerParser#grammer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrammer(GrammerParser.GrammerContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammerParser#expressions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressions(GrammerParser.ExpressionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammerParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(GrammerParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammerParser#terminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal(GrammerParser.TerminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammerParser#non_terminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNon_terminal(GrammerParser.Non_terminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammerParser#return_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_type(GrammerParser.Return_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammerParser#next_token_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNext_token_list(GrammerParser.Next_token_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammerParser#token_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToken_list(GrammerParser.Token_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammerParser#token}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToken(GrammerParser.TokenContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammerParser#header}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHeader(GrammerParser.HeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammerParser#start}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart(GrammerParser.StartContext ctx);
}