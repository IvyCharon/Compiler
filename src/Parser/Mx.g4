grammar Mx;

program
    :   programSection* suite EOF
    ;

programSection
    :   funcDecl
    |   classDecl
    |   varDecl
    ;

simpleType
    : Int
    | Bool
    | String
    | Identifier
    ;

type
    : simpleType
    | simpleType ('[' ']')+
    ;

funcType
    : type
    | Void
    ;

funcDecl
    : funcType? Identifier '(' paraDeclList? ')' suite
    ;

paraDeclList
    : type Identifier (',' type Identifier)*
    ;

classDecl
    : Class Identifier '{' (funcDecl | varDecl)* '}'
    ;

varDecl
    : type varList ';'
    ;

varList
    : singleVarDecl (',' singleVarDecl)*
    ;

singleVarDecl
    : Identifier ('=' expression)?
    ;

suite
    : '{' statement* '}'
    ;

statement
    : suite                                                 #block
    | varDecl                                                #vardefStmt
    | If '(' expression ')' trueStmt=statement
        (Else falseStmt=statement)?                         #ifStmt
    | loopStmt                                              #loopstmt
    | jumpStmt                                              #jumpstmt
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
    | New simpleType ('[' expression ']')+                  #NewArray
    | New simpleType '(' expressionList? ')'                #NewObject
    | New simpleType                                        #NewObject
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

//LeftParen : '(';
//RightParen : ')';
//LeftBracket : '[';
//RightBracket : ']';
//LeftBrace : '{';
//RightBrace : '}';

//Less : '<';
//LessEqual : '<=';
//Greater : '>';
//GreaterEqual : '>=';
//LeftShift : '<<';
//RightShift : '>>';

//Plus : '+';
//Minus : '-';
//Multi : '*';
//Div : '/';
//Mod : '%';

//And : '&';
//Or : '|';
//AndAnd : '&&';
//OrOr : '||';
//Caret : '^';
//Not : '!';
//Tilde : '~';

//Question : '?';
//Colon : ':';
//Semi : ';';
//Comma : ',';

//Assign : '=';
//Equal : '==';
//NotEqual : '!=';

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