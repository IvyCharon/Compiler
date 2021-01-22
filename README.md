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
```

