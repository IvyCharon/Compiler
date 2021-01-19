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

    public globalScope() {
        super(null);
        Type Inttmp = new Type(); Inttmp.isInt = true; type.put("int", Inttmp);
        Type Booltmp = new Type(); Inttmp.isBool = true; type.put("bool", Booltmp);
        Type Voidtmp = new Type(); Inttmp.isVoid= true; type.put("void", Voidtmp);
        Type Nulltmp = new Type(); Inttmp.isNull = true; type.put("null", Nulltmp);
        
        classType stringType=new classType("string");
        stringType.setScope(new Scope(this));
        type.put("string",stringType);

        funcType Functmp;

        Functmp = new funcType("print");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("str", stringType));
        Functmp.setRetType(Voidtmp);
        defineFunction("print", Functmp, new position(0,0));

        Functmp = new funcType("println");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("str", stringType));
        Functmp.setRetType(Voidtmp);
        defineFunction("println", Functmp, new position(0,0));

        Functmp = new funcType("printInt");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("n", Inttmp));
        Functmp.setRetType(Voidtmp);
        defineFunction("printInt", Functmp, new position(0,0));

        Functmp = new funcType("printlnInt");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("n", Inttmp));
        Functmp.setRetType(Voidtmp);
        defineFunction("printlnInt", Functmp, new position(0,0));

        Functmp = new funcType("getString");
        Functmp.setScope(new funcScope(this));
        Functmp.setRetType(stringType);
        defineFunction("getString", Functmp, new position(0,0));

        Functmp = new funcType("getInt");
        Functmp.setScope(new funcScope(this));
        Functmp.setRetType(Inttmp);
        defineFunction("getInt", Functmp, new position(0,0));

        Functmp = new funcType("toString");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("i", Inttmp));
        Functmp.setRetType(stringType);
        defineFunction("toString", Functmp, new position(0,0));

        Functmp = new funcType("size");
        Functmp.setScope(new funcScope(this));
        Functmp.setRetType(Inttmp);
        defineFunction("size", Functmp, new position(0,0));

        Functmp = new funcType("length");
        Functmp.setScope(new funcScope(this));
        Functmp.setRetType(Inttmp);
        stringType.defineFunction("length", Functmp, new position(0,0));

        Functmp = new funcType("substring");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("left", Inttmp));
        Functmp.addPara(new varEntity("right", Inttmp));
        Functmp.setRetType(stringType);
        stringType.defineFunction("substring", Functmp, new position(0,0));

        Functmp = new funcType("parseInt");
        Functmp.setScope(new funcScope(this));
        Functmp.setRetType(Inttmp);
        stringType.defineFunction("parseInt", Functmp, new position(0,0));

        Functmp = new funcType("ord");
        Functmp.setScope(new funcScope(this));
        Functmp.addPara(new varEntity("pos", Inttmp));
        Functmp.setRetType(Inttmp);
        stringType.defineFunction("ord", Functmp, new position(0,0));

    }

    public void defineClass(String name, classType t, position pos) {
        if(classes.containsKey(name)) 
            throw new semanticError("re-definition of class " + name, pos);
        classes.put(name, t);
    }

    public globalScope(Scope parentScope) {
        super(parentScope);
    }

    public Type getType(String name, position pos) {
        if(!type.containsKey(name)) throw new semanticError("no such Type: " + name, pos);
        return type.get(name);
    }

    public Type generateType(TypeNode t) {
        //array
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
