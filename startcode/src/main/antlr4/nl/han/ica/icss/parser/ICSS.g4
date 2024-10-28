grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';

//--- PARSER: ---
stylesheet: (variable_assignment | stylerule)+;

variable: (LOWER_IDENT | CAPITAL_IDENT);
variable_value: (COLOR | TRUE | FALSE | PIXELSIZE | PERCENTAGE | SCALAR | variable);
tag_selector: (LOWER_IDENT | CAPITAL_IDENT | ID_IDENT | CLASS_IDENT) ;
operator: (PLUS | MIN | MUL);

variable_assignment: variable ASSIGNMENT_OPERATOR variable_value (operator variable_value)* SEMICOLON;
element: (LOWER_IDENT | CAPITAL_IDENT);
element_assignment: element COLON (variable_value | equation)* SEMICOLON;

stylerule: tag_selector OPEN_BRACE body CLOSE_BRACE;

body: (element_assignment | if_statement | equation | variable_assignment)+;

equation: expression;
expression
    : expression MUL expression #multiply
    | expression MIN expression #subtraction
    | expression PLUS expression #addition
    | variable_value #var_val
    ;

if_statement: IF BOX_BRACKET_OPEN variable BOX_BRACKET_CLOSE OPEN_BRACE body CLOSE_BRACE else_statement*;
else_statement: ELSE OPEN_BRACE body CLOSE_BRACE;