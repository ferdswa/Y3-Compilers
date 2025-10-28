grammar Nottscript;
//Parser rules

//Lexer rules
fragment NUMBER: [0-9];
fragment LETTER: [a-z];
fragment ALPHANUM: [0-9a-z];
NAME: LETTER+[0-9a-z_]*;
INTEGER: [+|-]?NUMBER+;
REAL: [+|-]?NUMBER*'.'NUMBER*;
LOGICAL: '.true.'|'.false.';
CHARACTER: '"'[\t -~]+'"';
BINNUM: [b]'"'[0-1]+'"';
OCTNUM: [o]'"'[0-7]+'"';
HEXNUM: [z]'"'[0-9a-f]+'"';
NEWLINE: [\r\n]+;