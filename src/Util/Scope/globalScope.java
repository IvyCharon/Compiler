package Util.Scope;

import Util.error.*;
import Util.position;
import Util.Type.*;
import java.util.HashMap;

import AST.TypeNode;

public class globalScope extends Scope {

    private HashMap<String, Type> type = new HashMap<>();

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
}
