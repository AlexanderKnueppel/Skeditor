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
	//| tupelformula
	| connectiveformula
	| quantifier
	//| operatorformula
	//| hasconditionformula
	//| pred_constant LPAREN term
	
	//(
		//';' term
	//)* RPAREN
;

hascondition
:
	'has' LPAREN STRING RPAREN
;
quantifier
:
	QUANTIFER LPAREN TYPE? IDENTIFIER
	(
		';' TYPE? IDENTIFIER
	)* RPAREN
	LPAREN formula RPAREN
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
	boolexpression (connectoperator connectiveformula)*
	| NOT? LPAREN connectiveformula RPAREN (connectoperator connectiveformula)*
	
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

boolexpression
:
	(
	| NOT boolexpression
	| hascondition
	| compareformula
	| quantifier
	| TRUE
	| FALSE
	)
;
compareformula
:
LPAREN compareformula RPAREN
	|
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
LPAREN summformula RPAREN
	|
	(
		faktorformula
		(
			addoperator summformula
		)*
	)
;

faktorformula
:
	LPAREN faktorformula RPAREN
	|
	(
		powerformula
		(
			multoperator faktorformula
		)*
	)
;

powerformula
:
	LPAREN powerformula LPAREN
	|
	(
		term
		(
			POWER powerformula
		)*
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
	//| STRING
	|
	(
		LPAREN term RPAREN
	)
	//|
	//(
		//LPAREN formula RPAREN
	//)
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
	[0-9]+ ('.' [0-9]+)?
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
	| '_'
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

