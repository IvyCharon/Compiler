package MIR;

import java.util.LinkedHashMap;

import MIR.IROperand.operand;

public class IRScope {
    public IRScope parentScope;
    public LinkedHashMap<String, operand> operMap = new LinkedHashMap<>();

    public IRScope(IRScope pa) {
        parentScope = pa;
    }

    public boolean containsOper(String name) {
        if(operMap.containsKey(name)) return true;
        if(parentScope == null) return false;
        return parentScope.containsOper(name);
    }

    public operand getOper(String name) {
        if(operMap.containsKey(name)) return operMap.get(name);
        if(parentScope == null) return null;
        return parentScope.getOper(name);
    }
}
