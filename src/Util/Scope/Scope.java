package Util.Scope;

import Util.error.semanticError;
import Util.Entity.*;
import Util.Type.*;
import Util.position;
import java.util.HashMap;

public class Scope {

    private HashMap<String, varEntity> vars = new HashMap<>();
    private HashMap<String, funcType> funcs = new HashMap<>();
    private Scope parentScope;


    public Scope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    public Scope parentScope() {
        return parentScope;
    }

    public void defineVariable(String name, varEntity t, position pos) {
        if (vars.containsKey(name))
            throw new semanticError("[Scope][define variable] variable redefine", pos);
        vars.put(name, t);
    }

    public boolean containsVariable(String name, boolean lookUpon) {
        if (vars.containsKey(name)) return true;
        else if (parentScope != null && lookUpon)
            return parentScope.containsVariable(name, true);
        else return false;
    }

    public void defineFunction(String name, funcType f, position pos) {
        if(funcs.containsKey(name))
            throw new semanticError("[Scope][define function] function redefine", pos);
        funcs.put(name, f);
    }

    public boolean containsFunction(String name, boolean lookUpon) {
        if (funcs.containsKey(name)) return true;
        else if (parentScope != null && lookUpon)
            return parentScope.containsFunction(name, true);
        else return false;
    }

    public funcType getFunction(String name, boolean lookUpon) {
        if (funcs.containsKey(name)) return funcs.get(name);
        else if (parentScope != null && lookUpon)
            return parentScope.getFunction(name, true);
        return null;
    }

    public Type getMemberType(String name, position pos, boolean lookUpon) {
        if(vars.containsKey(name)) return vars.get(name).type();
        else if(lookUpon && parentScope != null) return parentScope.getMemberType(name, pos, lookUpon);
        else throw new semanticError("[Scope][get member type] no such member type", pos);
    }

    public varEntity getMember(String name, position pos, boolean lookUpon) {
        if(vars.containsKey(name)) return vars.get(name);
        else if(lookUpon && parentScope != null) return parentScope.getMember(name, pos, lookUpon);
        else throw new semanticError("[Scope][get member] no such member", pos);
    }
    
}
