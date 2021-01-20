package Util.Type;

import Util.Scope.*;
import Util.position;

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

    public void setScope(Scope tmp) {
        this.scope = tmp;
    }

    public void defineFunction(String name, funcType f, position pos) {
        scope.defineFunction(name, f, pos);
    }

    @Override
    public int dim() {
        return 0;
    }
}
