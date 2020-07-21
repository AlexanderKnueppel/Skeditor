// Generated from fol.g4 by ANTLR 4.7.1
package de.tubs.skeditor.contracting.grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class folLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, BOOL_LITERAL=2, VARIABLE=3, SCIENTIFIC_NUMBER=4, LPAREN=5, RPAREN=6, 
		PLUS=7, MINUS=8, TIMES=9, DIV=10, GT=11, LT=12, GEQ=13, LEQ=14, EQUAL=15, 
		NOT=16, FORALL=17, EXISTS=18, CHARACTER=19, CONJ=20, DISJ=21, IMPL=22, 
		BICOND=23, ENDLINE=24, WHITESPACE=25;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "BOOL_LITERAL", "VARIABLE", "VALID_ID_START", "VALID_ID_CHAR", 
		"SCIENTIFIC_NUMBER", "NUMBER", "UNSIGNED_INTEGER", "E", "SIGN", "LPAREN", 
		"RPAREN", "PLUS", "MINUS", "TIMES", "DIV", "GT", "LT", "GEQ", "LEQ", "EQUAL", 
		"NOT", "FORALL", "EXISTS", "CHARACTER", "CONJ", "DISJ", "IMPL", "BICOND", 
		"ENDLINE", "WHITESPACE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", null, null, null, "'('", "')'", "'+'", "'-'", "'*'", "'/'", 
		"'>'", "'<'", "'>='", "'<='", "'='", "'!'", "'Forall'", "'Exists'", null, 
		"'&'", "'|'", "'->'", "'<->'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, "BOOL_LITERAL", "VARIABLE", "SCIENTIFIC_NUMBER", "LPAREN", 
		"RPAREN", "PLUS", "MINUS", "TIMES", "DIV", "GT", "LT", "GEQ", "LEQ", "EQUAL", 
		"NOT", "FORALL", "EXISTS", "CHARACTER", "CONJ", "DISJ", "IMPL", "BICOND", 
		"ENDLINE", "WHITESPACE"
	};
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


	public folLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "fol.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\33\u00bc\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3M\n\3\3\4\3\4\7\4Q\n"+
		"\4\f\4\16\4T\13\4\3\5\5\5W\n\5\3\6\3\6\5\6[\n\6\3\7\3\7\3\7\5\7`\n\7\3"+
		"\7\3\7\5\7d\n\7\3\b\6\bg\n\b\r\b\16\bh\3\b\3\b\6\bm\n\b\r\b\16\bn\5\b"+
		"q\n\b\3\t\6\tt\n\t\r\t\16\tu\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3"+
		"\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3"+
		"\25\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3"+
		"\35\3\35\3\36\3\36\3\36\3\36\3\37\6\37\u00b2\n\37\r\37\16\37\u00b3\3 "+
		"\6 \u00b7\n \r \16 \u00b8\3 \3 \2\2!\3\3\5\4\7\5\t\2\13\2\r\6\17\2\21"+
		"\2\23\2\25\2\27\7\31\b\33\t\35\n\37\13!\f#\r%\16\'\17)\20+\21-\22/\23"+
		"\61\24\63\25\65\26\67\279\30;\31=\32?\33\3\2\b\5\2C\\aac|\4\2GGgg\4\2"+
		"--//\5\2\62;C\\c|\4\2\f\f\17\17\4\2\13\13\"\"\2\u00c0\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\7\3\2\2\2\2\r\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2"+
		"\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2"+
		"\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2"+
		"\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2"+
		"\2\2\3A\3\2\2\2\5L\3\2\2\2\7N\3\2\2\2\tV\3\2\2\2\13Z\3\2\2\2\r\\\3\2\2"+
		"\2\17f\3\2\2\2\21s\3\2\2\2\23w\3\2\2\2\25y\3\2\2\2\27{\3\2\2\2\31}\3\2"+
		"\2\2\33\177\3\2\2\2\35\u0081\3\2\2\2\37\u0083\3\2\2\2!\u0085\3\2\2\2#"+
		"\u0087\3\2\2\2%\u0089\3\2\2\2\'\u008b\3\2\2\2)\u008e\3\2\2\2+\u0091\3"+
		"\2\2\2-\u0093\3\2\2\2/\u0095\3\2\2\2\61\u009c\3\2\2\2\63\u00a3\3\2\2\2"+
		"\65\u00a5\3\2\2\2\67\u00a7\3\2\2\29\u00a9\3\2\2\2;\u00ac\3\2\2\2=\u00b1"+
		"\3\2\2\2?\u00b6\3\2\2\2AB\7.\2\2B\4\3\2\2\2CD\7v\2\2DE\7t\2\2EF\7w\2\2"+
		"FM\7g\2\2GH\7h\2\2HI\7c\2\2IJ\7n\2\2JK\7u\2\2KM\7g\2\2LC\3\2\2\2LG\3\2"+
		"\2\2M\6\3\2\2\2NR\5\t\5\2OQ\5\13\6\2PO\3\2\2\2QT\3\2\2\2RP\3\2\2\2RS\3"+
		"\2\2\2S\b\3\2\2\2TR\3\2\2\2UW\t\2\2\2VU\3\2\2\2W\n\3\2\2\2X[\5\t\5\2Y"+
		"[\4\62;\2ZX\3\2\2\2ZY\3\2\2\2[\f\3\2\2\2\\c\5\17\b\2]_\5\23\n\2^`\5\25"+
		"\13\2_^\3\2\2\2_`\3\2\2\2`a\3\2\2\2ab\5\21\t\2bd\3\2\2\2c]\3\2\2\2cd\3"+
		"\2\2\2d\16\3\2\2\2eg\4\62;\2fe\3\2\2\2gh\3\2\2\2hf\3\2\2\2hi\3\2\2\2i"+
		"p\3\2\2\2jl\7\60\2\2km\4\62;\2lk\3\2\2\2mn\3\2\2\2nl\3\2\2\2no\3\2\2\2"+
		"oq\3\2\2\2pj\3\2\2\2pq\3\2\2\2q\20\3\2\2\2rt\4\62;\2sr\3\2\2\2tu\3\2\2"+
		"\2us\3\2\2\2uv\3\2\2\2v\22\3\2\2\2wx\t\3\2\2x\24\3\2\2\2yz\t\4\2\2z\26"+
		"\3\2\2\2{|\7*\2\2|\30\3\2\2\2}~\7+\2\2~\32\3\2\2\2\177\u0080\7-\2\2\u0080"+
		"\34\3\2\2\2\u0081\u0082\7/\2\2\u0082\36\3\2\2\2\u0083\u0084\7,\2\2\u0084"+
		" \3\2\2\2\u0085\u0086\7\61\2\2\u0086\"\3\2\2\2\u0087\u0088\7@\2\2\u0088"+
		"$\3\2\2\2\u0089\u008a\7>\2\2\u008a&\3\2\2\2\u008b\u008c\7@\2\2\u008c\u008d"+
		"\7?\2\2\u008d(\3\2\2\2\u008e\u008f\7>\2\2\u008f\u0090\7?\2\2\u0090*\3"+
		"\2\2\2\u0091\u0092\7?\2\2\u0092,\3\2\2\2\u0093\u0094\7#\2\2\u0094.\3\2"+
		"\2\2\u0095\u0096\7H\2\2\u0096\u0097\7q\2\2\u0097\u0098\7t\2\2\u0098\u0099"+
		"\7c\2\2\u0099\u009a\7n\2\2\u009a\u009b\7n\2\2\u009b\60\3\2\2\2\u009c\u009d"+
		"\7G\2\2\u009d\u009e\7z\2\2\u009e\u009f\7k\2\2\u009f\u00a0\7u\2\2\u00a0"+
		"\u00a1\7v\2\2\u00a1\u00a2\7u\2\2\u00a2\62\3\2\2\2\u00a3\u00a4\t\5\2\2"+
		"\u00a4\64\3\2\2\2\u00a5\u00a6\7(\2\2\u00a6\66\3\2\2\2\u00a7\u00a8\7~\2"+
		"\2\u00a88\3\2\2\2\u00a9\u00aa\7/\2\2\u00aa\u00ab\7@\2\2\u00ab:\3\2\2\2"+
		"\u00ac\u00ad\7>\2\2\u00ad\u00ae\7/\2\2\u00ae\u00af\7@\2\2\u00af<\3\2\2"+
		"\2\u00b0\u00b2\t\6\2\2\u00b1\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b1"+
		"\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4>\3\2\2\2\u00b5\u00b7\t\7\2\2\u00b6"+
		"\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9\3\2"+
		"\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb\b \2\2\u00bb@\3\2\2\2\17\2LRVZ_c"+
		"hnpu\u00b3\u00b8\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}