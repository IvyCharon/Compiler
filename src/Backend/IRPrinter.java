package Backend;

import java.io.PrintStream;

import AST.programNode;
import MIR.BasicBlock;
import MIR.Function;
import MIR.Module;
import MIR.IRInst.Inst;
import MIR.IROperand.globalVariable;
import Util.Type.classType;

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
        out.println();

        module.classes.forEach((name, cla) -> print(cla));
        out.println();

        module.globalVars.forEach((name, gVar) -> print(gVar));
        out.println();

        module.functions.forEach((name, func) -> {
            print(func);
            out.println();
        });
    }

    public void print(Function func) {
        out.print("define " + func.retType.toString() + " @" + func.name + "(");
        int cnt = 0;
        for(int i = 0; i < func.paras.size(); ++ i) {
            out.print(func.paras.get(i).type().toString() + " %" + cnt++);
            if(i != func.paras.size() - 1)
                out.print(", ");
        }
        out.println(") {");

        BasicBlock bb = func.entranceBlock;
        while (bb != null) {
            out.println(bb.name);
            print(bb);
            out.println();
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

    public void print(classType cla) {
        out.println(cla.toString());
    }

    public void print(globalVariable gVar) {
        out.println(gVar.toString());
    }
}
