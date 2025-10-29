grammar Nottscript;
//Parser rules

//Lexer rules
//Keywords
ALLOCATE: 'allocate';
BREAK: 'break';
CALL: 'call';
CHARACTER: 'character';
DEALLOCATE: 'deallocate';
DO: 'do';
ELSE: 'else';
END: 'end';
FUNCTION: 'function';
IF: 'if';
INTEGER: 'integer';
LOGICAL: 'logical';
POINTER: 'pointer';
PROGRAM: 'program';
READ: 'read';
REAL: 'real';
RESULT: 'result';
SUBROUTINE: 'subroutine';
THEN: 'then';
TYPE: 'type';
WHILE: 'while';
WRITE: 'write';
//Operators
RIGHTBRACKET: ')';
LEFTBRACKET: '(';
PLUS: '+';
MINUS: '-';
DIV: '/';
MUL: '*';
OR: '.OR.';
AND: '.AND.';
CONCAT: '//';
//Relational
LT: '<'
    | '.lt.';
GT: '>'
    | '.gt.';
LEQ: '<='
    | '.le.';
GEQ: '>='
    | '.ge.';
EQ: '=='
    | '.eq.';
NEQ: '/='
    | '.neq.';
//OTHER THINGS
ASSIGN: '=';
COLON: ':';
DBLCOL: '::';
COMMA: ',';
NAME:LETTER+(ALPHANUM|USCORE)+;
STRING: '"'(CHAR|'""')*'"';
TRUE: '.true.';
FALSE: '.false.';
BINNUM: [b]'"'BINDIG+'"';
OCTNUM: [o]'"'OCTDIG+'"';
HEXNUM: [z]'"'HEXDIG+'"';
INTNUM: SIGN?DIGIT+;
REALNUM: SIGN?DIGIT*'.'DIGIT*;
COMMENT: SPACES*'!'[\t -~]*[\r\n] -> skip;//whitespace ignored pre comment
WHITESPACE: (SPACES|NEWLINE)+ -> skip;//Skip all whitespaces
NEWLINE: [\r\n]+;
fragment ALPHANUM: DIGIT|LETTER;
fragment USCORE: '_';
fragment SIGN: ('+'|'-');
fragment HEXDIG: [0-9a-f];
fragment BINDIG: [0-1];
fragment OCTDIG: [0-7];
fragment DIGIT: [0-9];
fragment LETTER: [a-zA-Z];
fragment SPACES: [\t ]+;
fragment CHAR: [\t !#-~];