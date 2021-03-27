package Util.Type;

import MIR.Function;
import Util.position;
import Util.Entity.varEntity;
import Util.Scope.*;

public class funcType extends Type {
    private String name;
    private funcScope scope;
    private Type retType;

    public Function func;

    public funcType(String name) {
        super(types.Func);
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

    public void addPara(varEntity t, position pos){
        scope.addPara(t, pos);
    }

    @Override
    public int dim() {
        return 0;
    }
}
