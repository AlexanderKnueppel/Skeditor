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
		IMPL=36, GREATER=37, SMALLER=38, SMALLEREQ=39, GREATEREQ=40, WHITESPACE=41;
	public static final int
		RULE_condition = 0, RULE_formula = 1, RULE_hascondition = 2, RULE_quantifier = 3, 
		RULE_operatorformula = 4, RULE_connectiveformula = 5, RULE_tupelformula = 6, 
		RULE_boolexpression = 7, RULE_compareformula = 8, RULE_mathematicalExpression = 9, 
		RULE_tupel = 10, RULE_term = 11, RULE_array = 12, RULE_compproperty = 13, 
		RULE_portproperty = 14, RULE_port = 15, RULE_predicate = 16, RULE_prefix = 17, 
		RULE_functioncall = 18, RULE_functionname = 19, RULE_compoperator = 20, 
		RULE_multoperator = 21, RULE_addoperator = 22, RULE_connectoperator = 23, 
		RULE_pred_constant = 24, RULE_variable = 25;
	public static final String[] ruleNames = {
		"condition", "formula", "hascondition", "quantifier", "operatorformula", 
		"connectiveformula", "tupelformula", "boolexpression", "compareformula", 
		"mathematicalExpression", "tupel", "term", "array", "compproperty", "portproperty", 
		"port", "predicate", "prefix", "functioncall", "functionname", "compoperator", 
		"multoperator", "addoperator", "connectoperator", "pred_constant", "variable"
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			formula();
			setState(53);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitFormula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_formula);
		try {
			setState(61);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(55);
				match(T__0);
				setState(56);
				formula();
				setState(57);
				match(T__0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(59);
				connectiveformula();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(60);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitHascondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HasconditionContext hascondition() throws RecognitionException {
		HasconditionContext _localctx = new HasconditionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_hascondition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			match(T__1);
			setState(64);
			match(LPAREN);
			setState(65);
			match(STRING);
			setState(66);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuantifierContext quantifier() throws RecognitionException {
		QuantifierContext _localctx = new QuantifierContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_quantifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(QUANTIFER);
			setState(69);
			match(LPAREN);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(70);
				match(TYPE);
				}
			}

			setState(73);
			match(IDENTIFIER);
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(74);
				match(T__2);
				setState(76);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TYPE) {
					{
					setState(75);
					match(TYPE);
					}
				}

				setState(78);
				match(IDENTIFIER);
				}
				}
				setState(83);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(84);
			match(RPAREN);
			setState(85);
			match(LPAREN);
			setState(86);
			formula();
			setState(87);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitOperatorformula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OperatorformulaContext operatorformula() throws RecognitionException {
		OperatorformulaContext _localctx = new OperatorformulaContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_operatorformula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(OPERATOR);
			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TYPE) {
				{
				setState(90);
				match(TYPE);
				}
			}

			setState(93);
			match(IDENTIFIER);
			setState(99);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(94);
				match(T__2);
				setState(95);
				term();
				setState(96);
				match(T__2);
				setState(97);
				term();
				}
				break;
			}
			setState(101);
			match(T__2);
			setState(102);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitConnectiveformula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConnectiveformulaContext connectiveformula() throws RecognitionException {
		ConnectiveformulaContext _localctx = new ConnectiveformulaContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_connectiveformula);
		int _la;
		try {
			int _alt;
			setState(127);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(104);
				boolexpression();
				setState(110);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(105);
						connectoperator();
						setState(106);
						connectiveformula();
						}
						} 
					}
					setState(112);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(114);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(113);
					match(NOT);
					}
				}

				setState(116);
				match(LPAREN);
				setState(117);
				connectiveformula();
				setState(118);
				match(RPAREN);
				setState(124);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(119);
						connectoperator();
						setState(120);
						connectiveformula();
						}
						} 
					}
					setState(126);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitTupelformula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TupelformulaContext tupelformula() throws RecognitionException {
		TupelformulaContext _localctx = new TupelformulaContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_tupelformula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				setState(129);
				tupel();
				}
				break;
			case T__8:
			case IDENTIFIER:
				{
				setState(130);
				functioncall();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(133);
			_la = _input.LA(1);
			if ( !(_la==EQUAL || _la==NEQUAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(136);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				setState(134);
				tupel();
				}
				break;
			case T__8:
			case IDENTIFIER:
				{
				setState(135);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitBoolexpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolexpressionContext boolexpression() throws RecognitionException {
		BoolexpressionContext _localctx = new BoolexpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_boolexpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
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
				{
				}
				break;
			case NOT:
				{
				setState(139);
				match(NOT);
				setState(140);
				boolexpression();
				}
				break;
			case T__1:
				{
				setState(141);
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
				setState(142);
				compareformula();
				}
				break;
			case QUANTIFER:
				{
				setState(143);
				quantifier();
				}
				break;
			case TRUE:
				{
				setState(144);
				match(TRUE);
				}
				break;
			case FALSE:
				{
				setState(145);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitCompareformula(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompareformulaContext compareformula() throws RecognitionException {
		CompareformulaContext _localctx = new CompareformulaContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_compareformula);
		int _la;
		try {
			setState(161);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(148);
				match(LPAREN);
				setState(149);
				compareformula();
				setState(150);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(152);
				mathematicalExpression(0);
				setState(158);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUAL) | (1L << NEQUAL) | (1L << GREATER) | (1L << SMALLER) | (1L << SMALLEREQ) | (1L << GREATEREQ))) != 0)) {
					{
					{
					setState(153);
					compoperator();
					setState(154);
					mathematicalExpression(0);
					}
					}
					setState(160);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitMathematicalExpression(this);
			else return visitor.visitChildren(this);
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
			setState(175);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(164);
				match(LPAREN);
				setState(165);
				mathematicalExpression(0);
				setState(166);
				match(RPAREN);
				}
				break;
			case 2:
				{
				setState(171);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ADD || _la==MINUS) {
					{
					{
					setState(168);
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
					setState(173);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(174);
				term();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(188);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(186);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
					case 1:
						{
						_localctx = new MathematicalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_mathematicalExpression);
						setState(177);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(178);
						match(POWER);
						setState(179);
						mathematicalExpression(6);
						}
						break;
					case 2:
						{
						_localctx = new MathematicalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_mathematicalExpression);
						setState(180);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(181);
						_la = _input.LA(1);
						if ( !(_la==MULTI || _la==DIVISION) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(182);
						mathematicalExpression(5);
						}
						break;
					case 3:
						{
						_localctx = new MathematicalExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_mathematicalExpression);
						setState(183);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(184);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==MINUS) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(185);
						mathematicalExpression(4);
						}
						break;
					}
					} 
				}
				setState(190);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitTupel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TupelContext tupel() throws RecognitionException {
		TupelContext _localctx = new TupelContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_tupel);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			match(LPAREN);
			setState(192);
			term();
			setState(197);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(193);
				match(T__3);
				setState(194);
				term();
				}
				}
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(200);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_term);
		try {
			setState(219);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(202);
				compproperty();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(203);
				portproperty();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(204);
				port();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(205);
				predicate();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(206);
				functioncall();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(207);
				array();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(208);
				variable();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(209);
				match(SCIENTIFIC_NUMBER);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				{
				setState(210);
				match(LPAREN);
				setState(211);
				match(MINUS);
				{
				setState(212);
				term();
				}
				setState(213);
				match(RPAREN);
				}
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				{
				setState(215);
				match(LPAREN);
				setState(216);
				term();
				setState(217);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_array);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				{
				setState(221);
				match(COMPONENT);
				setState(222);
				match(CODEWORD);
				}
				}
				break;
			case 2:
				{
				setState(225); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(223);
						match(IDENTIFIER);
						setState(224);
						match(T__4);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(227); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				}
				break;
			}
			setState(231);
			variable();
			setState(239); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(232);
					match(T__5);
					setState(235);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
					case 1:
						{
						setState(233);
						term();
						}
						break;
					case 2:
						{
						setState(234);
						formula();
						}
						break;
					}
					setState(237);
					match(T__6);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(241); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitCompproperty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComppropertyContext compproperty() throws RecognitionException {
		ComppropertyContext _localctx = new ComppropertyContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_compproperty);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243);
			match(COMPONENT);
			setState(244);
			match(CODEWORD);
			setState(245);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitPortproperty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PortpropertyContext portproperty() throws RecognitionException {
		PortpropertyContext _localctx = new PortpropertyContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_portproperty);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(IDENTIFIER);
			setState(248);
			match(CODEWORD);
			setState(251);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(249);
				match(IDENTIFIER);
				}
				break;
			case 2:
				{
				setState(250);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitPort(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_port);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(253);
			match(IDENTIFIER);
			setState(256); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(254);
					match(T__4);
					setState(255);
					match(IDENTIFIER);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(258); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitPredicate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_predicate);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(260);
			match(T__7);
			setState(261);
			functionname();
			setState(262);
			match(LPAREN);
			setState(265);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				{
				setState(263);
				mathematicalExpression(0);
				}
				break;
			case 2:
				{
				setState(264);
				formula();
				}
				break;
			}
			setState(274);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(267);
				match(T__3);
				setState(270);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
				case 1:
					{
					setState(268);
					mathematicalExpression(0);
					}
					break;
				case 2:
					{
					setState(269);
					formula();
					}
					break;
				}
				}
				}
				setState(276);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(277);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitPrefix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrefixContext prefix() throws RecognitionException {
		PrefixContext _localctx = new PrefixContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_prefix);
		try {
			setState(282);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(279);
				match(IDENTIFIER);
				setState(280);
				match(T__4);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(281);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitFunctioncall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctioncallContext functioncall() throws RecognitionException {
		FunctioncallContext _localctx = new FunctioncallContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_functioncall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
			case 1:
				{
				setState(284);
				prefix();
				}
				break;
			}
			setState(287);
			functionname();
			setState(288);
			match(LPAREN);
			setState(291);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,32,_ctx) ) {
			case 1:
				{
				setState(289);
				mathematicalExpression(0);
				}
				break;
			case 2:
				{
				setState(290);
				formula();
				}
				break;
			}
			setState(300);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(293);
				match(T__3);
				setState(296);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
				case 1:
					{
					setState(294);
					mathematicalExpression(0);
					}
					break;
				case 2:
					{
					setState(295);
					formula();
					}
					break;
				}
				}
				}
				setState(302);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(303);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitFunctionname(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionnameContext functionname() throws RecognitionException {
		FunctionnameContext _localctx = new FunctionnameContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_functionname);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitCompoperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompoperatorContext compoperator() throws RecognitionException {
		CompoperatorContext _localctx = new CompoperatorContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_compoperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitMultoperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultoperatorContext multoperator() throws RecognitionException {
		MultoperatorContext _localctx = new MultoperatorContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_multoperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitAddoperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AddoperatorContext addoperator() throws RecognitionException {
		AddoperatorContext _localctx = new AddoperatorContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_addoperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(311);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitConnectoperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConnectoperatorContext connectoperator() throws RecognitionException {
		ConnectoperatorContext _localctx = new ConnectoperatorContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_connectoperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(313);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << CONJ) | (1L << DISJ) | (1L << LEQUI) | (1L << IMPL))) != 0)) ) {
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitPred_constant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pred_constantContext pred_constant() throws RecognitionException {
		Pred_constantContext _localctx = new Pred_constantContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_pred_constant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			match(T__9);
			setState(319);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CHARACTER) {
				{
				{
				setState(316);
				match(CHARACTER);
				}
				}
				setState(321);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(322);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3+\u0147\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\5\3@\n\3\3\4"+
		"\3\4\3\4\3\4\3\4\3\5\3\5\3\5\5\5J\n\5\3\5\3\5\3\5\5\5O\n\5\3\5\7\5R\n"+
		"\5\f\5\16\5U\13\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\5\6^\n\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\5\6f\n\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\7\7o\n\7\f\7\16\7r\13\7"+
		"\3\7\5\7u\n\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7}\n\7\f\7\16\7\u0080\13\7\5\7"+
		"\u0082\n\7\3\b\3\b\5\b\u0086\n\b\3\b\3\b\3\b\5\b\u008b\n\b\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\5\t\u0095\n\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n"+
		"\u009f\n\n\f\n\16\n\u00a2\13\n\5\n\u00a4\n\n\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\7\13\u00ac\n\13\f\13\16\13\u00af\13\13\3\13\5\13\u00b2\n\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\7\13\u00bd\n\13\f\13\16\13\u00c0"+
		"\13\13\3\f\3\f\3\f\3\f\7\f\u00c6\n\f\f\f\16\f\u00c9\13\f\3\f\3\f\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00de"+
		"\n\r\3\16\3\16\3\16\3\16\6\16\u00e4\n\16\r\16\16\16\u00e5\5\16\u00e8\n"+
		"\16\3\16\3\16\3\16\3\16\5\16\u00ee\n\16\3\16\3\16\6\16\u00f2\n\16\r\16"+
		"\16\16\u00f3\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\5\20\u00fe\n\20\3"+
		"\21\3\21\3\21\6\21\u0103\n\21\r\21\16\21\u0104\3\22\3\22\3\22\3\22\3\22"+
		"\5\22\u010c\n\22\3\22\3\22\3\22\5\22\u0111\n\22\7\22\u0113\n\22\f\22\16"+
		"\22\u0116\13\22\3\22\3\22\3\23\3\23\3\23\5\23\u011d\n\23\3\24\5\24\u0120"+
		"\n\24\3\24\3\24\3\24\3\24\5\24\u0126\n\24\3\24\3\24\3\24\5\24\u012b\n"+
		"\24\7\24\u012d\n\24\f\24\16\24\u0130\13\24\3\24\3\24\3\25\3\25\3\26\3"+
		"\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\7\32\u0140\n\32\f\32\16\32"+
		"\u0143\13\32\3\33\3\33\3\33\2\3\24\34\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\60\62\64\2\7\3\2\36\37\3\2 !\3\2\"#\4\2\36\37\'*\4\2\26"+
		"\27%&\2\u0160\2\66\3\2\2\2\4?\3\2\2\2\6A\3\2\2\2\bF\3\2\2\2\n[\3\2\2\2"+
		"\f\u0081\3\2\2\2\16\u0085\3\2\2\2\20\u0094\3\2\2\2\22\u00a3\3\2\2\2\24"+
		"\u00b1\3\2\2\2\26\u00c1\3\2\2\2\30\u00dd\3\2\2\2\32\u00e7\3\2\2\2\34\u00f5"+
		"\3\2\2\2\36\u00f9\3\2\2\2 \u00ff\3\2\2\2\"\u0106\3\2\2\2$\u011c\3\2\2"+
		"\2&\u011f\3\2\2\2(\u0133\3\2\2\2*\u0135\3\2\2\2,\u0137\3\2\2\2.\u0139"+
		"\3\2\2\2\60\u013b\3\2\2\2\62\u013d\3\2\2\2\64\u0144\3\2\2\2\66\67\5\4"+
		"\3\2\678\7\2\2\38\3\3\2\2\29:\7\3\2\2:;\5\4\3\2;<\7\3\2\2<@\3\2\2\2=@"+
		"\5\f\7\2>@\5\b\5\2?9\3\2\2\2?=\3\2\2\2?>\3\2\2\2@\5\3\2\2\2AB\7\4\2\2"+
		"BC\7\32\2\2CD\7\25\2\2DE\7\34\2\2E\7\3\2\2\2FG\7\22\2\2GI\7\32\2\2HJ\7"+
		"\r\2\2IH\3\2\2\2IJ\3\2\2\2JK\3\2\2\2KS\7\30\2\2LN\7\5\2\2MO\7\r\2\2NM"+
		"\3\2\2\2NO\3\2\2\2OP\3\2\2\2PR\7\30\2\2QL\3\2\2\2RU\3\2\2\2SQ\3\2\2\2"+
		"ST\3\2\2\2TV\3\2\2\2US\3\2\2\2VW\7\34\2\2WX\7\32\2\2XY\5\4\3\2YZ\7\34"+
		"\2\2Z\t\3\2\2\2[]\7\23\2\2\\^\7\r\2\2]\\\3\2\2\2]^\3\2\2\2^_\3\2\2\2_"+
		"e\7\30\2\2`a\7\5\2\2ab\5\30\r\2bc\7\5\2\2cd\5\30\r\2df\3\2\2\2e`\3\2\2"+
		"\2ef\3\2\2\2fg\3\2\2\2gh\7\5\2\2hi\5\4\3\2i\13\3\2\2\2jp\5\20\t\2kl\5"+
		"\60\31\2lm\5\f\7\2mo\3\2\2\2nk\3\2\2\2or\3\2\2\2pn\3\2\2\2pq\3\2\2\2q"+
		"\u0082\3\2\2\2rp\3\2\2\2su\7\33\2\2ts\3\2\2\2tu\3\2\2\2uv\3\2\2\2vw\7"+
		"\32\2\2wx\5\f\7\2x~\7\34\2\2yz\5\60\31\2z{\5\f\7\2{}\3\2\2\2|y\3\2\2\2"+
		"}\u0080\3\2\2\2~|\3\2\2\2~\177\3\2\2\2\177\u0082\3\2\2\2\u0080~\3\2\2"+
		"\2\u0081j\3\2\2\2\u0081t\3\2\2\2\u0082\r\3\2\2\2\u0083\u0086\5\26\f\2"+
		"\u0084\u0086\5&\24\2\u0085\u0083\3\2\2\2\u0085\u0084\3\2\2\2\u0086\u0087"+
		"\3\2\2\2\u0087\u008a\t\2\2\2\u0088\u008b\5\26\f\2\u0089\u008b\5&\24\2"+
		"\u008a\u0088\3\2\2\2\u008a\u0089\3\2\2\2\u008b\17\3\2\2\2\u008c\u0095"+
		"\3\2\2\2\u008d\u008e\7\33\2\2\u008e\u0095\5\20\t\2\u008f\u0095\5\6\4\2"+
		"\u0090\u0095\5\22\n\2\u0091\u0095\5\b\5\2\u0092\u0095\7\16\2\2\u0093\u0095"+
		"\7\17\2\2\u0094\u008c\3\2\2\2\u0094\u008d\3\2\2\2\u0094\u008f\3\2\2\2"+
		"\u0094\u0090\3\2\2\2\u0094\u0091\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0093"+
		"\3\2\2\2\u0095\21\3\2\2\2\u0096\u0097\7\32\2\2\u0097\u0098\5\22\n\2\u0098"+
		"\u0099\7\34\2\2\u0099\u00a4\3\2\2\2\u009a\u00a0\5\24\13\2\u009b\u009c"+
		"\5*\26\2\u009c\u009d\5\24\13\2\u009d\u009f\3\2\2\2\u009e\u009b\3\2\2\2"+
		"\u009f\u00a2\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a4"+
		"\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a3\u0096\3\2\2\2\u00a3\u009a\3\2\2\2\u00a4"+
		"\23\3\2\2\2\u00a5\u00a6\b\13\1\2\u00a6\u00a7\7\32\2\2\u00a7\u00a8\5\24"+
		"\13\2\u00a8\u00a9\7\34\2\2\u00a9\u00b2\3\2\2\2\u00aa\u00ac\t\3\2\2\u00ab"+
		"\u00aa\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2"+
		"\2\2\u00ae\u00b0\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\u00b2\5\30\r\2\u00b1"+
		"\u00a5\3\2\2\2\u00b1\u00ad\3\2\2\2\u00b2\u00be\3\2\2\2\u00b3\u00b4\f\7"+
		"\2\2\u00b4\u00b5\7\35\2\2\u00b5\u00bd\5\24\13\b\u00b6\u00b7\f\6\2\2\u00b7"+
		"\u00b8\t\4\2\2\u00b8\u00bd\5\24\13\7\u00b9\u00ba\f\5\2\2\u00ba\u00bb\t"+
		"\3\2\2\u00bb\u00bd\5\24\13\6\u00bc\u00b3\3\2\2\2\u00bc\u00b6\3\2\2\2\u00bc"+
		"\u00b9\3\2\2\2\u00bd\u00c0\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2"+
		"\2\2\u00bf\25\3\2\2\2\u00c0\u00be\3\2\2\2\u00c1\u00c2\7\32\2\2\u00c2\u00c7"+
		"\5\30\r\2\u00c3\u00c4\7\6\2\2\u00c4\u00c6\5\30\r\2\u00c5\u00c3\3\2\2\2"+
		"\u00c6\u00c9\3\2\2\2\u00c7\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00ca"+
		"\3\2\2\2\u00c9\u00c7\3\2\2\2\u00ca\u00cb\7\34\2\2\u00cb\27\3\2\2\2\u00cc"+
		"\u00de\5\34\17\2\u00cd\u00de\5\36\20\2\u00ce\u00de\5 \21\2\u00cf\u00de"+
		"\5\"\22\2\u00d0\u00de\5&\24\2\u00d1\u00de\5\32\16\2\u00d2\u00de\5\64\33"+
		"\2\u00d3\u00de\7\31\2\2\u00d4\u00d5\7\32\2\2\u00d5\u00d6\7!\2\2\u00d6"+
		"\u00d7\5\30\r\2\u00d7\u00d8\7\34\2\2\u00d8\u00de\3\2\2\2\u00d9\u00da\7"+
		"\32\2\2\u00da\u00db\5\30\r\2\u00db\u00dc\7\34\2\2\u00dc\u00de\3\2\2\2"+
		"\u00dd\u00cc\3\2\2\2\u00dd\u00cd\3\2\2\2\u00dd\u00ce\3\2\2\2\u00dd\u00cf"+
		"\3\2\2\2\u00dd\u00d0\3\2\2\2\u00dd\u00d1\3\2\2\2\u00dd\u00d2\3\2\2\2\u00dd"+
		"\u00d3\3\2\2\2\u00dd\u00d4\3\2\2\2\u00dd\u00d9\3\2\2\2\u00de\31\3\2\2"+
		"\2\u00df\u00e0\7\24\2\2\u00e0\u00e8\7\20\2\2\u00e1\u00e2\7\30\2\2\u00e2"+
		"\u00e4\7\7\2\2\u00e3\u00e1\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e3\3\2"+
		"\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e8\3\2\2\2\u00e7\u00df\3\2\2\2\u00e7"+
		"\u00e3\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00f1\5\64"+
		"\33\2\u00ea\u00ed\7\b\2\2\u00eb\u00ee\5\30\r\2\u00ec\u00ee\5\4\3\2\u00ed"+
		"\u00eb\3\2\2\2\u00ed\u00ec\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\7\t"+
		"\2\2\u00f0\u00f2\3\2\2\2\u00f1\u00ea\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3"+
		"\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\33\3\2\2\2\u00f5\u00f6\7\24\2"+
		"\2\u00f6\u00f7\7\20\2\2\u00f7\u00f8\7\30\2\2\u00f8\35\3\2\2\2\u00f9\u00fa"+
		"\7\30\2\2\u00fa\u00fd\7\20\2\2\u00fb\u00fe\7\30\2\2\u00fc\u00fe\5\32\16"+
		"\2\u00fd\u00fb\3\2\2\2\u00fd\u00fc\3\2\2\2\u00fe\37\3\2\2\2\u00ff\u0102"+
		"\7\30\2\2\u0100\u0101\7\7\2\2\u0101\u0103\7\30\2\2\u0102\u0100\3\2\2\2"+
		"\u0103\u0104\3\2\2\2\u0104\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105!\3"+
		"\2\2\2\u0106\u0107\7\n\2\2\u0107\u0108\5(\25\2\u0108\u010b\7\32\2\2\u0109"+
		"\u010c\5\24\13\2\u010a\u010c\5\4\3\2\u010b\u0109\3\2\2\2\u010b\u010a\3"+
		"\2\2\2\u010c\u0114\3\2\2\2\u010d\u0110\7\6\2\2\u010e\u0111\5\24\13\2\u010f"+
		"\u0111\5\4\3\2\u0110\u010e\3\2\2\2\u0110\u010f\3\2\2\2\u0111\u0113\3\2"+
		"\2\2\u0112\u010d\3\2\2\2\u0113\u0116\3\2\2\2\u0114\u0112\3\2\2\2\u0114"+
		"\u0115\3\2\2\2\u0115\u0117\3\2\2\2\u0116\u0114\3\2\2\2\u0117\u0118\7\34"+
		"\2\2\u0118#\3\2\2\2\u0119\u011a\7\30\2\2\u011a\u011d\7\7\2\2\u011b\u011d"+
		"\7\13\2\2\u011c\u0119\3\2\2\2\u011c\u011b\3\2\2\2\u011d%\3\2\2\2\u011e"+
		"\u0120\5$\23\2\u011f\u011e\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u0121\3\2"+
		"\2\2\u0121\u0122\5(\25\2\u0122\u0125\7\32\2\2\u0123\u0126\5\24\13\2\u0124"+
		"\u0126\5\4\3\2\u0125\u0123\3\2\2\2\u0125\u0124\3\2\2\2\u0126\u012e\3\2"+
		"\2\2\u0127\u012a\7\6\2\2\u0128\u012b\5\24\13\2\u0129\u012b\5\4\3\2\u012a"+
		"\u0128\3\2\2\2\u012a\u0129\3\2\2\2\u012b\u012d\3\2\2\2\u012c\u0127\3\2"+
		"\2\2\u012d\u0130\3\2\2\2\u012e\u012c\3\2\2\2\u012e\u012f\3\2\2\2\u012f"+
		"\u0131\3\2\2\2\u0130\u012e\3\2\2\2\u0131\u0132\7\34\2\2\u0132\'\3\2\2"+
		"\2\u0133\u0134\7\30\2\2\u0134)\3\2\2\2\u0135\u0136\t\5\2\2\u0136+\3\2"+
		"\2\2\u0137\u0138\t\4\2\2\u0138-\3\2\2\2\u0139\u013a\t\3\2\2\u013a/\3\2"+
		"\2\2\u013b\u013c\t\6\2\2\u013c\61\3\2\2\2\u013d\u0141\7\f\2\2\u013e\u0140"+
		"\7$\2\2\u013f\u013e\3\2\2\2\u0140\u0143\3\2\2\2\u0141\u013f\3\2\2\2\u0141"+
		"\u0142\3\2\2\2\u0142\63\3\2\2\2\u0143\u0141\3\2\2\2\u0144\u0145\7\30\2"+
		"\2\u0145\65\3\2\2\2&?INS]ept~\u0081\u0085\u008a\u0094\u00a0\u00a3\u00ad"+
		"\u00b1\u00bc\u00be\u00c7\u00dd\u00e5\u00e7\u00ed\u00f3\u00fd\u0104\u010b"+
		"\u0110\u0114\u011c\u011f\u0125\u012a\u012e\u0141";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}