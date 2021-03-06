// Generated from /home/friendlyevil/IdeaProjects/MT_3Lab/lab3/src/main/java/antlr/PrefixExpression.g4 by ANTLR 4.7.2
package antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PrefixExpressionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, NUMER=2, PLUS=3, MINUS=4, MUL=5, DIV=6, DEF=7, EQUALS=8, 
		NOT_EQUAL=9, LOWER=10, LOWER_EQUAL=11, HIGHER=12, HIGHER_EQUAL=13, LEFT=14, 
		RIGHT=15, AND=16, OR=17, NOT=18, TRUE=19, FALSE=20, IF=21, WHILE=22, DO=23, 
		PRINT=24, VARIABLE=25;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WHITESPACE", "LETTER", "DIGIT", "NUMER", "PLUS", "MINUS", "MUL", "DIV", 
			"DEF", "EQUALS", "NOT_EQUAL", "LOWER", "LOWER_EQUAL", "HIGHER", "HIGHER_EQUAL", 
			"LEFT", "RIGHT", "AND", "OR", "NOT", "TRUE", "FALSE", "IF", "WHILE", 
			"DO", "PRINT", "VARIABLE"
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


	public PrefixExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PrefixExpression.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\33\u009d\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\3\2\6\2;\n\2\r\2\16\2<\3\2\3\2\3\3"+
		"\3\3\3\4\3\4\3\5\5\5F\n\5\3\5\3\5\7\5J\n\5\f\5\16\5M\13\5\3\5\5\5P\n\5"+
		"\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3"+
		"\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\23"+
		"\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\32"+
		"\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\7\34\u0099"+
		"\n\34\f\34\16\34\u009c\13\34\2\2\35\3\3\5\2\7\2\t\4\13\5\r\6\17\7\21\b"+
		"\23\t\25\n\27\13\31\f\33\r\35\16\37\17!\20#\21%\22\'\23)\24+\25-\26/\27"+
		"\61\30\63\31\65\32\67\33\3\2\7\5\2\13\f\17\17\"\"\4\2C\\c|\3\2\62;\3\2"+
		"\63;\3\2))\2\u00a1\2\3\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2"+
		"\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3"+
		"\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2"+
		"%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61"+
		"\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\3:\3\2\2\2\5@\3\2\2\2"+
		"\7B\3\2\2\2\tO\3\2\2\2\13Q\3\2\2\2\rS\3\2\2\2\17U\3\2\2\2\21W\3\2\2\2"+
		"\23Y\3\2\2\2\25[\3\2\2\2\27^\3\2\2\2\31a\3\2\2\2\33c\3\2\2\2\35f\3\2\2"+
		"\2\37h\3\2\2\2!k\3\2\2\2#m\3\2\2\2%o\3\2\2\2\'r\3\2\2\2)u\3\2\2\2+w\3"+
		"\2\2\2-|\3\2\2\2/\u0082\3\2\2\2\61\u0085\3\2\2\2\63\u008b\3\2\2\2\65\u008e"+
		"\3\2\2\2\67\u0094\3\2\2\29;\t\2\2\2:9\3\2\2\2;<\3\2\2\2<:\3\2\2\2<=\3"+
		"\2\2\2=>\3\2\2\2>?\b\2\2\2?\4\3\2\2\2@A\t\3\2\2A\6\3\2\2\2BC\t\4\2\2C"+
		"\b\3\2\2\2DF\7/\2\2ED\3\2\2\2EF\3\2\2\2FG\3\2\2\2GK\t\5\2\2HJ\5\7\4\2"+
		"IH\3\2\2\2JM\3\2\2\2KI\3\2\2\2KL\3\2\2\2LP\3\2\2\2MK\3\2\2\2NP\7\62\2"+
		"\2OE\3\2\2\2ON\3\2\2\2P\n\3\2\2\2QR\7-\2\2R\f\3\2\2\2ST\7/\2\2T\16\3\2"+
		"\2\2UV\7,\2\2V\20\3\2\2\2WX\7\61\2\2X\22\3\2\2\2YZ\7?\2\2Z\24\3\2\2\2"+
		"[\\\7?\2\2\\]\7?\2\2]\26\3\2\2\2^_\7#\2\2_`\7?\2\2`\30\3\2\2\2ab\7>\2"+
		"\2b\32\3\2\2\2cd\7>\2\2de\7?\2\2e\34\3\2\2\2fg\7@\2\2g\36\3\2\2\2hi\7"+
		"@\2\2ij\7?\2\2j \3\2\2\2kl\7}\2\2l\"\3\2\2\2mn\7\177\2\2n$\3\2\2\2op\7"+
		"(\2\2pq\7(\2\2q&\3\2\2\2rs\7~\2\2st\7~\2\2t(\3\2\2\2uv\7#\2\2v*\3\2\2"+
		"\2wx\7v\2\2xy\7t\2\2yz\7w\2\2z{\7g\2\2{,\3\2\2\2|}\7h\2\2}~\7c\2\2~\177"+
		"\7n\2\2\177\u0080\7u\2\2\u0080\u0081\7g\2\2\u0081.\3\2\2\2\u0082\u0083"+
		"\7k\2\2\u0083\u0084\7h\2\2\u0084\60\3\2\2\2\u0085\u0086\7y\2\2\u0086\u0087"+
		"\7j\2\2\u0087\u0088\7k\2\2\u0088\u0089\7n\2\2\u0089\u008a\7g\2\2\u008a"+
		"\62\3\2\2\2\u008b\u008c\7f\2\2\u008c\u008d\7q\2\2\u008d\64\3\2\2\2\u008e"+
		"\u008f\7r\2\2\u008f\u0090\7t\2\2\u0090\u0091\7k\2\2\u0091\u0092\7p\2\2"+
		"\u0092\u0093\7v\2\2\u0093\66\3\2\2\2\u0094\u009a\5\5\3\2\u0095\u0099\5"+
		"\5\3\2\u0096\u0099\5\7\4\2\u0097\u0099\t\6\2\2\u0098\u0095\3\2\2\2\u0098"+
		"\u0096\3\2\2\2\u0098\u0097\3\2\2\2\u0099\u009c\3\2\2\2\u009a\u0098\3\2"+
		"\2\2\u009a\u009b\3\2\2\2\u009b8\3\2\2\2\u009c\u009a\3\2\2\2\t\2<EKO\u0098"+
		"\u009a\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}