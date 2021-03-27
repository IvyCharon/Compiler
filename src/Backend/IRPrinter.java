package Backend;

import java.io.PrintStream;

import AST.programNode;
import MIR.BasicBlock;
import MIR.Function;
import MIR.Module;
import MIR.IRInst.Inst;

public class IRPrinter {
    private PrintStream out;
    
    public IRPrinter(PrintStream out) {
        this.out = out;
    }

    private void printBuiltinFuncDecl(Function func) {
        out.print("declare ");
        out.print(func.retType.toString() + " @" + func.name + "(");
        int i = 0;
        for(int j = 0; j < func.paras.size(); ++ j) {
            out.print(func.paras.get(j).type().toString() + " %" + i);
            i += 1;
            if(j != func.paras.size() - 1)
                out.print(", ");
        }
        func.paras.forEach(t -> {
            
        });
        out.println(")");
    }

    public void run(programNode root) {
        Module module = root.module;

        module.builtinFunctions.forEach((name, func) -> {
            printBuiltinFuncDecl(func);
        });

        module.classes.forEach((name, cla) -> {

        });

        module.globalVars.forEach((name, gVar) -> {

        });

        module.functions.forEach((name, func) -> {
            print(func);
        });
    }

    public void print(Function func) {
        out.print("define " + func.retType.toString() + " @" + func.name + "(");
        int cnt = 0;
        for(int i = 0; i < func.paras.size(); ++ i) {
            out.print(func.paras.get(i).type().toString() + " %" + cnt);
            if(i != func.paras.size() - 1)
                out.print(", ");
        }
        out.println(") {");
        
        cnt = 0;

        BasicBlock bb = func.entranceBlock;
        while (bb != null) {
            out.println("b." + cnt);
            bb = bb.next;
        }

        out.println("}");
    }

    public void print(BasicBlock bb) {
        //out.println("Block " + bb.name + ":");

        Inst inst = bb.instHead;

        while(inst != null) {
            inst.print(out);
            inst = inst.next;
        }
    }
}
