package org.eltech.ddm.associationrules.fpg;

import org.eltech.ddm.associationrules.*;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FPGModel extends AssociationRulesMiningModel implements Cloneable{
    private List<Item> sortedItemsList = new ArrayList<>();
    private List<HeaderTable> headerTableList = new ArrayList<>();
    private List<String> conditionalTransList = new ArrayList<>();

    private List<ConditionalTable> conditionalTableList = new ArrayList<>();
    private List<HeaderTable> conditionalHeaderTableList;
    private List<ItemSets> result = new ArrayList<>();
    private Node fpTree = null;
    private int minSupport;
    private int currentItemInSortedList = 0;
    private int currentHeaderTable = 0;
    private int currentConditionalTableList = 0;

    private int currentResult = 1;
    private int currentItemSet = 0;

    private int startIndex = 0;
    private int lastIndex = -1;


    public FPGModel(AssociationRulesFunctionSettings settings) throws MiningException {
        super(settings);
    }


    public void  setItemList(Map<String, Item> itemList){
        this.itemList = itemList;
    }
    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int lastIndex) {
        this.lastIndex = lastIndex;
    }

    @Override
    public int getCurrentItemSet() {
        return currentItemSet;
    }

    @Override
    public void setCurrentItemSet(int currentItemSet) {
        this.currentItemSet = currentItemSet;
    }

    public int getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(int currentResult) {
        this.currentResult = currentResult;
    }

    public List<String> getConditionalTransList() {
        return conditionalTransList;
    }

    public void setConditionalTransList(List<String> conditionalTransList) {
        this.conditionalTransList = conditionalTransList;
    }

    public List<HeaderTable> getConditionalHeaderTableList() {
        return conditionalHeaderTableList;
    }

    public void setConditionalHeaderTableList(List<HeaderTable> conditionalHeaderTableList) {
        this.conditionalHeaderTableList = conditionalHeaderTableList;
    }

    public int getCurrentConditionalTableList() {
        return currentConditionalTableList;
    }

    public void setCurrentConditionalTableList(int currentConditionalTableList) {
        this.currentConditionalTableList = currentConditionalTableList;
    }

    public List<ItemSets> getResult() {
        return result;
    }

    public List<ConditionalTable> getConditionalTableList() {
        return conditionalTableList;
    }

    public void setConditionalTableList(ConditionalTable conditionalTableList) {
        this.conditionalTableList.add(conditionalTableList);
    }

    public Node getFpTree() {
        return fpTree;
    }

    public void setFpTree(Node fpTree) {
        this.fpTree = fpTree;
    }

    public int getCurrentHeaderTable() {
        return currentHeaderTable;
    }

    public void setCurrentHeaderTable(int currentHeaderTable) {
        this.currentHeaderTable = currentHeaderTable;
    }

    public List<HeaderTable> getHeaderTableList() {
        return headerTableList;
    }

    public void setHeaderTableList(List<HeaderTable> headerTableList) {
        this.headerTableList = headerTableList;
    }

    public List<Item> getSortedItemsList() {
        return sortedItemsList;
    }

    public void setSortedItemsList(List<Item> sortedItemsList) {
        this.sortedItemsList = sortedItemsList;
    }


    public int getMinSupport() {
        return minSupport;
    }

    public void setMinSupport(int minSupport) {
        this.minSupport = minSupport;
    }

    public int getCurrentItemInSortedList() {
        return currentItemInSortedList;
    }

    public void setCurrentItemInSortedList(int currentItemInSortedList) {
        this.currentItemInSortedList = currentItemInSortedList;
    }

    @Override
    public Object clone() {
       FPGModel o  = (FPGModel)super.clone();
        if(itemList != null){
            o.itemList = new HashMap<>(itemList.size());
            for (Item item: itemList.values())
                o.itemList.put(item.getItemID(), (Item)item.clone());
        }

        if(transactionList != null){
            o.transactionList = new TransactionList();
            for (Transaction transaction : transactionList){
                Transaction coppedTrans = new Transaction();

                ArrayList<String> itemList = new ArrayList<>(transaction.getItemIDList().size());
                itemList.addAll(transaction.getItemIDList());

                coppedTrans.setItemIDList(itemList);
                coppedTrans.setTID(transaction.getTID());
                o.transactionList.add(coppedTrans);
            }
        }

        if (sortedItemsList != null){
            o.sortedItemsList = new ArrayList<>(sortedItemsList.size());
            for (Item item : sortedItemsList) {
                o.sortedItemsList.add((Item)item.clone());
            }
        }


        if (headerTableList != null){
            o.headerTableList = new ArrayList<>(headerTableList.size());
            for (HeaderTable headerTable : headerTableList) {
                o.headerTableList.add((HeaderTable) headerTable.clone());
            }
        }
        o.currentItem = currentItem;
        o.currentTransaction = currentTransaction;

        return o;
    }

    @Override
    public ArrayList<EMiningModel> split(int handlerCount) throws MiningException {
        ArrayList<EMiningModel> models = new ArrayList<>(handlerCount);
        if (!transactionList.isEmpty()){
            int start = 0;
            int block = transactionList.size()/handlerCount;
            for (int i = 0; i < transactionList.size()/handlerCount; i++) {
                FPGModel fpgModel = (FPGModel) this.clone();
                fpgModel.setStartIndex(start);
                fpgModel.setLastIndex(start + block);
                models.add(fpgModel);
                start = start + block;
            }
        }else {
            int start = 0;
            int numberVectors = this.getVectorCount();
            int block = numberVectors/handlerCount;
            for (int i = 0; i < handlerCount; i++) {
                FPGModel fpgModel = (FPGModel) this.clone();
                fpgModel.setStartIndex(start);
                fpgModel.setLastIndex(start + block);
                models.add(fpgModel);
                start = start + block;
            }
        }

        return models;
    }

    @Override
    public void join(List<EMiningModel> joinModels) throws MiningException {
        TransactionList transactions = new TransactionList();
        Map<String,Item> itemList = new HashMap<>();
        ArrayList<Item> sortItemList = new ArrayList<>();
        ArrayList<HeaderTable> headerTables = new ArrayList<>();
        for (EMiningModel eMiningModel : joinModels) {
            FPGModel fpgModel = (FPGModel) eMiningModel;
            transactions.addAll(fpgModel.getTransactionList());

            itemList.putAll(fpgModel.getItems());

            sortItemList.addAll(fpgModel.getSortedItemsList());

            headerTables.addAll(fpgModel.getHeaderTableList());
        }
        this.setTransactionList(transactions);
        this.setItemList(itemList);
        this.setSortedItemsList(sortItemList);
        this.setHeaderTableList(headerTables);

    }
}
