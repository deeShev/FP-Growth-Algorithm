package org.eltech.ddm.associationrules.nir.newfpg;

import org.eltech.ddm.associationrules.HashMapMiningModelElement;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class AssociationRulesMiningModel extends EMiningModel {

    static private final int TRANSACTION_LIST = 1;
    static private final int ELEMENT_ITEM = 2;
    static private final int LARGE_ITEM_SETS_LIST = 3;
    static private final int ASSOCIATION_RULE_SET = 4;

    static public final int[] INDEX_TRANSACTION_LIST = {TRANSACTION_LIST};
    static public final int[] INDEX_ELEMENT_ITEM = {ELEMENT_ITEM};
    static private final int[] INDEX_ITEM_SETS = {LARGE_ITEM_SETS_LIST};
    static private final int[] INDEX_ASSOCIATION_RULE_SET = {ASSOCIATION_RULE_SET};
    static public final int[] INDEX_CURRENT_TRANSACTION_ITEM = {TRANSACTION_LIST, CURRENT_ELEMENT};



    public AssociationRulesMiningModel(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
        sets.add(TRANSACTION_LIST, new TransactionList("Transactions"));
        sets.add(ELEMENT_ITEM, new HashMapMiningModelElement("Items") {
            @Override
            protected String propertiesToString() {
                return ", size=" + size();
            }

            @Override
            public void merge(List<MiningModelElement> elements) throws MiningException {

            }
        });
        sets.add(LARGE_ITEM_SETS_LIST, new LargeItemSetsList("Large item sets"));
        sets.add(ASSOCIATION_RULE_SET,new AssociationRuleSet("Association Rule Set"));
    }


    @Override
    public void initModel() {

    }

    public TransactionList getTransactionList() throws MiningException {
        return (TransactionList)getElement(index(TRANSACTION_LIST));
    }

    public int getCurrentIndexTransaction() throws MiningException {
        return getCurrentElementIndex(index(INDEX_TRANSACTION_LIST));
    }


    public int getCurrentIndexItemInTransaction() throws MiningException {
        return getCurrentElementIndex(index(INDEX_CURRENT_TRANSACTION_ITEM));
    }


    public AssociationRuleSet getAssociationRuleSet() throws MiningException {
        return (AssociationRuleSet)getElement(index(INDEX_ASSOCIATION_RULE_SET));
    }


    public MiningModelElement getItemSets() throws MiningException {
        return getElement(index(INDEX_ITEM_SETS));
    }

    public void addItemSets(ItemSet curItemSet) throws MiningException{
        int positionInLargeISs = curItemSet.size() - 2;
        LargeItemSetsList largeItemSets = (LargeItemSetsList) getItemSets();
        ItemSets itemSets = new ItemSets("" + curItemSet.size());
        //if (!largeItemSets.contains(itemSets)){
        if (!largeItemSets.containsKey(itemSets.getID())){
            itemSets.addItemSet(curItemSet);
            largeItemSets.addItemSets(positionInLargeISs ,itemSets);
        }else {
            itemSets = (ItemSets) largeItemSets.getElement(positionInLargeISs);
            if (itemSets.getElement(0).size() == positionInLargeISs + 2) {
                //if (!itemSets.contains(curItemSet)) {
                if (!itemSets.containsKey(curItemSet.getID())) {
                    itemSets.addItemSet(curItemSet);
                }else {
                    ItemSet itemSet = itemSets.getItemSet(curItemSet);
                    itemSet.setCount(itemSet.getCount() + curItemSet.getCount());
                    for (int i = 0; i < itemSet.size(); i++){
                        Item item = (Item) itemSet.getElement(i);
                        item.setCount(itemSet.getCount());
                    }
                }
            }else {
                itemSets = new ItemSets("" + curItemSet.size());
                itemSets.addItemSet(curItemSet);
                largeItemSets.addItemSets(positionInLargeISs ,itemSets);
            }
        }
    }


    public HashMapMiningModelElement getItem() throws MiningException {
        return (HashMapMiningModelElement) getElement(EMiningModel.index(INDEX_ELEMENT_ITEM));
    }

    public int getCurrentIndexItem() throws MiningException {
        return getCurrentElementIndex(index(INDEX_ELEMENT_ITEM));
    }

    public void addItem(int[] index, Item currentItem) throws MiningException {
        addElement(index, currentItem);
    }

}
