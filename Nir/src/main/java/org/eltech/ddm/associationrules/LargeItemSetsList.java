package org.eltech.ddm.associationrules;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class LargeItemSetsList  extends HashMapMiningModelElement {
    public LargeItemSetsList(String id) {
        super(id);
        set = new ArrayList<>();
    }

    public void  addItemSets(int index, ItemSets itemSets){
        set.add(index,itemSets);
        getEntry().put(itemSets.getID(),index);
    }

    /*public boolean contains(Object o) {
        boolean contain = false;
        for(MiningModelElement currItemSet : set) {
            ItemSets itemSets = (ItemSets) currItemSet;
            if(itemSets.equals(o)) {
                contain = true;
                break;
            } else {
                contain = false;
            }
        }
        return contain;
    }*/
    @Override
    protected String propertiesToString() {
        return ", size=" + size();
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }
}
