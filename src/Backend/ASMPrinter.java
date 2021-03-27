package Backend;

import java.io.PrintStream;

import Assembly.AssemBlock;
import Assembly.AssemFunction;
import Assembly.AssemModule;
import Assembly.AssemInst.asmInst;
import Assembly.Operand.AsmGlobalVar;
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

        asmModule.globalVars.forEach((name, gVar) -> printGlobalVar(gVar));

        
    }

    public void printFunc(AssemFunction func) {
        out.println(";------------------------");
        out.println("\t.globl\t" + func.name);
        out.println("\t.type\t" + func.name + ", @function");
        out.println(func.name + ":");
        AssemBlock tmp = func.entranBlock;
        while(tmp != null) {
            printBlock(tmp);
            tmp = tmp.next;
        }
        out.println("\t.size\t" + func.name + ", .-" + func.name);
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

    }

    public void printConstString(ConstString str) {
        
    }
}
