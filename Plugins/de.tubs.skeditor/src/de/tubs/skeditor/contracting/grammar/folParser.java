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
		T__0=1, HAS=2, BOOL_LITERAL=3, VARIABLE=4, SCIENTIFIC_NUMBER=5, LPAREN=6, 
		RPAREN=7, PLUS=8, MINUS=9, TIMES=10, DIV=11, GT=12, LT=13, GEQ=14, LEQ=15, 
		EQUAL=16, NOT=17, FORALL=18, EXISTS=19, CHARACTER=20, CONJ=21, DISJ=22, 
		IMPL=23, BICOND=24, ENDLINE=25, WHITESPACE=26, DOUBLEQUOTE=27, SINGLEQUOTE=28;
	public static final int
		RULE_condition = 0, RULE_formula = 1, RULE_has_condition = 2, RULE_term = 3, 
		RULE_scientific = 4, RULE_variable = 5, RULE_binop = 6, RULE_has_skill = 7, 
		RULE_skill_name = 8, RULE_bin_connective = 9, RULE_arith_operation = 10, 
		RULE_separator = 11;
	public static final String[] ruleNames = {
		"condition", "formula", "has_condition", "term", "scientific", "variable", 
		"binop", "has_skill", "skill_name", "bin_connective", "arith_operation", 
		"separator"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "','", "'has('", null, null, null, "'('", "')'", "'+'", "'-'", "'*'", 
		"'/'", "'>'", "'<'", "'>='", "'<='", "'='", "'!'", "'Forall'", "'Exists'", 
		null, "'&'", "'|'", "'->'", "'<->'", null, null, "'\"'", "'''"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, "HAS", "BOOL_LITERAL", "VARIABLE", "SCIENTIFIC_NUMBER", "LPAREN", 
		"RPAREN", "PLUS", "MINUS", "TIMES", "DIV", "GT", "LT", "GEQ", "LEQ", "EQUAL", 
		"NOT", "FORALL", "EXISTS", "CHARACTER", "CONJ", "DISJ", "IMPL", "BICOND", 
		"ENDLINE", "WHITESPACE", "DOUBLEQUOTE", "SINGLEQUOTE"
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
		public Has_conditionContext has_condition() {
			return getRuleContext(Has_conditionContext.class,0);
		}
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
			setState(30);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(24);
				formula(0);
				setState(25);
				match(EOF);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(27);
				has_condition(0);
				setState(28);
				match(EOF);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitFormula(this);
			else return visitor.visitChildren(this);
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
			setState(62);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(33);
				match(NOT);
				setState(34);
				formula(0);
				setState(35);
				bin_connective();
				setState(36);
				formula(8);
				}
				break;
			case 2:
				{
				setState(38);
				match(NOT);
				setState(39);
				formula(7);
				}
				break;
			case 3:
				{
				setState(40);
				match(FORALL);
				setState(41);
				match(LPAREN);
				setState(42);
				variable();
				setState(43);
				match(RPAREN);
				setState(44);
				formula(6);
				}
				break;
			case 4:
				{
				setState(46);
				match(EXISTS);
				setState(47);
				match(LPAREN);
				setState(48);
				variable();
				setState(49);
				match(RPAREN);
				setState(50);
				formula(5);
				}
				break;
			case 5:
				{
				setState(52);
				match(LPAREN);
				setState(53);
				formula(0);
				setState(54);
				match(RPAREN);
				}
				break;
			case 6:
				{
				setState(56);
				term(0);
				setState(57);
				binop();
				setState(58);
				term(0);
				}
				break;
			case 7:
				{
				setState(60);
				term(0);
				}
				break;
			case 8:
				{
				setState(61);
				match(BOOL_LITERAL);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(70);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new FormulaContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_formula);
					setState(64);
					if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
					setState(65);
					bin_connective();
					setState(66);
					formula(10);
					}
					} 
				}
				setState(72);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
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

	public static class Has_conditionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(folParser.LPAREN, 0); }
		public List<Has_conditionContext> has_condition() {
			return getRuleContexts(Has_conditionContext.class);
		}
		public Has_conditionContext has_condition(int i) {
			return getRuleContext(Has_conditionContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public TerminalNode NOT() { return getToken(folParser.NOT, 0); }
		public Has_skillContext has_skill() {
			return getRuleContext(Has_skillContext.class,0);
		}
		public Bin_connectiveContext bin_connective() {
			return getRuleContext(Bin_connectiveContext.class,0);
		}
		public Has_conditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_has_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterHas_condition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitHas_condition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitHas_condition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Has_conditionContext has_condition() throws RecognitionException {
		return has_condition(0);
	}

	private Has_conditionContext has_condition(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Has_conditionContext _localctx = new Has_conditionContext(_ctx, _parentState);
		Has_conditionContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_has_condition, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				{
				setState(74);
				match(LPAREN);
				setState(75);
				has_condition(0);
				setState(76);
				match(RPAREN);
				}
				break;
			case NOT:
				{
				setState(78);
				match(NOT);
				setState(79);
				has_condition(2);
				}
				break;
			case HAS:
				{
				setState(80);
				has_skill();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(89);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Has_conditionContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_has_condition);
					setState(83);
					if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
					setState(84);
					bin_connective();
					setState(85);
					has_condition(5);
					}
					} 
				}
				setState(91);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
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
		public TerminalNode PLUS() { return getToken(folParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(folParser.MINUS, 0); }
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
		public Arith_operationContext arith_operation() {
			return getRuleContext(Arith_operationContext.class,0);
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
		return term(0);
	}

	private TermContext term(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TermContext _localctx = new TermContext(_ctx, _parentState);
		TermContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_term, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(94);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PLUS || _la==MINUS) {
					{
					setState(93);
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

				setState(96);
				scientific();
				}
				break;
			case 2:
				{
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PLUS || _la==MINUS) {
					{
					setState(97);
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

				setState(100);
				variable();
				}
				break;
			case 3:
				{
				setState(101);
				match(LPAREN);
				setState(102);
				term(0);
				setState(103);
				match(RPAREN);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(113);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TermContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_term);
					setState(107);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(108);
					arith_operation();
					setState(109);
					term(2);
					}
					} 
				}
				setState(115);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitScientific(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScientificContext scientific() throws RecognitionException {
		ScientificContext _localctx = new ScientificContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_scientific);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitBinop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinopContext binop() throws RecognitionException {
		BinopContext _localctx = new BinopContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_binop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
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

	public static class Has_skillContext extends ParserRuleContext {
		public TerminalNode HAS() { return getToken(folParser.HAS, 0); }
		public List<TerminalNode> DOUBLEQUOTE() { return getTokens(folParser.DOUBLEQUOTE); }
		public TerminalNode DOUBLEQUOTE(int i) {
			return getToken(folParser.DOUBLEQUOTE, i);
		}
		public Skill_nameContext skill_name() {
			return getRuleContext(Skill_nameContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(folParser.RPAREN, 0); }
		public List<TerminalNode> SINGLEQUOTE() { return getTokens(folParser.SINGLEQUOTE); }
		public TerminalNode SINGLEQUOTE(int i) {
			return getToken(folParser.SINGLEQUOTE, i);
		}
		public Has_skillContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_has_skill; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterHas_skill(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitHas_skill(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitHas_skill(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Has_skillContext has_skill() throws RecognitionException {
		Has_skillContext _localctx = new Has_skillContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_has_skill);
		try {
			setState(134);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(122);
				match(HAS);
				setState(123);
				match(DOUBLEQUOTE);
				setState(124);
				skill_name();
				setState(125);
				match(DOUBLEQUOTE);
				setState(126);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(128);
				match(HAS);
				setState(129);
				match(SINGLEQUOTE);
				setState(130);
				skill_name();
				setState(131);
				match(SINGLEQUOTE);
				setState(132);
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

	public static class Skill_nameContext extends ParserRuleContext {
		public TerminalNode VARIABLE() { return getToken(folParser.VARIABLE, 0); }
		public Skill_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_skill_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterSkill_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitSkill_name(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitSkill_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Skill_nameContext skill_name() throws RecognitionException {
		Skill_nameContext _localctx = new Skill_nameContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_skill_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitBin_connective(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Bin_connectiveContext bin_connective() throws RecognitionException {
		Bin_connectiveContext _localctx = new Bin_connectiveContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_bin_connective);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
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

	public static class Arith_operationContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(folParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(folParser.MINUS, 0); }
		public TerminalNode TIMES() { return getToken(folParser.TIMES, 0); }
		public TerminalNode DIV() { return getToken(folParser.DIV, 0); }
		public Arith_operationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arith_operation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).enterArith_operation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof folListener ) ((folListener)listener).exitArith_operation(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitArith_operation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Arith_operationContext arith_operation() throws RecognitionException {
		Arith_operationContext _localctx = new Arith_operationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_arith_operation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << TIMES) | (1L << DIV))) != 0)) ) {
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
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof folVisitor ) return ((folVisitor<? extends T>)visitor).visitSeparator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SeparatorContext separator() throws RecognitionException {
		SeparatorContext _localctx = new SeparatorContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_separator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
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
			return has_condition_sempred((Has_conditionContext)_localctx, predIndex);
		case 3:
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
	private boolean has_condition_sempred(Has_conditionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 4);
		}
		return true;
	}
	private boolean term_sempred(TermContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\36\u0093\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\3\2\3\2\5\2!\n\2\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3A\n\3\3\3\3\3\3\3\3\3\7\3G\n\3\f"+
		"\3\16\3J\13\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4T\n\4\3\4\3\4\3\4\3\4"+
		"\7\4Z\n\4\f\4\16\4]\13\4\3\5\3\5\5\5a\n\5\3\5\3\5\5\5e\n\5\3\5\3\5\3\5"+
		"\3\5\3\5\5\5l\n\5\3\5\3\5\3\5\3\5\7\5r\n\5\f\5\16\5u\13\5\3\6\3\6\3\7"+
		"\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u0089"+
		"\n\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\2\5\4\6\b\16\2\4\6\b\n\f\16"+
		"\20\22\24\26\30\2\6\3\2\n\13\3\2\16\22\3\2\27\32\3\2\n\r\2\u0098\2 \3"+
		"\2\2\2\4@\3\2\2\2\6S\3\2\2\2\bk\3\2\2\2\nv\3\2\2\2\fx\3\2\2\2\16z\3\2"+
		"\2\2\20\u0088\3\2\2\2\22\u008a\3\2\2\2\24\u008c\3\2\2\2\26\u008e\3\2\2"+
		"\2\30\u0090\3\2\2\2\32\33\5\4\3\2\33\34\7\2\2\3\34!\3\2\2\2\35\36\5\6"+
		"\4\2\36\37\7\2\2\3\37!\3\2\2\2 \32\3\2\2\2 \35\3\2\2\2!\3\3\2\2\2\"#\b"+
		"\3\1\2#$\7\23\2\2$%\5\4\3\2%&\5\24\13\2&\'\5\4\3\n\'A\3\2\2\2()\7\23\2"+
		"\2)A\5\4\3\t*+\7\24\2\2+,\7\b\2\2,-\5\f\7\2-.\7\t\2\2./\5\4\3\b/A\3\2"+
		"\2\2\60\61\7\25\2\2\61\62\7\b\2\2\62\63\5\f\7\2\63\64\7\t\2\2\64\65\5"+
		"\4\3\7\65A\3\2\2\2\66\67\7\b\2\2\678\5\4\3\289\7\t\2\29A\3\2\2\2:;\5\b"+
		"\5\2;<\5\16\b\2<=\5\b\5\2=A\3\2\2\2>A\5\b\5\2?A\7\5\2\2@\"\3\2\2\2@(\3"+
		"\2\2\2@*\3\2\2\2@\60\3\2\2\2@\66\3\2\2\2@:\3\2\2\2@>\3\2\2\2@?\3\2\2\2"+
		"AH\3\2\2\2BC\f\13\2\2CD\5\24\13\2DE\5\4\3\fEG\3\2\2\2FB\3\2\2\2GJ\3\2"+
		"\2\2HF\3\2\2\2HI\3\2\2\2I\5\3\2\2\2JH\3\2\2\2KL\b\4\1\2LM\7\b\2\2MN\5"+
		"\6\4\2NO\7\t\2\2OT\3\2\2\2PQ\7\23\2\2QT\5\6\4\4RT\5\20\t\2SK\3\2\2\2S"+
		"P\3\2\2\2SR\3\2\2\2T[\3\2\2\2UV\f\6\2\2VW\5\24\13\2WX\5\6\4\7XZ\3\2\2"+
		"\2YU\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\\7\3\2\2\2][\3\2\2\2^`\b"+
		"\5\1\2_a\t\2\2\2`_\3\2\2\2`a\3\2\2\2ab\3\2\2\2bl\5\n\6\2ce\t\2\2\2dc\3"+
		"\2\2\2de\3\2\2\2ef\3\2\2\2fl\5\f\7\2gh\7\b\2\2hi\5\b\5\2ij\7\t\2\2jl\3"+
		"\2\2\2k^\3\2\2\2kd\3\2\2\2kg\3\2\2\2ls\3\2\2\2mn\f\3\2\2no\5\26\f\2op"+
		"\5\b\5\4pr\3\2\2\2qm\3\2\2\2ru\3\2\2\2sq\3\2\2\2st\3\2\2\2t\t\3\2\2\2"+
		"us\3\2\2\2vw\7\7\2\2w\13\3\2\2\2xy\7\6\2\2y\r\3\2\2\2z{\t\3\2\2{\17\3"+
		"\2\2\2|}\7\4\2\2}~\7\35\2\2~\177\5\22\n\2\177\u0080\7\35\2\2\u0080\u0081"+
		"\7\t\2\2\u0081\u0089\3\2\2\2\u0082\u0083\7\4\2\2\u0083\u0084\7\36\2\2"+
		"\u0084\u0085\5\22\n\2\u0085\u0086\7\36\2\2\u0086\u0087\7\t\2\2\u0087\u0089"+
		"\3\2\2\2\u0088|\3\2\2\2\u0088\u0082\3\2\2\2\u0089\21\3\2\2\2\u008a\u008b"+
		"\7\6\2\2\u008b\23\3\2\2\2\u008c\u008d\t\4\2\2\u008d\25\3\2\2\2\u008e\u008f"+
		"\t\5\2\2\u008f\27\3\2\2\2\u0090\u0091\7\3\2\2\u0091\31\3\2\2\2\f @HS["+
		"`dks\u0088";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}