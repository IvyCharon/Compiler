grammar Mx;

program
    :   programSection* EOF
    ;

programSection
    :   classDecl
    |   varDecl
    |   funcDecl
    ;

classDecl
    : Class Identifier '{' (funcDecl | varDecl)* '}' ';'
    ;

funcDecl
    : funcType = retType? funcName = Identifier '(' (type Identifier (',' type Identifier)*)? ')' suite
    ;

varDecl
    : type singleVarDecl (',' singleVarDecl)* ';'
    ;

retType
    : type
    | Void
    ;

simpleType
    : Int
    | Bool
    | String
    | Identifier
    ;

type
    : simpleType
    | simpleType ('[' ']')*
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
    | Break ';'                                               #breakStmt
    | Continue ';'                                            #continueStmt
    | Return expression? ';'                                  #returnStmt
    | expression ';'                                          #exprStmt
    | ';'                                                     #emptyStmt
    ;

expression
    : '(' expression ')'                                                     #atomExpr
    | Identifier                                                             #atomExpr
    | literal                                                                #atomExpr
    | This                                                                   #atomExpr
    | New simpleType ('[' expression ']')+ ('[' ']')+ ('[' expression ']')+  #errorNewArray
    | New simpleType ('[' expression ']')+ ('[' ']')*                        #NewArray    
    | New simpleType '(' (expression (',' expression)*)? ')'                 #NewInitObject
    | New simpleType                                                         #NewObject
    | expression '.' Identifier                                              #MemberAccess
    | array = expression '[' index = expression ']'                          #Subscript
    | funcName = expression '(' (expression (',' expression)*)? ')'          #FunctionCall
    | expression op=('++' | '--')                                            #PostfixIncDec
    | <assoc=right> op=('++' | '--') expression                              #UnaryExpr
    | <assoc=right> op=('+' | '-')  expression                               #UnaryExpr
    | <assoc=right> op=('!' | '~')  expression                               #UnaryExpr
    | expression op=('*' | '/' | '%' ) expression                            #binaryExpr
    | expression op=('+' | '-') expression                                   #binaryExpr
    | expression op=('<<' | '>>' ) expression                                #binaryExpr
    | expression op=('<' | '>' ) expression                                  #binaryExpr
    | expression op=('<=' | '>=' ) expression                                #binaryExpr
    | expression op=('==' | '!=' ) expression                                #binaryExpr
    | expression op='&'  expression                                          #binaryExpr
    | expression op='^'  expression                                          #binaryExpr
    | expression op='|'  expression                                          #binaryExpr
    | expression op='&&' expression                                          #binaryExpr
    | expression op='||' expression                                          #binaryExpr
    | <assoc=right> expression '=' expression                                #assignExpr
    ;

literal
    : IntegerConstant
    | True
    | False
    | Null
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

IntegerConstant
    : [1-9] [0-9]*
    | '0'
    ;

StringConstant
    : '"' ('\\n' | '\\\\' | '\\"' | .)*? '"'
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

Add : '+' ;
Sub : '-' ;
Mul : '*' ;
Div : '/' ;
Mod : '%' ;
Smallersmaller : '<<' ;
Biggerbigger : '>>' ;
Smaller : '<' ;
Bigger : '>' ;
Smaller_equal : '<=' ;
Bigger_equal : '>=' ;
Equal : '==' ;
Not_equal : '!=' ;
And : '&' ;
Xor : '^' ;
Or : '|' ;
Andand : '&&' ;
Oror : '||' ;

Plusplus : '++' ;
Subsub : '--' ;
Not : '!' ;
Bit_opposite : '~' ;

LeftParen : '(';
RightParen : ')';
LeftBracket : '[';
RightBracket : ']';
LeftBrace : '{';
RightBrace : '}';