// Generated from /home/friendlyevil/IdeaProjects/MT_3Lab/lab3/src/main/java/antlr/PrefixExpression.g4 by ANTLR 4.7.2
package antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PrefixExpressionParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, NUMER=2, PLUS=3, MINUS=4, MUL=5, DIV=6, DEF=7, EQUALS=8, 
		NOT_EQUAL=9, LOWER=10, LOWER_EQUAL=11, HIGHER=12, HIGHER_EQUAL=13, LEFT=14, 
		RIGHT=15, AND=16, OR=17, NOT=18, TRUE=19, FALSE=20, IF=21, WHILE=22, DO=23, 
		PRINT=24, VARIABLE=25;
	public static final int
		RULE_expr = 0, RULE_expression = 1, RULE_big_expression = 2, RULE_one_expression = 3, 
		RULE_do_whil = 4, RULE_whil = 5, RULE_if_rule = 6, RULE_else_rule = 7, 
		RULE_print = 8, RULE_define = 9, RULE_right_variable = 10, RULE_logic = 11, 
		RULE_compare_operation = 12, RULE_logic_operation = 13, RULE_arithmetic = 14, 
		RULE_arithmetic_operation = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"expr", "expression", "big_expression", "one_expression", "do_whil", 
			"whil", "if_rule", "else_rule", "print", "define", "right_variable", 
			"logic", "compare_operation", "logic_operation", "arithmetic", "arithmetic_operation"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'+'", "'-'", "'*'", "'/'", "'='", "'=='", "'!='", 
			"'<'", "'<='", "'>'", "'>='", "'{'", "'}'", "'&&'", "'||'", "'!'", "'true'", 
			"'false'", "'if'", "'while'", "'do'", "'print'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WHITESPACE", "NUMER", "PLUS", "MINUS", "MUL", "DIV", "DEF", "EQUALS", 
			"NOT_EQUAL", "LOWER", "LOWER_EQUAL", "HIGHER", "HIGHER_EQUAL", "LEFT", 
			"RIGHT", "AND", "OR", "NOT", "TRUE", "FALSE", "IF", "WHILE", "DO", "PRINT", 
			"VARIABLE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "PrefixExpression.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PrefixExpressionParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ExprContext extends ParserRuleContext {
		public String val;
		public ExpressionContext ex;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			((ExprContext)_localctx).ex = expression(0);
			((ExprContext)_localctx).val =  ((ExprContext)_localctx).ex.val;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public int tab;
		public String val;
		public One_expressionContext ex;
		public Big_expressionContext big;
		public One_expressionContext one_expression() {
			return getRuleContext(One_expressionContext.class,0);
		}
		public TerminalNode LEFT() { return getToken(PrefixExpressionParser.LEFT, 0); }
		public TerminalNode RIGHT() { return getToken(PrefixExpressionParser.RIGHT, 0); }
		public Big_expressionContext big_expression() {
			return getRuleContext(Big_expressionContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExpressionContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression(int tab) throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState(), tab);
		enterRule(_localctx, 2, RULE_expression);
		try {
			setState(43);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DEF:
			case IF:
			case WHILE:
			case DO:
			case PRINT:
				enterOuterAlt(_localctx, 1);
				{
				setState(35);
				((ExpressionContext)_localctx).ex = one_expression(tab);
				((ExpressionContext)_localctx).val =  ((ExpressionContext)_localctx).ex.val;
				}
				break;
			case LEFT:
				enterOuterAlt(_localctx, 2);
				{
				setState(38);
				match(LEFT);
				setState(39);
				((ExpressionContext)_localctx).big = big_expression(tab);
				setState(40);
				match(RIGHT);
				((ExpressionContext)_localctx).val =  ((ExpressionContext)_localctx).big.val;
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Big_expressionContext extends ParserRuleContext {
		public int tab;
		public String val;
		public One_expressionContext ex;
		public Big_expressionContext big;
		public One_expressionContext one_expression() {
			return getRuleContext(One_expressionContext.class,0);
		}
		public Big_expressionContext big_expression() {
			return getRuleContext(Big_expressionContext.class,0);
		}
		public Big_expressionContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Big_expressionContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_big_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterBig_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitBig_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitBig_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Big_expressionContext big_expression(int tab) throws RecognitionException {
		Big_expressionContext _localctx = new Big_expressionContext(_ctx, getState(), tab);
		enterRule(_localctx, 4, RULE_big_expression);
		try {
			setState(52);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(45);
				((Big_expressionContext)_localctx).ex = one_expression(tab);
				((Big_expressionContext)_localctx).val =  ((Big_expressionContext)_localctx).ex.val;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(48);
				((Big_expressionContext)_localctx).ex = one_expression(tab);
				setState(49);
				((Big_expressionContext)_localctx).big = big_expression(tab);
				((Big_expressionContext)_localctx).val =  ((Big_expressionContext)_localctx).ex.val + ((Big_expressionContext)_localctx).big.val;
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class One_expressionContext extends ParserRuleContext {
		public int tab;
		public String val;
		public If_ruleContext ex;
		public PrintContext pr;
		public DefineContext def;
		public WhilContext wh;
		public Do_whilContext d;
		public If_ruleContext if_rule() {
			return getRuleContext(If_ruleContext.class,0);
		}
		public PrintContext print() {
			return getRuleContext(PrintContext.class,0);
		}
		public DefineContext define() {
			return getRuleContext(DefineContext.class,0);
		}
		public WhilContext whil() {
			return getRuleContext(WhilContext.class,0);
		}
		public Do_whilContext do_whil() {
			return getRuleContext(Do_whilContext.class,0);
		}
		public One_expressionContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public One_expressionContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_one_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterOne_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitOne_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitOne_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final One_expressionContext one_expression(int tab) throws RecognitionException {
		One_expressionContext _localctx = new One_expressionContext(_ctx, getState(), tab);
		enterRule(_localctx, 6, RULE_one_expression);
		try {
			setState(69);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IF:
				enterOuterAlt(_localctx, 1);
				{
				setState(54);
				((One_expressionContext)_localctx).ex = if_rule(tab);
				((One_expressionContext)_localctx).val =  ((One_expressionContext)_localctx).ex.val + "\n";
				}
				break;
			case PRINT:
				enterOuterAlt(_localctx, 2);
				{
				setState(57);
				((One_expressionContext)_localctx).pr = print(tab);
				((One_expressionContext)_localctx).val =  ((One_expressionContext)_localctx).pr.val + "\n";
				}
				break;
			case DEF:
				enterOuterAlt(_localctx, 3);
				{
				setState(60);
				((One_expressionContext)_localctx).def = define(tab);
				((One_expressionContext)_localctx).val =  ((One_expressionContext)_localctx).def.val + "\n";
				}
				break;
			case WHILE:
				enterOuterAlt(_localctx, 4);
				{
				setState(63);
				((One_expressionContext)_localctx).wh = whil(tab);
				((One_expressionContext)_localctx).val =  ((One_expressionContext)_localctx).wh.val;
				}
				break;
			case DO:
				enterOuterAlt(_localctx, 5);
				{
				setState(66);
				((One_expressionContext)_localctx).d = do_whil(tab);
				((One_expressionContext)_localctx).val =  ((One_expressionContext)_localctx).d.val + "\n";
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Do_whilContext extends ParserRuleContext {
		public int tab;
		public String val;
		public LogicContext l;
		public ExpressionContext ex;
		public TerminalNode DO() { return getToken(PrefixExpressionParser.DO, 0); }
		public LogicContext logic() {
			return getRuleContext(LogicContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Do_whilContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Do_whilContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_do_whil; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterDo_whil(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitDo_whil(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitDo_whil(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Do_whilContext do_whil(int tab) throws RecognitionException {
		Do_whilContext _localctx = new Do_whilContext(_ctx, getState(), tab);
		enterRule(_localctx, 8, RULE_do_whil);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(DO);
			setState(72);
			((Do_whilContext)_localctx).l = logic();
			setState(73);
			((Do_whilContext)_localctx).ex = expression(tab+1);
			((Do_whilContext)_localctx).val =  String.format("%sdo\n%swhile %s:", "    ".repeat(_localctx.tab), ((Do_whilContext)_localctx).ex.val, ((Do_whilContext)_localctx).l.val);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhilContext extends ParserRuleContext {
		public int tab;
		public String val;
		public LogicContext l;
		public ExpressionContext ex;
		public TerminalNode WHILE() { return getToken(PrefixExpressionParser.WHILE, 0); }
		public LogicContext logic() {
			return getRuleContext(LogicContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public WhilContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public WhilContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_whil; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterWhil(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitWhil(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitWhil(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhilContext whil(int tab) throws RecognitionException {
		WhilContext _localctx = new WhilContext(_ctx, getState(), tab);
		enterRule(_localctx, 10, RULE_whil);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(WHILE);
			setState(77);
			((WhilContext)_localctx).l = logic();
			setState(78);
			((WhilContext)_localctx).ex = expression(tab+1);
			((WhilContext)_localctx).val =  String.format("%swhile %s:\n%s", "    ".repeat(_localctx.tab), ((WhilContext)_localctx).l.val, ((WhilContext)_localctx).ex.val);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class If_ruleContext extends ParserRuleContext {
		public int tab;
		public String val;
		public LogicContext l;
		public ExpressionContext ex;
		public Else_ruleContext el;
		public TerminalNode IF() { return getToken(PrefixExpressionParser.IF, 0); }
		public LogicContext logic() {
			return getRuleContext(LogicContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Else_ruleContext else_rule() {
			return getRuleContext(Else_ruleContext.class,0);
		}
		public If_ruleContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public If_ruleContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_if_rule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterIf_rule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitIf_rule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitIf_rule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final If_ruleContext if_rule(int tab) throws RecognitionException {
		If_ruleContext _localctx = new If_ruleContext(_ctx, getState(), tab);
		enterRule(_localctx, 12, RULE_if_rule);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(IF);
			setState(82);
			((If_ruleContext)_localctx).l = logic();
			setState(83);
			((If_ruleContext)_localctx).ex = expression(tab+1);
			setState(84);
			((If_ruleContext)_localctx).el = else_rule(tab);
			((If_ruleContext)_localctx).val =  String.format("%sif %s:\n%s%s", "    ".repeat(_localctx.tab), ((If_ruleContext)_localctx).l.val, ((If_ruleContext)_localctx).ex.val, ((If_ruleContext)_localctx).el.val);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Else_ruleContext extends ParserRuleContext {
		public int tab;
		public String val;
		public ExpressionContext ex;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Else_ruleContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public Else_ruleContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_else_rule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterElse_rule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitElse_rule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitElse_rule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Else_ruleContext else_rule(int tab) throws RecognitionException {
		Else_ruleContext _localctx = new Else_ruleContext(_ctx, getState(), tab);
		enterRule(_localctx, 14, RULE_else_rule);
		try {
			setState(91);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(87);
				((Else_ruleContext)_localctx).ex = expression(tab+1);
				((Else_ruleContext)_localctx).val =  String.format("%selse:\n%s", "    ".repeat(_localctx.tab), ((Else_ruleContext)_localctx).ex.val);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				((Else_ruleContext)_localctx).val =  "";
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrintContext extends ParserRuleContext {
		public int tab;
		public String val;
		public Right_variableContext v;
		public TerminalNode PRINT() { return getToken(PrefixExpressionParser.PRINT, 0); }
		public Right_variableContext right_variable() {
			return getRuleContext(Right_variableContext.class,0);
		}
		public PrintContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public PrintContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_print; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterPrint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitPrint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitPrint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintContext print(int tab) throws RecognitionException {
		PrintContext _localctx = new PrintContext(_ctx, getState(), tab);
		enterRule(_localctx, 16, RULE_print);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(PRINT);
			setState(94);
			((PrintContext)_localctx).v = right_variable();
			((PrintContext)_localctx).val =  String.format("%sprint(%s)", "    ".repeat(_localctx.tab), ((PrintContext)_localctx).v.val);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DefineContext extends ParserRuleContext {
		public int tab;
		public String val;
		public Token var;
		public Right_variableContext v;
		public TerminalNode DEF() { return getToken(PrefixExpressionParser.DEF, 0); }
		public TerminalNode VARIABLE() { return getToken(PrefixExpressionParser.VARIABLE, 0); }
		public Right_variableContext right_variable() {
			return getRuleContext(Right_variableContext.class,0);
		}
		public DefineContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public DefineContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_define; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterDefine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitDefine(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitDefine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefineContext define(int tab) throws RecognitionException {
		DefineContext _localctx = new DefineContext(_ctx, getState(), tab);
		enterRule(_localctx, 18, RULE_define);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(DEF);
			setState(98);
			((DefineContext)_localctx).var = match(VARIABLE);
			setState(99);
			((DefineContext)_localctx).v = right_variable();
			((DefineContext)_localctx).val =  String.format("%s%s = %s", "    ".repeat(_localctx.tab), (((DefineContext)_localctx).var!=null?((DefineContext)_localctx).var.getText():null), ((DefineContext)_localctx).v.val);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Right_variableContext extends ParserRuleContext {
		public String val;
		public ArithmeticContext ar;
		public LogicContext l;
		public Token var;
		public ArithmeticContext arithmetic() {
			return getRuleContext(ArithmeticContext.class,0);
		}
		public LogicContext logic() {
			return getRuleContext(LogicContext.class,0);
		}
		public TerminalNode VARIABLE() { return getToken(PrefixExpressionParser.VARIABLE, 0); }
		public Right_variableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_right_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterRight_variable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitRight_variable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitRight_variable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Right_variableContext right_variable() throws RecognitionException {
		Right_variableContext _localctx = new Right_variableContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_right_variable);
		try {
			setState(110);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(102);
				((Right_variableContext)_localctx).ar = arithmetic();
				((Right_variableContext)_localctx).val =  ((Right_variableContext)_localctx).ar.val;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				((Right_variableContext)_localctx).l = logic();
				((Right_variableContext)_localctx).val =  ((Right_variableContext)_localctx).l.val;
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(108);
				((Right_variableContext)_localctx).var = match(VARIABLE);
				((Right_variableContext)_localctx).val =  (((Right_variableContext)_localctx).var!=null?((Right_variableContext)_localctx).var.getText():null);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicContext extends ParserRuleContext {
		public String val;
		public Compare_operationContext op;
		public ArithmeticContext left;
		public ArithmeticContext right;
		public LogicContext value;
		public Logic_operationContext op_l;
		public LogicContext left_l;
		public LogicContext right_l;
		public Compare_operationContext compare_operation() {
			return getRuleContext(Compare_operationContext.class,0);
		}
		public List<ArithmeticContext> arithmetic() {
			return getRuleContexts(ArithmeticContext.class);
		}
		public ArithmeticContext arithmetic(int i) {
			return getRuleContext(ArithmeticContext.class,i);
		}
		public TerminalNode NOT() { return getToken(PrefixExpressionParser.NOT, 0); }
		public List<LogicContext> logic() {
			return getRuleContexts(LogicContext.class);
		}
		public LogicContext logic(int i) {
			return getRuleContext(LogicContext.class,i);
		}
		public TerminalNode TRUE() { return getToken(PrefixExpressionParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(PrefixExpressionParser.FALSE, 0); }
		public Logic_operationContext logic_operation() {
			return getRuleContext(Logic_operationContext.class,0);
		}
		public LogicContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterLogic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitLogic(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitLogic(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicContext logic() throws RecognitionException {
		LogicContext _localctx = new LogicContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_logic);
		try {
			setState(130);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EQUALS:
			case NOT_EQUAL:
			case LOWER:
			case LOWER_EQUAL:
			case HIGHER:
			case HIGHER_EQUAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(112);
				((LogicContext)_localctx).op = compare_operation();
				setState(113);
				((LogicContext)_localctx).left = arithmetic();
				setState(114);
				((LogicContext)_localctx).right = arithmetic();
				((LogicContext)_localctx).val =  String.format("(%s %s %s)", ((LogicContext)_localctx).left.val, ((LogicContext)_localctx).op.op, ((LogicContext)_localctx).right.val);
				}
				break;
			case NOT:
				enterOuterAlt(_localctx, 2);
				{
				setState(117);
				match(NOT);
				setState(118);
				((LogicContext)_localctx).value = logic();
				((LogicContext)_localctx).val =  String.format("not %s", ((LogicContext)_localctx).value.val);
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 3);
				{
				setState(121);
				match(TRUE);
				((LogicContext)_localctx).val =  "True";
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 4);
				{
				setState(123);
				match(FALSE);
				((LogicContext)_localctx).val =  "False";
				}
				break;
			case AND:
			case OR:
				enterOuterAlt(_localctx, 5);
				{
				setState(125);
				((LogicContext)_localctx).op_l = logic_operation();
				setState(126);
				((LogicContext)_localctx).left_l = logic();
				setState(127);
				((LogicContext)_localctx).right_l = logic();
				((LogicContext)_localctx).val =  String.format("(%s %s %s)", ((LogicContext)_localctx).left_l.val, ((LogicContext)_localctx).op_l.op, ((LogicContext)_localctx).right_l.val);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Compare_operationContext extends ParserRuleContext {
		public String op;
		public TerminalNode EQUALS() { return getToken(PrefixExpressionParser.EQUALS, 0); }
		public TerminalNode NOT_EQUAL() { return getToken(PrefixExpressionParser.NOT_EQUAL, 0); }
		public TerminalNode LOWER() { return getToken(PrefixExpressionParser.LOWER, 0); }
		public TerminalNode LOWER_EQUAL() { return getToken(PrefixExpressionParser.LOWER_EQUAL, 0); }
		public TerminalNode HIGHER() { return getToken(PrefixExpressionParser.HIGHER, 0); }
		public TerminalNode HIGHER_EQUAL() { return getToken(PrefixExpressionParser.HIGHER_EQUAL, 0); }
		public Compare_operationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compare_operation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterCompare_operation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitCompare_operation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitCompare_operation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Compare_operationContext compare_operation() throws RecognitionException {
		Compare_operationContext _localctx = new Compare_operationContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_compare_operation);
		try {
			setState(144);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EQUALS:
				enterOuterAlt(_localctx, 1);
				{
				setState(132);
				match(EQUALS);
				((Compare_operationContext)_localctx).op =  "==";
				}
				break;
			case NOT_EQUAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(134);
				match(NOT_EQUAL);
				((Compare_operationContext)_localctx).op =  "!=";
				}
				break;
			case LOWER:
				enterOuterAlt(_localctx, 3);
				{
				setState(136);
				match(LOWER);
				((Compare_operationContext)_localctx).op =  "<";
				}
				break;
			case LOWER_EQUAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(138);
				match(LOWER_EQUAL);
				((Compare_operationContext)_localctx).op =  "<=";
				}
				break;
			case HIGHER:
				enterOuterAlt(_localctx, 5);
				{
				setState(140);
				match(HIGHER);
				((Compare_operationContext)_localctx).op =  ">";
				}
				break;
			case HIGHER_EQUAL:
				enterOuterAlt(_localctx, 6);
				{
				setState(142);
				match(HIGHER_EQUAL);
				((Compare_operationContext)_localctx).op =  ">=";
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Logic_operationContext extends ParserRuleContext {
		public String op;
		public TerminalNode AND() { return getToken(PrefixExpressionParser.AND, 0); }
		public TerminalNode OR() { return getToken(PrefixExpressionParser.OR, 0); }
		public Logic_operationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logic_operation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterLogic_operation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitLogic_operation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitLogic_operation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Logic_operationContext logic_operation() throws RecognitionException {
		Logic_operationContext _localctx = new Logic_operationContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_logic_operation);
		try {
			setState(150);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AND:
				enterOuterAlt(_localctx, 1);
				{
				setState(146);
				match(AND);
				((Logic_operationContext)_localctx).op =  "and";
				}
				break;
			case OR:
				enterOuterAlt(_localctx, 2);
				{
				setState(148);
				match(OR);
				((Logic_operationContext)_localctx).op =  "or";
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArithmeticContext extends ParserRuleContext {
		public String val;
		public Arithmetic_operationContext op;
		public ArithmeticContext left;
		public ArithmeticContext right;
		public Token num;
		public Token var;
		public Arithmetic_operationContext arithmetic_operation() {
			return getRuleContext(Arithmetic_operationContext.class,0);
		}
		public List<ArithmeticContext> arithmetic() {
			return getRuleContexts(ArithmeticContext.class);
		}
		public ArithmeticContext arithmetic(int i) {
			return getRuleContext(ArithmeticContext.class,i);
		}
		public TerminalNode NUMER() { return getToken(PrefixExpressionParser.NUMER, 0); }
		public TerminalNode VARIABLE() { return getToken(PrefixExpressionParser.VARIABLE, 0); }
		public ArithmeticContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmetic; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterArithmetic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitArithmetic(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitArithmetic(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArithmeticContext arithmetic() throws RecognitionException {
		ArithmeticContext _localctx = new ArithmeticContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_arithmetic);
		try {
			setState(161);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
			case MUL:
			case DIV:
				enterOuterAlt(_localctx, 1);
				{
				setState(152);
				((ArithmeticContext)_localctx).op = arithmetic_operation();
				setState(153);
				((ArithmeticContext)_localctx).left = arithmetic();
				setState(154);
				((ArithmeticContext)_localctx).right = arithmetic();
				((ArithmeticContext)_localctx).val =  String.format("(%s %s %s)", ((ArithmeticContext)_localctx).left.val, ((ArithmeticContext)_localctx).op.op, ((ArithmeticContext)_localctx).right.val);
				}
				break;
			case NUMER:
				enterOuterAlt(_localctx, 2);
				{
				setState(157);
				((ArithmeticContext)_localctx).num = match(NUMER);
				((ArithmeticContext)_localctx).val =  (((ArithmeticContext)_localctx).num!=null?((ArithmeticContext)_localctx).num.getText():null);
				}
				break;
			case VARIABLE:
				enterOuterAlt(_localctx, 3);
				{
				setState(159);
				((ArithmeticContext)_localctx).var = match(VARIABLE);
				((ArithmeticContext)_localctx).val =  (((ArithmeticContext)_localctx).var!=null?((ArithmeticContext)_localctx).var.getText():null);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Arithmetic_operationContext extends ParserRuleContext {
		public String op;
		public TerminalNode PLUS() { return getToken(PrefixExpressionParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(PrefixExpressionParser.MINUS, 0); }
		public TerminalNode MUL() { return getToken(PrefixExpressionParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(PrefixExpressionParser.DIV, 0); }
		public Arithmetic_operationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arithmetic_operation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterArithmetic_operation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitArithmetic_operation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitArithmetic_operation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Arithmetic_operationContext arithmetic_operation() throws RecognitionException {
		Arithmetic_operationContext _localctx = new Arithmetic_operationContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_arithmetic_operation);
		try {
			setState(171);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(163);
				match(PLUS);
				((Arithmetic_operationContext)_localctx).op =  "+";
				}
				break;
			case MINUS:
				enterOuterAlt(_localctx, 2);
				{
				setState(165);
				match(MINUS);
				((Arithmetic_operationContext)_localctx).op =  "-";
				}
				break;
			case MUL:
				enterOuterAlt(_localctx, 3);
				{
				setState(167);
				match(MUL);
				((Arithmetic_operationContext)_localctx).op =  "*";
				}
				break;
			case DIV:
				enterOuterAlt(_localctx, 4);
				{
				setState(169);
				match(DIV);
				((Arithmetic_operationContext)_localctx).op =  "/";
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\33\u00b0\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3.\n\3\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\5\4\67\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\5\5H\n\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\t\3\t\3\t\3\t\5\t^\n\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\fq\n\f\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0085\n\r\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u0093\n\16"+
		"\3\17\3\17\3\17\3\17\5\17\u0099\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\5\20\u00a4\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21"+
		"\u00ae\n\21\3\21\2\2\22\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \2\2\2"+
		"\u00b7\2\"\3\2\2\2\4-\3\2\2\2\6\66\3\2\2\2\bG\3\2\2\2\nI\3\2\2\2\fN\3"+
		"\2\2\2\16S\3\2\2\2\20]\3\2\2\2\22_\3\2\2\2\24c\3\2\2\2\26p\3\2\2\2\30"+
		"\u0084\3\2\2\2\32\u0092\3\2\2\2\34\u0098\3\2\2\2\36\u00a3\3\2\2\2 \u00ad"+
		"\3\2\2\2\"#\5\4\3\2#$\b\2\1\2$\3\3\2\2\2%&\5\b\5\2&\'\b\3\1\2\'.\3\2\2"+
		"\2()\7\20\2\2)*\5\6\4\2*+\7\21\2\2+,\b\3\1\2,.\3\2\2\2-%\3\2\2\2-(\3\2"+
		"\2\2.\5\3\2\2\2/\60\5\b\5\2\60\61\b\4\1\2\61\67\3\2\2\2\62\63\5\b\5\2"+
		"\63\64\5\6\4\2\64\65\b\4\1\2\65\67\3\2\2\2\66/\3\2\2\2\66\62\3\2\2\2\67"+
		"\7\3\2\2\289\5\16\b\29:\b\5\1\2:H\3\2\2\2;<\5\22\n\2<=\b\5\1\2=H\3\2\2"+
		"\2>?\5\24\13\2?@\b\5\1\2@H\3\2\2\2AB\5\f\7\2BC\b\5\1\2CH\3\2\2\2DE\5\n"+
		"\6\2EF\b\5\1\2FH\3\2\2\2G8\3\2\2\2G;\3\2\2\2G>\3\2\2\2GA\3\2\2\2GD\3\2"+
		"\2\2H\t\3\2\2\2IJ\7\31\2\2JK\5\30\r\2KL\5\4\3\2LM\b\6\1\2M\13\3\2\2\2"+
		"NO\7\30\2\2OP\5\30\r\2PQ\5\4\3\2QR\b\7\1\2R\r\3\2\2\2ST\7\27\2\2TU\5\30"+
		"\r\2UV\5\4\3\2VW\5\20\t\2WX\b\b\1\2X\17\3\2\2\2YZ\5\4\3\2Z[\b\t\1\2[^"+
		"\3\2\2\2\\^\b\t\1\2]Y\3\2\2\2]\\\3\2\2\2^\21\3\2\2\2_`\7\32\2\2`a\5\26"+
		"\f\2ab\b\n\1\2b\23\3\2\2\2cd\7\t\2\2de\7\33\2\2ef\5\26\f\2fg\b\13\1\2"+
		"g\25\3\2\2\2hi\5\36\20\2ij\b\f\1\2jq\3\2\2\2kl\5\30\r\2lm\b\f\1\2mq\3"+
		"\2\2\2no\7\33\2\2oq\b\f\1\2ph\3\2\2\2pk\3\2\2\2pn\3\2\2\2q\27\3\2\2\2"+
		"rs\5\32\16\2st\5\36\20\2tu\5\36\20\2uv\b\r\1\2v\u0085\3\2\2\2wx\7\24\2"+
		"\2xy\5\30\r\2yz\b\r\1\2z\u0085\3\2\2\2{|\7\25\2\2|\u0085\b\r\1\2}~\7\26"+
		"\2\2~\u0085\b\r\1\2\177\u0080\5\34\17\2\u0080\u0081\5\30\r\2\u0081\u0082"+
		"\5\30\r\2\u0082\u0083\b\r\1\2\u0083\u0085\3\2\2\2\u0084r\3\2\2\2\u0084"+
		"w\3\2\2\2\u0084{\3\2\2\2\u0084}\3\2\2\2\u0084\177\3\2\2\2\u0085\31\3\2"+
		"\2\2\u0086\u0087\7\n\2\2\u0087\u0093\b\16\1\2\u0088\u0089\7\13\2\2\u0089"+
		"\u0093\b\16\1\2\u008a\u008b\7\f\2\2\u008b\u0093\b\16\1\2\u008c\u008d\7"+
		"\r\2\2\u008d\u0093\b\16\1\2\u008e\u008f\7\16\2\2\u008f\u0093\b\16\1\2"+
		"\u0090\u0091\7\17\2\2\u0091\u0093\b\16\1\2\u0092\u0086\3\2\2\2\u0092\u0088"+
		"\3\2\2\2\u0092\u008a\3\2\2\2\u0092\u008c\3\2\2\2\u0092\u008e\3\2\2\2\u0092"+
		"\u0090\3\2\2\2\u0093\33\3\2\2\2\u0094\u0095\7\22\2\2\u0095\u0099\b\17"+
		"\1\2\u0096\u0097\7\23\2\2\u0097\u0099\b\17\1\2\u0098\u0094\3\2\2\2\u0098"+
		"\u0096\3\2\2\2\u0099\35\3\2\2\2\u009a\u009b\5 \21\2\u009b\u009c\5\36\20"+
		"\2\u009c\u009d\5\36\20\2\u009d\u009e\b\20\1\2\u009e\u00a4\3\2\2\2\u009f"+
		"\u00a0\7\4\2\2\u00a0\u00a4\b\20\1\2\u00a1\u00a2\7\33\2\2\u00a2\u00a4\b"+
		"\20\1\2\u00a3\u009a\3\2\2\2\u00a3\u009f\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a4"+
		"\37\3\2\2\2\u00a5\u00a6\7\5\2\2\u00a6\u00ae\b\21\1\2\u00a7\u00a8\7\6\2"+
		"\2\u00a8\u00ae\b\21\1\2\u00a9\u00aa\7\7\2\2\u00aa\u00ae\b\21\1\2\u00ab"+
		"\u00ac\7\b\2\2\u00ac\u00ae\b\21\1\2\u00ad\u00a5\3\2\2\2\u00ad\u00a7\3"+
		"\2\2\2\u00ad\u00a9\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae!\3\2\2\2\f-\66G]"+
		"p\u0084\u0092\u0098\u00a3\u00ad";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}