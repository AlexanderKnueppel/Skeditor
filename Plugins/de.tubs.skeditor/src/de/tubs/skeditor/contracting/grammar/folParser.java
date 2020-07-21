// Generated from fol.g4 by ANTLR 4.7.1
package de.tubs.skeditor.contracting.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class folParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, BOOL_LITERAL=2, VARIABLE=3, SCIENTIFIC_NUMBER=4, LPAREN=5, RPAREN=6, 
		PLUS=7, MINUS=8, TIMES=9, DIV=10, GT=11, LT=12, GEQ=13, LEQ=14, EQUAL=15, 
		NOT=16, FORALL=17, EXISTS=18, CHARACTER=19, CONJ=20, DISJ=21, IMPL=22, 
		BICOND=23, ENDLINE=24, WHITESPACE=25;
	public static final int
		RULE_condition = 0, RULE_formula = 1, RULE_term = 2, RULE_scientific = 3, 
		RULE_variable = 4, RULE_binop = 5, RULE_bin_connective = 6, RULE_separator = 7;
	public static final String[] ruleNames = {
		"condition", "formula", "term", "scientific", "variable", "binop", "bin_connective", 
		"separator"
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

	@Override
	public String getGrammarFileName() { return "fol.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public folParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ConditionContext extends ParserRuleContext {
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TerminalNode EOF() { return getToken(folParser.EOF, 0); }
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitCondition(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			formula(0);
			setState(17);
			match(EOF);
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

	public static class FormulaContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(folParser.NOT, 0); }
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public Bin_connectiveContext bin_connective() {
			return getRuleContext(Bin_connectiveContext.class,0);
		}
		public TerminalNode FORALL() { return getToken(folParser.FORALL, 0); }
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public TerminalNode EXISTS() { return getToken(folParser.EXISTS, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public BinopContext binop() {
			return getRuleContext(BinopContext.class,0);
		}
		public TerminalNode BOOL_LITERAL() { return getToken(folParser.BOOL_LITERAL, 0); }
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitFormula(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		return formula(0);
	}

	private FormulaContext formula(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		FormulaContext _localctx = new FormulaContext(_ctx, _parentState);
		FormulaContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_formula, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(20);
				match(NOT);
				setState(21);
				formula(0);
				setState(22);
				bin_connective();
				setState(23);
				formula(8);
				}
				break;
			case 2:
				{
				setState(25);
				match(NOT);
				setState(26);
				formula(7);
				}
				break;
			case 3:
				{
				setState(27);
				match(FORALL);
				setState(28);
				match(LPAREN);
				setState(29);
				variable();
				setState(30);
				match(RPAREN);
				setState(31);
				formula(6);
				}
				break;
			case 4:
				{
				setState(33);
				match(EXISTS);
				setState(34);
				match(LPAREN);
				setState(35);
				variable();
				setState(36);
				match(RPAREN);
				setState(37);
				formula(5);
				}
				break;
			case 5:
				{
				setState(39);
				match(LPAREN);
				setState(40);
				formula(0);
				setState(41);
				match(RPAREN);
				}
				break;
			case 6:
				{
				setState(43);
				term(0);
				setState(44);
				binop();
				setState(45);
				term(0);
				}
				break;
			case 7:
				{
				setState(47);
				term(0);
				}
				break;
			case 8:
				{
				setState(48);
				match(BOOL_LITERAL);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(57);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new FormulaContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_formula);
					setState(51);
					if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
					setState(52);
					bin_connective();
					setState(53);
					formula(10);
					}
					} 
				}
				setState(59);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public ScientificContext scientific() {
			return getRuleContext(ScientificContext.class,0);
		}
		public List<TerminalNode> PLUS() { return getTokens(folParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(folParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(folParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(folParser.MINUS, i);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public TerminalNode TIMES() { return getToken(folParser.TIMES, 0); }
		public TerminalNode DIV() { return getToken(folParser.DIV, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		return term(0);
	}

	private TermContext term(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TermContext _localctx = new TermContext(_ctx, _parentState);
		TermContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_term, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(64);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==PLUS || _la==MINUS) {
					{
					{
					setState(61);
					_la = _input.LA(1);
					if ( !(_la==PLUS || _la==MINUS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					}
					setState(66);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(67);
				scientific();
				}
				break;
			case 2:
				{
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==PLUS || _la==MINUS) {
					{
					{
					setState(68);
					_la = _input.LA(1);
					if ( !(_la==PLUS || _la==MINUS) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					}
					setState(73);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(74);
				variable();
				}
				break;
			case 3:
				{
				setState(75);
				match(LPAREN);
				setState(76);
				term(0);
				setState(77);
				match(RPAREN);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(89);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(87);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
					case 1:
						{
						_localctx = new TermContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_term);
						setState(81);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(82);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(83);
						term(3);
						}
						break;
					case 2:
						{
						_localctx = new TermContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_term);
						setState(84);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(85);
						_la = _input.LA(1);
						if ( !(_la==TIMES || _la==DIV) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(86);
						term(2);
						}
						break;
					}
					} 
				}
				setState(91);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ScientificContext extends ParserRuleContext {
		public TerminalNode SCIENTIFIC_NUMBER() { return getToken(folParser.SCIENTIFIC_NUMBER, 0); }
		public ScientificContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scientific; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterScientific(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitScientific(this);
		}
	}

	public final ScientificContext scientific() throws RecognitionException {
		ScientificContext _localctx = new ScientificContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_scientific);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			match(SCIENTIFIC_NUMBER);
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

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode VARIABLE() { return getToken(folParser.VARIABLE, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitVariable(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(VARIABLE);
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

	public static class BinopContext extends ParserRuleContext {
		public TerminalNode EQUAL() { return getToken(folParser.EQUAL, 0); }
		public TerminalNode LEQ() { return getToken(folParser.LEQ, 0); }
		public TerminalNode LT() { return getToken(folParser.LT, 0); }
		public TerminalNode GEQ() { return getToken(folParser.GEQ, 0); }
		public TerminalNode GT() { return getToken(folParser.GT, 0); }
		public BinopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterBinop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitBinop(this);
		}
	}

	public final BinopContext binop() throws RecognitionException {
		BinopContext _localctx = new BinopContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_binop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << LT) | (1L << GEQ) | (1L << LEQ) | (1L << EQUAL))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

	public static class Bin_connectiveContext extends ParserRuleContext {
		public TerminalNode CONJ() { return getToken(folParser.CONJ, 0); }
		public TerminalNode DISJ() { return getToken(folParser.DISJ, 0); }
		public TerminalNode IMPL() { return getToken(folParser.IMPL, 0); }
		public TerminalNode BICOND() { return getToken(folParser.BICOND, 0); }
		public Bin_connectiveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bin_connective; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterBin_connective(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitBin_connective(this);
		}
	}

	public final Bin_connectiveContext bin_connective() throws RecognitionException {
		Bin_connectiveContext _localctx = new Bin_connectiveContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_bin_connective);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CONJ) | (1L << DISJ) | (1L << IMPL) | (1L << BICOND))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
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

	public static class SeparatorContext extends ParserRuleContext {
		public SeparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_separator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterSeparator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitSeparator(this);
		}
	}

	public final SeparatorContext separator() throws RecognitionException {
		SeparatorContext _localctx = new SeparatorContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_separator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			match(T__0);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return formula_sempred((FormulaContext)_localctx, predIndex);
		case 2:
			return term_sempred((TermContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean formula_sempred(FormulaContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 9);
		}
		return true;
	}
	private boolean term_sempred(TermContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 2);
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\33i\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\64\n\3\3\3\3\3\3\3\3\3"+
		"\7\3:\n\3\f\3\16\3=\13\3\3\4\3\4\7\4A\n\4\f\4\16\4D\13\4\3\4\3\4\7\4H"+
		"\n\4\f\4\16\4K\13\4\3\4\3\4\3\4\3\4\3\4\5\4R\n\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\7\4Z\n\4\f\4\16\4]\13\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\t"+
		"\2\4\4\6\n\2\4\6\b\n\f\16\20\2\6\3\2\t\n\3\2\13\f\3\2\r\21\3\2\26\31\2"+
		"n\2\22\3\2\2\2\4\63\3\2\2\2\6Q\3\2\2\2\b^\3\2\2\2\n`\3\2\2\2\fb\3\2\2"+
		"\2\16d\3\2\2\2\20f\3\2\2\2\22\23\5\4\3\2\23\24\7\2\2\3\24\3\3\2\2\2\25"+
		"\26\b\3\1\2\26\27\7\22\2\2\27\30\5\4\3\2\30\31\5\16\b\2\31\32\5\4\3\n"+
		"\32\64\3\2\2\2\33\34\7\22\2\2\34\64\5\4\3\t\35\36\7\23\2\2\36\37\7\7\2"+
		"\2\37 \5\n\6\2 !\7\b\2\2!\"\5\4\3\b\"\64\3\2\2\2#$\7\24\2\2$%\7\7\2\2"+
		"%&\5\n\6\2&\'\7\b\2\2\'(\5\4\3\7(\64\3\2\2\2)*\7\7\2\2*+\5\4\3\2+,\7\b"+
		"\2\2,\64\3\2\2\2-.\5\6\4\2./\5\f\7\2/\60\5\6\4\2\60\64\3\2\2\2\61\64\5"+
		"\6\4\2\62\64\7\4\2\2\63\25\3\2\2\2\63\33\3\2\2\2\63\35\3\2\2\2\63#\3\2"+
		"\2\2\63)\3\2\2\2\63-\3\2\2\2\63\61\3\2\2\2\63\62\3\2\2\2\64;\3\2\2\2\65"+
		"\66\f\13\2\2\66\67\5\16\b\2\678\5\4\3\f8:\3\2\2\29\65\3\2\2\2:=\3\2\2"+
		"\2;9\3\2\2\2;<\3\2\2\2<\5\3\2\2\2=;\3\2\2\2>B\b\4\1\2?A\t\2\2\2@?\3\2"+
		"\2\2AD\3\2\2\2B@\3\2\2\2BC\3\2\2\2CE\3\2\2\2DB\3\2\2\2ER\5\b\5\2FH\t\2"+
		"\2\2GF\3\2\2\2HK\3\2\2\2IG\3\2\2\2IJ\3\2\2\2JL\3\2\2\2KI\3\2\2\2LR\5\n"+
		"\6\2MN\7\7\2\2NO\5\6\4\2OP\7\b\2\2PR\3\2\2\2Q>\3\2\2\2QI\3\2\2\2QM\3\2"+
		"\2\2R[\3\2\2\2ST\f\4\2\2TU\t\2\2\2UZ\5\6\4\5VW\f\3\2\2WX\t\3\2\2XZ\5\6"+
		"\4\4YS\3\2\2\2YV\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\\7\3\2\2\2]["+
		"\3\2\2\2^_\7\6\2\2_\t\3\2\2\2`a\7\5\2\2a\13\3\2\2\2bc\t\4\2\2c\r\3\2\2"+
		"\2de\t\5\2\2e\17\3\2\2\2fg\7\3\2\2g\21\3\2\2\2\t\63;BIQY[";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}