package Util.Entity;

import Util.Type.*;

public class varEntity extends Entity {
    private Type type;

    public varEntity(String name, Type t) {
        super(name);
        this.type = t;
    }

    public Type type() {
        return type;
    }
}
