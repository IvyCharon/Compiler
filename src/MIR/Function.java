package MIR;

import java.util.ArrayList;

import MIR.IROperand.parameter;
import MIR.IRType.IRBaseType;

public class Function {
    public String name;
    public IRBaseType retType;
    public ArrayList<parameter> paras;
    public BasicBlock body;


}
