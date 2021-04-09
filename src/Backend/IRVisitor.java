package Backend;

import MIR.BasicBlock;
import MIR.Function;
import MIR.Module;
import MIR.IRInst.*;

public interface IRVisitor {
    void visit(Module it);
    void visit(Function func);
    void visit(BasicBlock bb);

    void visit(BinaryInst inst);
    void visit(BitCastInst inst);
    void visit(BranchInst inst);
    void visit(CallInst inst);
    void visit(CompareInst inst);
    void visit(GetElementPtrInst inst);
    void visit(LoadInst inst);
    void visit(PhiInst inst);
    void visit(ReturnInst inst);
    void visit(StoreInst inst);
}
