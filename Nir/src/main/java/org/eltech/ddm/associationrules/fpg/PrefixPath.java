package org.eltech.ddm.associationrules.fpg;

import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class PrefixPath extends MiningModelElement {
    private int count;

    public PrefixPath(String id, int count) {
        super(id);
        this.count = count;
        set = new ArrayList<>();
    }

    public int getCount() {
        return count;
    }

    public List<MiningModelElement> getPrefixPath(){
        return set;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    protected String propertiesToString() {
        return null;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }

    public void addItemInPP(Item item){
        add(item);
    }
}
