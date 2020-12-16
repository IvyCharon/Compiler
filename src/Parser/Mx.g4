grammar Mx;

program
    :   programSection*
    ;

programSection
    :   funcDecl
    |   classDecl
    |   variableDecl
    ;

SimpleType
    : Int
    | Bool
    | String
    | Identifier
    ;

Type
    : SimpleType
    | Type '[' ']'
    ;

FuncType
    : Type
    | Void
    ;

funcDecl
    : FuncType? Identifier '(' paraDeclList? ')' suite
    ;

paraDeclList
    : Type Identifier (',' Type Identifier)*
    ;

classDecl
    : Class Identifier '{' (funcDecl | variableDecl)* '}'
    ;

variableDecl
    : Type variableList ';'
    ;

variableList
    : singleVariableDecl (',' singleVariableDecl)*
    ;

singleVariableDecl
    : Identifier ('=' expression) ? 
    ;

suite 
    : '{' statement* '}'
    ;

statement
    : suite                                                 #block
    | varDef                                                #vardefStmt
    | If '(' expression ')' trueStmt=statement 
        (Else falseStmt=statement)?                         #ifStmt
    | loopStmt
    | jumpStmt
    | expression ';'                                        #pureExprStmt
    | ';'                                                   #emptyStmt
    ;

loopStmt
    : For '(' expression? ';' expression? ';' expression? ')' #forStmt
    | While '(' expression ')'                                #whileStmt
    ;

jumpStmt
    : Return expression? ';'                                #returnStmt
    | Break ';'                                             #breakStmt
    | Continue ';'                                          #continueStmt
    ;

expression
    : primary                                               #atomExpr
    | expression op=('+' | '-') expression                  #binaryExpr
    | expression op=('==' | '!=' ) expression               #binaryExpr
    | expression op=('*' | '/' | '%' ) expression           #binaryExpr
    | expression op=('<<' | '>>' ) expression               #binaryExpr
    | expression op=('<' | '>' ) expression                 #binaryExpr
    | expression op=('<=' | '>=' ) expression               #binaryExpr
    | expression op='&'  expression                         #binaryExpr
    | expression op='^'  expression                         #binaryExpr
    | expression op='|'  expression                         #binaryExpr
    | expression op='&&' expression                         #binaryExpr
    | expression op='||' expression                         #binaryExpr
    | <assoc=right> expression '=' expression               #assignExpr
    | expression '.' Identifier                             #MemberAccess
    | New SimpleType ('[' expression ']')+                  #NewArray
    | New SimpleType '(' expressionList? ')'                #NewObject
    | New SimpleType                                        #NewObject
    | expression op=('++' | '--')                           #PostfixIncDec
    | <assoc=right> op=('++' | '--') expression             #UnaryExpr
    | <assoc=right> op=('+' | '-')  expression              #UnaryExpr
    | <assoc=right> op=('!' | '~')  expression              #UnaryExpr 
    | expression '(' expressionList? ')'                    #FunctionCall    
    | array = expression '[' index = expression ']'         #Subscript
    ;

expressionList
    : expression (',' expression)*
    ;

primary
    : '(' expression ')'
    | Identifier 
    | literal 
    | This
    ;

literal
    : IntegerConstant
    | BoolConstant
    | NullConstant
    | StringConstant
    ;

Int : 'int';
Bool : 'bool';
String : 'string';
Null : 'null';
Void : 'void';
True : 'true';
False : 'false';
If : 'if';
Else : 'else';
For : 'for';
While : 'while';
Break : 'break';
Continue : 'continue';
Return : 'return';
New : 'new';
Class : 'class';
This : 'this';

LeftParen : '(';
RightParen : ')';
LeftBracket : '[';
RightBracket : ']';
LeftBrace : '{';
RightBrace : '}';

Less : '<';
LessEqual : '<=';
Greater : '>';
GreaterEqual : '>=';
LeftShift : '<<';
RightShift : '>>';

Plus : '+';
Minus : '-';
Multi : '*';
Div : '/';
Mod : '%';

And : '&';
Or : '|';
AndAnd : '&&';
OrOr : '||';
Caret : '^';
Not : '!';
Tilde : '~';

Question : '?';
Colon : ':';
Semi : ';';
Comma : ',';

Assign : '=';
Equal : '==';
NotEqual : '!=';

// Constant
StringConstant
    : '"' ('\\n' | '\\\\' | '\\"' | .)*? '"'
    ;

BoolConstant
    : True
    | False
    ;

IntegerConstant
    : [1-9] [0-9]*
    | '0'
    ;

NullConstant
    : Null
    ;

Identifier
    : [a-zA-Z] [a-zA-Z_0-9]*
    ;    

Whitespace
    :   [ \t]+
        -> skip
    ;

Newline
    :   (   '\r' '\n'?
        |   '\n'
        )
        -> skip
    ;

BlockComment
    :   '/*' .*? '*/'
        -> skip
    ;

LineComment
    :   '//' ~[\r\n]*
        -> skip
    ;