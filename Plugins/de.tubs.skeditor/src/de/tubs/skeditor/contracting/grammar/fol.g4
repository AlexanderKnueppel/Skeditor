/*
* FOL with arithmetic
*
*/

grammar fol;

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

 condition
   :formula EOF
   ;
 formula
   : formula bin_connective formula
   | NOT formula bin_connective formula
   | NOT formula 
   | FORALL LPAREN variable RPAREN formula 
   | EXISTS LPAREN variable RPAREN formula
   | LPAREN formula RPAREN
   | term binop term
   | term
   | BOOL_LITERAL
   ;

term
   : (PLUS|MINUS)* scientific
   | (PLUS|MINUS)* variable
   | LPAREN term RPAREN
   | term (PLUS | MINUS) term
   | term (TIMES | DIV) term
   ;
   
scientific
   : SCIENTIFIC_NUMBER
   ;

variable
   : VARIABLE
   ;

binop
   : EQUAL
   | LEQ
   | LT
   | GEQ
   | GT
   ;
   
BOOL_LITERAL:       'true'
            |       'false'
            ;

bin_connective
   : CONJ
   | DISJ
   | IMPL
   | BICOND
   ;
//used in FORALL|EXISTS and following predicates
VARIABLE
   : VALID_ID_START VALID_ID_CHAR*
   ;


fragment VALID_ID_START
   : ('a' .. 'z') | ('A' .. 'Z') | '_'
   ;


fragment VALID_ID_CHAR
   : VALID_ID_START | ('0' .. '9')
   ;
   
//The NUMBER part gets its potential sign from "(PLUS | MINUS)* atom" in the expression rule
SCIENTIFIC_NUMBER
   : NUMBER (E SIGN? UNSIGNED_INTEGER)?
   ;

fragment NUMBER
   : ('0' .. '9') + ('.' ('0' .. '9') +)?
   ;

fragment UNSIGNED_INTEGER
   : ('0' .. '9')+
   ;


fragment E
   : 'E' | 'e'
   ;


fragment SIGN
   : ('+' | '-')
   ;

LPAREN
   :'('
   ;
RPAREN
   :')'
   ;
   
PLUS
   : '+'
   ;


MINUS
   : '-'
   ;


TIMES
   : '*'
   ;


DIV
   : '/'
   ;


GT
   : '>'
   ;


LT
   : '<'
   ;
GEQ
   : '>='
   ;


LEQ
   : '<='
   ;
   
separator
   :','
   ;
EQUAL
   :'='
   ;
NOT
   :'!'
   ;
FORALL
   :'Forall'
   ;
EXISTS
   :'Exists'
   ;
CHARACTER
   :('0' .. '9' | 'a' .. 'z' | 'A' .. 'Z')
   ;
CONJ
   :'&'
   ;
DISJ
   :'|'
   ;
IMPL
   :'->'
   ;
BICOND
   :'<->'
   ;
ENDLINE
   :('\r'|'\n')+
   ;
WHITESPACE
   :(' '|'\t')+->skip
   ;