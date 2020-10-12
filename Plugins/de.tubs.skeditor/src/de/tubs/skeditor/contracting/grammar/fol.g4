/*
* FOL with arithmetic
*
*/

grammar fol;

condition
:
	formula EOF
	
;

formula
:
	'"' formula '"' 
	| tupelformula
	| connectiveformula
	| quantifier
	| operatorformula
	| pred_constant LPAREN term
	
	(
		';' term
	)* RPAREN
;

quantifier
:
	QUANTIFER TYPE? IDENTIFIER
	(
		';' TYPE? IDENTIFIER
	)*
	(
		';' formula
	)? ';' formula
;

operatorformula
:
	OPERATOR TYPE? IDENTIFIER
	(
		';' term ';'  term
	)? ';' formula
;

connectiveformula
:
	(
		compareformula
		(
			connectoperator compareformula
		)*
	)
	
;
 
tupelformula
:
	(
		tupel
		| functioncall
	) (EQUAL | NEQUAL)
	(
		tupel
		| functioncall
	)
;

compareformula
:
	(
		summformula
		(
			compoperator summformula
		)*
	)
;

tupel
:
	LPAREN term
	(
		',' term
	)* RPAREN
;

summformula
:
	(
		faktorformula
		(
			addoperator faktorformula
		)*
	)
;

faktorformula
:
	(
		powerformula
		(
			multoperator powerformula
		)*
	)
;

powerformula
:
	(
		notterm
		(
			POWER notterm
		)*
	)
;

notterm
:
	term
	|
	(
		NOT term
	)
;

term
:
	compproperty
	| portproperty
	| port
	| functioncall
	| array
	| variable
	| NUMBER
	|
	(
		LPAREN MINUS
		(
			term
		) RPAREN
	)
	| TRUE
	| FALSE
	| STRING
	|
	(
		LPAREN term RPAREN
	)
	|
	(
		LPAREN formula RPAREN
	)
;

array
:
	(
		(
			COMPONENT CODEWORD
		)
		|
		(
			IDENTIFIER '.' 
		)+
	)? variable
	(
		'['
		(
			term
			| formula
		) ']'
	)+
;

compproperty
:
	COMPONENT CODEWORD IDENTIFIER
;

portproperty
:
	IDENTIFIER CODEWORD
	(
		IDENTIFIER
		| array
	)
;

port
:
	IDENTIFIER
	(
		'.' IDENTIFIER
	)+
;

functioncall
:
	IDENTIFIER '.' functionname LPAREN
	(
		term
		| formula
	)
	(
		','
		(
			term
			| formula
		)
	)* RPAREN
;

functionname
:
	IDENTIFIER
;

compoperator
:
	GREATER
	| SMALLER
	| SMALLEREQ
	| GREATEREQ
	| EQUAL
	| NEQUAL
;

multoperator
:
	MULTI
	| DIVISION
;

addoperator
:
	MINUS
	| ADD
;

connectoperator
:
	CONJ
	| DISJ
	| IMPL
	| BICOND
;

pred_constant
:
	'_' CHARACTER*
;

variable
:
	IDENTIFIER
;

TYPE
:
	'int'
	| 'float'
;

TRUE
:
	'\\true'
;

FALSE
:
	'\\false'
;

CODEWORD
:
	'.$'
;

NULL
:
	'\\null'
;

QUANTIFER
:
	'\\forall'
	| '\\exists'
;

OPERATOR
:
	'\\sum'
	| '\\product'
	| '\\max'
	| '\\min'
;

COMPONENT
:
	'\\super'
	| '\\this'
;

STRING
:
	'"' CHARACTER+ '"'
;

IDENTIFIER
:(
[A-Z]
		| [a-z]
		| '_')
	(
		[A-Z]
		| [a-z]
		| '_'
		| NUMBER
	)*
;

NUMBER
:
	[0-9]+
;

LPAREN
:
	'('
;

NOT
:
	'!'
;

RPAREN
:
	')'
;

POWER
:
	'^'
;

EQUAL
:
	'=='
;

NEQUAL
:
	'!='
;

ADD
:
	'+'
;

MINUS
:
	'-'
;

MULTI
:
	'*'
;

DIVISION
:
	'/'
;

CHARACTER
:
	[0-9]
	| [A-Z]
	| [a-z]
	| '.'
;

CONJ
:
	'&&' | '&' | 'and'
;

DISJ
:
	'||' | '|' | 'or'
;

IMPL
:
	'=>'
;

BICOND
:
	'<>'
;

GREATER
:
	'>'
;

SMALLER
:
	'<'
;

SMALLEREQ
:
	'<='
;

GREATEREQ
:
	'>='
;

WHITESPACE
:
	(
		' '
		| '\t'
		| '\n'
	)+ -> skip
; 