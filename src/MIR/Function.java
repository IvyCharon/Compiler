package MIR;

import java.util.ArrayList;
import java.util.HashMap;

//import MIR.IRInst.AllocInst;
//import MIR.IRInst.LoadInst;
//import MIR.IRInst.ReturnInst;
//import MIR.IRInst.StoreInst;
import MIR.IROperand.Register;
import MIR.IROperand.operand;
import MIR.IROperand.parameter;
import MIR.IRType.IRBaseType;
//import MIR.IRType.PointerType;
//import MIR.IRType.VoidType;

public class Function {
    public String name;
    public IRBaseType retType;
    public ArrayList<parameter> paras;
    public Register retVal;

    public HashMap<String, ArrayList<operand>> symbols = new HashMap<>();
    //public HashMap<String, ArrayList<BasicBlock>> BBs = new HashMap<>();

    public BasicBlock entranceBlock;
    public BasicBlock exitBlock;
    public BasicBlock retBlock;

    public Function(String name, IRBaseType retType, ArrayList<parameter> paras) {
        this.name = name;
        this.retType = retType;
        this.paras = paras;
        ArrayList<operand> tmp;
        for(parameter para : paras) {
            tmp = new ArrayList<>();
            tmp.add(para);
            symbols.put(para.name(), tmp);
        }
        entranceBlock = new BasicBlock(name + ".entrance", this);
        exitBlock = new BasicBlock(name + ".exit", this);
        retBlock = new BasicBlock(name + ".ret", this);

        entranceBlock.next = exitBlock;
        exitBlock.pre = entranceBlock;
        exitBlock.next = retBlock;
        retBlock.pre = exitBlock;
        //BasicBlockAdd(entranceBlock.name, entranceBlock);
        
        //exitBlock = new BasicBlock(name + ".exit", this);
        //exitBlock.pre = entranceBlock; entranceBlock.next = exitBlock;
        //retBlock = new BasicBlock(name + ".ret", this);
        //BasicBlockAdd(retBlock.name, retBlock);

        /* if(retType instanceof VoidType) {
            retVal = null;
            retBlock.addInst(new ReturnInst(retBlock, new VoidType(), null));
        } else {
            retVal = new Register(new PointerType(retType), "return_value.addr");
            entranceBlock.addInst(new AllocInst(entranceBlock, retVal, retType));
            entranceBlock.addInst(new StoreInst(entranceBlock, retType.toOper(), retVal));
            Register returnV = new Register(retType, "ret");
            retBlock.addInst(new LoadInst(retBlock, retType, retVal, returnV));
            retBlock.addInst(new ReturnInst(retBlock, retType, returnV));

            symbolAdd(retVal.name, retVal);
            symbolAdd(returnV.name, returnV);
        } */
    }

    public void symbolAdd(String key, operand oper) {
        if(symbols.containsKey(key)) {
            symbols.get(key).add(oper);
        } else {
            ArrayList<operand> value = new ArrayList<>();
            value.add(oper);
            symbols.put(key, value);
        }
    }

    /* public void BasicBlockAdd(String key, BasicBlock bb) {
        if(BBs.containsKey(key)) {
            BBs.get(key).add(bb);
        } else {
            ArrayList<BasicBlock> b = new ArrayList<>();
            b.add(bb);
            BBs.put(key, b);
        }
    } */

    public void addBasicBlock(BasicBlock bb) {
        exitBlock.pre.next = bb;
        bb.pre = exitBlock.pre;

        bb.next = exitBlock;
        exitBlock.pre = bb;
    }
    
}
