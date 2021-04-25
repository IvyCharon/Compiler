package Assembly.AssemInst;

import java.util.LinkedHashSet;

import Assembly.AssemBlock;
import Assembly.Operand.Register;

abstract public class asmInst {
    public asmInst pre, next;
    public AssemBlock block;

    abstract public String toString();

    public void addPreInst(asmInst tmp) {
        if(this.pre == null) {
            this.pre = tmp;
            tmp.next = this;
            block.instHead = tmp;
        } else {
            this.pre.next = tmp;
            tmp.pre = this.pre;

            this.pre = tmp;
            tmp.next = this;
        }
    }

    public void addNextInst(asmInst tmp) {
        if(this.next == null) {
            this.next = tmp;
            tmp.pre = this;
            block.instTail = tmp;
        } else {
            this.next.pre = tmp;
            tmp.next = this.next;

            this.next = tmp;
            tmp.pre = this;
        }
    }

    public void replaceSelf(asmInst i) {
        if(this.pre != null) {
            this.pre.next = i;
            i.pre = this.pre;
        } else {
            block.instHead = i;
            i.pre = null;
        }
        if(this.next != null) {
            this.next.pre = i;
            i.next = this.next;
        } else {
            block.instTail = i;
            i.next = null;
        }
    }

    public void deleteSelf() {
        if(this.pre != null)
            this.pre.next = this.next;
        else 
            block.instHead = this.next;
        if(this.next != null)
            this.next.pre = this.pre;
        else
            block.instTail = this.pre;
    }

    abstract public void setStackImm(int s);

    abstract public LinkedHashSet<Register> use();

    abstract public LinkedHashSet<Register> def();

    abstract public Register rd();

    abstract public void replaceUse(Register ori, Register rep);

    abstract public void replaceDef(Register ori, Register rep);
}
