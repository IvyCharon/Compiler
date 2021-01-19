package Util.Type;

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

    public Type retType() {
        return retType;
    }
}
