package Util.Type;

import MIR.IRType.BoolType;
import MIR.IRType.ClassType;
import MIR.IRType.IRBaseType;
import MIR.IRType.IntType;
import MIR.IRType.NullType;
import MIR.IRType.PointerType;
import MIR.IRType.VoidType;

abstract public class Type {
    public enum types {Int, Bool, String, Void, Class, Null, Array, Func}

    private types type;

    public abstract int dim();

    public Type() {
        type = null;
    }

    public Type(Type.types t) {
        type = t;
    }

    public void setType(Type.types mytype) {
        type = mytype;
    }

    public boolean isInt() {
        return type == types.Int;
    }

    public boolean isBool() {
        return type == types.Bool;
    }

    public boolean isString() {
        return type == types.String;
    }

    public boolean isVoid() {
        return type == types.Void;
    }

    public boolean isClass() {
        return type == types.Class;
    }

    public boolean isNull() {
        return type == types.Null;
    }

    public boolean isArray() {
        return type == types.Array;
    }

    public boolean isFunc() {
        return type == types.Func;
    }

    public types getType() {
        return type;
    }
}
