package Util.Type;

import Util.Entity.varEntity;
import Util.Scope.*;

public class funcType extends Type {
    private String name;
    private funcScope scope;
    private Type retType;

    public funcType(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public funcScope getScope() {
        return scope;
    }

    public void setScope(funcScope t) {
        this.scope = t;
    }

    public Type retType() {
        return retType;
    }

    public void setRetType(Type t) {
        this.retType = t;
    }

    public void addPara(varEntity t) {
        scope.addPara(t);
    }
}
