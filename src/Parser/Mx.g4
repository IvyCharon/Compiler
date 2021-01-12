grammar Mx;

program
    :   programSection* suite EOF
    ;

programSection
    :   funcDecl
    |   classDecl
    |   varDecl
    ;

funcDecl
    : funcType? Identifier '(' paraDeclList? ')' suite
    ;

classDecl
    : Class Identifier '{' (funcDecl | varDecl)* '}'
    ;

varDecl
    : type varList ';'
    ;

funcType
    : type
    | Void
    ;

paraDeclList
    : type Identifier (',' type Identifier)*
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
    : suite                                                   #block
    | varDecl                                                 #vardefStmt
    | If '(' expression ')' trueStmt = statement
                    (Else falseStmt = statement)?             #ifstmt
    | For '(' expression? ';' expression? ';' expression? ')' #forStmt
    | While '(' expression ')'                                #whileStmt
    | Return expression? ';'                                  #returnStmt
    | Break ';'                                               #breakStmt
    | Continue ';'                                            #continueStmt
    | expression ';'                                          #exprStmt
    | ';'                                                     #emptyStmt
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

//some key words
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

////
Identifier
    : [a-zA-Z] [a-zA-Z_0-9]*
    ;

// Constant
BoolConstant
    : True
    | False
    ;

IntegerConstant
    : [1-9] [0-9]*
    | '0'
    ;

StringConstant
    : '"' ('\\n' | '\\\\' | '\\"' | .)*? '"'
    ;

NullConstant
    : Null
    ;

////
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