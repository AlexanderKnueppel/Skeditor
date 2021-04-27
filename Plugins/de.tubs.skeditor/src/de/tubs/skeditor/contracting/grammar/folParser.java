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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, TYPE=11, TRUE=12, FALSE=13, CODEWORD=14, NULL=15, QUANTIFER=16, 
		OPERATOR=17, COMPONENT=18, STRING=19, CONJ=20, DISJ=21, IDENTIFIER=22, 
		SCIENTIFIC_NUMBER=23, LPAREN=24, NOT=25, RPAREN=26, POWER=27, EQUAL=28, 
		NEQUAL=29, ADD=30, MINUS=31, MULTI=32, DIVISION=33, CHARACTER=34, LEQUI=35, 
		IMPL=36, BICOND=37, GREATER=38, SMALLER=39, SMALLEREQ=40, GREATEREQ=41, 
		WHITESPACE=42;
	public static final int
		RULE_condition = 0, RULE_formula = 1, RULE_hascondition = 2, RULE_quantifier = 3, 
		RULE_operatorformula = 4, RULE_connectiveformula = 5, RULE_tupelformula = 6, 
		RULE_boolexpression = 7, RULE_compareformula = 8, RULE_mathematicalExpression = 9, 
		RULE_tupel = 10, RULE_summformula = 11, RULE_faktorformula = 12, RULE_powerformula = 13, 
		RULE_term = 14, RULE_array = 15, RULE_compproperty = 16, RULE_portproperty = 17, 
		RULE_port = 18, RULE_predicate = 19, RULE_prefix = 20, RULE_functioncall = 21, 
		RULE_functionname = 22, RULE_compoperator = 23, RULE_multoperator = 24, 
		RULE_addoperator = 25, RULE_connectoperator = 26, RULE_pred_constant = 27, 
		RULE_variable = 28;
	public static final String[] ruleNames = {
		"condition", "formula", "hascondition", "quantifier", "operatorformula", 
		"connectiveformula", "tupelformula", "boolexpression", "compareformula", 
		"mathematicalExpression", "tupel", "summformula", "faktorformula", "powerformula", 
		"term", "array", "compproperty", "portproperty", "port", "predicate", 
		"prefix", "functioncall", "functionname", "compoperator", "multoperator", 
		"addoperator", "connectoperator", "pred_constant", "variable"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'\"'", "'has'", "';'", "','", "'.'", "'['", "']'", "'#'", "'\\'", 
		"'_'", null, "'\\true'", "'\\false'", "'.$'", "'\\null'", null, null, 
		null, null, null, null, null, null, "'('", "'!'", "')'", "'^'", "'='", 
		"'!='", "'+'", "'-'", "'*'", "'/'", null, "'<->'", "'->'", "'<>'", "'>'", 
		"'<'", "'<='", "'>='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, "TYPE", 
		"TRUE", "FALSE", "CODEWORD", "NULL", "QUANTIFER", "OPERATOR", "COMPONENT", 
		"STRING", "CONJ", "DISJ", "IDENTIFIER", "SCIENTIFIC_NUMBER", "LPAREN", 
		"NOT", "RPAREN", "POWER", "EQUAL", "NEQUAL", "ADD", "MINUS", "MULTI", 
		"DIVISION", "CHARACTER", "LEQUI", "IMPL", "BICOND", "GREATER", "SMALLER", 
		"SMALLEREQ", "GREATEREQ", "WHITESPACE"
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
			setState(58);
			formula();
			setState(59);
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
		public ConnectiveformulaContext connectiveformula() {
			return getRuleContext(ConnectiveformulaContext.class,0);
		}
		public QuantifierContext quantifier() {
			return getRuleContext(QuantifierContext.class,0);
		}
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
		try {
			setState(67);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(61);
				match(T__0);
				setState(62);
				formula();
				setState(63);
				match(T__0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				connectiveformula();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(66);
				quantifier();
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

	public static class HasconditionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public TerminalNode STRING() { return getToken(folParser.STRING, 0); }
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public HasconditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hascondition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterHascondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitHascondition(this);
		}
	}

	public final HasconditionContext hascondition() throws RecognitionException {
		HasconditionContext _localctx = new HasconditionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_hascondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			match(T__1);
			setState(70);
			match(LPAREN);
			setState(71);
			match(STRING);
			setState(72);
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

	public static class QuantifierContext extends ParserRuleContext {
		public TerminalNode QUANTIFER() { return getToken(folParser.QUANTIFER, 0); }
		public List<TerminalNode> LPAREN() { return getTokens(folParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(folParser.LPAREN, i);
		}
		public List<TerminalNode> IDENTIFIER() { return getTokens(folParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(folParser.IDENTIFIER, i);
		}
		public List<TerminalNode> RPAREN() { return getTokens(folParser.RPAREN); }
		public TerminalNode RPAREN(int i) {
			return getToken(folParser.RPAREN, i);
		}
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
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
		enterRule(_localctx, 6, RULE_quantifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			match(QUANTIFER);
			setState(75);
			match(LPAREN);
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
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(80);
				match(T__2);
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
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(90);
			match(RPAREN);
			setState(91);
			match(LPAREN);
			setState(92);
			formula();
			setState(93);
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
		enterRule(_localctx, 8, RULE_operatorformula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(OPERATOR);
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(96);
				match(TYPE);
				}
			}

			setState(99);
			match(IDENTIFIER);
			setState(105);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(100);
				match(T__2);
				setState(101);
				term();
				setState(102);
				match(T__2);
				setState(103);
				term();
				}
				break;
			}
			setState(107);
			match(T__2);
			setState(108);
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
		public BoolexpressionContext boolexpression() {
			return getRuleContext(BoolexpressionContext.class,0);
		}
		public List<ConnectoperatorContext> connectoperator() {
			return getRuleContexts(ConnectoperatorContext.class);
		}
		public ConnectoperatorContext connectoperator(int i) {
			return getRuleContext(ConnectoperatorContext.class,i);
		}
		public List<ConnectiveformulaContext> connectiveformula() {
			return getRuleContexts(ConnectiveformulaContext.class);
		}
		public ConnectiveformulaContext connectiveformula(int i) {
			return getRuleContext(ConnectiveformulaContext.class,i);
		}
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public TerminalNode NOT() { return getToken(folParser.NOT, 0); }
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
		enterRule(_localctx, 10, RULE_connectiveformula);
		int _la;
		try {
			int _alt;
			setState(133);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(110);
				boolexpression();
				setState(116);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(111);
						connectoperator();
						setState(112);
						connectiveformula();
						}
						} 
					}
					setState(118);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(119);
					match(NOT);
					}
				}

				setState(122);
				match(LPAREN);
				setState(123);
				connectiveformula();
				setState(124);
				match(RPAREN);
				setState(130);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(125);
						connectoperator();
						setState(126);
						connectiveformula();
						}
						} 
					}
					setState(132);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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
		enterRule(_localctx, 12, RULE_tupelformula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				setState(135);
				tupel();
				}
				break;
			case T__8:
			case IDENTIFIER:
				{
				setState(136);
				functioncall();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(139);
			_la = _input.LA(1);
			if ( !(_la==EQUAL || _la==NEQUAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(142);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				setState(140);
				tupel();
				}
				break;
			case T__8:
			case IDENTIFIER:
				{
				setState(141);
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

	public static class BoolexpressionContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(folParser.NOT, 0); }
		public BoolexpressionContext boolexpression() {
			return getRuleContext(BoolexpressionContext.class,0);
		}
		public HasconditionContext hascondition() {
			return getRuleContext(HasconditionContext.class,0);
		}
		public CompareformulaContext compareformula() {
			return getRuleContext(CompareformulaContext.class,0);
		}
		public QuantifierContext quantifier() {
			return getRuleContext(QuantifierContext.class,0);
		}
		public TerminalNode TRUE() { return getToken(folParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(folParser.FALSE, 0); }
		public BoolexpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolexpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterBoolexpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitBoolexpression(this);
		}
	}

	public final BoolexpressionContext boolexpression() throws RecognitionException {
		BoolexpressionContext _localctx = new BoolexpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_boolexpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EOF:
			case T__0:
			case T__3:
			case T__6:
			case CONJ:
			case DISJ:
			case RPAREN:
			case LEQUI:
			case IMPL:
			case BICOND:
				{
				}
				break;
			case NOT:
				{
				setState(145);
				match(NOT);
				setState(146);
				boolexpression();
				}
				break;
			case T__1:
				{
				setState(147);
				hascondition();
				}
				break;
			case T__7:
			case T__8:
			case COMPONENT:
			case IDENTIFIER:
			case SCIENTIFIC_NUMBER:
			case LPAREN:
			case ADD:
			case MINUS:
				{
				setState(148);
				compareformula();
				}
				break;
			case QUANTIFER:
				{
				setState(149);
				quantifier();
				}
				break;
			case TRUE:
				{
				setState(150);
				match(TRUE);
				}
				break;
			case FALSE:
				{
				setState(151);
				match(FALSE);
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
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public CompareformulaContext compareformula() {
			return getRuleContext(CompareformulaContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public List<MathematicalExpressionContext> mathematicalExpression() {
			return getRuleContexts(MathematicalExpressionContext.class);
		}
		public MathematicalExpressionContext mathematicalExpression(int i) {
			return getRuleContext(MathematicalExpressionContext.class,i);
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
		enterRule(_localctx, 16, RULE_compareformula);
		int _la;
		try {
			setState(167);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(154);
				match(LPAREN);
				setState(155);
				compareformula();
				setState(156);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(158);
				mathematicalExpression(0);
				setState(164);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUAL) | (1L << NEQUAL) | (1L << GREATER) | (1L << SMALLER) | (1L << SMALLEREQ) | (1L << GREATEREQ))) != 0)) {
					{
					{
					setState(159);
					compoperator();
					setState(160);
					mathematicalExpression(0);
					}
					}
					setState(166);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
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

	public static class MathematicalExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public List<MathematicalExpressionContext> mathematicalExpression() {
			return getRuleContexts(MathematicalExpressionContext.class);
		}
		public MathematicalExpressionContext mathematicalExpression(int i) {
			return getRuleContext(MathematicalExpressionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public List<TerminalNode> ADD() { return getTokens(folParser.ADD); }
		public TerminalNode ADD(int i) {
			return getToken(folParser.ADD, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(folParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(folParser.MINUS, i);
		}
		public TerminalNode POWER() { return getToken(folParser.POWER, 0); }
		public TerminalNode MULTI() { return getToken(folParser.MULTI, 0); }
		public TerminalNode DIVISION() { return getToken(folParser.DIVISION, 0); }
		public MathematicalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mathematicalExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterMathematicalExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitMathematicalExpression(this);
		}
	}

	public final MathematicalExpressionContext mathematicalExpression() throws RecognitionException {
		return mathematicalExpression(0);
	}

	private MathematicalExpressionContext mathematicalExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		MathematicalExpressionContext _localctx = new MathematicalExpressionContext(_ctx, _parentState);
		MathematicalExpressionContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_mathematicalExpression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(170);
				match(LPAREN);
				setState(171);
				mathematicalExpression(0);
				setState(172);
				match(RPAREN);
				}
				break;
			case 2:
				{
				setState(177);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ADD || _la==MINUS) {
					{
					{
					setState(174);
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
					setState(179);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(180);
				term();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(194);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(192);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
					case 1:
						{
						_localctx = new MathematicalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_mathematicalExpression);
						setState(183);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(184);
						match(POWER);
						setState(185);
						mathematicalExpression(6);
						}
						break;
					case 2:
						{
						_localctx = new MathematicalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_mathematicalExpression);
						setState(186);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(187);
						_la = _input.LA(1);
						if ( !(_la==MULTI || _la==DIVISION) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(188);
						mathematicalExpression(5);
						}
						break;
					case 3:
						{
						_localctx = new MathematicalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_mathematicalExpression);
						setState(189);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(190);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==MINUS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(191);
						mathematicalExpression(4);
						}
						break;
					}
					} 
				}
				setState(196);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
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
		enterRule(_localctx, 20, RULE_tupel);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			match(LPAREN);
			setState(198);
			term();
			setState(203);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(199);
				match(T__3);
				setState(200);
				term();
				}
				}
				setState(205);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(206);
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
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public List<SummformulaContext> summformula() {
			return getRuleContexts(SummformulaContext.class);
		}
		public SummformulaContext summformula(int i) {
			return getRuleContext(SummformulaContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public FaktorformulaContext faktorformula() {
			return getRuleContext(FaktorformulaContext.class,0);
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
		enterRule(_localctx, 22, RULE_summformula);
		try {
			int _alt;
			setState(221);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(208);
				match(LPAREN);
				setState(209);
				summformula();
				setState(210);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(212);
				faktorformula();
				setState(218);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(213);
						addoperator();
						setState(214);
						summformula();
						}
						} 
					}
					setState(220);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
				}
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

	public static class FaktorformulaContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public List<FaktorformulaContext> faktorformula() {
			return getRuleContexts(FaktorformulaContext.class);
		}
		public FaktorformulaContext faktorformula(int i) {
			return getRuleContext(FaktorformulaContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public PowerformulaContext powerformula() {
			return getRuleContext(PowerformulaContext.class,0);
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
		enterRule(_localctx, 24, RULE_faktorformula);
		try {
			int _alt;
			setState(236);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(223);
				match(LPAREN);
				setState(224);
				faktorformula();
				setState(225);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(227);
				powerformula();
				setState(233);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(228);
						multoperator();
						setState(229);
						faktorformula();
						}
						} 
					}
					setState(235);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
				}
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

	public static class PowerformulaContext extends ParserRuleContext {
		public List<TerminalNode> LPAREN() { return getTokens(folParser.LPAREN); }
		public TerminalNode LPAREN(int i) {
			return getToken(folParser.LPAREN, i);
		}
		public List<PowerformulaContext> powerformula() {
			return getRuleContexts(PowerformulaContext.class);
		}
		public PowerformulaContext powerformula(int i) {
			return getRuleContext(PowerformulaContext.class,i);
		}
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
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
		enterRule(_localctx, 26, RULE_powerformula);
		try {
			int _alt;
			setState(250);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(238);
				match(LPAREN);
				setState(239);
				powerformula();
				setState(240);
				match(LPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(242);
				term();
				setState(247);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(243);
						match(POWER);
						setState(244);
						powerformula();
						}
						} 
					}
					setState(249);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				}
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
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
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
		public TerminalNode SCIENTIFIC_NUMBER() { return getToken(folParser.SCIENTIFIC_NUMBER, 0); }
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public TerminalNode MINUS() { return getToken(folParser.MINUS, 0); }
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
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
		enterRule(_localctx, 28, RULE_term);
		try {
			setState(269);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(252);
				compproperty();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(253);
				portproperty();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(254);
				port();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(255);
				predicate();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(256);
				functioncall();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(257);
				array();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(258);
				variable();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(259);
				match(SCIENTIFIC_NUMBER);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				{
				setState(260);
				match(LPAREN);
				setState(261);
				match(MINUS);
				{
				setState(262);
				term();
				}
				setState(263);
				match(RPAREN);
				}
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				{
				setState(265);
				match(LPAREN);
				setState(266);
				term();
				setState(267);
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
		enterRule(_localctx, 30, RULE_array);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(279);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				{
				setState(271);
				match(COMPONENT);
				setState(272);
				match(CODEWORD);
				}
				}
				break;
			case 2:
				{
				setState(275); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(273);
						match(IDENTIFIER);
						setState(274);
						match(T__4);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(277); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			}
			setState(281);
			variable();
			setState(289); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(282);
					match(T__5);
					setState(285);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
					case 1:
						{
						setState(283);
						term();
						}
						break;
					case 2:
						{
						setState(284);
						formula();
						}
						break;
					}
					setState(287);
					match(T__6);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(291); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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
		enterRule(_localctx, 32, RULE_compproperty);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(293);
			match(COMPONENT);
			setState(294);
			match(CODEWORD);
			setState(295);
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
		enterRule(_localctx, 34, RULE_portproperty);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(297);
			match(IDENTIFIER);
			setState(298);
			match(CODEWORD);
			setState(301);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(299);
				match(IDENTIFIER);
				}
				break;
			case 2:
				{
				setState(300);
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
		enterRule(_localctx, 36, RULE_port);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			match(IDENTIFIER);
			setState(306); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(304);
					match(T__4);
					setState(305);
					match(IDENTIFIER);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(308); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class PredicateContext extends ParserRuleContext {
		public FunctionnameContext functionname() {
			return getRuleContext(FunctionnameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public List<MathematicalExpressionContext> mathematicalExpression() {
			return getRuleContexts(MathematicalExpressionContext.class);
		}
		public MathematicalExpressionContext mathematicalExpression(int i) {
			return getRuleContext(MathematicalExpressionContext.class,i);
		}
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitPredicate(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_predicate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(310);
			match(T__7);
			setState(311);
			functionname();
			setState(312);
			match(LPAREN);
			setState(315);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				{
				setState(313);
				mathematicalExpression(0);
				}
				break;
			case 2:
				{
				setState(314);
				formula();
				}
				break;
			}
			setState(324);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(317);
				match(T__3);
				setState(320);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
				case 1:
					{
					setState(318);
					mathematicalExpression(0);
					}
					break;
				case 2:
					{
					setState(319);
					formula();
					}
					break;
				}
				}
				}
				setState(326);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(327);
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

	public static class PrefixContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(folParser.IDENTIFIER, 0); }
		public PrefixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prefix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterPrefix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitPrefix(this);
		}
	}

	public final PrefixContext prefix() throws RecognitionException {
		PrefixContext _localctx = new PrefixContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_prefix);
		try {
			setState(332);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(329);
				match(IDENTIFIER);
				setState(330);
				match(T__4);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(331);
				match(T__8);
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

	public static class FunctioncallContext extends ParserRuleContext {
		public FunctionnameContext functionname() {
			return getRuleContext(FunctionnameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public List<MathematicalExpressionContext> mathematicalExpression() {
			return getRuleContexts(MathematicalExpressionContext.class);
		}
		public MathematicalExpressionContext mathematicalExpression(int i) {
			return getRuleContext(MathematicalExpressionContext.class,i);
		}
		public List<FormulaContext> formula() {
			return getRuleContexts(FormulaContext.class);
		}
		public FormulaContext formula(int i) {
			return getRuleContext(FormulaContext.class,i);
		}
		public PrefixContext prefix() {
			return getRuleContext(PrefixContext.class,0);
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
		enterRule(_localctx, 42, RULE_functioncall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
			case 1:
				{
				setState(334);
				prefix();
				}
				break;
			}
			setState(337);
			functionname();
			setState(338);
			match(LPAREN);
			setState(341);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				setState(339);
				mathematicalExpression(0);
				}
				break;
			case 2:
				{
				setState(340);
				formula();
				}
				break;
			}
			setState(350);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(343);
				match(T__3);
				setState(346);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
				case 1:
					{
					setState(344);
					mathematicalExpression(0);
					}
					break;
				case 2:
					{
					setState(345);
					formula();
					}
					break;
				}
				}
				}
				setState(352);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(353);
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
		enterRule(_localctx, 44, RULE_functionname);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(355);
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
		enterRule(_localctx, 46, RULE_compoperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(357);
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
		enterRule(_localctx, 48, RULE_multoperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(359);
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
		enterRule(_localctx, 50, RULE_addoperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
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
		public TerminalNode LEQUI() { return getToken(folParser.LEQUI, 0); }
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
		enterRule(_localctx, 52, RULE_connectoperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(363);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CONJ) | (1L << DISJ) | (1L << LEQUI) | (1L << IMPL) | (1L << BICOND))) != 0)) ) {
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
		enterRule(_localctx, 54, RULE_pred_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
			match(T__9);
			setState(369);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CHARACTER) {
				{
				{
				setState(366);
				match(CHARACTER);
				}
				}
				setState(371);
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
		enterRule(_localctx, 56, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(372);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 9:
			return mathematicalExpression_sempred((MathematicalExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean mathematicalExpression_sempred(MathematicalExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 5);
		case 1:
			return precpred(_ctx, 4);
		case 2:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3,\u0179\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\5\3F\n\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\5\5P\n\5\3\5"+
		"\3\5\3\5\5\5U\n\5\3\5\7\5X\n\5\f\5\16\5[\13\5\3\5\3\5\3\5\3\5\3\5\3\6"+
		"\3\6\5\6d\n\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6l\n\6\3\6\3\6\3\6\3\7\3\7\3\7"+
		"\3\7\7\7u\n\7\f\7\16\7x\13\7\3\7\5\7{\n\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7"+
		"\u0083\n\7\f\7\16\7\u0086\13\7\5\7\u0088\n\7\3\b\3\b\5\b\u008c\n\b\3\b"+
		"\3\b\3\b\5\b\u0091\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u009b\n\t\3"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u00a5\n\n\f\n\16\n\u00a8\13\n\5\n\u00aa"+
		"\n\n\3\13\3\13\3\13\3\13\3\13\3\13\7\13\u00b2\n\13\f\13\16\13\u00b5\13"+
		"\13\3\13\5\13\u00b8\n\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\7\13\u00c3\n\13\f\13\16\13\u00c6\13\13\3\f\3\f\3\f\3\f\7\f\u00cc\n\f"+
		"\f\f\16\f\u00cf\13\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00db"+
		"\n\r\f\r\16\r\u00de\13\r\5\r\u00e0\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\7\16\u00ea\n\16\f\16\16\16\u00ed\13\16\5\16\u00ef\n\16\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\7\17\u00f8\n\17\f\17\16\17\u00fb\13\17\5"+
		"\17\u00fd\n\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u0110\n\20\3\21\3\21\3\21\3\21\6\21"+
		"\u0116\n\21\r\21\16\21\u0117\5\21\u011a\n\21\3\21\3\21\3\21\3\21\5\21"+
		"\u0120\n\21\3\21\3\21\6\21\u0124\n\21\r\21\16\21\u0125\3\22\3\22\3\22"+
		"\3\22\3\23\3\23\3\23\3\23\5\23\u0130\n\23\3\24\3\24\3\24\6\24\u0135\n"+
		"\24\r\24\16\24\u0136\3\25\3\25\3\25\3\25\3\25\5\25\u013e\n\25\3\25\3\25"+
		"\3\25\5\25\u0143\n\25\7\25\u0145\n\25\f\25\16\25\u0148\13\25\3\25\3\25"+
		"\3\26\3\26\3\26\5\26\u014f\n\26\3\27\5\27\u0152\n\27\3\27\3\27\3\27\3"+
		"\27\5\27\u0158\n\27\3\27\3\27\3\27\5\27\u015d\n\27\7\27\u015f\n\27\f\27"+
		"\16\27\u0162\13\27\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3"+
		"\34\3\34\3\35\3\35\7\35\u0172\n\35\f\35\16\35\u0175\13\35\3\36\3\36\3"+
		"\36\2\3\24\37\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64"+
		"\668:\2\7\3\2\36\37\3\2 !\3\2\"#\4\2\36\37(+\4\2\26\27%\'\2\u0195\2<\3"+
		"\2\2\2\4E\3\2\2\2\6G\3\2\2\2\bL\3\2\2\2\na\3\2\2\2\f\u0087\3\2\2\2\16"+
		"\u008b\3\2\2\2\20\u009a\3\2\2\2\22\u00a9\3\2\2\2\24\u00b7\3\2\2\2\26\u00c7"+
		"\3\2\2\2\30\u00df\3\2\2\2\32\u00ee\3\2\2\2\34\u00fc\3\2\2\2\36\u010f\3"+
		"\2\2\2 \u0119\3\2\2\2\"\u0127\3\2\2\2$\u012b\3\2\2\2&\u0131\3\2\2\2(\u0138"+
		"\3\2\2\2*\u014e\3\2\2\2,\u0151\3\2\2\2.\u0165\3\2\2\2\60\u0167\3\2\2\2"+
		"\62\u0169\3\2\2\2\64\u016b\3\2\2\2\66\u016d\3\2\2\28\u016f\3\2\2\2:\u0176"+
		"\3\2\2\2<=\5\4\3\2=>\7\2\2\3>\3\3\2\2\2?@\7\3\2\2@A\5\4\3\2AB\7\3\2\2"+
		"BF\3\2\2\2CF\5\f\7\2DF\5\b\5\2E?\3\2\2\2EC\3\2\2\2ED\3\2\2\2F\5\3\2\2"+
		"\2GH\7\4\2\2HI\7\32\2\2IJ\7\25\2\2JK\7\34\2\2K\7\3\2\2\2LM\7\22\2\2MO"+
		"\7\32\2\2NP\7\r\2\2ON\3\2\2\2OP\3\2\2\2PQ\3\2\2\2QY\7\30\2\2RT\7\5\2\2"+
		"SU\7\r\2\2TS\3\2\2\2TU\3\2\2\2UV\3\2\2\2VX\7\30\2\2WR\3\2\2\2X[\3\2\2"+
		"\2YW\3\2\2\2YZ\3\2\2\2Z\\\3\2\2\2[Y\3\2\2\2\\]\7\34\2\2]^\7\32\2\2^_\5"+
		"\4\3\2_`\7\34\2\2`\t\3\2\2\2ac\7\23\2\2bd\7\r\2\2cb\3\2\2\2cd\3\2\2\2"+
		"de\3\2\2\2ek\7\30\2\2fg\7\5\2\2gh\5\36\20\2hi\7\5\2\2ij\5\36\20\2jl\3"+
		"\2\2\2kf\3\2\2\2kl\3\2\2\2lm\3\2\2\2mn\7\5\2\2no\5\4\3\2o\13\3\2\2\2p"+
		"v\5\20\t\2qr\5\66\34\2rs\5\f\7\2su\3\2\2\2tq\3\2\2\2ux\3\2\2\2vt\3\2\2"+
		"\2vw\3\2\2\2w\u0088\3\2\2\2xv\3\2\2\2y{\7\33\2\2zy\3\2\2\2z{\3\2\2\2{"+
		"|\3\2\2\2|}\7\32\2\2}~\5\f\7\2~\u0084\7\34\2\2\177\u0080\5\66\34\2\u0080"+
		"\u0081\5\f\7\2\u0081\u0083\3\2\2\2\u0082\177\3\2\2\2\u0083\u0086\3\2\2"+
		"\2\u0084\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0088\3\2\2\2\u0086\u0084"+
		"\3\2\2\2\u0087p\3\2\2\2\u0087z\3\2\2\2\u0088\r\3\2\2\2\u0089\u008c\5\26"+
		"\f\2\u008a\u008c\5,\27\2\u008b\u0089\3\2\2\2\u008b\u008a\3\2\2\2\u008c"+
		"\u008d\3\2\2\2\u008d\u0090\t\2\2\2\u008e\u0091\5\26\f\2\u008f\u0091\5"+
		",\27\2\u0090\u008e\3\2\2\2\u0090\u008f\3\2\2\2\u0091\17\3\2\2\2\u0092"+
		"\u009b\3\2\2\2\u0093\u0094\7\33\2\2\u0094\u009b\5\20\t\2\u0095\u009b\5"+
		"\6\4\2\u0096\u009b\5\22\n\2\u0097\u009b\5\b\5\2\u0098\u009b\7\16\2\2\u0099"+
		"\u009b\7\17\2\2\u009a\u0092\3\2\2\2\u009a\u0093\3\2\2\2\u009a\u0095\3"+
		"\2\2\2\u009a\u0096\3\2\2\2\u009a\u0097\3\2\2\2\u009a\u0098\3\2\2\2\u009a"+
		"\u0099\3\2\2\2\u009b\21\3\2\2\2\u009c\u009d\7\32\2\2\u009d\u009e\5\22"+
		"\n\2\u009e\u009f\7\34\2\2\u009f\u00aa\3\2\2\2\u00a0\u00a6\5\24\13\2\u00a1"+
		"\u00a2\5\60\31\2\u00a2\u00a3\5\24\13\2\u00a3\u00a5\3\2\2\2\u00a4\u00a1"+
		"\3\2\2\2\u00a5\u00a8\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7"+
		"\u00aa\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9\u009c\3\2\2\2\u00a9\u00a0\3\2"+
		"\2\2\u00aa\23\3\2\2\2\u00ab\u00ac\b\13\1\2\u00ac\u00ad\7\32\2\2\u00ad"+
		"\u00ae\5\24\13\2\u00ae\u00af\7\34\2\2\u00af\u00b8\3\2\2\2\u00b0\u00b2"+
		"\t\3\2\2\u00b1\u00b0\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3"+
		"\u00b4\3\2\2\2\u00b4\u00b6\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6\u00b8\5\36"+
		"\20\2\u00b7\u00ab\3\2\2\2\u00b7\u00b3\3\2\2\2\u00b8\u00c4\3\2\2\2\u00b9"+
		"\u00ba\f\7\2\2\u00ba\u00bb\7\35\2\2\u00bb\u00c3\5\24\13\b\u00bc\u00bd"+
		"\f\6\2\2\u00bd\u00be\t\4\2\2\u00be\u00c3\5\24\13\7\u00bf\u00c0\f\5\2\2"+
		"\u00c0\u00c1\t\3\2\2\u00c1\u00c3\5\24\13\6\u00c2\u00b9\3\2\2\2\u00c2\u00bc"+
		"\3\2\2\2\u00c2\u00bf\3\2\2\2\u00c3\u00c6\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c4"+
		"\u00c5\3\2\2\2\u00c5\25\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c7\u00c8\7\32\2"+
		"\2\u00c8\u00cd\5\36\20\2\u00c9\u00ca\7\6\2\2\u00ca\u00cc\5\36\20\2\u00cb"+
		"\u00c9\3\2\2\2\u00cc\u00cf\3\2\2\2\u00cd\u00cb\3\2\2\2\u00cd\u00ce\3\2"+
		"\2\2\u00ce\u00d0\3\2\2\2\u00cf\u00cd\3\2\2\2\u00d0\u00d1\7\34\2\2\u00d1"+
		"\27\3\2\2\2\u00d2\u00d3\7\32\2\2\u00d3\u00d4\5\30\r\2\u00d4\u00d5\7\34"+
		"\2\2\u00d5\u00e0\3\2\2\2\u00d6\u00dc\5\32\16\2\u00d7\u00d8\5\64\33\2\u00d8"+
		"\u00d9\5\30\r\2\u00d9\u00db\3\2\2\2\u00da\u00d7\3\2\2\2\u00db\u00de\3"+
		"\2\2\2\u00dc\u00da\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00e0\3\2\2\2\u00de"+
		"\u00dc\3\2\2\2\u00df\u00d2\3\2\2\2\u00df\u00d6\3\2\2\2\u00e0\31\3\2\2"+
		"\2\u00e1\u00e2\7\32\2\2\u00e2\u00e3\5\32\16\2\u00e3\u00e4\7\34\2\2\u00e4"+
		"\u00ef\3\2\2\2\u00e5\u00eb\5\34\17\2\u00e6\u00e7\5\62\32\2\u00e7\u00e8"+
		"\5\32\16\2\u00e8\u00ea\3\2\2\2\u00e9\u00e6\3\2\2\2\u00ea\u00ed\3\2\2\2"+
		"\u00eb\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ef\3\2\2\2\u00ed\u00eb"+
		"\3\2\2\2\u00ee\u00e1\3\2\2\2\u00ee\u00e5\3\2\2\2\u00ef\33\3\2\2\2\u00f0"+
		"\u00f1\7\32\2\2\u00f1\u00f2\5\34\17\2\u00f2\u00f3\7\32\2\2\u00f3\u00fd"+
		"\3\2\2\2\u00f4\u00f9\5\36\20\2\u00f5\u00f6\7\35\2\2\u00f6\u00f8\5\34\17"+
		"\2\u00f7\u00f5\3\2\2\2\u00f8\u00fb\3\2\2\2\u00f9\u00f7\3\2\2\2\u00f9\u00fa"+
		"\3\2\2\2\u00fa\u00fd\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fc\u00f0\3\2\2\2\u00fc"+
		"\u00f4\3\2\2\2\u00fd\35\3\2\2\2\u00fe\u0110\5\"\22\2\u00ff\u0110\5$\23"+
		"\2\u0100\u0110\5&\24\2\u0101\u0110\5(\25\2\u0102\u0110\5,\27\2\u0103\u0110"+
		"\5 \21\2\u0104\u0110\5:\36\2\u0105\u0110\7\31\2\2\u0106\u0107\7\32\2\2"+
		"\u0107\u0108\7!\2\2\u0108\u0109\5\36\20\2\u0109\u010a\7\34\2\2\u010a\u0110"+
		"\3\2\2\2\u010b\u010c\7\32\2\2\u010c\u010d\5\36\20\2\u010d\u010e\7\34\2"+
		"\2\u010e\u0110\3\2\2\2\u010f\u00fe\3\2\2\2\u010f\u00ff\3\2\2\2\u010f\u0100"+
		"\3\2\2\2\u010f\u0101\3\2\2\2\u010f\u0102\3\2\2\2\u010f\u0103\3\2\2\2\u010f"+
		"\u0104\3\2\2\2\u010f\u0105\3\2\2\2\u010f\u0106\3\2\2\2\u010f\u010b\3\2"+
		"\2\2\u0110\37\3\2\2\2\u0111\u0112\7\24\2\2\u0112\u011a\7\20\2\2\u0113"+
		"\u0114\7\30\2\2\u0114\u0116\7\7\2\2\u0115\u0113\3\2\2\2\u0116\u0117\3"+
		"\2\2\2\u0117\u0115\3\2\2\2\u0117\u0118\3\2\2\2\u0118\u011a\3\2\2\2\u0119"+
		"\u0111\3\2\2\2\u0119\u0115\3\2\2\2\u0119\u011a\3\2\2\2\u011a\u011b\3\2"+
		"\2\2\u011b\u0123\5:\36\2\u011c\u011f\7\b\2\2\u011d\u0120\5\36\20\2\u011e"+
		"\u0120\5\4\3\2\u011f\u011d\3\2\2\2\u011f\u011e\3\2\2\2\u0120\u0121\3\2"+
		"\2\2\u0121\u0122\7\t\2\2\u0122\u0124\3\2\2\2\u0123\u011c\3\2\2\2\u0124"+
		"\u0125\3\2\2\2\u0125\u0123\3\2\2\2\u0125\u0126\3\2\2\2\u0126!\3\2\2\2"+
		"\u0127\u0128\7\24\2\2\u0128\u0129\7\20\2\2\u0129\u012a\7\30\2\2\u012a"+
		"#\3\2\2\2\u012b\u012c\7\30\2\2\u012c\u012f\7\20\2\2\u012d\u0130\7\30\2"+
		"\2\u012e\u0130\5 \21\2\u012f\u012d\3\2\2\2\u012f\u012e\3\2\2\2\u0130%"+
		"\3\2\2\2\u0131\u0134\7\30\2\2\u0132\u0133\7\7\2\2\u0133\u0135\7\30\2\2"+
		"\u0134\u0132\3\2\2\2\u0135\u0136\3\2\2\2\u0136\u0134\3\2\2\2\u0136\u0137"+
		"\3\2\2\2\u0137\'\3\2\2\2\u0138\u0139\7\n\2\2\u0139\u013a\5.\30\2\u013a"+
		"\u013d\7\32\2\2\u013b\u013e\5\24\13\2\u013c\u013e\5\4\3\2\u013d\u013b"+
		"\3\2\2\2\u013d\u013c\3\2\2\2\u013e\u0146\3\2\2\2\u013f\u0142\7\6\2\2\u0140"+
		"\u0143\5\24\13\2\u0141\u0143\5\4\3\2\u0142\u0140\3\2\2\2\u0142\u0141\3"+
		"\2\2\2\u0143\u0145\3\2\2\2\u0144\u013f\3\2\2\2\u0145\u0148\3\2\2\2\u0146"+
		"\u0144\3\2\2\2\u0146\u0147\3\2\2\2\u0147\u0149\3\2\2\2\u0148\u0146\3\2"+
		"\2\2\u0149\u014a\7\34\2\2\u014a)\3\2\2\2\u014b\u014c\7\30\2\2\u014c\u014f"+
		"\7\7\2\2\u014d\u014f\7\13\2\2\u014e\u014b\3\2\2\2\u014e\u014d\3\2\2\2"+
		"\u014f+\3\2\2\2\u0150\u0152\5*\26\2\u0151\u0150\3\2\2\2\u0151\u0152\3"+
		"\2\2\2\u0152\u0153\3\2\2\2\u0153\u0154\5.\30\2\u0154\u0157\7\32\2\2\u0155"+
		"\u0158\5\24\13\2\u0156\u0158\5\4\3\2\u0157\u0155\3\2\2\2\u0157\u0156\3"+
		"\2\2\2\u0158\u0160\3\2\2\2\u0159\u015c\7\6\2\2\u015a\u015d\5\24\13\2\u015b"+
		"\u015d\5\4\3\2\u015c\u015a\3\2\2\2\u015c\u015b\3\2\2\2\u015d\u015f\3\2"+
		"\2\2\u015e\u0159\3\2\2\2\u015f\u0162\3\2\2\2\u0160\u015e\3\2\2\2\u0160"+
		"\u0161\3\2\2\2\u0161\u0163\3\2\2\2\u0162\u0160\3\2\2\2\u0163\u0164\7\34"+
		"\2\2\u0164-\3\2\2\2\u0165\u0166\7\30\2\2\u0166/\3\2\2\2\u0167\u0168\t"+
		"\5\2\2\u0168\61\3\2\2\2\u0169\u016a\t\4\2\2\u016a\63\3\2\2\2\u016b\u016c"+
		"\t\3\2\2\u016c\65\3\2\2\2\u016d\u016e\t\6\2\2\u016e\67\3\2\2\2\u016f\u0173"+
		"\7\f\2\2\u0170\u0172\7$\2\2\u0171\u0170\3\2\2\2\u0172\u0175\3\2\2\2\u0173"+
		"\u0171\3\2\2\2\u0173\u0174\3\2\2\2\u01749\3\2\2\2\u0175\u0173\3\2\2\2"+
		"\u0176\u0177\7\30\2\2\u0177;\3\2\2\2,EOTYckvz\u0084\u0087\u008b\u0090"+
		"\u009a\u00a6\u00a9\u00b3\u00b7\u00c2\u00c4\u00cd\u00dc\u00df\u00eb\u00ee"+
		"\u00f9\u00fc\u010f\u0117\u0119\u011f\u0125\u012f\u0136\u013d\u0142\u0146"+
		"\u014e\u0151\u0157\u015c\u0160\u0173";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}