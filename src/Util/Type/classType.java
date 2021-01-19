package Util.Type;

import Util.Scope.*;

public class classType extends Type {
    private Scope scope;
    private String name;

    public classType(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Scope getScope() {
        return scope;
    }
}
