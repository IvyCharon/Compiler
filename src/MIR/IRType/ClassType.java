package MIR.IRType;

import java.util.ArrayList;
import java.util.HashMap;

import MIR.Function;

public class ClassType extends IRBaseType {
    public String className;
    public ArrayList<IRBaseType> memberType = new ArrayList<>();
    public ArrayList<String> memberName = new ArrayList<>();
    
    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "%" + className;
    }
}
