package MIR.IRInst;

import java.io.PrintStream;
import java.util.ArrayList;

import Backend.IRVisitor;
import MIR.BasicBlock;
import MIR.Function;
import MIR.IROperand.Register;
import MIR.IROperand.operand;

public class CallInst extends Inst {
    public Function func;
    public ArrayList<operand> paras;
    public Register result;

    public CallInst(BasicBlock bb, Function func, ArrayList<operand> paras, Register result) {
        super(bb);
        this.func = func;
        this.paras = paras;
        this.result = result;
    }

    @Override
    public void print(PrintStream out) {
        out.print("\t");
        String o = "";
        if(result != null) o += result.toString() + " = ";
        o += "call " + func.retType.toString() + " ";
        o += "@" + func.name + "(";
        for(int i = 0; i < paras.size(); ++ i) {
            if(i != 0) o += ", ";
            o += paras.get(i).type().toString() + " " + paras.get(i).toString();
        }
        o += ")";
        out.println(o);
    }

    @Override
    public void accept(IRVisitor it) {
        it.visit(this);
    }
}
