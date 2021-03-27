package MIR;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import MIR.IROperand.ConstString;
import MIR.IROperand.globalVariable;
import MIR.IROperand.parameter;
import MIR.IRType.ArrayType;
import MIR.IRType.BoolType;
import MIR.IRType.IRBaseType;
import MIR.IRType.IntType;
import MIR.IRType.PointerType;
import MIR.IRType.VoidType;
import Util.Type.classType;

public class Module {
    public LinkedHashMap<String, Function> functions = new LinkedHashMap<>();
    public LinkedHashMap<String, Function> builtinFunctions = new LinkedHashMap<>();
    public LinkedHashMap<String, globalVariable> globalVars = new LinkedHashMap<>();
    public LinkedHashMap<String, globalVariable> constStringMap = new LinkedHashMap<>();
    public LinkedHashMap<String, classType> classes = new LinkedHashMap<>();

    public Module() {
        //add builtin function
        ArrayList<parameter> paras;
        IRBaseType retType;
        Function func;

        //void print(string str)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "str"));
        retType = new VoidType();
        func = new Function("print", retType, paras);
        builtinFunctions.put("print", func);

        //void println(string str)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "str"));
        retType = new VoidType();
        func = new Function("println", retType, paras);
        builtinFunctions.put("println", func);

        //void printInt(int x)
        paras = new ArrayList<>();
        paras.add(new parameter(new IntType(32), "x"));
        retType = new VoidType();
        func = new Function("printInt", retType, paras);
        builtinFunctions.put("printInt", func);

        //void printlnInt(int x)
        paras = new ArrayList<>();
        paras.add(new parameter(new IntType(32), "x"));
        retType = new VoidType();
        func = new Function("printlnInt", retType, paras);
        builtinFunctions.put("printlnInt", func);

        //string getString()
        paras = new ArrayList<>();
        retType = new PointerType(new IntType(32));
        func = new Function("getString", retType, paras);
        builtinFunctions.put("getString", func);

        //int getInt()
        paras = new ArrayList<>();
        retType = new IntType(32);
        func = new Function("getInt", retType, paras);
        builtinFunctions.put("getInt", func);

        //string toString(int x)
        paras = new ArrayList<>();
        paras.add(new parameter(new IntType(32), "x"));
        retType = new PointerType(new IntType(32));
        func = new Function("toString", retType, paras);
        builtinFunctions.put("toString", func);

        //int array.size(array arr)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "arr"));
        retType = new IntType(32);
        func = new Function("array.size", retType, paras);
        builtinFunctions.put("array.size", func);

        //int string.length(string str)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "str"));
        retType = new IntType(32);
        func = new Function("string.length", retType, paras);
        builtinFunctions.put("string.length", func);

        //string string.substring(string str, int left, int right)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "str"));
        paras.add(new parameter(new IntType(32), "left"));
        paras.add(new parameter(new IntType(32), "right"));
        retType = new PointerType(new IntType(32));
        func = new Function("string.substring", retType, paras);
        builtinFunctions.put("string.substring", func);

        //int parseInt(string str)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "str"));
        retType = new IntType(32);
        func = new Function("parseInt", retType, paras);
        builtinFunctions.put("parseInt", func);

        //int ord(string str, int ord)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "str"));
        paras.add(new parameter(new IntType(32), "ord"));
        retType = new IntType(32);
        func = new Function("ord", retType, paras);
        builtinFunctions.put("ord", func);
        
        //bool string.add(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("string.add", retType, paras);
        builtinFunctions.put("string.add", func);

        //bool string.equal(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("string.equal", retType, paras);
        builtinFunctions.put("string.equal", func);

        //bool string.not_equal(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("string.not_equal", retType, paras);
        builtinFunctions.put("string.not_equal", func);
        
        //bool stirng.smaller(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("string.smaller", retType, paras);
        builtinFunctions.put("string.smaller", func);

        //bool string.bigger(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("string.bigger", retType, paras);
        builtinFunctions.put("string.bigger", func);
        
        //bool string.smaller_equal(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("string.smaller_equal", retType, paras);
        builtinFunctions.put("string.smaller_equal", func);
        
        //bool string.bigger_equal(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("string.bigger_equal", retType, paras);
        builtinFunctions.put("string.bigger_equal", func);

        //init func
        paras = new ArrayList<>();
        retType = new VoidType();
        func = new Function("__init__", retType, paras);
        functions.put("__init__", func);
    } 

    public globalVariable addString(String s) {
        if(constStringMap.containsKey(s)) {
            return constStringMap.get(s);
        } else {
            String name = "string." + constStringMap.size();
            globalVariable tmp = new globalVariable(
                new ArrayType(new IntType(32), s.length()),
                name,
                new ConstString(s)
            );
            constStringMap.put(s, tmp);
            globalVars.put(name, tmp);

            return tmp;
        }
    }

}
