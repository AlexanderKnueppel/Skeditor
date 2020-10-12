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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, TYPE=8, TRUE=9, 
		FALSE=10, CODEWORD=11, NULL=12, QUANTIFER=13, OPERATOR=14, COMPONENT=15, 
		STRING=16, IDENTIFIER=17, NUMBER=18, LPAREN=19, NOT=20, RPAREN=21, POWER=22, 
		EQUAL=23, NEQUAL=24, ADD=25, MINUS=26, MULTI=27, DIVISION=28, CHARACTER=29, 
		CONJ=30, DISJ=31, IMPL=32, BICOND=33, GREATER=34, SMALLER=35, SMALLEREQ=36, 
		GREATEREQ=37, WHITESPACE=38;
	public static final int
		RULE_condition = 0, RULE_formula = 1, RULE_quantifier = 2, RULE_operatorformula = 3, 
		RULE_connectiveformula = 4, RULE_tupelformula = 5, RULE_compareformula = 6, 
		RULE_tupel = 7, RULE_summformula = 8, RULE_faktorformula = 9, RULE_powerformula = 10, 
		RULE_notterm = 11, RULE_term = 12, RULE_array = 13, RULE_compproperty = 14, 
		RULE_portproperty = 15, RULE_port = 16, RULE_functioncall = 17, RULE_functionname = 18, 
		RULE_compoperator = 19, RULE_multoperator = 20, RULE_addoperator = 21, 
		RULE_connectoperator = 22, RULE_pred_constant = 23, RULE_variable = 24;
	public static final String[] ruleNames = {
		"condition", "formula", "quantifier", "operatorformula", "connectiveformula", 
		"tupelformula", "compareformula", "tupel", "summformula", "faktorformula", 
		"powerformula", "notterm", "term", "array", "compproperty", "portproperty", 
		"port", "functioncall", "functionname", "compoperator", "multoperator", 
		"addoperator", "connectoperator", "pred_constant", "variable"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'\"'", "';'", "','", "'.'", "'['", "']'", "'_'", null, "'\\true'", 
		"'\\false'", "'.$'", "'\\null'", null, null, null, null, null, null, "'('", 
		"'!'", "')'", "'^'", "'=='", "'!='", "'+'", "'-'", "'*'", "'/'", null, 
		null, null, "'=>'", "'<>'", "'>'", "'<'", "'<='", "'>='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, "TYPE", "TRUE", "FALSE", 
		"CODEWORD", "NULL", "QUANTIFER", "OPERATOR", "COMPONENT", "STRING", "IDENTIFIER", 
		"NUMBER", "LPAREN", "NOT", "RPAREN", "POWER", "EQUAL", "NEQUAL", "ADD", 
		"MINUS", "MULTI", "DIVISION", "CHARACTER", "CONJ", "DISJ", "IMPL", "BICOND", 
		"GREATER", "SMALLER", "SMALLEREQ", "GREATEREQ", "WHITESPACE"
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
			setState(50);
			formula();
			setState(51);
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
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TupelformulaContext tupelformula() {
			return getRuleContext(TupelformulaContext.class,0);
		}
		public ConnectiveformulaContext connectiveformula() {
			return getRuleContext(ConnectiveformulaContext.class,0);
		}
		public QuantifierContext quantifier() {
			return getRuleContext(QuantifierContext.class,0);
		}
		public OperatorformulaContext operatorformula() {
			return getRuleContext(OperatorformulaContext.class,0);
		}
		public Pred_constantContext pred_constant() {
			return getRuleContext(Pred_constantContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
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
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_formula);
		int _la;
		try {
			setState(73);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(53);
				match(T__0);
				setState(54);
				formula();
				setState(55);
				match(T__0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(57);
				tupelformula();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(58);
				connectiveformula();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(59);
				quantifier();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(60);
				operatorformula();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(61);
				pred_constant();
				setState(62);
				match(LPAREN);
				setState(63);
				term();
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					setState(64);
					match(T__1);
					setState(65);
					term();
					}
					}
					setState(70);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(71);
				match(RPAREN);
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

	public static class QuantifierContext extends ParserRuleContext {
		public TerminalNode QUANTIFER() { return getToken(folParser.QUANTIFER, 0); }
		public List<TerminalNode> IDENTIFIER() { return getTokens(folParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(folParser.IDENTIFIER, i);
		}
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public List<TerminalNode> TYPE() { return getTokens(folParser.TYPE); }
		public TerminalNode TYPE(int i) {
			return getToken(folParser.TYPE, i);
		}
		public QuantifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quantifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterQuantifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitQuantifier(this);
		}
	}

	public final QuantifierContext quantifier() throws RecognitionException {
		QuantifierContext _localctx = new QuantifierContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_quantifier);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			match(QUANTIFER);
			setState(77);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(76);
				match(TYPE);
				}
			}

			setState(79);
			match(IDENTIFIER);
			setState(87);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(80);
					match(T__1);
					setState(82);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==TYPE) {
						{
						setState(81);
						match(TYPE);
						}
					}

					setState(84);
					match(IDENTIFIER);
					}
					} 
				}
				setState(89);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			setState(92);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(90);
				match(T__1);
				setState(91);
				formula();
				}
				break;
			}
			setState(94);
			match(T__1);
			setState(95);
			formula();
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

	public static class OperatorformulaContext extends ParserRuleContext {
		public TerminalNode OPERATOR() { return getToken(folParser.OPERATOR, 0); }
		public TerminalNode IDENTIFIER() { return getToken(folParser.IDENTIFIER, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TerminalNode TYPE() { return getToken(folParser.TYPE, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public OperatorformulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_operatorformula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterOperatorformula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitOperatorformula(this);
		}
	}

	public final OperatorformulaContext operatorformula() throws RecognitionException {
		OperatorformulaContext _localctx = new OperatorformulaContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_operatorformula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(OPERATOR);
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(98);
				match(TYPE);
				}
			}

			setState(101);
			match(IDENTIFIER);
			setState(107);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(102);
				match(T__1);
				setState(103);
				term();
				setState(104);
				match(T__1);
				setState(105);
				term();
				}
				break;
			}
			setState(109);
			match(T__1);
			setState(110);
			formula();
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

	public static class ConnectiveformulaContext extends ParserRuleContext {
		public List<CompareformulaContext> compareformula() {
			return getRuleContexts(CompareformulaContext.class);
		}
		public CompareformulaContext compareformula(int i) {
			return getRuleContext(CompareformulaContext.class,i);
		}
		public List<ConnectoperatorContext> connectoperator() {
			return getRuleContexts(ConnectoperatorContext.class);
		}
		public ConnectoperatorContext connectoperator(int i) {
			return getRuleContext(ConnectoperatorContext.class,i);
		}
		public ConnectiveformulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_connectiveformula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterConnectiveformula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitConnectiveformula(this);
		}
	}

	public final ConnectiveformulaContext connectiveformula() throws RecognitionException {
		ConnectiveformulaContext _localctx = new ConnectiveformulaContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_connectiveformula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(112);
			compareformula();
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CONJ) | (1L << DISJ) | (1L << IMPL) | (1L << BICOND))) != 0)) {
				{
				{
				setState(113);
				connectoperator();
				setState(114);
				compareformula();
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class TupelformulaContext extends ParserRuleContext {
		public TerminalNode EQUAL() { return getToken(folParser.EQUAL, 0); }
		public TerminalNode NEQUAL() { return getToken(folParser.NEQUAL, 0); }
		public List<TupelContext> tupel() {
			return getRuleContexts(TupelContext.class);
		}
		public TupelContext tupel(int i) {
			return getRuleContext(TupelContext.class,i);
		}
		public List<FunctioncallContext> functioncall() {
			return getRuleContexts(FunctioncallContext.class);
		}
		public FunctioncallContext functioncall(int i) {
			return getRuleContext(FunctioncallContext.class,i);
		}
		public TupelformulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupelformula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterTupelformula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitTupelformula(this);
		}
	}

	public final TupelformulaContext tupelformula() throws RecognitionException {
		TupelformulaContext _localctx = new TupelformulaContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_tupelformula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				setState(121);
				tupel();
				}
				break;
			case IDENTIFIER:
				{
				setState(122);
				functioncall();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(125);
			_la = _input.LA(1);
			if ( !(_la==EQUAL || _la==NEQUAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(128);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				setState(126);
				tupel();
				}
				break;
			case IDENTIFIER:
				{
				setState(127);
				functioncall();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class CompareformulaContext extends ParserRuleContext {
		public List<SummformulaContext> summformula() {
			return getRuleContexts(SummformulaContext.class);
		}
		public SummformulaContext summformula(int i) {
			return getRuleContext(SummformulaContext.class,i);
		}
		public List<CompoperatorContext> compoperator() {
			return getRuleContexts(CompoperatorContext.class);
		}
		public CompoperatorContext compoperator(int i) {
			return getRuleContext(CompoperatorContext.class,i);
		}
		public CompareformulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compareformula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterCompareformula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitCompareformula(this);
		}
	}

	public final CompareformulaContext compareformula() throws RecognitionException {
		CompareformulaContext _localctx = new CompareformulaContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_compareformula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(130);
			summformula();
			setState(136);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUAL) | (1L << NEQUAL) | (1L << GREATER) | (1L << SMALLER) | (1L << SMALLEREQ) | (1L << GREATEREQ))) != 0)) {
				{
				{
				setState(131);
				compoperator();
				setState(132);
				summformula();
				}
				}
				setState(138);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class TupelContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public TupelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupel; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterTupel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitTupel(this);
		}
	}

	public final TupelContext tupel() throws RecognitionException {
		TupelContext _localctx = new TupelContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_tupel);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			match(LPAREN);
			setState(140);
			term();
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(141);
				match(T__2);
				setState(142);
				term();
				}
				}
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(148);
			match(RPAREN);
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

	public static class SummformulaContext extends ParserRuleContext {
		public List<FaktorformulaContext> faktorformula() {
			return getRuleContexts(FaktorformulaContext.class);
		}
		public FaktorformulaContext faktorformula(int i) {
			return getRuleContext(FaktorformulaContext.class,i);
		}
		public List<AddoperatorContext> addoperator() {
			return getRuleContexts(AddoperatorContext.class);
		}
		public AddoperatorContext addoperator(int i) {
			return getRuleContext(AddoperatorContext.class,i);
		}
		public SummformulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_summformula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterSummformula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitSummformula(this);
		}
	}

	public final SummformulaContext summformula() throws RecognitionException {
		SummformulaContext _localctx = new SummformulaContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_summformula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(150);
			faktorformula();
			setState(156);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ADD || _la==MINUS) {
				{
				{
				setState(151);
				addoperator();
				setState(152);
				faktorformula();
				}
				}
				setState(158);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class FaktorformulaContext extends ParserRuleContext {
		public List<PowerformulaContext> powerformula() {
			return getRuleContexts(PowerformulaContext.class);
		}
		public PowerformulaContext powerformula(int i) {
			return getRuleContext(PowerformulaContext.class,i);
		}
		public List<MultoperatorContext> multoperator() {
			return getRuleContexts(MultoperatorContext.class);
		}
		public MultoperatorContext multoperator(int i) {
			return getRuleContext(MultoperatorContext.class,i);
		}
		public FaktorformulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_faktorformula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterFaktorformula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitFaktorformula(this);
		}
	}

	public final FaktorformulaContext faktorformula() throws RecognitionException {
		FaktorformulaContext _localctx = new FaktorformulaContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_faktorformula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(159);
			powerformula();
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MULTI || _la==DIVISION) {
				{
				{
				setState(160);
				multoperator();
				setState(161);
				powerformula();
				}
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class PowerformulaContext extends ParserRuleContext {
		public List<NottermContext> notterm() {
			return getRuleContexts(NottermContext.class);
		}
		public NottermContext notterm(int i) {
			return getRuleContext(NottermContext.class,i);
		}
		public List<TerminalNode> POWER() { return getTokens(folParser.POWER); }
		public TerminalNode POWER(int i) {
			return getToken(folParser.POWER, i);
		}
		public PowerformulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_powerformula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterPowerformula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitPowerformula(this);
		}
	}

	public final PowerformulaContext powerformula() throws RecognitionException {
		PowerformulaContext _localctx = new PowerformulaContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_powerformula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(168);
			notterm();
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==POWER) {
				{
				{
				setState(169);
				match(POWER);
				setState(170);
				notterm();
				}
				}
				setState(175);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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

	public static class NottermContext extends ParserRuleContext {
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TerminalNode NOT() { return getToken(folParser.NOT, 0); }
		public NottermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notterm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterNotterm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitNotterm(this);
		}
	}

	public final NottermContext notterm() throws RecognitionException {
		NottermContext _localctx = new NottermContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_notterm);
		try {
			setState(179);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
			case FALSE:
			case COMPONENT:
			case STRING:
			case IDENTIFIER:
			case NUMBER:
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(176);
				term();
				}
				break;
			case NOT:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(177);
				match(NOT);
				setState(178);
				term();
				}
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

	public static class TermContext extends ParserRuleContext {
		public ComppropertyContext compproperty() {
			return getRuleContext(ComppropertyContext.class,0);
		}
		public PortpropertyContext portproperty() {
			return getRuleContext(PortpropertyContext.class,0);
		}
		public PortContext port() {
			return getRuleContext(PortContext.class,0);
		}
		public FunctioncallContext functioncall() {
			return getRuleContext(FunctioncallContext.class,0);
		}
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode NUMBER() { return getToken(folParser.NUMBER, 0); }
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public TerminalNode MINUS() { return getToken(folParser.MINUS, 0); }
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TerminalNode TRUE() { return getToken(folParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(folParser.FALSE, 0); }
		public TerminalNode STRING() { return getToken(folParser.STRING, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
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
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_term);
		try {
			setState(204);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(181);
				compproperty();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(182);
				portproperty();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(183);
				port();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(184);
				functioncall();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(185);
				array();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(186);
				variable();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(187);
				match(NUMBER);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				{
				setState(188);
				match(LPAREN);
				setState(189);
				match(MINUS);
				{
				setState(190);
				term();
				}
				setState(191);
				match(RPAREN);
				}
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(193);
				match(TRUE);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(194);
				match(FALSE);
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(195);
				match(STRING);
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				{
				setState(196);
				match(LPAREN);
				setState(197);
				term();
				setState(198);
				match(RPAREN);
				}
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				{
				setState(200);
				match(LPAREN);
				setState(201);
				formula();
				setState(202);
				match(RPAREN);
				}
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

	public static class ArrayContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode COMPONENT() { return getToken(folParser.COMPONENT, 0); }
		public TerminalNode CODEWORD() { return getToken(folParser.CODEWORD, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public List<TerminalNode> IDENTIFIER() { return getTokens(folParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(folParser.IDENTIFIER, i);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitArray(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_array);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(214);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				{
				setState(206);
				match(COMPONENT);
				setState(207);
				match(CODEWORD);
				}
				}
				break;
			case 2:
				{
				setState(210); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(208);
						match(IDENTIFIER);
						setState(209);
						match(T__3);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(212); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			}
			setState(216);
			variable();
			setState(224); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(217);
				match(T__4);
				setState(220);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
				case 1:
					{
					setState(218);
					term();
					}
					break;
				case 2:
					{
					setState(219);
					formula();
					}
					break;
				}
				setState(222);
				match(T__5);
				}
				}
				setState(226); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__4 );
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

	public static class ComppropertyContext extends ParserRuleContext {
		public TerminalNode COMPONENT() { return getToken(folParser.COMPONENT, 0); }
		public TerminalNode CODEWORD() { return getToken(folParser.CODEWORD, 0); }
		public TerminalNode IDENTIFIER() { return getToken(folParser.IDENTIFIER, 0); }
		public ComppropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compproperty; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterCompproperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitCompproperty(this);
		}
	}

	public final ComppropertyContext compproperty() throws RecognitionException {
		ComppropertyContext _localctx = new ComppropertyContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_compproperty);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			match(COMPONENT);
			setState(229);
			match(CODEWORD);
			setState(230);
			match(IDENTIFIER);
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

	public static class PortpropertyContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(folParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(folParser.IDENTIFIER, i);
		}
		public TerminalNode CODEWORD() { return getToken(folParser.CODEWORD, 0); }
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public PortpropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_portproperty; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterPortproperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitPortproperty(this);
		}
	}

	public final PortpropertyContext portproperty() throws RecognitionException {
		PortpropertyContext _localctx = new PortpropertyContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_portproperty);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			match(IDENTIFIER);
			setState(233);
			match(CODEWORD);
			setState(236);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(234);
				match(IDENTIFIER);
				}
				break;
			case 2:
				{
				setState(235);
				array();
				}
				break;
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

	public static class PortContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(folParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(folParser.IDENTIFIER, i);
		}
		public PortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_port; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterPort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitPort(this);
		}
	}

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_port);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(238);
			match(IDENTIFIER);
			setState(241); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(239);
				match(T__3);
				setState(240);
				match(IDENTIFIER);
				}
				}
				setState(243); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__3 );
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

	public static class FunctioncallContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(folParser.IDENTIFIER, 0); }
		public FunctionnameContext functionname() {
			return getRuleContext(FunctionnameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public FunctioncallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functioncall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterFunctioncall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitFunctioncall(this);
		}
	}

	public final FunctioncallContext functioncall() throws RecognitionException {
		FunctioncallContext _localctx = new FunctioncallContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_functioncall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			match(IDENTIFIER);
			setState(246);
			match(T__3);
			setState(247);
			functionname();
			setState(248);
			match(LPAREN);
			setState(251);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(249);
				term();
				}
				break;
			case 2:
				{
				setState(250);
				formula();
				}
				break;
			}
			setState(260);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(253);
				match(T__2);
				setState(256);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
				case 1:
					{
					setState(254);
					term();
					}
					break;
				case 2:
					{
					setState(255);
					formula();
					}
					break;
				}
				}
				}
				setState(262);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(263);
			match(RPAREN);
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

	public static class FunctionnameContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(folParser.IDENTIFIER, 0); }
		public FunctionnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionname; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterFunctionname(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitFunctionname(this);
		}
	}

	public final FunctionnameContext functionname() throws RecognitionException {
		FunctionnameContext _localctx = new FunctionnameContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_functionname);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			match(IDENTIFIER);
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

	public static class CompoperatorContext extends ParserRuleContext {
		public TerminalNode GREATER() { return getToken(folParser.GREATER, 0); }
		public TerminalNode SMALLER() { return getToken(folParser.SMALLER, 0); }
		public TerminalNode SMALLEREQ() { return getToken(folParser.SMALLEREQ, 0); }
		public TerminalNode GREATEREQ() { return getToken(folParser.GREATEREQ, 0); }
		public TerminalNode EQUAL() { return getToken(folParser.EQUAL, 0); }
		public TerminalNode NEQUAL() { return getToken(folParser.NEQUAL, 0); }
		public CompoperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compoperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterCompoperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitCompoperator(this);
		}
	}

	public final CompoperatorContext compoperator() throws RecognitionException {
		CompoperatorContext _localctx = new CompoperatorContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_compoperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUAL) | (1L << NEQUAL) | (1L << GREATER) | (1L << SMALLER) | (1L << SMALLEREQ) | (1L << GREATEREQ))) != 0)) ) {
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

	public static class MultoperatorContext extends ParserRuleContext {
		public TerminalNode MULTI() { return getToken(folParser.MULTI, 0); }
		public TerminalNode DIVISION() { return getToken(folParser.DIVISION, 0); }
		public MultoperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multoperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterMultoperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitMultoperator(this);
		}
	}

	public final MultoperatorContext multoperator() throws RecognitionException {
		MultoperatorContext _localctx = new MultoperatorContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_multoperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
			_la = _input.LA(1);
			if ( !(_la==MULTI || _la==DIVISION) ) {
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

	public static class AddoperatorContext extends ParserRuleContext {
		public TerminalNode MINUS() { return getToken(folParser.MINUS, 0); }
		public TerminalNode ADD() { return getToken(folParser.ADD, 0); }
		public AddoperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_addoperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterAddoperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitAddoperator(this);
		}
	}

	public final AddoperatorContext addoperator() throws RecognitionException {
		AddoperatorContext _localctx = new AddoperatorContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_addoperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
			_la = _input.LA(1);
			if ( !(_la==ADD || _la==MINUS) ) {
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

	public static class ConnectoperatorContext extends ParserRuleContext {
		public TerminalNode CONJ() { return getToken(folParser.CONJ, 0); }
		public TerminalNode DISJ() { return getToken(folParser.DISJ, 0); }
		public TerminalNode IMPL() { return getToken(folParser.IMPL, 0); }
		public TerminalNode BICOND() { return getToken(folParser.BICOND, 0); }
		public ConnectoperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_connectoperator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterConnectoperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitConnectoperator(this);
		}
	}

	public final ConnectoperatorContext connectoperator() throws RecognitionException {
		ConnectoperatorContext _localctx = new ConnectoperatorContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_connectoperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
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

	public static class Pred_constantContext extends ParserRuleContext {
		public List<TerminalNode> CHARACTER() { return getTokens(folParser.CHARACTER); }
		public TerminalNode CHARACTER(int i) {
			return getToken(folParser.CHARACTER, i);
		}
		public Pred_constantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pred_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterPred_constant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitPred_constant(this);
		}
	}

	public final Pred_constantContext pred_constant() throws RecognitionException {
		Pred_constantContext _localctx = new Pred_constantContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_pred_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(275);
			match(T__6);
			setState(279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CHARACTER) {
				{
				{
				setState(276);
				match(CHARACTER);
				}
				}
				setState(281);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(folParser.IDENTIFIER, 0); }
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
		enterRule(_localctx, 48, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			match(IDENTIFIER);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3(\u011f\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\7\3E\n\3\f\3\16\3H\13\3\3\3\3\3\5\3L\n\3\3\4\3\4\5\4P\n\4\3\4\3\4"+
		"\3\4\5\4U\n\4\3\4\7\4X\n\4\f\4\16\4[\13\4\3\4\3\4\5\4_\n\4\3\4\3\4\3\4"+
		"\3\5\3\5\5\5f\n\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5n\n\5\3\5\3\5\3\5\3\6\3\6"+
		"\3\6\3\6\7\6w\n\6\f\6\16\6z\13\6\3\7\3\7\5\7~\n\7\3\7\3\7\3\7\5\7\u0083"+
		"\n\7\3\b\3\b\3\b\3\b\7\b\u0089\n\b\f\b\16\b\u008c\13\b\3\t\3\t\3\t\3\t"+
		"\7\t\u0092\n\t\f\t\16\t\u0095\13\t\3\t\3\t\3\n\3\n\3\n\3\n\7\n\u009d\n"+
		"\n\f\n\16\n\u00a0\13\n\3\13\3\13\3\13\3\13\7\13\u00a6\n\13\f\13\16\13"+
		"\u00a9\13\13\3\f\3\f\3\f\7\f\u00ae\n\f\f\f\16\f\u00b1\13\f\3\r\3\r\3\r"+
		"\5\r\u00b6\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00cf"+
		"\n\16\3\17\3\17\3\17\3\17\6\17\u00d5\n\17\r\17\16\17\u00d6\5\17\u00d9"+
		"\n\17\3\17\3\17\3\17\3\17\5\17\u00df\n\17\3\17\3\17\6\17\u00e3\n\17\r"+
		"\17\16\17\u00e4\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\5\21\u00ef\n\21"+
		"\3\22\3\22\3\22\6\22\u00f4\n\22\r\22\16\22\u00f5\3\23\3\23\3\23\3\23\3"+
		"\23\3\23\5\23\u00fe\n\23\3\23\3\23\3\23\5\23\u0103\n\23\7\23\u0105\n\23"+
		"\f\23\16\23\u0108\13\23\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3"+
		"\27\3\30\3\30\3\31\3\31\7\31\u0118\n\31\f\31\16\31\u011b\13\31\3\32\3"+
		"\32\3\32\2\2\33\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62"+
		"\2\7\3\2\31\32\4\2\31\32$\'\3\2\35\36\3\2\33\34\3\2 #\2\u0131\2\64\3\2"+
		"\2\2\4K\3\2\2\2\6M\3\2\2\2\bc\3\2\2\2\nr\3\2\2\2\f}\3\2\2\2\16\u0084\3"+
		"\2\2\2\20\u008d\3\2\2\2\22\u0098\3\2\2\2\24\u00a1\3\2\2\2\26\u00aa\3\2"+
		"\2\2\30\u00b5\3\2\2\2\32\u00ce\3\2\2\2\34\u00d8\3\2\2\2\36\u00e6\3\2\2"+
		"\2 \u00ea\3\2\2\2\"\u00f0\3\2\2\2$\u00f7\3\2\2\2&\u010b\3\2\2\2(\u010d"+
		"\3\2\2\2*\u010f\3\2\2\2,\u0111\3\2\2\2.\u0113\3\2\2\2\60\u0115\3\2\2\2"+
		"\62\u011c\3\2\2\2\64\65\5\4\3\2\65\66\7\2\2\3\66\3\3\2\2\2\678\7\3\2\2"+
		"89\5\4\3\29:\7\3\2\2:L\3\2\2\2;L\5\f\7\2<L\5\n\6\2=L\5\6\4\2>L\5\b\5\2"+
		"?@\5\60\31\2@A\7\25\2\2AF\5\32\16\2BC\7\4\2\2CE\5\32\16\2DB\3\2\2\2EH"+
		"\3\2\2\2FD\3\2\2\2FG\3\2\2\2GI\3\2\2\2HF\3\2\2\2IJ\7\27\2\2JL\3\2\2\2"+
		"K\67\3\2\2\2K;\3\2\2\2K<\3\2\2\2K=\3\2\2\2K>\3\2\2\2K?\3\2\2\2L\5\3\2"+
		"\2\2MO\7\17\2\2NP\7\n\2\2ON\3\2\2\2OP\3\2\2\2PQ\3\2\2\2QY\7\23\2\2RT\7"+
		"\4\2\2SU\7\n\2\2TS\3\2\2\2TU\3\2\2\2UV\3\2\2\2VX\7\23\2\2WR\3\2\2\2X["+
		"\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z^\3\2\2\2[Y\3\2\2\2\\]\7\4\2\2]_\5\4\3\2"+
		"^\\\3\2\2\2^_\3\2\2\2_`\3\2\2\2`a\7\4\2\2ab\5\4\3\2b\7\3\2\2\2ce\7\20"+
		"\2\2df\7\n\2\2ed\3\2\2\2ef\3\2\2\2fg\3\2\2\2gm\7\23\2\2hi\7\4\2\2ij\5"+
		"\32\16\2jk\7\4\2\2kl\5\32\16\2ln\3\2\2\2mh\3\2\2\2mn\3\2\2\2no\3\2\2\2"+
		"op\7\4\2\2pq\5\4\3\2q\t\3\2\2\2rx\5\16\b\2st\5.\30\2tu\5\16\b\2uw\3\2"+
		"\2\2vs\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y\13\3\2\2\2zx\3\2\2\2{~\5"+
		"\20\t\2|~\5$\23\2}{\3\2\2\2}|\3\2\2\2~\177\3\2\2\2\177\u0082\t\2\2\2\u0080"+
		"\u0083\5\20\t\2\u0081\u0083\5$\23\2\u0082\u0080\3\2\2\2\u0082\u0081\3"+
		"\2\2\2\u0083\r\3\2\2\2\u0084\u008a\5\22\n\2\u0085\u0086\5(\25\2\u0086"+
		"\u0087\5\22\n\2\u0087\u0089\3\2\2\2\u0088\u0085\3\2\2\2\u0089\u008c\3"+
		"\2\2\2\u008a\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\17\3\2\2\2\u008c"+
		"\u008a\3\2\2\2\u008d\u008e\7\25\2\2\u008e\u0093\5\32\16\2\u008f\u0090"+
		"\7\5\2\2\u0090\u0092\5\32\16\2\u0091\u008f\3\2\2\2\u0092\u0095\3\2\2\2"+
		"\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0096\3\2\2\2\u0095\u0093"+
		"\3\2\2\2\u0096\u0097\7\27\2\2\u0097\21\3\2\2\2\u0098\u009e\5\24\13\2\u0099"+
		"\u009a\5,\27\2\u009a\u009b\5\24\13\2\u009b\u009d\3\2\2\2\u009c\u0099\3"+
		"\2\2\2\u009d\u00a0\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f"+
		"\23\3\2\2\2\u00a0\u009e\3\2\2\2\u00a1\u00a7\5\26\f\2\u00a2\u00a3\5*\26"+
		"\2\u00a3\u00a4\5\26\f\2\u00a4\u00a6\3\2\2\2\u00a5\u00a2\3\2\2\2\u00a6"+
		"\u00a9\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\25\3\2\2"+
		"\2\u00a9\u00a7\3\2\2\2\u00aa\u00af\5\30\r\2\u00ab\u00ac\7\30\2\2\u00ac"+
		"\u00ae\5\30\r\2\u00ad\u00ab\3\2\2\2\u00ae\u00b1\3\2\2\2\u00af\u00ad\3"+
		"\2\2\2\u00af\u00b0\3\2\2\2\u00b0\27\3\2\2\2\u00b1\u00af\3\2\2\2\u00b2"+
		"\u00b6\5\32\16\2\u00b3\u00b4\7\26\2\2\u00b4\u00b6\5\32\16\2\u00b5\u00b2"+
		"\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6\31\3\2\2\2\u00b7\u00cf\5\36\20\2\u00b8"+
		"\u00cf\5 \21\2\u00b9\u00cf\5\"\22\2\u00ba\u00cf\5$\23\2\u00bb\u00cf\5"+
		"\34\17\2\u00bc\u00cf\5\62\32\2\u00bd\u00cf\7\24\2\2\u00be\u00bf\7\25\2"+
		"\2\u00bf\u00c0\7\34\2\2\u00c0\u00c1\5\32\16\2\u00c1\u00c2\7\27\2\2\u00c2"+
		"\u00cf\3\2\2\2\u00c3\u00cf\7\13\2\2\u00c4\u00cf\7\f\2\2\u00c5\u00cf\7"+
		"\22\2\2\u00c6\u00c7\7\25\2\2\u00c7\u00c8\5\32\16\2\u00c8\u00c9\7\27\2"+
		"\2\u00c9\u00cf\3\2\2\2\u00ca\u00cb\7\25\2\2\u00cb\u00cc\5\4\3\2\u00cc"+
		"\u00cd\7\27\2\2\u00cd\u00cf\3\2\2\2\u00ce\u00b7\3\2\2\2\u00ce\u00b8\3"+
		"\2\2\2\u00ce\u00b9\3\2\2\2\u00ce\u00ba\3\2\2\2\u00ce\u00bb\3\2\2\2\u00ce"+
		"\u00bc\3\2\2\2\u00ce\u00bd\3\2\2\2\u00ce\u00be\3\2\2\2\u00ce\u00c3\3\2"+
		"\2\2\u00ce\u00c4\3\2\2\2\u00ce\u00c5\3\2\2\2\u00ce\u00c6\3\2\2\2\u00ce"+
		"\u00ca\3\2\2\2\u00cf\33\3\2\2\2\u00d0\u00d1\7\21\2\2\u00d1\u00d9\7\r\2"+
		"\2\u00d2\u00d3\7\23\2\2\u00d3\u00d5\7\6\2\2\u00d4\u00d2\3\2\2\2\u00d5"+
		"\u00d6\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d9\3\2"+
		"\2\2\u00d8\u00d0\3\2\2\2\u00d8\u00d4\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9"+
		"\u00da\3\2\2\2\u00da\u00e2\5\62\32\2\u00db\u00de\7\7\2\2\u00dc\u00df\5"+
		"\32\16\2\u00dd\u00df\5\4\3\2\u00de\u00dc\3\2\2\2\u00de\u00dd\3\2\2\2\u00df"+
		"\u00e0\3\2\2\2\u00e0\u00e1\7\b\2\2\u00e1\u00e3\3\2\2\2\u00e2\u00db\3\2"+
		"\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5"+
		"\35\3\2\2\2\u00e6\u00e7\7\21\2\2\u00e7\u00e8\7\r\2\2\u00e8\u00e9\7\23"+
		"\2\2\u00e9\37\3\2\2\2\u00ea\u00eb\7\23\2\2\u00eb\u00ee\7\r\2\2\u00ec\u00ef"+
		"\7\23\2\2\u00ed\u00ef\5\34\17\2\u00ee\u00ec\3\2\2\2\u00ee\u00ed\3\2\2"+
		"\2\u00ef!\3\2\2\2\u00f0\u00f3\7\23\2\2\u00f1\u00f2\7\6\2\2\u00f2\u00f4"+
		"\7\23\2\2\u00f3\u00f1\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f3\3\2\2\2"+
		"\u00f5\u00f6\3\2\2\2\u00f6#\3\2\2\2\u00f7\u00f8\7\23\2\2\u00f8\u00f9\7"+
		"\6\2\2\u00f9\u00fa\5&\24\2\u00fa\u00fd\7\25\2\2\u00fb\u00fe\5\32\16\2"+
		"\u00fc\u00fe\5\4\3\2\u00fd\u00fb\3\2\2\2\u00fd\u00fc\3\2\2\2\u00fe\u0106"+
		"\3\2\2\2\u00ff\u0102\7\5\2\2\u0100\u0103\5\32\16\2\u0101\u0103\5\4\3\2"+
		"\u0102\u0100\3\2\2\2\u0102\u0101\3\2\2\2\u0103\u0105\3\2\2\2\u0104\u00ff"+
		"\3\2\2\2\u0105\u0108\3\2\2\2\u0106\u0104\3\2\2\2\u0106\u0107\3\2\2\2\u0107"+
		"\u0109\3\2\2\2\u0108\u0106\3\2\2\2\u0109\u010a\7\27\2\2\u010a%\3\2\2\2"+
		"\u010b\u010c\7\23\2\2\u010c\'\3\2\2\2\u010d\u010e\t\3\2\2\u010e)\3\2\2"+
		"\2\u010f\u0110\t\4\2\2\u0110+\3\2\2\2\u0111\u0112\t\5\2\2\u0112-\3\2\2"+
		"\2\u0113\u0114\t\6\2\2\u0114/\3\2\2\2\u0115\u0119\7\t\2\2\u0116\u0118"+
		"\7\37\2\2\u0117\u0116\3\2\2\2\u0118\u011b\3\2\2\2\u0119\u0117\3\2\2\2"+
		"\u0119\u011a\3\2\2\2\u011a\61\3\2\2\2\u011b\u0119\3\2\2\2\u011c\u011d"+
		"\7\23\2\2\u011d\63\3\2\2\2\36FKOTY^emx}\u0082\u008a\u0093\u009e\u00a7"+
		"\u00af\u00b5\u00ce\u00d6\u00d8\u00de\u00e4\u00ee\u00f5\u00fd\u0102\u0106"+
		"\u0119";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}