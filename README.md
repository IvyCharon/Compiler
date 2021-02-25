# Compiler

## AST Node

```
ASTNode
programNode
    classDeclNode
    funcDeclNode
    
StmtNode
    blockStmtNode
    varDeclNode
        singleVarDeclNode
    ifStmtNode
    forStmtNode
    whileStmtNode
    breakStmtNode
    continueStmtNode
    returnStmtNode
    exprStmtNode
    emptyStmtNode
    
ExprNode
    varNode
    constExprNode
        intConstNode
        boolConstNode
        nullConstNode
        stringConstNode
    thisExprNode
    newArrayExprNode
    newInitObjectExprNode
    newObjectExprNode
    memberAccessExprNode
    subscriptExprNode
    funcCallExprNode
        funcNode
        methodNode
    postfixExprNode
    binaryExprNode
    unaryExprNode
    assignExprNode

TypeNode
simpleTypeNode
```
reference
https://blog.csdn.net/qq_42570601/article/details/107366946?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control