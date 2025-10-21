grammar Nottscript;
//Parser rules
add: NUMBER '+' add
    | NUMBER
    ;

//Lexer rules
NUMBER: [0-9]+;
LETTER: [a-z]+;
BINDIG: [0-1]+;
OCTDIG: [0-7]+;
HEXDIG: [0-9a-f]+;