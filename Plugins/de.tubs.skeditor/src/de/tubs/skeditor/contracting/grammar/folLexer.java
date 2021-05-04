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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, TYPE=11, TRUE=12, FALSE=13, CODEWORD=14, NULL=15, QUANTIFER=16, 
		OPERATOR=17, COMPONENT=18, STRING=19, CONJ=20, DISJ=21, IDENTIFIER=22, 
		SCIENTIFIC_NUMBER=23, LPAREN=24, NOT=25, RPAREN=26, POWER=27, EQUAL=28, 
		NEQUAL=29, ADD=30, MINUS=31, MULTI=32, DIVISION=33, CHARACTER=34, LEQUI=35, 
		IMPL=36, GREATER=37, SMALLER=38, SMALLEREQ=39, GREATEREQ=40, WHITESPACE=41;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "TYPE", "TRUE", "FALSE", "CODEWORD", "NULL", "QUANTIFER", "OPERATOR", 
		"COMPONENT", "STRING", "CONJ", "DISJ", "IDENTIFIER", "SCIENTIFIC_NUMBER", 
		"NUMBER", "UNSIGNED_INTEGER", "E", "SIGN", "LPAREN", "NOT", "RPAREN", 
		"POWER", "EQUAL", "NEQUAL", "ADD", "MINUS", "MULTI", "DIVISION", "CHARACTER", 
		"LEQUI", "IMPL", "GREATER", "SMALLER", "SMALLEREQ", "GREATEREQ", "WHITESPACE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'\"'", "'has'", "';'", "','", "'.'", "'['", "']'", "'#'", "'\\'", 
		"'_'", null, "'\\true'", "'\\false'", "'.$'", "'\\null'", null, null, 
		null, null, null, null, null, null, "'('", "'!'", "')'", "'^'", "'='", 
		"'!='", "'+'", "'-'", "'*'", "'/'", null, "'<->'", "'->'", "'>'", "'<'", 
		"'<='", "'>='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, "TYPE", 
		"TRUE", "FALSE", "CODEWORD", "NULL", "QUANTIFER", "OPERATOR", "COMPONENT", 
		"STRING", "CONJ", "DISJ", "IDENTIFIER", "SCIENTIFIC_NUMBER", "LPAREN", 
		"NOT", "RPAREN", "POWER", "EQUAL", "NEQUAL", "ADD", "MINUS", "MULTI", 
		"DIVISION", "CHARACTER", "LEQUI", "IMPL", "GREATER", "SMALLER", "SMALLEREQ", 
		"GREATEREQ", "WHITESPACE"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2+\u012e\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3"+
		"\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5"+
		"\f|\n\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17"+
		"\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00a2\n\21\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u00b0\n\22\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00bd\n\23\3\24\3\24"+
		"\6\24\u00c1\n\24\r\24\16\24\u00c2\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3"+
		"\25\5\25\u00cd\n\25\3\26\3\26\3\26\3\26\3\26\5\26\u00d4\n\26\3\27\5\27"+
		"\u00d7\n\27\3\27\3\27\7\27\u00db\n\27\f\27\16\27\u00de\13\27\3\30\3\30"+
		"\3\30\5\30\u00e3\n\30\3\30\3\30\5\30\u00e7\n\30\3\31\6\31\u00ea\n\31\r"+
		"\31\16\31\u00eb\3\31\3\31\6\31\u00f0\n\31\r\31\16\31\u00f1\5\31\u00f4"+
		"\n\31\3\32\6\32\u00f7\n\32\r\32\16\32\u00f8\3\33\3\33\3\34\3\34\3\35\3"+
		"\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3"+
		"&\3\'\5\'\u0115\n\'\3(\3(\3(\3(\3)\3)\3)\3*\3*\3+\3+\3,\3,\3,\3-\3-\3"+
		"-\3.\6.\u0129\n.\r.\16.\u012a\3.\3.\2\2/\3\3\5\4\7\5\t\6\13\7\r\b\17\t"+
		"\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27"+
		"-\30/\31\61\2\63\2\65\2\67\29\32;\33=\34?\35A\36C\37E G!I\"K#M$O%Q&S\'"+
		"U(W)Y*[+\3\2\b\5\2C\\aac|\3\2\62;\4\2GGgg\4\2--//\7\2\60\60\62;C\\aac"+
		"|\4\2\13\f\"\"\2\u013b\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2"+
		"\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2"+
		"A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3"+
		"\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2"+
		"\2\2[\3\2\2\2\3]\3\2\2\2\5_\3\2\2\2\7c\3\2\2\2\te\3\2\2\2\13g\3\2\2\2"+
		"\ri\3\2\2\2\17k\3\2\2\2\21m\3\2\2\2\23o\3\2\2\2\25q\3\2\2\2\27{\3\2\2"+
		"\2\31}\3\2\2\2\33\u0083\3\2\2\2\35\u008a\3\2\2\2\37\u008d\3\2\2\2!\u00a1"+
		"\3\2\2\2#\u00af\3\2\2\2%\u00bc\3\2\2\2\'\u00be\3\2\2\2)\u00cc\3\2\2\2"+
		"+\u00d3\3\2\2\2-\u00d6\3\2\2\2/\u00df\3\2\2\2\61\u00e9\3\2\2\2\63\u00f6"+
		"\3\2\2\2\65\u00fa\3\2\2\2\67\u00fc\3\2\2\29\u00fe\3\2\2\2;\u0100\3\2\2"+
		"\2=\u0102\3\2\2\2?\u0104\3\2\2\2A\u0106\3\2\2\2C\u0108\3\2\2\2E\u010b"+
		"\3\2\2\2G\u010d\3\2\2\2I\u010f\3\2\2\2K\u0111\3\2\2\2M\u0114\3\2\2\2O"+
		"\u0116\3\2\2\2Q\u011a\3\2\2\2S\u011d\3\2\2\2U\u011f\3\2\2\2W\u0121\3\2"+
		"\2\2Y\u0124\3\2\2\2[\u0128\3\2\2\2]^\7$\2\2^\4\3\2\2\2_`\7j\2\2`a\7c\2"+
		"\2ab\7u\2\2b\6\3\2\2\2cd\7=\2\2d\b\3\2\2\2ef\7.\2\2f\n\3\2\2\2gh\7\60"+
		"\2\2h\f\3\2\2\2ij\7]\2\2j\16\3\2\2\2kl\7_\2\2l\20\3\2\2\2mn\7%\2\2n\22"+
		"\3\2\2\2op\7^\2\2p\24\3\2\2\2qr\7a\2\2r\26\3\2\2\2st\7k\2\2tu\7p\2\2u"+
		"|\7v\2\2vw\7h\2\2wx\7n\2\2xy\7q\2\2yz\7c\2\2z|\7v\2\2{s\3\2\2\2{v\3\2"+
		"\2\2|\30\3\2\2\2}~\7^\2\2~\177\7v\2\2\177\u0080\7t\2\2\u0080\u0081\7w"+
		"\2\2\u0081\u0082\7g\2\2\u0082\32\3\2\2\2\u0083\u0084\7^\2\2\u0084\u0085"+
		"\7h\2\2\u0085\u0086\7c\2\2\u0086\u0087\7n\2\2\u0087\u0088\7u\2\2\u0088"+
		"\u0089\7g\2\2\u0089\34\3\2\2\2\u008a\u008b\7\60\2\2\u008b\u008c\7&\2\2"+
		"\u008c\36\3\2\2\2\u008d\u008e\7^\2\2\u008e\u008f\7p\2\2\u008f\u0090\7"+
		"w\2\2\u0090\u0091\7n\2\2\u0091\u0092\7n\2\2\u0092 \3\2\2\2\u0093\u0094"+
		"\7^\2\2\u0094\u0095\7h\2\2\u0095\u0096\7q\2\2\u0096\u0097\7t\2\2\u0097"+
		"\u0098\7c\2\2\u0098\u0099\7n\2\2\u0099\u00a2\7n\2\2\u009a\u009b\7^\2\2"+
		"\u009b\u009c\7g\2\2\u009c\u009d\7z\2\2\u009d\u009e\7k\2\2\u009e\u009f"+
		"\7u\2\2\u009f\u00a0\7v\2\2\u00a0\u00a2\7u\2\2\u00a1\u0093\3\2\2\2\u00a1"+
		"\u009a\3\2\2\2\u00a2\"\3\2\2\2\u00a3\u00a4\7^\2\2\u00a4\u00a5\7u\2\2\u00a5"+
		"\u00a6\7w\2\2\u00a6\u00b0\7o\2\2\u00a7\u00a8\7^\2\2\u00a8\u00a9\7r\2\2"+
		"\u00a9\u00aa\7t\2\2\u00aa\u00ab\7q\2\2\u00ab\u00ac\7f\2\2\u00ac\u00ad"+
		"\7w\2\2\u00ad\u00ae\7e\2\2\u00ae\u00b0\7v\2\2\u00af\u00a3\3\2\2\2\u00af"+
		"\u00a7\3\2\2\2\u00b0$\3\2\2\2\u00b1\u00b2\7^\2\2\u00b2\u00b3\7u\2\2\u00b3"+
		"\u00b4\7w\2\2\u00b4\u00b5\7r\2\2\u00b5\u00b6\7g\2\2\u00b6\u00bd\7t\2\2"+
		"\u00b7\u00b8\7^\2\2\u00b8\u00b9\7v\2\2\u00b9\u00ba\7j\2\2\u00ba\u00bb"+
		"\7k\2\2\u00bb\u00bd\7u\2\2\u00bc\u00b1\3\2\2\2\u00bc\u00b7\3\2\2\2\u00bd"+
		"&\3\2\2\2\u00be\u00c0\7$\2\2\u00bf\u00c1\5M\'\2\u00c0\u00bf\3\2\2\2\u00c1"+
		"\u00c2\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\u00c4\3\2"+
		"\2\2\u00c4\u00c5\7$\2\2\u00c5(\3\2\2\2\u00c6\u00c7\7(\2\2\u00c7\u00cd"+
		"\7(\2\2\u00c8\u00cd\7(\2\2\u00c9\u00ca\7c\2\2\u00ca\u00cb\7p\2\2\u00cb"+
		"\u00cd\7f\2\2\u00cc\u00c6\3\2\2\2\u00cc\u00c8\3\2\2\2\u00cc\u00c9\3\2"+
		"\2\2\u00cd*\3\2\2\2\u00ce\u00cf\7~\2\2\u00cf\u00d4\7~\2\2\u00d0\u00d4"+
		"\7~\2\2\u00d1\u00d2\7q\2\2\u00d2\u00d4\7t\2\2\u00d3\u00ce\3\2\2\2\u00d3"+
		"\u00d0\3\2\2\2\u00d3\u00d1\3\2\2\2\u00d4,\3\2\2\2\u00d5\u00d7\t\2\2\2"+
		"\u00d6\u00d5\3\2\2\2\u00d7\u00dc\3\2\2\2\u00d8\u00db\t\2\2\2\u00d9\u00db"+
		"\5\61\31\2\u00da\u00d8\3\2\2\2\u00da\u00d9\3\2\2\2\u00db\u00de\3\2\2\2"+
		"\u00dc\u00da\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd.\3\2\2\2\u00de\u00dc\3"+
		"\2\2\2\u00df\u00e6\5\61\31\2\u00e0\u00e2\5\65\33\2\u00e1\u00e3\5\67\34"+
		"\2\u00e2\u00e1\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e5"+
		"\5\63\32\2\u00e5\u00e7\3\2\2\2\u00e6\u00e0\3\2\2\2\u00e6\u00e7\3\2\2\2"+
		"\u00e7\60\3\2\2\2\u00e8\u00ea\t\3\2\2\u00e9\u00e8\3\2\2\2\u00ea\u00eb"+
		"\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00f3\3\2\2\2\u00ed"+
		"\u00ef\7\60\2\2\u00ee\u00f0\t\3\2\2\u00ef\u00ee\3\2\2\2\u00f0\u00f1\3"+
		"\2\2\2\u00f1\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f4\3\2\2\2\u00f3"+
		"\u00ed\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\62\3\2\2\2\u00f5\u00f7\t\3\2"+
		"\2\u00f6\u00f5\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9"+
		"\3\2\2\2\u00f9\64\3\2\2\2\u00fa\u00fb\t\4\2\2\u00fb\66\3\2\2\2\u00fc\u00fd"+
		"\t\5\2\2\u00fd8\3\2\2\2\u00fe\u00ff\7*\2\2\u00ff:\3\2\2\2\u0100\u0101"+
		"\7#\2\2\u0101<\3\2\2\2\u0102\u0103\7+\2\2\u0103>\3\2\2\2\u0104\u0105\7"+
		"`\2\2\u0105@\3\2\2\2\u0106\u0107\7?\2\2\u0107B\3\2\2\2\u0108\u0109\7#"+
		"\2\2\u0109\u010a\7?\2\2\u010aD\3\2\2\2\u010b\u010c\7-\2\2\u010cF\3\2\2"+
		"\2\u010d\u010e\7/\2\2\u010eH\3\2\2\2\u010f\u0110\7,\2\2\u0110J\3\2\2\2"+
		"\u0111\u0112\7\61\2\2\u0112L\3\2\2\2\u0113\u0115\t\6\2\2\u0114\u0113\3"+
		"\2\2\2\u0115N\3\2\2\2\u0116\u0117\7>\2\2\u0117\u0118\7/\2\2\u0118\u0119"+
		"\7@\2\2\u0119P\3\2\2\2\u011a\u011b\7/\2\2\u011b\u011c\7@\2\2\u011cR\3"+
		"\2\2\2\u011d\u011e\7@\2\2\u011eT\3\2\2\2\u011f\u0120\7>\2\2\u0120V\3\2"+
		"\2\2\u0121\u0122\7>\2\2\u0122\u0123\7?\2\2\u0123X\3\2\2\2\u0124\u0125"+
		"\7@\2\2\u0125\u0126\7?\2\2\u0126Z\3\2\2\2\u0127\u0129\t\7\2\2\u0128\u0127"+
		"\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u0128\3\2\2\2\u012a\u012b\3\2\2\2\u012b"+
		"\u012c\3\2\2\2\u012c\u012d\b.\2\2\u012d\\\3\2\2\2\25\2{\u00a1\u00af\u00bc"+
		"\u00c2\u00cc\u00d3\u00d6\u00da\u00dc\u00e2\u00e6\u00eb\u00f1\u00f3\u00f8"+
		"\u0114\u012a\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}