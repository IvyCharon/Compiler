package Util.Scope;

import Util.error.*;
import Util.position;
import Util.Entity.varEntity;
import Util.Type.*;

import java.util.HashMap;

import AST.TypeNode;

public class globalScope extends Scope {

    private HashMap<String, Type> type = new HashMap<>();
    private HashMap<String, classType> classes = new HashMap<>();

    public primitiveType intType = new primitiveType("int");
    public primitiveType boolType = new primitiveType("bool");
    public primitiveType voidType = new primitiveType("void");
    public primitiveType nullType = new primitiveType("null");

    public globalScope() {
        super(null);
        intType.setType(Type.types.Int);
        boolType.setType(Type.types.Bool);
        voidType.setType(Type.types.Void);
        nullType.setType(Type.types.Null);
        type.put("int", intType);
        type.put("bool", boolType);
        type.put("void", voidType);
        type.put("null", nullType);
        
        classType stringType=new classType("string", Type.types.String);
        stringType.setScope(new Scope(this));
        type.put("string",stringType);

        funcType Functmp;

        Functmp = new funcType("print");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("str", stringType), new position(0,0));
        Functmp.setRetType(voidType);
        defineFunction("print", Functmp, new position(0,0));

        Functmp = new funcType("println");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("str", stringType), new position(0,0));
        Functmp.setRetType(voidType);
        defineFunction("println", Functmp, new position(0,0));

        Functmp = new funcType("printInt");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("n", intType), new position(0,0));
        Functmp.setRetType(voidType);
        defineFunction("printInt", Functmp, new position(0,0));

        Functmp = new funcType("printlnInt");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("n", intType), new position(0,0));
        Functmp.setRetType(voidType);
        defineFunction("printlnInt", Functmp, new position(0,0));

        Functmp = new funcType("getString");
        Functmp.setScope(new funcScope(this));
        Functmp.setRetType(stringType);
        defineFunction("getString", Functmp, new position(0,0));

        Functmp = new funcType("getInt");
        Functmp.setScope(new funcScope(this));
        Functmp.setRetType(intType);
        defineFunction("getInt", Functmp, new position(0,0));

        Functmp = new funcType("toString");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("i", intType), new position(0,0));
        Functmp.setRetType(stringType);
        defineFunction("toString", Functmp, new position(0,0));

        Functmp = new funcType("size");
        Functmp.setScope(new funcScope(this));
        Functmp.setRetType(intType);
        defineFunction("size", Functmp, new position(0,0));

        Functmp = new funcType("length");
        Functmp.setScope(new funcScope(this));
        Functmp.setRetType(intType);
        stringType.defineFunction("length", Functmp, new position(0,0));

        Functmp = new funcType("substring");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("left", intType), new position(0,0));
        Functmp.addPara(new varEntity("right", intType), new position(0,0));
        Functmp.setRetType(stringType);
        stringType.defineFunction("substring", Functmp, new position(0,0));

        Functmp = new funcType("parseInt");
        Functmp.setScope(new funcScope(this));
        Functmp.setRetType(intType);
        stringType.defineFunction("parseInt", Functmp, new position(0,0));

        Functmp = new funcType("ord");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("pos", intType), new position(0,0));
        Functmp.setRetType(intType);
        stringType.defineFunction("ord", Functmp, new position(0,0));

    }

    public void defineClass(String name, classType t, position pos) {
        if(classes.containsKey(name)) 
            throw new semanticError("re-definition of class " + name, pos);
        classes.put(name, t);
        type.put(name, t);
    }

    public globalScope(Scope parentScope) {
        super(parentScope);
    }

    public Type getType(String name, position pos) {
        if(!type.containsKey(name)) throw new semanticError("no such Type: " + name, pos);
        return type.get(name);
    }

    public Type generateType(TypeNode t) {
        if(t.dim != 0)
            return new arrayType(getType(t.identifier, t.pos), t.dim);
        return getType(t.identifier, t.pos);
    }

    public boolean hasType(String name) {
        return type.containsKey(name);
    }

    public classType getClass(String name, position pos) {
        if(!classes.containsKey(name)) throw new semanticError("no such class", pos);
        return classes.get(name);
    }
}
