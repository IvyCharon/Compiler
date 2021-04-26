package Assembly;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.PhysicalRegister;

public class AssemModule {
    public LinkedHashMap<String, AssemFunction> functions = new LinkedHashMap<>(), builtinFunctions = new LinkedHashMap<>();
    public LinkedHashMap<String, AsmGlobalVar> globalVars = new LinkedHashMap<>();
    public static LinkedHashMap<String, PhysicalRegister> PhyRegs = new LinkedHashMap<>();
    
    public int VirRegCnt = 0;

    public static ArrayList<PhysicalRegister> callerRegs = new ArrayList<>();
    public static ArrayList<PhysicalRegister> calleeRegs = new ArrayList<>();
    public static ArrayList<PhysicalRegister> assignRegs = new ArrayList<>();

    public static String [] PhyRegName = {
        "zero", "ra", "sp", "gp", "tp", "t0", "t1", "t2",
        "s0", "s1", "a0", "a1", "a2", "a3", "a4", "a5", 
        "a6", "a7", "s2", "s3", "s4", "s5", "s6", "s7", 
        "s8", "s9", "s10", "s11", "t3", "t4", "t5", "t6"
    };
    public static String [] calleePhyRegName = {
        "s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10", "s11" //12
    };
    public static String [] callerPhyRegName = {
        "ra", "t0", "t1", "t2", "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", 
        "t3", "t4", "t5", "t6" //16
    };
    public static String [] assignPhyRegName = {
        "t0", "t1", "t2", "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", 
        "t3", "t4", "t5", "t6", 
        "s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10", "s11", "ra"
    };

    public AssemModule() {
        for(int i = 0; i <= 31; ++ i) {
            PhyRegs.put(PhyRegName[i], new PhysicalRegister(PhyRegName[i]));
        }

        for(int i = 0; i < 28; ++ i) {
            assignRegs.add(getPhyReg(assignPhyRegName[i]));
        }
        for(int i = 0; i < 12; ++ i) {
            calleeRegs.add(getPhyReg(calleePhyRegName[i]));
        }
        for(int i = 0; i < 16; ++ i) {
            callerRegs.add(getPhyReg(callerPhyRegName[i]));
        }
    }    

    public static PhysicalRegister getPhyReg(String name) {
        return PhyRegs.get(name);
    }
}
