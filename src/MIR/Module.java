package MIR;

import java.util.HashMap;

import MIR.IROperand.globalVariable;

public class Module {
    public HashMap<String, Function> functions = new HashMap<>();
    public HashMap<String, Function> builtinFunction = new HashMap<>();
    public HashMap<String, globalVariable> globalVars = new HashMap<>();

    public Module() {
        //add builtin function

    } 

}
