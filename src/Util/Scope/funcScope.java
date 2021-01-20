package Util.Scope;

import java.util.ArrayList;

import Util.position;
import Util.Entity.*;

public class funcScope extends Scope {
    private ArrayList<varEntity> paras = new ArrayList<>();

    public funcScope(Scope pa) {
        super(pa);
    }

    public ArrayList<varEntity> getParas() {
        return paras;
    }

    public void addPara(varEntity m, position pos) {
        paras.add(m);
        defineVariable(m.name(), m, pos);
    }
}
