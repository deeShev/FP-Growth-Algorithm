package org.eltech.ddm.associationrules.nir.newfpg;

import org.eltech.ddm.associationrules.HashMapMiningModelElement;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class ItemSets extends HashMapMiningModelElement {

    public ItemSets(String id) {
        super(id);
        set = new ArrayList<>();
    }


    /*public boolean contains(Object o) {
        boolean contain = false;
        for(MiningModelElement currItemSet : set) {
            ItemSet itemSet = (ItemSet) currItemSet;
            if(itemSet.equals(o)) {
                contain = true;
                break;
            } else {
                contain = false;
            }
        }
        return contain;
    }*/

    public ItemSet getItemSet(ItemSet currItemSet){
        //ItemSet foundItemSet = null;
        if (containsKey(currItemSet.getID())){
            return (ItemSet) getElement(currItemSet.getID());
        }
        return null;
        /*for (MiningModelElement itemSet : this.set){
            if (itemSet.equals(currItemSet)){
                foundItemSet = (ItemSet) itemSet;
                break;
            }
        }
        return foundItemSet;*/
    }

    public void addItemSet(MiningModelElement miningModelElement){
        add(miningModelElement);
    }

    @Override
    protected String propertiesToString() {
        return ", size=" + set.size();
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }
}
