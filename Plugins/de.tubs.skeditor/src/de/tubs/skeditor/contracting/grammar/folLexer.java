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
		T__0=1, T__1=2, HAS=3, BOOL_LITERAL=4, VARIABLE=5, SCIENTIFIC_NUMBER=6, 
		LPAREN=7, RPAREN=8, PLUS=9, MINUS=10, TIMES=11, DIV=12, GT=13, LT=14, 
		GEQ=15, LEQ=16, EQUAL=17, NOT=18, FORALL=19, EXISTS=20, CHARACTER=21, 
		CONJ=22, DISJ=23, IMPL=24, BICOND=25, ENDLINE=26, WHITESPACE=27, DOUBLEQUOTE=28, 
		SINGLEQUOTE=29;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "HAS", "BOOL_LITERAL", "VARIABLE", "VALID_ID_START", "VALID_ID_CHAR", 
		"SCIENTIFIC_NUMBER", "NUMBER", "UNSIGNED_INTEGER", "E", "SIGN", "LPAREN", 
		"RPAREN", "PLUS", "MINUS", "TIMES", "DIV", "GT", "LT", "GEQ", "LEQ", "EQUAL", 
		"NOT", "FORALL", "EXISTS", "CHARACTER", "CONJ", "DISJ", "IMPL", "BICOND", 
		"ENDLINE", "WHITESPACE", "DOUBLEQUOTE", "SINGLEQUOTE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "' '", "','", "'has('", null, null, null, "'('", "')'", "'+'", "'-'", 
		"'*'", "'/'", "'>'", "'<'", "'>='", "'<='", "'='", "'!'", "'Forall'", 
		"'Exists'", null, "'&'", "'|'", "'->'", "'<->'", null, null, "'\"'", "'''"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "HAS", "BOOL_LITERAL", "VARIABLE", "SCIENTIFIC_NUMBER", 
		"LPAREN", "RPAREN", "PLUS", "MINUS", "TIMES", "DIV", "GT", "LT", "GEQ", 
		"LEQ", "EQUAL", "NOT", "FORALL", "EXISTS", "CHARACTER", "CONJ", "DISJ", 
		"IMPL", "BICOND", "ENDLINE", "WHITESPACE", "DOUBLEQUOTE", "SINGLEQUOTE"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\37\u00cf\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\\\n\5\3\6\3\6\7\6`\n\6\f\6\16\6c\13"+
		"\6\3\7\5\7f\n\7\3\b\3\b\5\bj\n\b\3\t\3\t\3\t\5\to\n\t\3\t\3\t\5\ts\n\t"+
		"\3\n\6\nv\n\n\r\n\16\nw\3\n\3\n\6\n|\n\n\r\n\16\n}\5\n\u0080\n\n\3\13"+
		"\6\13\u0083\n\13\r\13\16\13\u0084\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17"+
		"\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26"+
		"\3\26\3\27\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36"+
		"\3\37\3\37\3\37\3 \3 \3 \3 \3!\6!\u00c1\n!\r!\16!\u00c2\3\"\6\"\u00c6"+
		"\n\"\r\"\16\"\u00c7\3\"\3\"\3#\3#\3$\3$\2\2%\3\3\5\4\7\5\t\6\13\7\r\2"+
		"\17\2\21\b\23\2\25\2\27\2\31\2\33\t\35\n\37\13!\f#\r%\16\'\17)\20+\21"+
		"-\22/\23\61\24\63\25\65\26\67\279\30;\31=\32?\33A\34C\35E\36G\37\3\2\b"+
		"\5\2C\\aac|\4\2GGgg\4\2--//\5\2\62;C\\c|\4\2\f\f\17\17\4\2\13\13\"\"\2"+
		"\u00d3\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\21\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2"+
		"\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2"+
		"\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2"+
		"\3I\3\2\2\2\5K\3\2\2\2\7M\3\2\2\2\t[\3\2\2\2\13]\3\2\2\2\re\3\2\2\2\17"+
		"i\3\2\2\2\21k\3\2\2\2\23u\3\2\2\2\25\u0082\3\2\2\2\27\u0086\3\2\2\2\31"+
		"\u0088\3\2\2\2\33\u008a\3\2\2\2\35\u008c\3\2\2\2\37\u008e\3\2\2\2!\u0090"+
		"\3\2\2\2#\u0092\3\2\2\2%\u0094\3\2\2\2\'\u0096\3\2\2\2)\u0098\3\2\2\2"+
		"+\u009a\3\2\2\2-\u009d\3\2\2\2/\u00a0\3\2\2\2\61\u00a2\3\2\2\2\63\u00a4"+
		"\3\2\2\2\65\u00ab\3\2\2\2\67\u00b2\3\2\2\29\u00b4\3\2\2\2;\u00b6\3\2\2"+
		"\2=\u00b8\3\2\2\2?\u00bb\3\2\2\2A\u00c0\3\2\2\2C\u00c5\3\2\2\2E\u00cb"+
		"\3\2\2\2G\u00cd\3\2\2\2IJ\7\"\2\2J\4\3\2\2\2KL\7.\2\2L\6\3\2\2\2MN\7j"+
		"\2\2NO\7c\2\2OP\7u\2\2PQ\7*\2\2Q\b\3\2\2\2RS\7v\2\2ST\7t\2\2TU\7w\2\2"+
		"U\\\7g\2\2VW\7h\2\2WX\7c\2\2XY\7n\2\2YZ\7u\2\2Z\\\7g\2\2[R\3\2\2\2[V\3"+
		"\2\2\2\\\n\3\2\2\2]a\5\r\7\2^`\5\17\b\2_^\3\2\2\2`c\3\2\2\2a_\3\2\2\2"+
		"ab\3\2\2\2b\f\3\2\2\2ca\3\2\2\2df\t\2\2\2ed\3\2\2\2f\16\3\2\2\2gj\5\r"+
		"\7\2hj\4\62;\2ig\3\2\2\2ih\3\2\2\2j\20\3\2\2\2kr\5\23\n\2ln\5\27\f\2m"+
		"o\5\31\r\2nm\3\2\2\2no\3\2\2\2op\3\2\2\2pq\5\25\13\2qs\3\2\2\2rl\3\2\2"+
		"\2rs\3\2\2\2s\22\3\2\2\2tv\4\62;\2ut\3\2\2\2vw\3\2\2\2wu\3\2\2\2wx\3\2"+
		"\2\2x\177\3\2\2\2y{\7\60\2\2z|\4\62;\2{z\3\2\2\2|}\3\2\2\2}{\3\2\2\2}"+
		"~\3\2\2\2~\u0080\3\2\2\2\177y\3\2\2\2\177\u0080\3\2\2\2\u0080\24\3\2\2"+
		"\2\u0081\u0083\4\62;\2\u0082\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0082"+
		"\3\2\2\2\u0084\u0085\3\2\2\2\u0085\26\3\2\2\2\u0086\u0087\t\3\2\2\u0087"+
		"\30\3\2\2\2\u0088\u0089\t\4\2\2\u0089\32\3\2\2\2\u008a\u008b\7*\2\2\u008b"+
		"\34\3\2\2\2\u008c\u008d\7+\2\2\u008d\36\3\2\2\2\u008e\u008f\7-\2\2\u008f"+
		" \3\2\2\2\u0090\u0091\7/\2\2\u0091\"\3\2\2\2\u0092\u0093\7,\2\2\u0093"+
		"$\3\2\2\2\u0094\u0095\7\61\2\2\u0095&\3\2\2\2\u0096\u0097\7@\2\2\u0097"+
		"(\3\2\2\2\u0098\u0099\7>\2\2\u0099*\3\2\2\2\u009a\u009b\7@\2\2\u009b\u009c"+
		"\7?\2\2\u009c,\3\2\2\2\u009d\u009e\7>\2\2\u009e\u009f\7?\2\2\u009f.\3"+
		"\2\2\2\u00a0\u00a1\7?\2\2\u00a1\60\3\2\2\2\u00a2\u00a3\7#\2\2\u00a3\62"+
		"\3\2\2\2\u00a4\u00a5\7H\2\2\u00a5\u00a6\7q\2\2\u00a6\u00a7\7t\2\2\u00a7"+
		"\u00a8\7c\2\2\u00a8\u00a9\7n\2\2\u00a9\u00aa\7n\2\2\u00aa\64\3\2\2\2\u00ab"+
		"\u00ac\7G\2\2\u00ac\u00ad\7z\2\2\u00ad\u00ae\7k\2\2\u00ae\u00af\7u\2\2"+
		"\u00af\u00b0\7v\2\2\u00b0\u00b1\7u\2\2\u00b1\66\3\2\2\2\u00b2\u00b3\t"+
		"\5\2\2\u00b38\3\2\2\2\u00b4\u00b5\7(\2\2\u00b5:\3\2\2\2\u00b6\u00b7\7"+
		"~\2\2\u00b7<\3\2\2\2\u00b8\u00b9\7/\2\2\u00b9\u00ba\7@\2\2\u00ba>\3\2"+
		"\2\2\u00bb\u00bc\7>\2\2\u00bc\u00bd\7/\2\2\u00bd\u00be\7@\2\2\u00be@\3"+
		"\2\2\2\u00bf\u00c1\t\6\2\2\u00c0\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2"+
		"\u00c0\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3B\3\2\2\2\u00c4\u00c6\t\7\2\2"+
		"\u00c5\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c5\3\2\2\2\u00c7\u00c8"+
		"\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00ca\b\"\2\2\u00caD\3\2\2\2\u00cb"+
		"\u00cc\7$\2\2\u00ccF\3\2\2\2\u00cd\u00ce\7)\2\2\u00ceH\3\2\2\2\17\2[a"+
		"einrw}\177\u0084\u00c2\u00c7\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}