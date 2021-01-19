grammar Mx;

program
    :   programSection* EOF
    ;

programSection
    :   funcDecl
    |   classDecl
    |   varDecl
    ;

funcDecl
    : funcType = type? funcName = Identifier '(' (type Identifier (',' type Identifier)*)? ')' suite
    ;

classDecl
    : Class Identifier '{' (funcDecl | varDecl)* '}'
    ;

varDecl
    : type singleVarDecl (',' singleVarDecl)* ';'
    ;


simpleType
    : Int
    | Bool
    | String
    | Identifier
    ;

type
    : simpleType
    | type '[' ']'
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
    | For '(' i    = expression? ';'
              con  = expression? ';' 
              step = expression? ')'  
              statement                                       #forStmt
    | While '(' expression ')' statement                      #whileStmt
    | Return expression? ';'                                  #returnStmt
    | Break ';'                                               #breakStmt
    | Continue ';'                                            #continueStmt
    | expression ';'                                          #exprStmt
    | ';'                                                     #emptyStmt
    ;

expression
    : '(' expression ')'                                             #atomExpr
    | Identifier                                                     #atomExpr
    | literal                                                        #atomExpr
    | This                                                           #atomExpr
    | expression op=('+' | '-') expression                           #binaryExpr
    | expression op=('==' | '!=' ) expression                        #binaryExpr
    | expression op=('*' | '/' | '%' ) expression                    #binaryExpr
    | expression op=('<<' | '>>' ) expression                        #binaryExpr
    | expression op=('<' | '>' ) expression                          #binaryExpr
    | expression op=('<=' | '>=' ) expression                        #binaryExpr
    | expression op='&'  expression                                  #binaryExpr
    | expression op='^'  expression                                  #binaryExpr
    | expression op='|'  expression                                  #binaryExpr
    | expression op='&&' expression                                  #binaryExpr
    | expression op='||' expression                                  #binaryExpr
    | <assoc=right> expression '=' expression                        #assignExpr
    | expression '.' Identifier                                      #MemberAccess
    | New simpleType ('[' expression ']')+                           #NewArray
    | New simpleType '(' (expression (',' expression)*)? ')'         #NewInitObject
    | New simpleType                                                 #NewObject
    | expression op=('++' | '--')                                    #PostfixIncDec
    | <assoc=right> op=('++' | '--') expression                      #UnaryExpr
    | <assoc=right> op=('+' | '-')  expression                       #UnaryExpr
    | <assoc=right> op=('!' | '~')  expression                       #UnaryExpr
    | funcName = expression '(' (expression (',' expression)*)? ')'  #FunctionCall
    | array = expression '[' index = expression ']'                  #Subscript
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