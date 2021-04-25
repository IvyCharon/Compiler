package Assembly.AssemInst;

import java.util.LinkedHashSet;

import Assembly.AssemBlock;
import Assembly.Operand.Register;

public class retInst extends asmInst {
    public retInst(AssemBlock b) {
        this.block = b;
    }
    
    @Override
    public String toString() {
        return "ret";
    }

    @Override
    public void setStackImm(int s) {}
    
    @Override
    public LinkedHashSet<Register> use() {
        LinkedHashSet<Register> use = new LinkedHashSet<>();
        return use;
    }

    @Override
    public LinkedHashSet<Register> def() {
        LinkedHashSet<Register> def = new LinkedHashSet<>();
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
