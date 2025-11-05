grammar Nottscript;
//Parser rules
file: (func | subrt )* program (func | subrt )*;
progStmt: PROGRAM NAME;
endProgStmt: END PROGRAM NAME?;
program: progStmt progContent;
progContent: declaration* statement* endProgStmt;

statement: assignment
            | customType
            | call
            | ifBlock
            | ifStmt
            | do
            | doWhile
            | read
            | write
            | allocPtr
            | deallocPtr
            | funcCall;

subrtStmt: SUBROUTINE sN LEFTBRACKET nvList? RIGHTBRACKET;
endSubrtStmt: END SUBROUTINE sN;
subrt: subrtStmt declaration* statement* endSubrtStmt;
call: CALL sN LEFTBRACKET paramList? RIGHTBRACKET;

funcStmt: FUNCTION fN LEFTBRACKET nvList? RIGHTBRACKET
        | FUNCTION fN LEFTBRACKET nvList? RIGHTBRACKET RESULT LEFTBRACKET paramN RIGHTBRACKET;
endFuncStmt: END FUNCTION fN;
func: funcStmt declaration* statement* endFuncStmt;
funcCall: fN LEFTBRACKET paramList? RIGHTBRACKET;

declPtr: (INTEGER | REAL | CHARACTER | LOGICAL | TYPE LEFTBRACKET customTypeName RIGHTBRACKET) (LEFTBRACKET '*' (COMMA '*')* RIGHTBRACKET)? POINTER DBLCOL nvList;
typeSpec: (INTEGER | REAL | CHARACTER | LOGICAL | TYPE LEFTBRACKET customTypeName RIGHTBRACKET) (LEFTBRACKET maxIndex (COMMA maxIndex)* RIGHTBRACKET)?;

array: varN LEFTBRACKET (index|varN) (COMMA (index|varN))* RIGHTBRACKET;
nvList: varN (COMMA varN)*;
paramList: (paramN|expr0) (COMMA (paramN|expr0))*;

declaration: (typeSpec DBLCOL nvList) | declPtr;

ifStmt: IF LEFTBRACKET expr0 RIGHTBRACKET statement;

ifBlock: IF LEFTBRACKET expr0 RIGHTBRACKET THEN statement+ END IF|
         IF LEFTBRACKET expr0 RIGHTBRACKET THEN statement+ elseCond END IF;
elseCond: ELSE statement+;

do: DO varN ASSIGN (intnum|varN) COMMA (intnum|varN) COMMA (intnum|varN) statement+ END DO
    | DO varN ASSIGN (intnum|varN) COMMA (intnum|varN) statement+ END DO;
doWhile: DO WHILE LEFTBRACKET expr0 RIGHTBRACKET statement+ END DO;

read: READ (varN|array) (COMMA (varN|array))*;
write: WRITE expr0 (COMMA expr0)*;

allocPtr: ALLOCATE ptrName
        | ALLOCATE ptrName COMMA (USIGNINT|varN);
deallocPtr: DEALLOCATE ptrName;

//Operators in order
powExpr: basic (POW basic)*;
mulDivExpr: powExpr ((MUL|DIV) powExpr)*;
addSubExpr: (PLUS|MINUS)? mulDivExpr((PLUS|MINUS)mulDivExpr)*;
concatExpr: addSubExpr(CONCAT addSubExpr)*;
relExpr: concatExpr(relativeOp concatExpr)*;
logExpr: relExpr((AND|OR)relExpr)*;
basic: (TRUE | FALSE | REALNUM | HEXNUM | BINNUM | STRING | intnum |LEFTBRACKET expr0 RIGHTBRACKET| array | funcCall | varN);//pointers can be read as arrays
expr0: logExpr;

customType: TYPE customTypeName declaration+ END TYPE customTypeName;
ctField: NAME;
customTypeName: NAME;
assignment: varN ASSIGN expr0 | array ASSIGN expr0 | customTypeName FIELDACCESS ctField ASSIGN expr0 | customTypeName FIELDACCESS array ASSIGN expr0 ;//statement not expr
relativeOp: LT|GT|EQ|LEQ|GEQ|NEQ;
intnum: (PLUS|MINUS)? USIGNINT;
index: USIGNINT;
maxIndex: USIGNINT;
varN: NAME;
paramN: NAME;
ptrName: NAME;
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
FIELDACCESS: '%';
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