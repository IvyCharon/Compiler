package Util.Scope;

import java.util.ArrayList;
import Util.Entity.*;

public class funcScope extends Scope {
    private ArrayList<varEntity> paras;

    public funcScope(Scope pa) {
        super(pa);
    }

    public ArrayList<varEntity> getParas() {
        return paras;
    }
}
