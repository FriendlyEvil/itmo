// Generated from /home/friendlyevil/doc/mt/lab4/src/main/java/antlr/Grammer.g4 by ANTLR 4.7.2
package antlr;

import java.util.Arrays;
import utils.Pair;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammerParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, WHITESPACE=3, HEADER=4, RETURNS=5, OR=6, EQALS=7, END=8, 
		TERMINAL=9, NON_TERMINAL=10, JAVA_CODE=11, TYPE=12, REG_EXPR=13;
	public static final int
		RULE_grammer = 0, RULE_expressions = 1, RULE_expression = 2, RULE_terminal = 3, 
		RULE_non_terminal = 4, RULE_return_type = 5, RULE_next_token_list = 6, 
		RULE_token_list = 7, RULE_token = 8, RULE_header = 9, RULE_start = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"grammer", "expressions", "expression", "terminal", "non_terminal", "return_type", 
			"next_token_list", "token_list", "token", "header", "itemSetId"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'['", "']'", null, "'@header'", "'returns'", "'|'", "':'", "';'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "WHITESPACE", "HEADER", "RETURNS", "OR", "EQALS", "END", 
			"TERMINAL", "NON_TERMINAL", "JAVA_CODE", "TYPE", "REG_EXPR"
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
	public String getGrammarFileName() { return "Grammer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GrammerParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class GrammerContext extends ParserRuleContext {
		public ParserInput val;
		public HeaderContext h;
		public StartContext s;
		public ExpressionsContext e;
		public StartContext start() {
			return getRuleContext(StartContext.class,0);
		}
		public ExpressionsContext expressions() {
			return getRuleContext(ExpressionsContext.class,0);
		}
		public HeaderContext header() {
			return getRuleContext(HeaderContext.class,0);
		}
		public GrammerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterGrammer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitGrammer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitGrammer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrammerContext grammer() throws RecognitionException {
		GrammerContext _localctx = new GrammerContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_grammer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(23);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(22);
				((GrammerContext)_localctx).h = header();
				}
				break;
			}
			setState(25);
			((GrammerContext)_localctx).s = start();
			setState(26);
			((GrammerContext)_localctx).e = expressions();
			((GrammerContext)_localctx).val =  new ParserInput(((GrammerContext)_localctx).h.t, ((GrammerContext)_localctx).s.t, ((GrammerContext)_localctx).e.t);
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

	public static class ExpressionsContext extends ParserRuleContext {
		public List<Term> t;
		public ExpressionContext f;
		public ExpressionsContext s;
		public ExpressionContext ex;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpressionsContext expressions() {
			return getRuleContext(ExpressionsContext.class,0);
		}
		public ExpressionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterExpressions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitExpressions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitExpressions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionsContext expressions() throws RecognitionException {
		ExpressionsContext _localctx = new ExpressionsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_expressions);
		try {
			setState(36);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(29);
				((ExpressionsContext)_localctx).f = expression();
				setState(30);
				((ExpressionsContext)_localctx).s = expressions();
				((ExpressionsContext)_localctx).t =  ((ExpressionsContext)_localctx).s.t; _localctx.t.add(((ExpressionsContext)_localctx).f.t);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(33);
				((ExpressionsContext)_localctx).ex = expression();
				((ExpressionsContext)_localctx).t =  new ArrayList<>(Arrays.asList(((ExpressionsContext)_localctx).ex.t));
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
		public Term t;
		public Non_terminalContext ter;
		public TerminalContext non;
		public Non_terminalContext non_terminal() {
			return getRuleContext(Non_terminalContext.class,0);
		}
		public TerminalContext terminal() {
			return getRuleContext(TerminalContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_expression);
		try {
			setState(44);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NON_TERMINAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(38);
				((ExpressionContext)_localctx).ter = non_terminal();
				((ExpressionContext)_localctx).t =  ((ExpressionContext)_localctx).ter.t;
				}
				break;
			case TERMINAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				((ExpressionContext)_localctx).non = terminal();
				((ExpressionContext)_localctx).t =  ((ExpressionContext)_localctx).non.t;
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

	public static class TerminalContext extends ParserRuleContext {
		public Terminal t;
		public Token name;
		public Token reg;
		public TerminalNode EQALS() { return getToken(GrammerParser.EQALS, 0); }
		public TerminalNode END() { return getToken(GrammerParser.END, 0); }
		public TerminalNode TERMINAL() { return getToken(GrammerParser.TERMINAL, 0); }
		public TerminalNode REG_EXPR() { return getToken(GrammerParser.REG_EXPR, 0); }
		public TerminalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterTerminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitTerminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitTerminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TerminalContext terminal() throws RecognitionException {
		TerminalContext _localctx = new TerminalContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_terminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			((TerminalContext)_localctx).name = match(TERMINAL);
			setState(47);
			match(EQALS);
			setState(48);
			((TerminalContext)_localctx).reg = match(REG_EXPR);
			setState(49);
			match(END);
			((TerminalContext)_localctx).t =  new Terminal((((TerminalContext)_localctx).name!=null?((TerminalContext)_localctx).name.getText():null), (((TerminalContext)_localctx).reg!=null?((TerminalContext)_localctx).reg.getText():null));
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

	public static class Non_terminalContext extends ParserRuleContext {
		public NonTerminal t;
		public Token name;
		public Return_typeContext typ;
		public Token_listContext f_list;
		public Token code;
		public Next_token_listContext s_list;
		public TerminalNode EQALS() { return getToken(GrammerParser.EQALS, 0); }
		public TerminalNode END() { return getToken(GrammerParser.END, 0); }
		public TerminalNode NON_TERMINAL() { return getToken(GrammerParser.NON_TERMINAL, 0); }
		public Return_typeContext return_type() {
			return getRuleContext(Return_typeContext.class,0);
		}
		public Token_listContext token_list() {
			return getRuleContext(Token_listContext.class,0);
		}
		public TerminalNode JAVA_CODE() { return getToken(GrammerParser.JAVA_CODE, 0); }
		public Next_token_listContext next_token_list() {
			return getRuleContext(Next_token_listContext.class,0);
		}
		public Non_terminalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_non_terminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterNon_terminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitNon_terminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitNon_terminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Non_terminalContext non_terminal() throws RecognitionException {
		Non_terminalContext _localctx = new Non_terminalContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_non_terminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			((Non_terminalContext)_localctx).name = match(NON_TERMINAL);
			setState(53);
			((Non_terminalContext)_localctx).typ = return_type();
			setState(54);
			match(EQALS);
			setState(55);
			((Non_terminalContext)_localctx).f_list = token_list();
			setState(56);
			((Non_terminalContext)_localctx).code = match(JAVA_CODE);
			setState(57);
			((Non_terminalContext)_localctx).s_list = next_token_list();
			setState(58);
			match(END);
			((Non_terminalContext)_localctx).t =  new NonTerminal((((Non_terminalContext)_localctx).name!=null?((Non_terminalContext)_localctx).name.getText():null), ((Non_terminalContext)_localctx).typ.t, ((Non_terminalContext)_localctx).f_list.t, ((Non_terminalContext)_localctx).s_list.t, (((Non_terminalContext)_localctx).code!=null?((Non_terminalContext)_localctx).code.getText():null));
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

	public static class Return_typeContext extends ParserRuleContext {
		public ReturnType t;
		public Token type;
		public TerminalNode RETURNS() { return getToken(GrammerParser.RETURNS, 0); }
		public TerminalNode TYPE() { return getToken(GrammerParser.TYPE, 0); }
		public Return_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterReturn_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitReturn_type(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitReturn_type(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Return_typeContext return_type() throws RecognitionException {
		Return_typeContext _localctx = new Return_typeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_return_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(RETURNS);
			setState(62);
			match(T__0);
			setState(63);
			((Return_typeContext)_localctx).type = match(TYPE);
			setState(64);
			match(T__1);
			((Return_typeContext)_localctx).t =  new ReturnType((((Return_typeContext)_localctx).type!=null?((Return_typeContext)_localctx).type.getText():null));
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

	public static class Next_token_listContext extends ParserRuleContext {
		public List<Pair<List<String>, String>> t;
		public Token_listContext tok;
		public Token code;
		public Next_token_listContext next;
		public TerminalNode OR() { return getToken(GrammerParser.OR, 0); }
		public Token_listContext token_list() {
			return getRuleContext(Token_listContext.class,0);
		}
		public TerminalNode JAVA_CODE() { return getToken(GrammerParser.JAVA_CODE, 0); }
		public Next_token_listContext next_token_list() {
			return getRuleContext(Next_token_listContext.class,0);
		}
		public Next_token_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_next_token_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterNext_token_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitNext_token_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitNext_token_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Next_token_listContext next_token_list() throws RecognitionException {
		Next_token_listContext _localctx = new Next_token_listContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_next_token_list);
		try {
			setState(74);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OR:
				enterOuterAlt(_localctx, 1);
				{
				setState(67);
				match(OR);
				setState(68);
				((Next_token_listContext)_localctx).tok = token_list();
				setState(69);
				((Next_token_listContext)_localctx).code = match(JAVA_CODE);
				setState(70);
				((Next_token_listContext)_localctx).next = next_token_list();
				((Next_token_listContext)_localctx).t =  ((Next_token_listContext)_localctx).next.t; _localctx.t.add(new Pair<>(((Next_token_listContext)_localctx).tok.t, (((Next_token_listContext)_localctx).code!=null?((Next_token_listContext)_localctx).code.getText():null)));
				}
				break;
			case END:
				enterOuterAlt(_localctx, 2);
				{
				((Next_token_listContext)_localctx).t =  new ArrayList<>();
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

	public static class Token_listContext extends ParserRuleContext {
		public List<String> t;
		public TokenContext f;
		public Token_listContext s;
		public TokenContext tok;
		public TokenContext token() {
			return getRuleContext(TokenContext.class,0);
		}
		public Token_listContext token_list() {
			return getRuleContext(Token_listContext.class,0);
		}
		public Token_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_token_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterToken_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitToken_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitToken_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Token_listContext token_list() throws RecognitionException {
		Token_listContext _localctx = new Token_listContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_token_list);
		try {
			setState(83);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(76);
				((Token_listContext)_localctx).f = token();
				setState(77);
				((Token_listContext)_localctx).s = token_list();
				((Token_listContext)_localctx).t =  ((Token_listContext)_localctx).s.t; _localctx.t.add(((Token_listContext)_localctx).f.t);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(80);
				((Token_listContext)_localctx).tok = token();
				((Token_listContext)_localctx).t =  new ArrayList<>(Arrays.asList(((Token_listContext)_localctx).tok.t));
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

	public static class TokenContext extends ParserRuleContext {
		public String t;
		public Token ter;
		public Token non;
		public TerminalNode TERMINAL() { return getToken(GrammerParser.TERMINAL, 0); }
		public TerminalNode NON_TERMINAL() { return getToken(GrammerParser.NON_TERMINAL, 0); }
		public TokenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_token; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterToken(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitToken(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitToken(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TokenContext token() throws RecognitionException {
		TokenContext _localctx = new TokenContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_token);
		try {
			setState(89);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TERMINAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(85);
				((TokenContext)_localctx).ter = match(TERMINAL);
				((TokenContext)_localctx).t =  (((TokenContext)_localctx).ter!=null?((TokenContext)_localctx).ter.getText():null);
				}
				break;
			case NON_TERMINAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(87);
				((TokenContext)_localctx).non = match(NON_TERMINAL);
				((TokenContext)_localctx).t =  (((TokenContext)_localctx).non!=null?((TokenContext)_localctx).non.getText():null);
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

	public static class HeaderContext extends ParserRuleContext {
		public String t;
		public Token code;
		public TerminalNode HEADER() { return getToken(GrammerParser.HEADER, 0); }
		public TerminalNode JAVA_CODE() { return getToken(GrammerParser.JAVA_CODE, 0); }
		public HeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_header; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitHeader(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitHeader(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HeaderContext header() throws RecognitionException {
		HeaderContext _localctx = new HeaderContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_header);
		try {
			setState(95);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case HEADER:
				enterOuterAlt(_localctx, 1);
				{
				setState(91);
				match(HEADER);
				setState(92);
				((HeaderContext)_localctx).code = match(JAVA_CODE);
				((HeaderContext)_localctx).t =  (((HeaderContext)_localctx).code!=null?((HeaderContext)_localctx).code.getText():null);
				}
				break;
			case NON_TERMINAL:
				enterOuterAlt(_localctx, 2);
				{
				((HeaderContext)_localctx).t =  "";
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

	public static class StartContext extends ParserRuleContext {
		public NonTerminal t;
		public Non_terminalContext f;
		public Non_terminalContext non_terminal() {
			return getRuleContext(Non_terminalContext.class,0);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammerListener ) ((GrammerListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammerVisitor ) return ((GrammerVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			((StartContext)_localctx).f = non_terminal();
			((StartContext)_localctx).t =  ((StartContext)_localctx).f.t;
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\17g\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\5\2\32\n\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3"+
		"\'\n\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4/\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\5\bM\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\tV\n\t\3\n\3\n\3\n"+
		"\3\n\5\n\\\n\n\3\13\3\13\3\13\3\13\5\13b\n\13\3\f\3\f\3\f\3\f\2\2\r\2"+
		"\4\6\b\n\f\16\20\22\24\26\2\2\2b\2\31\3\2\2\2\4&\3\2\2\2\6.\3\2\2\2\b"+
		"\60\3\2\2\2\n\66\3\2\2\2\f?\3\2\2\2\16L\3\2\2\2\20U\3\2\2\2\22[\3\2\2"+
		"\2\24a\3\2\2\2\26c\3\2\2\2\30\32\5\24\13\2\31\30\3\2\2\2\31\32\3\2\2\2"+
		"\32\33\3\2\2\2\33\34\5\26\f\2\34\35\5\4\3\2\35\36\b\2\1\2\36\3\3\2\2\2"+
		"\37 \5\6\4\2 !\5\4\3\2!\"\b\3\1\2\"\'\3\2\2\2#$\5\6\4\2$%\b\3\1\2%\'\3"+
		"\2\2\2&\37\3\2\2\2&#\3\2\2\2\'\5\3\2\2\2()\5\n\6\2)*\b\4\1\2*/\3\2\2\2"+
		"+,\5\b\5\2,-\b\4\1\2-/\3\2\2\2.(\3\2\2\2.+\3\2\2\2/\7\3\2\2\2\60\61\7"+
		"\13\2\2\61\62\7\t\2\2\62\63\7\17\2\2\63\64\7\n\2\2\64\65\b\5\1\2\65\t"+
		"\3\2\2\2\66\67\7\f\2\2\678\5\f\7\289\7\t\2\29:\5\20\t\2:;\7\r\2\2;<\5"+
		"\16\b\2<=\7\n\2\2=>\b\6\1\2>\13\3\2\2\2?@\7\7\2\2@A\7\3\2\2AB\7\16\2\2"+
		"BC\7\4\2\2CD\b\7\1\2D\r\3\2\2\2EF\7\b\2\2FG\5\20\t\2GH\7\r\2\2HI\5\16"+
		"\b\2IJ\b\b\1\2JM\3\2\2\2KM\b\b\1\2LE\3\2\2\2LK\3\2\2\2M\17\3\2\2\2NO\5"+
		"\22\n\2OP\5\20\t\2PQ\b\t\1\2QV\3\2\2\2RS\5\22\n\2ST\b\t\1\2TV\3\2\2\2"+
		"UN\3\2\2\2UR\3\2\2\2V\21\3\2\2\2WX\7\13\2\2X\\\b\n\1\2YZ\7\f\2\2Z\\\b"+
		"\n\1\2[W\3\2\2\2[Y\3\2\2\2\\\23\3\2\2\2]^\7\6\2\2^_\7\r\2\2_b\b\13\1\2"+
		"`b\b\13\1\2a]\3\2\2\2a`\3\2\2\2b\25\3\2\2\2cd\5\n\6\2de\b\f\1\2e\27\3"+
		"\2\2\2\t\31&.LU[a";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}