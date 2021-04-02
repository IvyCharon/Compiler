package MIR;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import MIR.IROperand.ConstString;
import MIR.IROperand.Register;
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
        func.retVal = new Register(new PointerType(new IntType(8)), "getString_ret");
        builtinFunctions.put("getString", func);

        //int getInt()
        paras = new ArrayList<>();
        retType = new IntType(32);
        func = new Function("getInt", retType, paras);
        func.retVal = new Register(new PointerType(new IntType(32)), "getInt_ret");
        builtinFunctions.put("getInt", func);

        //string toString(int x)
        paras = new ArrayList<>();
        paras.add(new parameter(new IntType(32), "x"));
        retType = new PointerType(new IntType(32));
        func = new Function("toString", retType, paras);
        func.retVal = new Register(new PointerType(new IntType(8)), "toString_ret");
        builtinFunctions.put("toString", func);

        //int __array_size(array arr)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "arr"));
        retType = new IntType(32);
        func = new Function("__array_size", retType, paras);
        func.retVal = new Register(new PointerType(new IntType(32)), "__array_size_ret");
        builtinFunctions.put("__array_size", func);

        //int __string_length(string str)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "str"));
        retType = new IntType(32);
        func = new Function("__string_length", retType, paras);
        func.retVal = new Register(new PointerType(new IntType(32)), "__string_length_ret");
        builtinFunctions.put("__string_length", func);

        //string __string_substring(string str, int left, int right)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "str"));
        paras.add(new parameter(new IntType(32), "left"));
        paras.add(new parameter(new IntType(32), "right"));
        retType = new PointerType(new IntType(32));
        func = new Function("__string_substring", retType, paras);
        func.retVal = new Register(new PointerType(new IntType(8)), "__string_substring_ret");
        builtinFunctions.put("__string_substring", func);

        //int parseInt(string str)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "str"));
        retType = new IntType(32);
        func = new Function("__string_parseInt", retType, paras);
        func.retVal = new Register(new PointerType(new IntType(32)), "__string_parseInt_ret");
        builtinFunctions.put("__string_parseInt", func);

        //int ord(string str, int ord)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "str"));
        paras.add(new parameter(new IntType(32), "ord"));
        retType = new IntType(32);
        func = new Function("__string_ord", retType, paras);
        func.retVal = new Register(new PointerType(new IntType(32)), "__string_ord_ret");
        builtinFunctions.put("__string_ord", func);
        
        //string __string_add(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new PointerType(new IntType(32));
        func = new Function("__string_add", retType, paras);
        func.retVal = new Register(new PointerType(new IntType(8)), "__string_add_ret");
        builtinFunctions.put("__string_add", func);

        //bool __string_equal(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("__string_equal", retType, paras);
        func.retVal = new Register(new PointerType(new BoolType(1)), "__string_equal_ret");
        builtinFunctions.put("__string_equal", func);

        //bool __string_not_equal(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("__string_not_equal", retType, paras);
        func.retVal = new Register(new PointerType(new BoolType(1)), "__string_not_equal_ret");
        builtinFunctions.put("__string_not_equal", func);
        
        //bool __stirng_smaller(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("__string_smaller", retType, paras);
        func.retVal = new Register(new PointerType(new BoolType(1)), "__string_smaller_ret");
        builtinFunctions.put("__string_smaller", func);

        //bool __string_bigger(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("__string_bigger", retType, paras);
        func.retVal = new Register(new PointerType(new BoolType(1)), "__string_bigger_ret");
        builtinFunctions.put("__string_bigger", func);
        
        //bool __string_smaller_equal(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("__string_smaller_equal", retType, paras);
        func.retVal = new Register(new PointerType(new BoolType(1)), "__string_smaller_equal_ret");
        builtinFunctions.put("__string_smaller_equal", func);
        
        //bool __string_bigger_equal(string s1, string s2)
        paras = new ArrayList<>();
        paras.add(new parameter(new PointerType(new IntType(32)), "s1"));
        paras.add(new parameter(new PointerType(new IntType(32)), "s2"));
        retType = new BoolType(1);
        func = new Function("__string_bigger_equal", retType, paras);
        func.retVal = new Register(new PointerType(new BoolType(1)), "__string_bigger_equal_ret");
        builtinFunctions.put("__string_bigger_equal", func);

        //byte* malloc(int size)
        paras = new ArrayList<>();
        paras.add(new parameter(new IntType(32), "size"));
        retType = new PointerType(new IntType(8));
        func = new Function("malloc", retType, paras);
        func.retVal = new Register(new PointerType(new IntType(32)), "malloc_ret");
        builtinFunctions.put("malloc", func);

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
            String name = "__string_" + constStringMap.size();
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
