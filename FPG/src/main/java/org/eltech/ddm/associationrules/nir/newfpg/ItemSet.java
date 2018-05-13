package org.eltech.ddm.associationrules.nir.newfpg;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class ItemSet extends MiningModelElement {
    private int count = 0;

    public ItemSet(String id) {
        super(id);
        set = new ArrayList<>();
    }

    public ItemSet(String id, Item... items){
        super(id);
        for (Item item : items){
            addInSet(item);
        }
    }
    public ItemSet(String id, int count, Item... items){
        super(id);
        this.count = count;
        for (Item item : items){
            addInSet(item);
        }
    }

    public ItemSet(String id, int count, List<MiningModelElement> itemIDs) {
        super(id);
        this.count = count;
        set = new ArrayList<>();
        for(MiningModelElement itemID : itemIDs) {
            Item item = new Item(itemID.getID());
            item.setCount(count);
            set.add(item);
        }
    }


    public void addInSet(Item currentItem){
        super.add(currentItem);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MiningModelElement> getItemIDList() {
        return set;
    }


    @Override
    public boolean equals(Object obj) {
        ItemSet itemSet = (ItemSet) obj;
        if(this.size() != itemSet.getItemIDList().size()) {
            return false;
        }
        for(int i = 0; i < size(); i++){
            if(!getElement(i).equals(itemSet.getItemIDList().get(i)))
                return false;
        }
        return true;
    }

    @Override
    protected String propertiesToString() {
        return ", count=" + getCount();
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }
}
