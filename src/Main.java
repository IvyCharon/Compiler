import AST.*;
import Assembly.AssemModule;
import Backend.ASMPrinter;
import Backend.IRBuilder;
import Backend.IRPrinter;
//import Backend.IRPrinter;
import Backend.InstSelector;
import Backend.RegisterAllocator;
import Frontend.ASTBuilder;
import Frontend.SemanticChecker;
import Frontend.SymbolCollector;
import Frontend.TypeFilter;
import Parser.MxLexer;
import Parser.MxParser;
import Util.MxErrorListener;
import Util.error.error;
import Util.Scope.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;


public class Main {
    public static void main(String[] args) throws Exception{

        InputStream input = System.in;
        //InputStream input = new FileInputStream("test.mx");
        PrintStream output = new PrintStream("output.s");

        try {
            programNode ASTRoot;
            globalScope gScope = new globalScope();

            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree parseTreeRoot = parser.program();
            ASTBuilder astBuilder = new ASTBuilder();
            ASTRoot = (programNode)astBuilder.visit(parseTreeRoot);
            
            new SymbolCollector(gScope).visit(ASTRoot);
            new TypeFilter(gScope).visit(ASTRoot);
            new SemanticChecker(gScope).visit(ASTRoot);

            new IRBuilder(gScope).visit(ASTRoot);
            //new IRPrinter(new PrintStream("output.ll")).run(ASTRoot);

            AssemModule asmModule = new AssemModule();
            new InstSelector(ASTRoot.module, asmModule).run();
            new RegisterAllocator(asmModule).run();
            new ASMPrinter(output, asmModule).run();
            
        } catch (error er) {
            System.err.println(er.toString());
            throw new RuntimeException();
        }
    }
}