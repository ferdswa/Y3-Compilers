grammar Nottscript;
//Parser rules
program: (unit)+ EOF;
unit: nameUnit declaration* statement* END nameUnit #programBlock
      | nameUnit LEFTBRACKET declaratorParamList? RIGHTBRACKET declaration* statement* END nameUnit #voidFuncBlock
      | nameUnit LEFTBRACKET declaratorParamList? RIGHTBRACKET nodeAtom LEFTBRACKET nameAtom RIGHTBRACKET declaration* statement* END nameUnit #returnFuncBlock
      | nameUnit LEFTBRACKET declaratorParamList? RIGHTBRACKET declaration* statement* END nameUnit #subrtBlock
      | nameUnit declaration+ nameUnit #customTypeDeclBlock;
declaratorParamList: nameAtom (COMMA nameAtom)*;
nameUnit: nodeAtom nameAtom ;
//Declarations
declaration: typeSpec DBLCOL nameAtom (COMMA nameAtom)* #declareVar
            | typeSpec nodeAtom DBLCOL nameAtom (COMMA nameAtom)* #declPtr
            | typeSpec LEFTBRACKET numAtom (COMMA numAtom)* RIGHTBRACKET DBLCOL nameAtom (COMMA nameAtom)* #declArray
            | typeSpec (LEFTBRACKET star (COMMA star)* RIGHTBRACKET) nodeAtom DBLCOL nameAtom (COMMA nameAtom)* #declPtrArray;
typeSpec:   typeAtom #inbuilt
                |nodeAtom LEFTBRACKET nameAtom RIGHTBRACKET #custom;
//All the statements
statement: nameAtom ASSIGN expr #baseAssign
           | array ASSIGN expr #arrayAssign
           | nameAtom FIELDACCESS nameAtom nodeAtom expr #ctAssign
           | nameAtom FIELDACCESS array nodeAtom expr #ctArrayAssign
           | nodeAtom nameAtom LEFTBRACKET paramList? RIGHTBRACKET #call
           | nodeAtom LEFTBRACKET expr RIGHTBRACKET nodeAtom statement+ nodeAtom nodeAtom #ifBlock
           | nodeAtom LEFTBRACKET expr RIGHTBRACKET nodeAtom statement+ elseStmt nodeAtom nodeAtom #ifElse
           | nodeAtom LEFTBRACKET expr RIGHTBRACKET statement #ifStmt
           | nodeAtom nameAtom ASSIGN doParam COMMA doParam COMMA doParam statement+ nodeAtom nodeAtom #doIncrN1
           | nodeAtom nameAtom ASSIGN doParam COMMA doParam statement+ nodeAtom nodeAtom #doIncr1
           | nodeAtom nodeAtom LEFTBRACKET expr RIGHTBRACKET statement+ nodeAtom nodeAtom #doWhile
           | READ readParam (COMMA readParam)* #read
           | WRITE expr (COMMA expr)* #write
           | nodeAtom nameAtom #allocPtr
           | nodeAtom nameAtom COMMA arrayIndex #allocPtrArray
           | nodeAtom nameAtom #deallocPtr
           | nameAtom LEFTBRACKET paramList? RIGHTBRACKET #funcCall;

elseStmt: nodeAtom statement+;//Done
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
       | OCTNUM    #octSExpr
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
nodeAtom:
         ALLOCATE
       | BREAK
       | CALL
       | CHARACTER
       | DEALLOCATE
       | DO
       | ELSE
       | END
       | FUNCTION
       | IF
       | INTEGER
       | LOGICAL
       | POINTER
       | PROGRAM
       | READ
       | REAL
       | RESULT
       | SUBROUTINE
       | THEN
       | TYPE
       | WHILE
       | WRITE;
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