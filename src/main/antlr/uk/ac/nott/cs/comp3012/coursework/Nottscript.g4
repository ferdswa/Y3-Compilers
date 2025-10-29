grammar Nottscript;
//Parser rules

//Lexer rules


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
COMMENT: SPACES*'!'[\t -~]*[\r\n] -> skip;//whitespace ignored pre comment
NEWLINE: [\r\n]+;
ALPHANUM: DIGIT|LETTER;
fragment USCORE: '_';
fragment SIGN: ('+'|'-');
fragment HEXDIG: [0-9a-f];
fragment BINDIG: [0-1];
fragment OCTDIG: [0-7];
fragment DIGIT: [0-9];
fragment LETTER: [a-zA-Z];
fragment SPACES: [\t ]+;
fragment CHAR: [\t !#-~];