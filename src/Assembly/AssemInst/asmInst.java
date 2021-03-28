package Assembly.AssemInst;

abstract public class asmInst {
    public asmInst pre, next;

    abstract public String toString();

    public void addPreInst(asmInst tmp) {
        if(this.pre == null) {
            this.pre = tmp;
            tmp.next = this;
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
        } else {
            this.next.pre = tmp;
            tmp.next = this.next;

            this.next = tmp;
            tmp.pre = this;
        }
    }

    abstract public void setStackImm(int s);
}
