package MIR.IRType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import MIR.IROperand.operand;
import Util.error.runtimeError;

import java.util.HashMap;

public class ClassType extends IRBaseType {
    public String className;
    //public ArrayList<IRBaseType> memberType = new ArrayList<>();
    //public ArrayList<String> memberName = new ArrayList<>();
    public LinkedHashMap<String, IRBaseType> members = new LinkedHashMap<>();
    public HashMap<String, ArrayList<operand>> symbols = new HashMap<>();

    public ClassType(String name) {
        this.className = name;
    }

    public void symbolAdd(String key, operand oper) {
        if(symbols.containsKey(key)) {
            symbols.get(key).add(oper);
        } else {
            ArrayList<operand> value = new ArrayList<>();
            value.add(oper);
            symbols.put(key, value);
        }
    }

    public String name() {
        return className;
    }

    public operand symbolGet(String name) {
        ArrayList<operand> tmp = symbols.get(name);
        if(tmp == null) throw new runtimeError("[ClassType] no such member!");
        return tmp.get(tmp.size() - 1);
    }

    public int memberOff(String name) {
        int i = 0;
        for(Entry<String, IRBaseType> entry : members.entrySet()) {
            if(entry.getKey().equals(name)) break;
            i++;
        }
        return i;        
    }

    public int sizeOffset(int no) {
        int i = 0, s = 0;
        for(Entry<String, IRBaseType> entry : members.entrySet()) {
            if(i == no) break;
            s += entry.getValue().size();
            i++;
        }
        return s;
    }

    public String getMemName(int no) {
        int i = 0;
        for(Entry<String, IRBaseType> entry : members.entrySet()) {
            if(i == no) return entry.getKey();
            i++;
        }
        return null;
    }

    public IRBaseType getMemType(int no) {
        int i = 0;
        for(Entry<String, IRBaseType> entry : members.entrySet()) {
            if(i == no) return entry.getValue();
            i++;
        }
        return null;
    }
    
    @Override
    public int size() {
        int s = 0;
        for(Entry<String, IRBaseType> entry : members.entrySet()) {
            s += entry.getValue().size();
        }
        return s;
    }

    @Override
    public String toString() {
        return className;
    }
}
