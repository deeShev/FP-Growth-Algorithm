package org.eltech.ddm.associationrules.fpg;

import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class FPGSortedItemList extends MiningModelElement {

    public FPGSortedItemList(String id) {
        super(id);
        set = new ArrayList<>();
    }

    public List<MiningModelElement> getItemList(){
        return set;
    }

    public void addItemInSortedList(Item currentItem){
        if (set.isEmpty()){
            add(currentItem);
        }else {
            if (!set.contains(currentItem)) {
                for (int i = 0; i < size(); i++) {
                    Item item = (Item) getElement(i);
                    if (currentItem.getCount() > item.getCount()) {
                        set.add(i, currentItem);
                        break;
                    } else if (currentItem.getCount() == item.getCount()) {
                        int checkId = item.getID().compareTo(currentItem.getID());
                        if (checkId > 0) {
                            set.add(i, currentItem);
                            break;
                        }else {
                            if (i == size() - 1){
                                set.add(currentItem);
                                break;
                            }
                        }
                    } else {
                        if (i == size() - 1) {
                            set.add(currentItem);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    protected String propertiesToString() {
        return ", size=" + set.size();
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }
}
