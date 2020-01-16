// Generated from /home/friendlyevil/doc/mt/lab4/src/main/java/antlr/Grammer.g4 by ANTLR 4.7.2
package antlr;

import java.util.Arrays;
import utils.Pair;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammerLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, WHITESPACE=3, HEADER=4, RETURNS=5, OR=6, EQALS=7, END=8, 
		TERMINAL=9, NON_TERMINAL=10, JAVA_CODE=11, TYPE=12, REG_EXPR=13;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "WHITESPACE", "HEADER", "RETURNS", "OR", "EQALS", "END", 
			"TERMINAL", "NON_TERMINAL", "JAVA_CODE", "TYPE", "REG_EXPR"
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


	public GrammerLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\17j\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\3\3\3\3\4\6\4#\n\4\r\4\16\4$\3"+
		"\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\7\3\7\3\b\3\b\3\t\3\t\3\n\6\n@\n\n\r\n\16\nA\3\13\6\13E\n\13\r\13\16"+
		"\13F\3\f\3\f\6\fK\n\f\r\f\16\fL\3\f\5\fP\n\f\7\fR\n\f\f\f\16\fU\13\f\3"+
		"\f\3\f\3\r\3\r\7\r[\n\r\f\r\16\r^\13\r\3\16\3\16\3\16\3\16\7\16d\n\16"+
		"\f\16\16\16g\13\16\3\16\3\16\2\2\17\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n"+
		"\23\13\25\f\27\r\31\16\33\17\3\2\t\5\2\13\f\17\17\"\"\5\2\62;C\\aa\5\2"+
		"\62;aac|\5\2<<}}\177\177\3\2C\\\7\2\62;>>@@C\\c|\4\2$$^^\2r\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3"+
		"\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\3\35\3\2\2\2\5\37\3\2\2\2\7\"\3\2\2\2\t(\3\2\2\2\13\60"+
		"\3\2\2\2\r8\3\2\2\2\17:\3\2\2\2\21<\3\2\2\2\23?\3\2\2\2\25D\3\2\2\2\27"+
		"H\3\2\2\2\31X\3\2\2\2\33_\3\2\2\2\35\36\7]\2\2\36\4\3\2\2\2\37 \7_\2\2"+
		" \6\3\2\2\2!#\t\2\2\2\"!\3\2\2\2#$\3\2\2\2$\"\3\2\2\2$%\3\2\2\2%&\3\2"+
		"\2\2&\'\b\4\2\2\'\b\3\2\2\2()\7B\2\2)*\7j\2\2*+\7g\2\2+,\7c\2\2,-\7f\2"+
		"\2-.\7g\2\2./\7t\2\2/\n\3\2\2\2\60\61\7t\2\2\61\62\7g\2\2\62\63\7v\2\2"+
		"\63\64\7w\2\2\64\65\7t\2\2\65\66\7p\2\2\66\67\7u\2\2\67\f\3\2\2\289\7"+
		"~\2\29\16\3\2\2\2:;\7<\2\2;\20\3\2\2\2<=\7=\2\2=\22\3\2\2\2>@\t\3\2\2"+
		"?>\3\2\2\2@A\3\2\2\2A?\3\2\2\2AB\3\2\2\2B\24\3\2\2\2CE\t\4\2\2DC\3\2\2"+
		"\2EF\3\2\2\2FD\3\2\2\2FG\3\2\2\2G\26\3\2\2\2HS\7}\2\2IK\n\5\2\2JI\3\2"+
		"\2\2KL\3\2\2\2LJ\3\2\2\2LM\3\2\2\2MO\3\2\2\2NP\5\27\f\2ON\3\2\2\2OP\3"+
		"\2\2\2PR\3\2\2\2QJ\3\2\2\2RU\3\2\2\2SQ\3\2\2\2ST\3\2\2\2TV\3\2\2\2US\3"+
		"\2\2\2VW\7\177\2\2W\30\3\2\2\2X\\\t\6\2\2Y[\t\7\2\2ZY\3\2\2\2[^\3\2\2"+
		"\2\\Z\3\2\2\2\\]\3\2\2\2]\32\3\2\2\2^\\\3\2\2\2_e\7$\2\2`d\n\b\2\2ab\7"+
		"^\2\2bd\t\b\2\2c`\3\2\2\2ca\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2fh\3"+
		"\2\2\2ge\3\2\2\2hi\7$\2\2i\34\3\2\2\2\f\2$AFLOS\\ce\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}