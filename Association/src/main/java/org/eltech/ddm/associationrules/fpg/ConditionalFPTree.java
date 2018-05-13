package org.eltech.ddm.associationrules.fpg;

import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.ItemSets;

import java.util.*;

/**
 * Created by denis on 11.12.17.
 */
public class ConditionalFPTree extends Node {
    private String itemID = super.itemID;
    private int supportItem = 0;
    private ConditionalFPTree parent = null;
    private ConditionalFPTree next = null;
    private HashMap<String, ConditionalFPTree> children = null;


    public ConditionalFPTree(List<List<Item>> prefixPath, boolean largePrefix, List<HeaderTable> conditionalHeaderTableList) {
        int sizePrefixPath = prefixPath.size();
        int currentPrefixPath = 0;
        addNode(prefixPath, currentPrefixPath, sizePrefixPath, largePrefix, conditionalHeaderTableList);
    }


    private ConditionalFPTree(String itemID, int supportItem, ConditionalFPTree parent) {
        this.itemID = itemID;
        this.supportItem = supportItem;
        this.parent = parent;
    }

    public ConditionalFPTree getNext() {
        return next;
    }

    public void setNext(ConditionalFPTree next) {
        this.next = next;
    }

    @Override
    public String getItemID() {
        return itemID;
    }

    @Override
    public int getSupportItem() {
        return supportItem;
    }

    @Override
    public ConditionalFPTree getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "ItemID: " + itemID + " sup: " + supportItem;
    }

    private void addNode(List<List<Item>> prefixPath, int currentPrefixPath,
                         int sizePrefixPath, boolean largePrefix, List<HeaderTable> conditionalHeaderTableList) {
        if (prefixPath.size() != 0) {
            List<Item> itemList = prefixPath.get(currentPrefixPath);
            if (itemList.size() != 0) {
                int startIndex = itemList.size() - 1;
                int support = itemList.get(0).getSupportCount();
                appendNode(itemList, startIndex, support, largePrefix, conditionalHeaderTableList);
                currentPrefixPath++;
                if (currentPrefixPath < sizePrefixPath) {
                    addNode(prefixPath, currentPrefixPath, sizePrefixPath, largePrefix, conditionalHeaderTableList);
                }
            }
        }
    }

    private void appendNode(List<Item> itemList, int startIndex, int supportItem,
                            boolean largePrefix, List<HeaderTable> conditionalHeaderTableList) {

        if (children == null) {
            children = new HashMap<>();
        }
        ConditionalFPTree child = children.get(itemList.get(startIndex).getItemID());
        if (child != null) {
            child.supportItem += supportItem;
            startIndex--;
            if (startIndex > -1) {
                child.appendNode(itemList, startIndex, supportItem, largePrefix, conditionalHeaderTableList);
            }
        } else {
            String itemID = itemList.get(startIndex).getItemID();
            child = new ConditionalFPTree(itemID, supportItem, Integer.parseInt(itemID) < 0 ? null : this);

            if (largePrefix) {
                for (HeaderTable currentHT : conditionalHeaderTableList) {
                    if (currentHT.getItemID().equals(itemID)) {
                        child.addToHeaderTable(currentHT);
                        break;
                    }
                }
            }

            children.put(itemID, child);
            startIndex--;
            if (startIndex > -1) {
                child.appendNode(itemList, startIndex, supportItem, largePrefix, conditionalHeaderTableList);
            }
        }
    }

    private void addToHeaderTable(HeaderTable headerTable) {
        next = (ConditionalFPTree) headerTable.getNode();
        headerTable.setNode(this);
    }

    public void getFerqItemSet(ItemSet suffix, FPGModel fpgModel, ConditionalFPTree conditionalFPTree,
                               List<List<Item>> prefixPath, int minSupport) {
        List<Item> itemList = prefixPath.get(0);
        int count = itemList.size() -1;
        formItemSet1(suffix, fpgModel, conditionalFPTree, itemList, minSupport,count);

    }

    private void formItemSet1(ItemSet suffix, FPGModel fpgModel, ConditionalFPTree conditionalFPTree,
                              List<Item> itemList, int minSupport, int count){

        if (conditionalFPTree.children != null){
            ConditionalFPTree child = children.get(itemList.get(count).getItemID());
            if (child != null){
                if (child.supportItem >= minSupport) {
                    getTwoItemSets(child,suffix,itemList,fpgModel,count);
                    count = itemList.size() -1;
                    getBigItemSets(child,suffix,itemList,fpgModel,count);
                }
            }
        }
    }

    private void getTwoItemSets(ConditionalFPTree child, ItemSet suffix,List<Item> itemList, FPGModel fpgModel,int count) {
        if (child.supportItem >= fpgModel.getMinSupport()) {
            Item currentItem = fpgModel.getItem(child.itemID);
            ItemSet newItemSet = new ItemSet(suffix.getItemIDList());
            newItemSet.addItem(currentItem);
            newItemSet.setSupportCount(child.supportItem);
            List<ItemSets> result = fpgModel.getResult();
            addItemSet(result, newItemSet);
            if (child.children != null) {
                count--;
                child.getTwoItemSets(child.children.get(itemList.get(count).getItemID()), suffix, itemList, fpgModel, count);
            }
        }
    }

    private void getBigItemSets(ConditionalFPTree child, ItemSet suffix,List<Item> itemList, FPGModel fpgModel,int count){
        if (child.supportItem >= fpgModel.getMinSupport()) {
            Item currentItem = fpgModel.getItem(child.itemID);
            ItemSet newItemSet = new ItemSet(suffix.getItemIDList());
            newItemSet.addItem(currentItem);
            newItemSet.setSupportCount(child.supportItem);
            List<ItemSets> result = fpgModel.getResult();
            addItemSet(result,newItemSet);
            if (child.children != null) {
                count--;
                child.getBigItemSets(child.children.get(itemList.get(count).getItemID()),newItemSet,itemList,fpgModel,count);
            }
        }
    }


    public void addItemSet(List<ItemSets> result, ItemSet newItemSet){
        int positionInResult = getPositionInResult(newItemSet);
        ItemSets itemSets;
        if (result.size() < positionInResult + 1) {
            itemSets = new ItemSets();
            itemSets.add(newItemSet);
            result.add(positionInResult ,itemSets);
        } else {
            if (result.get(positionInResult).get(0).getItemIDList().size() == positionInResult + 1) {
                itemSets = result.get(positionInResult);
                if (!itemSets.contains(newItemSet)) {
                    itemSets.add(newItemSet);
                }
            }else {
                itemSets = new ItemSets();
                itemSets.add(newItemSet);
                result.add(positionInResult,itemSets);
            }
        }
    }


    private int getPositionInResult(ItemSet itemSet) {
        return itemSet.getItemIDList().size() -1;
    }
}
