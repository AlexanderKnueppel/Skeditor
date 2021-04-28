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
		mathematicalExpression
		(
			compoperator mathematicalExpression
		)*
	)
;

mathematicalExpression
   :  mathematicalExpression  POWER mathematicalExpression
   |  mathematicalExpression  (MULTI | DIVISION)  mathematicalExpression
   |  mathematicalExpression  (ADD | MINUS) mathematicalExpression
   |  LPAREN mathematicalExpression RPAREN
   |  (ADD | MINUS)* term
   ;

tupel
:
	LPAREN term
	(
		',' term
	)* RPAREN
;
	
term
:
	compproperty
	| portproperty
	| port
	| predicate
	| functioncall
	| array
	| variable
	| SCIENTIFIC_NUMBER
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

predicate
:
	'#' functionname LPAREN
	(
		mathematicalExpression
		| formula
	)
	(
		','
		(
			mathematicalExpression
			| formula
		)
	)* RPAREN
;

prefix
:
    IDENTIFIER '.'
    | '\\'
;

functioncall
:
	prefix? functionname LPAREN
	(
		mathematicalExpression
		| formula
	)
	(
		','
		(
			mathematicalExpression
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
	| LEQUI
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

CONJ
:
	'&&' | '&' | 'and'
;

DISJ
:
	'||' | '|' | 'or'
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

//The NUMBER part gets its potential sign from "(PLUS | MINUS)* atom" in the expression rule
SCIENTIFIC_NUMBER
   : NUMBER (E SIGN? UNSIGNED_INTEGER)?
   ;

fragment NUMBER
   : [0-9]+ ('.' [0-9]+)?
   ;

fragment UNSIGNED_INTEGER
   : [0-9]+
   ;


fragment E
   : 'E' | 'e'
   ;


fragment SIGN
   : ('+' | '-')
   ;

/*NUMBER
:
	[0-9]+ ('.' [0-9]+)?
;*/

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
	'='
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

LEQUI
:
	'<->'
;

IMPL
:
	'->'
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

