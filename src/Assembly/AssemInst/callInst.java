package Assembly.AssemInst;

import java.util.LinkedHashSet;

import Assembly.AssemBlock;
import Assembly.AssemFunction;
import Assembly.AssemModule;
import Assembly.Operand.Register;

public class callInst extends asmInst {
    public AssemFunction func;

    public callInst(AssemFunction func, AssemBlock b) {
        this.func = func;
        this.block = b;
    }

    @Override
    public String toString() {
        return "call " + func.toString();
    }

    @Override
    public void setStackImm(int s) {
        
    }
    
    @Override
    public LinkedHashSet<Register> use() {
        LinkedHashSet<Register> use = new LinkedHashSet<>();
        int m = 8 < func.func.paras.size() ? 8 : func.func.paras.size();
        for(int i = 0; i < m; ++ i)
            use.add(AssemModule.getPhyReg("a" + i));
        return use;
    }

    @Override
    public LinkedHashSet<Register> def() {
        LinkedHashSet<Register> def = new LinkedHashSet<>();
        def.addAll(AssemModule.callerRegs);
        return def;
    }

    @Override
    public Register rd() {
        return null;
    }

    @Override
    public void replaceUse(Register ori, Register rep) {

    }

    @Override
    public void replaceDef(Register ori, Register rep) {
        
    }
}
