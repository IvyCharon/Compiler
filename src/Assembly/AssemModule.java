package Assembly;

import java.util.LinkedHashMap;

import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.PhysicalRegister;

public class AssemModule {
    public LinkedHashMap<String, AssemFunction> functions = new LinkedHashMap<>(), builtinFunctions = new LinkedHashMap<>();
    public LinkedHashMap<String, AsmGlobalVar> globalVars = new LinkedHashMap<>();
    public LinkedHashMap<String, PhysicalRegister> PhyRegs = new LinkedHashMap<>();
    
    public int VirRegCnt = 0;

    public static String [] PhyRegName = {
        "zero", "ra", "sp", "gp", "tp", "t0", "t1", "t2",
        "s0", "s1", "a0", "a1", "a2", "a3", "a4", "a5", 
        "a6", "a7", "s2", "s3", "s4", "s5", "s6", "s7", 
        "s8", "s9", "s10", "s11", "t3", "t4", "t5", "t6"
    };

    public AssemModule() {
        for(int i = 0; i <= 31; ++ i) {
            PhyRegs.put(PhyRegName[i], new PhysicalRegister(PhyRegName[i]));
        }
    }    

    public PhysicalRegister getPhyReg(String name) {
        return PhyRegs.get(name);
    }
}
