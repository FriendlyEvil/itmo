// Generated from /home/friendlyevil/IdeaProjects/MT_3Lab/src/main/java/antlr/PrefixExpression.g4 by ANTLR 4.7.2
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
		RIGHT=15, AND=16, OR=17, NOT=18, TRUE=19, FALSE=20, IF=21, PRINT=22, VARIABLE=23;
	public static final int
		RULE_expr = 0, RULE_s_expression = 1, RULE_big_expression = 2, RULE_expression = 3, 
		RULE_if_rule = 4, RULE_else_rule = 5, RULE_print = 6, RULE_define = 7, 
		RULE_right_variable = 8, RULE_arithmetic = 9, RULE_arithmetic_operation = 10, 
		RULE_logic_operation = 11, RULE_logic = 12, RULE_compare_operation = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"expr", "s_expression", "big_expression", "expression", "if_rule", "else_rule", 
			"print", "define", "right_variable", "arithmetic", "arithmetic_operation", 
			"logic_operation", "logic", "compare_operation"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'+'", "'-'", "'*'", "'/'", "'='", "'=='", "'!='", 
			"'<'", "'<='", "'>'", "'>='", "'{'", "'}'", "'&&'", "'||'", "'!'", "'true'", 
			"'false'", "'if'", "'print'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WHITESPACE", "NUMER", "PLUS", "MINUS", "MUL", "DIV", "DEF", "EQUALS", 
			"NOT_EQUAL", "LOWER", "LOWER_EQUAL", "HIGHER", "HIGHER_EQUAL", "LEFT", 
			"RIGHT", "AND", "OR", "NOT", "TRUE", "FALSE", "IF", "PRINT", "VARIABLE"
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
		public S_expressionContext ex;
		public S_expressionContext s_expression() {
			return getRuleContext(S_expressionContext.class,0);
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
			setState(28);
			((ExprContext)_localctx).ex = s_expression(0);
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

	public static class S_expressionContext extends ParserRuleContext {
		public int tab;
		public String val;
		public ExpressionContext expression;
		public Big_expressionContext f;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode LEFT() { return getToken(PrefixExpressionParser.LEFT, 0); }
		public TerminalNode RIGHT() { return getToken(PrefixExpressionParser.RIGHT, 0); }
		public Big_expressionContext big_expression() {
			return getRuleContext(Big_expressionContext.class,0);
		}
		public S_expressionContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public S_expressionContext(ParserRuleContext parent, int invokingState, int tab) {
			super(parent, invokingState);
			this.tab = tab;
		}
		@Override public int getRuleIndex() { return RULE_s_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).enterS_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PrefixExpressionListener ) ((PrefixExpressionListener)listener).exitS_expression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PrefixExpressionVisitor ) return ((PrefixExpressionVisitor<? extends T>)visitor).visitS_expression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final S_expressionContext s_expression(int tab) throws RecognitionException {
		S_expressionContext _localctx = new S_expressionContext(_ctx, getState(), tab);
		enterRule(_localctx, 2, RULE_s_expression);
		try {
			setState(39);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DEF:
			case IF:
			case PRINT:
				enterOuterAlt(_localctx, 1);
				{
				setState(31);
				((S_expressionContext)_localctx).expression = expression(tab);
				((S_expressionContext)_localctx).val =  ((S_expressionContext)_localctx).expression.val;
				}
				break;
			case LEFT:
				enterOuterAlt(_localctx, 2);
				{
				setState(34);
				match(LEFT);
				setState(35);
				((S_expressionContext)_localctx).f = big_expression(tab);
				setState(36);
				match(RIGHT);
				((S_expressionContext)_localctx).val =  ((S_expressionContext)_localctx).f.val;
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
		public ExpressionContext expression;
		public ExpressionContext f;
		public Big_expressionContext s;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
			setState(48);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(41);
				((Big_expressionContext)_localctx).expression = expression(tab);
				((Big_expressionContext)_localctx).val =  ((Big_expressionContext)_localctx).expression.val;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				((Big_expressionContext)_localctx).f = expression(tab);
				setState(45);
				((Big_expressionContext)_localctx).s = big_expression(tab);
				((Big_expressionContext)_localctx).val =  ((Big_expressionContext)_localctx).f.val + ((Big_expressionContext)_localctx).s.val;
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

	public static class ExpressionContext extends ParserRuleContext {
		public int tab;
		public String val;
		public If_ruleContext ex;
		public PrintContext e;
		public DefineContext exx;
		public If_ruleContext if_rule() {
			return getRuleContext(If_ruleContext.class,0);
		}
		public PrintContext print() {
			return getRuleContext(PrintContext.class,0);
		}
		public DefineContext define() {
			return getRuleContext(DefineContext.class,0);
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
		enterRule(_localctx, 6, RULE_expression);
		try {
			setState(59);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IF:
				enterOuterAlt(_localctx, 1);
				{
				setState(50);
				((ExpressionContext)_localctx).ex = if_rule(tab);
				((ExpressionContext)_localctx).val =  ((ExpressionContext)_localctx).ex.val + "\n";
				}
				break;
			case PRINT:
				enterOuterAlt(_localctx, 2);
				{
				setState(53);
				((ExpressionContext)_localctx).e = print(tab);
				((ExpressionContext)_localctx).val =  ((ExpressionContext)_localctx).e.val + "\n";
				}
				break;
			case DEF:
				enterOuterAlt(_localctx, 3);
				{
				setState(56);
				((ExpressionContext)_localctx).exx = define(tab);
				((ExpressionContext)_localctx).val =  ((ExpressionContext)_localctx).exx.val + "\n";
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

	public static class If_ruleContext extends ParserRuleContext {
		public int tab;
		public String val;
		public LogicContext c;
		public S_expressionContext ex;
		public Else_ruleContext el;
		public TerminalNode IF() { return getToken(PrefixExpressionParser.IF, 0); }
		public LogicContext logic() {
			return getRuleContext(LogicContext.class,0);
		}
		public S_expressionContext s_expression() {
			return getRuleContext(S_expressionContext.class,0);
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
		enterRule(_localctx, 8, RULE_if_rule);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(IF);
			setState(62);
			((If_ruleContext)_localctx).c = logic();
			setState(63);
			((If_ruleContext)_localctx).ex = s_expression(tab+1);
			setState(64);
			((If_ruleContext)_localctx).el = else_rule(tab);
			((If_ruleContext)_localctx).val =  String.format("%sif %s:\n%s%s", "    ".repeat(_localctx.tab), ((If_ruleContext)_localctx).c.val, ((If_ruleContext)_localctx).ex.val, ((If_ruleContext)_localctx).el.val);
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
		public S_expressionContext ex;
		public S_expressionContext s_expression() {
			return getRuleContext(S_expressionContext.class,0);
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
		enterRule(_localctx, 10, RULE_else_rule);
		try {
			setState(71);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(67);
				((Else_ruleContext)_localctx).ex = s_expression(tab+1);
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
		enterRule(_localctx, 12, RULE_print);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(PRINT);
			setState(74);
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
		enterRule(_localctx, 14, RULE_define);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(77);
			match(DEF);
			setState(78);
			((DefineContext)_localctx).var = match(VARIABLE);
			setState(79);
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
		public ArithmeticContext v;
		public LogicContext v1;
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
		enterRule(_localctx, 16, RULE_right_variable);
		try {
			setState(90);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(82);
				((Right_variableContext)_localctx).v = arithmetic();
				((Right_variableContext)_localctx).val =  ((Right_variableContext)_localctx).v.val;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				((Right_variableContext)_localctx).v1 = logic();
				((Right_variableContext)_localctx).val =  ((Right_variableContext)_localctx).v1.val;
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(88);
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
		enterRule(_localctx, 18, RULE_arithmetic);
		try {
			setState(101);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
			case MUL:
			case DIV:
				enterOuterAlt(_localctx, 1);
				{
				setState(92);
				((ArithmeticContext)_localctx).op = arithmetic_operation();
				setState(93);
				((ArithmeticContext)_localctx).left = arithmetic();
				setState(94);
				((ArithmeticContext)_localctx).right = arithmetic();
				((ArithmeticContext)_localctx).val =  String.format("(%s %s %s)", ((ArithmeticContext)_localctx).left.val, ((ArithmeticContext)_localctx).op.op, ((ArithmeticContext)_localctx).right.val);
				}
				break;
			case NUMER:
				enterOuterAlt(_localctx, 2);
				{
				setState(97);
				((ArithmeticContext)_localctx).num = match(NUMER);
				((ArithmeticContext)_localctx).val =  (((ArithmeticContext)_localctx).num!=null?((ArithmeticContext)_localctx).num.getText():null);
				}
				break;
			case VARIABLE:
				enterOuterAlt(_localctx, 3);
				{
				setState(99);
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
		enterRule(_localctx, 20, RULE_arithmetic_operation);
		try {
			setState(111);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(103);
				match(PLUS);
				((Arithmetic_operationContext)_localctx).op =  "+";
				}
				break;
			case MINUS:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				match(MINUS);
				((Arithmetic_operationContext)_localctx).op =  "-";
				}
				break;
			case MUL:
				enterOuterAlt(_localctx, 3);
				{
				setState(107);
				match(MUL);
				((Arithmetic_operationContext)_localctx).op =  "*";
				}
				break;
			case DIV:
				enterOuterAlt(_localctx, 4);
				{
				setState(109);
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
		enterRule(_localctx, 22, RULE_logic_operation);
		try {
			setState(117);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case AND:
				enterOuterAlt(_localctx, 1);
				{
				setState(113);
				match(AND);
				((Logic_operationContext)_localctx).op =  "and";
				}
				break;
			case OR:
				enterOuterAlt(_localctx, 2);
				{
				setState(115);
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

	public static class LogicContext extends ParserRuleContext {
		public String val;
		public Compare_operationContext op;
		public ArithmeticContext left;
		public ArithmeticContext right;
		public LogicContext value;
		public Logic_operationContext op1;
		public LogicContext left1;
		public LogicContext right1;
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
		enterRule(_localctx, 24, RULE_logic);
		try {
			setState(137);
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
				setState(119);
				((LogicContext)_localctx).op = compare_operation();
				setState(120);
				((LogicContext)_localctx).left = arithmetic();
				setState(121);
				((LogicContext)_localctx).right = arithmetic();
				((LogicContext)_localctx).val =  String.format("(%s %s %s)", ((LogicContext)_localctx).left.val, ((LogicContext)_localctx).op.op, ((LogicContext)_localctx).right.val);
				}
				break;
			case NOT:
				enterOuterAlt(_localctx, 2);
				{
				setState(124);
				match(NOT);
				setState(125);
				((LogicContext)_localctx).value = logic();
				((LogicContext)_localctx).val =  String.format("not %s", ((LogicContext)_localctx).value.val);
				}
				break;
			case TRUE:
				enterOuterAlt(_localctx, 3);
				{
				setState(128);
				match(TRUE);
				((LogicContext)_localctx).val =  "True";
				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 4);
				{
				setState(130);
				match(FALSE);
				((LogicContext)_localctx).val =  "False";
				}
				break;
			case AND:
			case OR:
				enterOuterAlt(_localctx, 5);
				{
				setState(132);
				((LogicContext)_localctx).op1 = logic_operation();
				setState(133);
				((LogicContext)_localctx).left1 = logic();
				setState(134);
				((LogicContext)_localctx).right1 = logic();
				((LogicContext)_localctx).val =  String.format("(%s %s %s)", ((LogicContext)_localctx).left1.val, ((LogicContext)_localctx).op1.op, ((LogicContext)_localctx).right1.val);
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
		enterRule(_localctx, 26, RULE_compare_operation);
		try {
			setState(151);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EQUALS:
				enterOuterAlt(_localctx, 1);
				{
				setState(139);
				match(EQUALS);
				((Compare_operationContext)_localctx).op =  "==";
				}
				break;
			case NOT_EQUAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(141);
				match(NOT_EQUAL);
				((Compare_operationContext)_localctx).op =  "!=";
				}
				break;
			case LOWER:
				enterOuterAlt(_localctx, 3);
				{
				setState(143);
				match(LOWER);
				((Compare_operationContext)_localctx).op =  "<";
				}
				break;
			case LOWER_EQUAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(145);
				match(LOWER_EQUAL);
				((Compare_operationContext)_localctx).op =  "<=";
				}
				break;
			case HIGHER:
				enterOuterAlt(_localctx, 5);
				{
				setState(147);
				match(HIGHER);
				((Compare_operationContext)_localctx).op =  ">";
				}
				break;
			case HIGHER_EQUAL:
				enterOuterAlt(_localctx, 6);
				{
				setState(149);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\31\u009c\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\5\3*\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\63\n\4\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5>\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3"+
		"\7\3\7\3\7\5\7J\n\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\n\5\n]\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\5\13h\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\fr\n\f\3\r\3\r\3\r\3"+
		"\r\5\rx\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u008c\n\16\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u009a\n\17\3\17\2\2\20\2\4\6"+
		"\b\n\f\16\20\22\24\26\30\32\34\2\2\2\u00a3\2\36\3\2\2\2\4)\3\2\2\2\6\62"+
		"\3\2\2\2\b=\3\2\2\2\n?\3\2\2\2\fI\3\2\2\2\16K\3\2\2\2\20O\3\2\2\2\22\\"+
		"\3\2\2\2\24g\3\2\2\2\26q\3\2\2\2\30w\3\2\2\2\32\u008b\3\2\2\2\34\u0099"+
		"\3\2\2\2\36\37\5\4\3\2\37 \b\2\1\2 \3\3\2\2\2!\"\5\b\5\2\"#\b\3\1\2#*"+
		"\3\2\2\2$%\7\20\2\2%&\5\6\4\2&\'\7\21\2\2\'(\b\3\1\2(*\3\2\2\2)!\3\2\2"+
		"\2)$\3\2\2\2*\5\3\2\2\2+,\5\b\5\2,-\b\4\1\2-\63\3\2\2\2./\5\b\5\2/\60"+
		"\5\6\4\2\60\61\b\4\1\2\61\63\3\2\2\2\62+\3\2\2\2\62.\3\2\2\2\63\7\3\2"+
		"\2\2\64\65\5\n\6\2\65\66\b\5\1\2\66>\3\2\2\2\678\5\16\b\289\b\5\1\29>"+
		"\3\2\2\2:;\5\20\t\2;<\b\5\1\2<>\3\2\2\2=\64\3\2\2\2=\67\3\2\2\2=:\3\2"+
		"\2\2>\t\3\2\2\2?@\7\27\2\2@A\5\32\16\2AB\5\4\3\2BC\5\f\7\2CD\b\6\1\2D"+
		"\13\3\2\2\2EF\5\4\3\2FG\b\7\1\2GJ\3\2\2\2HJ\b\7\1\2IE\3\2\2\2IH\3\2\2"+
		"\2J\r\3\2\2\2KL\7\30\2\2LM\5\22\n\2MN\b\b\1\2N\17\3\2\2\2OP\7\t\2\2PQ"+
		"\7\31\2\2QR\5\22\n\2RS\b\t\1\2S\21\3\2\2\2TU\5\24\13\2UV\b\n\1\2V]\3\2"+
		"\2\2WX\5\32\16\2XY\b\n\1\2Y]\3\2\2\2Z[\7\31\2\2[]\b\n\1\2\\T\3\2\2\2\\"+
		"W\3\2\2\2\\Z\3\2\2\2]\23\3\2\2\2^_\5\26\f\2_`\5\24\13\2`a\5\24\13\2ab"+
		"\b\13\1\2bh\3\2\2\2cd\7\4\2\2dh\b\13\1\2ef\7\31\2\2fh\b\13\1\2g^\3\2\2"+
		"\2gc\3\2\2\2ge\3\2\2\2h\25\3\2\2\2ij\7\5\2\2jr\b\f\1\2kl\7\6\2\2lr\b\f"+
		"\1\2mn\7\7\2\2nr\b\f\1\2op\7\b\2\2pr\b\f\1\2qi\3\2\2\2qk\3\2\2\2qm\3\2"+
		"\2\2qo\3\2\2\2r\27\3\2\2\2st\7\22\2\2tx\b\r\1\2uv\7\23\2\2vx\b\r\1\2w"+
		"s\3\2\2\2wu\3\2\2\2x\31\3\2\2\2yz\5\34\17\2z{\5\24\13\2{|\5\24\13\2|}"+
		"\b\16\1\2}\u008c\3\2\2\2~\177\7\24\2\2\177\u0080\5\32\16\2\u0080\u0081"+
		"\b\16\1\2\u0081\u008c\3\2\2\2\u0082\u0083\7\25\2\2\u0083\u008c\b\16\1"+
		"\2\u0084\u0085\7\26\2\2\u0085\u008c\b\16\1\2\u0086\u0087\5\30\r\2\u0087"+
		"\u0088\5\32\16\2\u0088\u0089\5\32\16\2\u0089\u008a\b\16\1\2\u008a\u008c"+
		"\3\2\2\2\u008by\3\2\2\2\u008b~\3\2\2\2\u008b\u0082\3\2\2\2\u008b\u0084"+
		"\3\2\2\2\u008b\u0086\3\2\2\2\u008c\33\3\2\2\2\u008d\u008e\7\n\2\2\u008e"+
		"\u009a\b\17\1\2\u008f\u0090\7\13\2\2\u0090\u009a\b\17\1\2\u0091\u0092"+
		"\7\f\2\2\u0092\u009a\b\17\1\2\u0093\u0094\7\r\2\2\u0094\u009a\b\17\1\2"+
		"\u0095\u0096\7\16\2\2\u0096\u009a\b\17\1\2\u0097\u0098\7\17\2\2\u0098"+
		"\u009a\b\17\1\2\u0099\u008d\3\2\2\2\u0099\u008f\3\2\2\2\u0099\u0091\3"+
		"\2\2\2\u0099\u0093\3\2\2\2\u0099\u0095\3\2\2\2\u0099\u0097\3\2\2\2\u009a"+
		"\35\3\2\2\2\f)\62=I\\gqw\u008b\u0099";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}