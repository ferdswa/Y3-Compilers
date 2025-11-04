grammar Nottscript;
//Parser rules
progStmt: PROGRAM NAME;
endProgStmt: END PROGRAM NAME?;
program: progStmt progContent;
progContent: codeBodyExt? endProgStmt;

codeBodyExt: (func | subrt | codeBodyInt | declaration)+;
codeBodyInt: (assignment|customType|call|ifStmt|ifBlock|do|doWhile)+;

subrtStmt: SUBROUTINE sN LEFTBRACKET paramN (COMMA paramN)* RIGHTBRACKET;
endSubrtStmt: END SUBROUTINE sN;
subrt: subrtStmt declaration+ codeBodyInt+ endSubrtStmt;

funcStmt: FUNCTION fN LEFTBRACKET paramN (COMMA paramN)* RIGHTBRACKET
        | FUNCTION fN LEFTBRACKET paramN (COMMA paramN)* RIGHTBRACKET RESULT LEFTBRACKET paramN RIGHTBRACKET;
endFuncStmt: END FUNCTION fN;
func: funcStmt declaration+ codeBodyInt+ endFuncStmt;

declPtr: (INTEGER | REAL | CHARACTER | LOGICAL | TYPE LEFTBRACKET customTypeName RIGHTBRACKET) (LEFTBRACKET '*' (COMMA '*')* RIGHTBRACKET)? POINTER DBLCOL nvList;
typeSpec: (INTEGER | REAL | CHARACTER | LOGICAL | TYPE LEFTBRACKET customTypeName RIGHTBRACKET) (LEFTBRACKET maxIndex (COMMA maxIndex)* RIGHTBRACKET)?;

array: varN LEFTBRACKET index (COMMA index)* RIGHTBRACKET;
nvList: varN (COMMA varN)*;

declaration: (typeSpec DBLCOL nvList) | declPtr;

call: CALL sN LEFTBRACKET paramN (COMMA paramN)* RIGHTBRACKET;

ifStmt: IF LEFTBRACKET (TRUE|FALSE) RIGHTBRACKET assignment | IF LEFTBRACKET varN RIGHTBRACKET assignment;

ifBlock: IF LEFTBRACKET expr0 RIGHTBRACKET THEN codeBodyInt END IF|
         IF LEFTBRACKET expr0 RIGHTBRACKET THEN codeBodyInt elseCond END IF;
elseCond: ELSE codeBodyInt;
do: DO varN ASSIGN intnum COMMA intnum COMMA intnum codeBodyInt END DO
    | DO varN ASSIGN intnum COMMA intnum codeBodyInt END DO;
doWhile: DO WHILE LEFTBRACKET expr0 RIGHTBRACKET codeBodyInt END DO;


//namedAssign: NAME ASSIGN expr0;
expr0: (TRUE | FALSE | REALNUM | HEXNUM | BINNUM | STRING | intnum);
customType: TYPE customTypeName declaration+ END TYPE customTypeName;
ctField: NAME;
customTypeName: NAME;
assignment: varN ASSIGN expr0 | array ASSIGN expr0 | customTypeName '%' ctField ASSIGN expr0 | customTypeName '%' array ASSIGN expr0 ;//statement not expr
intnum: ('+'|'-')? USIGNINT;
index: USIGNINT;
maxIndex: USIGNINT;
varN: NAME;
paramN: NAME;
fN: NAME;
sN: NAME;
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
MODULO: '%';
POW: '**';
DIV: '/';
MUL: '*';
PLUS: '+';
MINUS: '-';
CONCAT: '//';
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
OR: '.or.';
AND: '.and.';
//OTHER THINGS
ASSIGN: '=';
COLON: ':';
DBLCOL: '::';
COMMA: ',';
NAME:LETTER+(ALPHANUM|USCORE)*;
STRING: '"'(CHAR|'""')*'"';
TRUE: '.true.';
FALSE: '.false.';
BINNUM: [b]'"'BINDIG+'"';
OCTNUM: [o]'"'OCTDIG+'"';
HEXNUM: [z]'"'HEXDIG+'"';
//INTNUM: [+-]?DIGIT+;
USIGNINT: DIGIT+;
REALNUM: SIGN?DIGIT+'.'DIGIT*//Either not both
        | SIGN?DIGIT*'.'DIGIT+;
COMMENT: SPACES*'!'[\t -~]*NEWLINE* -> skip;//whitespace ignored pre comment
WHITESPACE: (SPACES|NEWLINE)+ -> skip;//Skip all whitespaces
NEWLINE: [\r\n];
//Fragments
fragment ALPHANUM: DIGIT|LETTER;
fragment USCORE: '_';
fragment SIGN: ('+'|'-');
fragment HEXDIG: [0-9a-f];
fragment BINDIG: [0-1];
fragment OCTDIG: [0-7];
fragment DIGIT: [0-9];
fragment LETTER: [a-zA-Z];
fragment SPACES: [\t ];
fragment CHAR: [\t !#-~];