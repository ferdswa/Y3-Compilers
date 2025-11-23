grammar Nottscript;
//Parser rules
program: (block)+ EOF;
block: PROGRAM nameAtom declaration* statement* END PROGRAM nameAtom #programBlock
      | FUNCTION nameAtom LEFTBRACKET declaratorParamList? RIGHTBRACKET declaration* statement* END FUNCTION nameAtom #voidFuncBlock
      | FUNCTION nameAtom LEFTBRACKET declaratorParamList? RIGHTBRACKET RESULT LEFTBRACKET nameAtom RIGHTBRACKET declaration* statement* END FUNCTION nameAtom #returnFuncBlock
      | SUBROUTINE nameAtom LEFTBRACKET declaratorParamList? RIGHTBRACKET declaration* statement* END SUBROUTINE nameAtom #subrtBlock
      | TYPE nameAtom declaration+ END TYPE nameAtom #customTypeDeclBlock;
declaratorParamList: nameAtom (COMMA nameAtom)*;
//Declarations
declaration: typeSpec DBLCOL nameAtom (COMMA nameAtom)* #declareVar
            | typeSpec POINTER DBLCOL nameAtom (COMMA nameAtom)* #declPtr
            | typeSpec LEFTBRACKET numAtom (COMMA numAtom)* RIGHTBRACKET DBLCOL nameAtom (COMMA nameAtom)* #declArray
            | typeSpec (LEFTBRACKET star (COMMA star)* RIGHTBRACKET) POINTER DBLCOL nameAtom (COMMA nameAtom)* #declPtrArray;
typeSpec:   typeAtom #inbuilt
                |TYPE LEFTBRACKET nameAtom RIGHTBRACKET #custom;
//All the statements
statement: nameAtom ASSIGN expr #baseAssign
           | array ASSIGN expr #arrayAssign
           | nameAtom FIELDACCESS nameAtom ASSIGN expr #ctAssign
           | nameAtom FIELDACCESS array ASSIGN expr #ctArrayAssign
           | CALL nameAtom LEFTBRACKET paramList? RIGHTBRACKET #call
           | IF LEFTBRACKET expr RIGHTBRACKET THEN statement+ END IF #ifBlock
           | IF LEFTBRACKET expr RIGHTBRACKET THEN statement+ elseStmt END IF #ifElse
           | IF LEFTBRACKET expr RIGHTBRACKET statement #ifStmt
           | DO nameAtom ASSIGN doParam COMMA doParam COMMA doParam statement+ END DO #doIncrN1
           | DO nameAtom ASSIGN doParam COMMA doParam statement+ END DO #doIncr1
           | DO WHILE LEFTBRACKET expr RIGHTBRACKET statement+ END DO #doWhile
           | READ readParam (COMMA readParam)* #read
           | WRITE expr (COMMA expr)* #write
           | ALLOCATE nameAtom #allocPtr
           | ALLOCATE nameAtom COMMA arrayIndex #allocPtrArray
           | DEALLOCATE nameAtom #deallocPtr
           | nameAtom LEFTBRACKET paramList? RIGHTBRACKET #funcCall;

elseStmt: ELSE statement+;//Done
doParam: (intnum|nameAtom);//Done
readParam: (nameAtom|array);//Done
arrayIndex: (numAtom|nameAtom);//Done
array: nameAtom LEFTBRACKET arrayIndex (COMMA arrayIndex)* RIGHTBRACKET;
paramSubList: (nameAtom|expr);
paramList: paramSubList (COMMA paramSubList)*;
//Expressions - ordered in inverse precedence
expr: logExpr;
logExpr: relExpr(logicalOp relExpr)*;
relExpr: concatExpr(relativeOp concatExpr)*;
concatExpr: addSubExpr(CONCAT addSubExpr)*;
addSubExpr: addSubOp? mulDivExpr(addSubOp mulDivExpr)*;
mulDivExpr: powExpr (mulDivOp powExpr)*;
powExpr: fieldAccExpr (POW fieldAccExpr)*;
fieldAccExpr: basic(FIELDACCESS basic)*;
basic: (TRUE|FALSE)  #logicSExpr
       | REALNUM   #realSExpr
       | HEXNUM    #hexSExpr
       | BINNUM    #binSExpr
       | STRING    #charSeqSExpr
       | intnum    #intSExpr
       | LEFTBRACKET expr RIGHTBRACKET #exprSExpr
       | array     #arrSExpr //Can be confused for function call and vice versa
       | nameAtom LEFTBRACKET paramList? RIGHTBRACKET #funcSExpr
       | nameAtom      #nameSExpr;//pointers can be read as arrays
//atoms
relativeOp: LT|GT|EQ|LEQ|GEQ|NEQ;

typeAtom: INTEGER | REAL | CHARACTER | LOGICAL;
logicalOp: AND|OR ;
mulDivOp: MUL|DIV;
star: MUL;
addSubOp: PLUS|MINUS;
intnum: addSubOp? numAtom;
numAtom: USIGNINT;
nameAtom: NAME;
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
NEWLINE: [\r\n]+;
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