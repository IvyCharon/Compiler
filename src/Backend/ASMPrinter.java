package Backend;

import java.io.PrintStream;

import Assembly.AssemBlock;
import Assembly.AssemFunction;
import Assembly.AssemModule;
import Assembly.AssemInst.asmInst;
import Assembly.Operand.AsmGlobalVar;
import Assembly.Operand.AsmGlobalVar.type;
import MIR.IROperand.ConstString;

public class ASMPrinter {
    public PrintStream out;
    public AssemModule asmModule;

    public ASMPrinter(PrintStream out, AssemModule asmModule) {
        this.out = out;
        this.asmModule = asmModule;
    }

    public void run() {
        out.println("\t.text");
        asmModule.functions.forEach((name, func) -> printFunc(func));

        out.println(".section	.sdata,\"aw\",@progbits");

        asmModule.globalVars.forEach((name, gVar) -> printGlobalVar(gVar));
        
    }

    public void printFunc(AssemFunction func) {
        //out.println(";------------------------");
        out.println("\t.globl\t" + func.name);
        out.println("\t.type\t" + func.name + ", @function");
        out.println(func.name + ":");
        AssemBlock tmp = func.entranBlock;
        while(tmp != null) {
            printBlock(tmp);
            tmp = tmp.next;
        }
        out.println("\t.size\t" + func.name + ", .-" + func.name);
        out.println();
    }

    public void printBlock(AssemBlock block) {
        out.println(block.name + ":");
        asmInst tmp = block.instHead;
        while(tmp != null) {
            out.println("\t" + tmp.toString());
            tmp = tmp.next;
        }
    }

    public void printGlobalVar(AsmGlobalVar gVar) {
        if(gVar.gvType == type.Int) {
            out.println("\t.p2align\t2");
            out.println(gVar.name + ":");
            out.println("\t.word\t" + gVar.intVal);
        } else if(gVar.gvType == type.Bool) {
            out.println(gVar.name + ":");
            out.println("\t.byte\t" + gVar.boolVal);
        } else if(gVar.gvType == type.String) {
            out.println(gVar.name + ":");
            String o = gVar.stringVal;
            o = o.replace("\\", "\\\\");
            o = o.replace("\n", "\\n");
            o = o.replace("\"", "\\\"");
            out.println("\t.asciz\t\"" + o + "\"");
        }
        out.println();
    }
}
