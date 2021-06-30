package Optim;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Stack;

import Assembly.AssemBlock;
import Assembly.AssemFunction;
import Assembly.AssemModule;
import Assembly.AssemInst.asmInst;
import Assembly.AssemInst.loadInst;
import Assembly.AssemInst.mvInst;
import Assembly.AssemInst.storeInst;
import Assembly.Operand.Imm;
import Assembly.Operand.PhysicalRegister;
import Assembly.Operand.Register;
import Assembly.Operand.VirtualRegister;

public class GraphColoringRegisterAllocater {
    private static class Edge {
        Register u, v;

        private Edge(Register u, Register v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public boolean equals(Object rhs) {
            return rhs instanceof Edge && ((Edge)rhs).u == u && ((Edge)rhs).v == v;
        }

        @Override
        public int hashCode() {
            return u.toString().hashCode() ^ v.toString().hashCode();
        }
    }

    private HashSet<Register> preColored = new HashSet<>();
    private AssemModule module;
    private AssemFunction cur_func;

    private HashSet<Register> spillWorkList = new LinkedHashSet<>();
    private HashSet<Register> freezeWorkList = new HashSet<>();
    private HashSet<Register> simplifyWorkList = new LinkedHashSet<>();
    private HashSet<Register> spilledNodes = new LinkedHashSet<>();
    private HashSet<Register> coloredNodes = new HashSet<>();
    private HashSet<Register> coalescedNodes = new LinkedHashSet<>();
    private HashSet<Register> spillIntroduce = new HashSet<>();
    private HashSet<Register> initial = new LinkedHashSet<>();

    private HashSet<mvInst> workListMoves = new LinkedHashSet<>();
    private HashSet<mvInst> activeMoves = new HashSet<>();
    private HashSet<mvInst> coalescedMoves = new HashSet<>();
    private HashSet<mvInst> constrainedMoves = new HashSet<>();
    private HashSet<mvInst> frozenMoves = new HashSet<>();

    private HashSet<Edge> edgeSet = new HashSet<>();
    private Stack<Register> selectStack = new Stack<>();
    private int stackLen = 0;
    private int K = 0;

    private static int inf = 2147483647;

    public GraphColoringRegisterAllocater(AssemModule m) {
        this.module = m;
        K = AssemModule.assignRegs.size();
        preColored = new HashSet<>(AssemModule.PhyRegs.values());
    }

    public void run() {
        module.functions.forEach((name, func) -> {
            cur_func = func;
            stackLen = 0;
            runFunc(func);
            stackLen += func.paraOff;
            if(stackLen % 16 != 0)
                stackLen += 16 - stackLen % 16;
            update();
        });
    }

    private void runFunc(AssemFunction func) {
        while(true) {
            init();
            new LivenessAnalysis(func).run();
            build();
            
            initial.forEach(t -> {
                if(t.degree >= K) spillWorkList.add(t);
                else if(moveRelated(t)) freezeWorkList.add(t);
                else simplifyWorkList.add(t);
            });

            do {
                if(!simplifyWorkList.isEmpty()) {
                    Simplify();
                } else if(!workListMoves.isEmpty()) {
                    Coalesce();
                } else if(!freezeWorkList.isEmpty()) {
                    Freeze();
                } else if(!spillWorkList.isEmpty()) {
                    selectSpill();
                }
            } while(! (simplifyWorkList.isEmpty() && workListMoves.isEmpty() && freezeWorkList.isEmpty() && spillWorkList.isEmpty()));

            assignColors();

            if(!spilledNodes.isEmpty())
                rewrite();
            else 
                break;
        }
    }

    private boolean moveRelated(Register reg) {
        return !nodeMoves(reg).isEmpty();
    }

    private HashSet<mvInst> nodeMoves(Register reg) {
        HashSet<mvInst> ret = new HashSet<>(workListMoves);
        ret.addAll(activeMoves);
        ret.retainAll(reg.moveList);
        return ret;
    }

    private void init() {
        spillWorkList.clear();
        freezeWorkList.clear();
        simplifyWorkList.clear();
        spilledNodes.clear();
        coloredNodes.clear();
        coalescedNodes.clear();
        initial.clear();

        workListMoves.clear();
        activeMoves.clear();
        coalescedMoves.clear();
        constrainedMoves.clear();
        frozenMoves.clear();

        edgeSet.clear();
        selectStack.clear();

        AssemBlock b = cur_func.entranBlock;
        while(b != null) {
            asmInst i = b.instHead;
            while(i != null) {
                initial.addAll(i.def());
                initial.addAll(i.use());
                i = i.next;
            }
            b = b.next;
        }
        initial.removeAll(preColored);

        initial.forEach(r -> {
            r.clear();
        });

        preColored.forEach(r -> {
            r.linkList.clear();
            r.moveList.clear();
            r.degree = inf;
            r.color = (PhysicalRegister)r;
            r.alias = null;
        });

        b = cur_func.entranBlock;
        while(b != null) {
            asmInst i = b.instHead;
            double w = Math.pow(10.0, Math.min(b.predecessor.size(), b.successor.size()));
            while(i != null) {
                i.use().forEach(r -> r.weight += w);
                i.def().forEach(r -> r.weight += w);
                i = i.next;
            }
            b = b.next;
        }
    }

    private void build() {
        AssemBlock b = cur_func.entranBlock;
        while(b != null) {
            HashSet<Register> liveReg = new HashSet<>(b.liveOut);
            asmInst i = b.instTail;
            while(i != null) {
                if(i instanceof mvInst) {
                    liveReg.removeAll(i.use());
                    HashSet<Register> mvReg = i.use();
                    mvReg.addAll(i.def());
                    for(var r : mvReg) r.moveList.add((mvInst)i);
                    workListMoves.add((mvInst)i);
                }
                HashSet<Register> defReg = i.def();
                liveReg.addAll(defReg);
                liveReg.add(AssemModule.getPhyReg("zero"));
                for(var def : defReg) 
                    liveReg.forEach(reg -> addEdge(reg, def));
                liveReg.removeAll(defReg);
                liveReg.addAll(i.use());
                i = i.pre;
            }
            b = b.next;
        }
    }

    private void Simplify() {
        Register reg = simplifyWorkList.iterator().next();
        simplifyWorkList.remove(reg);
        selectStack.push(reg);
        adjacent(reg).forEach(r -> {
            decrementDegree(r);
        });
    }

    private HashSet<Register> adjacent(Register c) {
        HashSet<Register> tmp = new HashSet<>(c.linkList);
        tmp.removeAll(selectStack);
        tmp.removeAll(coalescedNodes);
        return tmp;
    }

    private void decrementDegree(Register r) {
        r.degree --;
        if(r.degree == K - 1) {
            HashSet<Register> nodes = new HashSet<>(adjacent(r));
            nodes.add(r);
            enableMoves(nodes);
            spillWorkList.remove(r);
            if(moveRelated(r))
                freezeWorkList.add(r);
            else 
                simplifyWorkList.add(r);
        }
    }

    private void enableMoves(HashSet<Register> s) {
        s.forEach(r -> {
            nodeMoves(r).forEach(i -> {
                if(activeMoves.contains(i)) {
                    activeMoves.remove(i);
                    workListMoves.add(i);
                }
            });
        });
    }

    private void Coalesce() {
        mvInst i = workListMoves.iterator().next();
        Register x = getAlias(i.rd);
        Register y = getAlias(i.rs);
        Register u, v;
        if(preColored.contains(y)) {
            u = y;
            v = x;
        } else {
            u = x;
            v = y;
        }
        workListMoves.remove(i);
        if(u == v) {
            coalescedMoves.add(i);
            addWorkList(u);
        } else if(preColored.contains(v) || edgeSet.contains(new Edge(u, v))) {
            constrainedMoves.add(i);
            addWorkList(u);
            addWorkList(v);
        } else if((preColored.contains(u) && checkOK(u, v)) || (!preColored.contains(u) && conservative(u, v))) {
            coalescedMoves.add(i);
            combine(u, v);
            addWorkList(u);
        } else {
            activeMoves.add(i);
        }
    }

    private Register getAlias(Register reg) {
        if(coalescedNodes.contains(reg)) {
            Register a = getAlias(reg.alias);
            reg.alias = a;
            return a;
        } else return reg;
    }

    private void addWorkList(Register reg) {
        if((!preColored.contains(reg)) && (!moveRelated(reg)) && reg.degree < K) {
            freezeWorkList.remove(reg);
            simplifyWorkList.add(reg);
        }
    }

    private boolean checkOK(Register u, Register v) {           //George
        boolean ret = true;
        for(var t : adjacent(v))
            ret &= ok(t, u);
        return ret;
    }

    private boolean ok(Register t, Register u) {
        return t.degree < K || preColored.contains(t) || edgeSet.contains(new Edge(t, u));
    }

    private boolean conservative(Register u, Register v) {      //Briggs
        HashSet<Register> q = new HashSet<>(adjacent(u));
        q.addAll(adjacent(v));
        int cnt = 0;
        for(var t : q) {
            if(t.degree >= K) cnt ++;
        }
        return cnt < K;
    }

    private void combine(Register u, Register v) {
        if(freezeWorkList.contains(v)) {
            freezeWorkList.remove(v);
        } else {
            spillWorkList.remove(v);
        }
        coalescedNodes.add(v);
        v.alias = u;
        u.moveList.addAll(v.moveList);

        HashSet<Register> t = new HashSet<>();
        t.add(v);
        enableMoves(t);

        adjacent(v).forEach(y -> {
            addEdge(y, u);
            decrementDegree(y);
        });
        if(u.degree >= K && freezeWorkList.contains(u)) {
            freezeWorkList.remove(u);
            spillWorkList.add(u);
        }
    }

    private void Freeze() {
        Register u = freezeWorkList.iterator().next();
        freezeWorkList.remove(u);
        simplifyWorkList.add(u);
        freezeMoves(u);
    }

    private void freezeMoves(Register u) {
        nodeMoves(u).forEach(t -> {
            Register x = t.rd, y = t.rs, v;
            if(getAlias(u) == getAlias(y)) v = getAlias(x);
            else v = getAlias(y);
            activeMoves.remove(t);
            frozenMoves.add(t);
            if(v.degree < K && nodeMoves(v).isEmpty()) {
                freezeWorkList.remove(v);
                simplifyWorkList.add(v);
            }
        });
    }

    private void selectSpill() {
        Register r = getSpill();
        spillWorkList.remove(r);
        simplifyWorkList.add(r);
        freezeMoves(r);
    }

    private Register getSpill() {
        Register min = null;
        double mc = 0.0;
        Iterator<Register> iter = spillWorkList.iterator();
        while(iter.hasNext()) {
            min = iter.next();
            mc = min.weight / min.degree;
            if(!spillIntroduce.contains(min)) break;
        }
        while(iter.hasNext()) {
            Register reg = iter.next();
            if(!spillIntroduce.contains(reg) && (reg.weight / reg.degree < mc)) {
                min = reg;
                mc = reg.weight / reg.degree;
            }
        }

        return min;
    }

    private void assignColors() {
        while(!selectStack.isEmpty()) {
            Register r = selectStack.pop();
            ArrayList<PhysicalRegister> okColor = new ArrayList<>(AssemModule.assignRegs);
            HashSet<Register> colored = new HashSet<>(coloredNodes);
            colored.addAll(preColored);
            r.linkList.forEach(t -> {
                if(colored.contains(getAlias(t))) okColor.remove(getAlias(t).color);
            });
            if(okColor.isEmpty()) spilledNodes.add(r);
            else {
                r.color = okColor.get(0);
                coloredNodes.add(r);
            }
        }
        coalescedNodes.forEach(t -> t.color = getAlias(t).color);
    }

    private void rewrite() {
        spilledNodes.forEach(t -> {
            t.stackOff = new Imm(-stackLen - 4, true);
            stackLen += 4;
        });
        AssemBlock b = cur_func.entranBlock;
        while(b != null) {
            asmInst i = b.instHead;
            while(i != null) {
                if(i.rd() != null && i.rd() instanceof VirtualRegister)
                    getAlias(i.rd());
                i = i.next;
            }
            b = b.next;
        }

        b = cur_func.entranBlock;
        while(b != null) {
            asmInst i = b.instHead;
            while(i != null) {
                for(var reg : i.use()) {
                    if(reg.stackOff != null) {
                        if(i.def().contains(reg)) {
                            VirtualRegister tmp = new VirtualRegister(module.VirRegCnt ++, cur_func.VirReg ++);
                            spillIntroduce.add(tmp);
                            i.replaceUse(reg, tmp);
                            i.replaceDef(reg, tmp);
                            i.addPreInst(new loadInst(tmp, AssemModule.getPhyReg("sp"), reg.stackOff, b));
                            i.addNextInst(new storeInst(tmp, AssemModule.getPhyReg("sp"), reg.stackOff, b));
                        } else {
                            if(i instanceof mvInst && ((mvInst)i).rs == reg && ((mvInst)i).rd.stackOff == null) {
                                asmInst ii = new loadInst(((mvInst)i).rd, AssemModule.getPhyReg("sp"), reg.stackOff, b);
                                i.replaceSelf(ii);
                                i = ii;
                            } else {
                                VirtualRegister tmp = new VirtualRegister(module.VirRegCnt ++, cur_func.VirReg ++);
                                spillIntroduce.add(tmp);
                                i.addPreInst(new loadInst(tmp, AssemModule.getPhyReg("sp"), reg.stackOff, b));
                                i.replaceUse(reg, tmp);
                            }
                        }
                    }
                }
                for(var reg : i.def()) {
                    if(reg.stackOff != null) {
                        if(!i.use().contains(reg)) {
                            if(i instanceof mvInst && ((mvInst)i).rs.stackOff == null) {
                                asmInst ii = new storeInst(((mvInst)i).rs, AssemModule.getPhyReg("sp"), reg.stackOff, b);
                                i.replaceSelf(ii);
                                i = ii;
                            } else {
                                VirtualRegister tmp = new VirtualRegister(module.VirRegCnt ++, cur_func.VirReg ++);
                                spillIntroduce.add(tmp);
                                i.replaceDef(reg, tmp);
                                i.addNextInst(new storeInst(tmp, AssemModule.getPhyReg("sp"), reg.stackOff, b));
                            }
                        }
                    }
                }
                i = i.next;
            }
            b = b.next;
        }
    }

    private void update() {
        AssemBlock b = cur_func.entranBlock;
        while(b != null) {
            asmInst i = b.instHead;
            while(i != null) {
                i.setStackImm(stackLen);
                i = i.next;
            }
            b = b.next;
        }
        b = cur_func.entranBlock;
        while(b != null) {
            asmInst i = b.instHead;
            while(i != null) {
                if(i instanceof mvInst && ((mvInst)i).rs.color == ((mvInst)i).rd.color)
                    i.deleteSelf();
                i = i.next;
            }
            b = b.next;
        }
    }

    private void addEdge(Register u, Register v) {
        if(u != v && !edgeSet.contains(new Edge(u, v))) {
            edgeSet.add(new Edge(u, v));
            edgeSet.add(new Edge(v, u));
            if(!preColored.contains(u)) {
                u.linkList.add(v);
                u.degree ++;
            }
            if(!preColored.contains(v)) {
                v.linkList.add(u);
                v.degree ++;
            }
        }
    }

}
