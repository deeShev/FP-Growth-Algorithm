package org.eltech.ddm.associationrules.nir.newfpg;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class Item extends MiningModelElement {
    private int count = 0;

    public Item(String id) {
        super(id);
    }


    public Item(String id, int count) {
        super(id);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    @Override
    protected String propertiesToString() {
        return ", support= " + count;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {
        int delta = 0;
        for(MiningModelElement element: elements) {
            delta = delta + (((Item)element).count);
        }
        count = delta;
    }
}
