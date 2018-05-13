package org.eltech.ddm.associationrules.nir.newfpg;

import org.eltech.ddm.associationrules.HashMapMiningModelElement;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class  Transaction extends HashMapMiningModelElement {
    private Transaction conditionalTransaction = null;
    private boolean positiveStatus;

    public Transaction(String id) {
        super(id);
        set = new ArrayList<>();
    }

    public void setPositiveStatus(boolean positiveStatus) {
        this.positiveStatus = positiveStatus;
    }

    public boolean isPositiveStatus() {
        return positiveStatus;
    }

    public List<MiningModelElement> getSet(){
        return set;
    }

    public void addElementInTransaction(Item currentItem){
        add(currentItem);
    }

    /*public boolean contains(Item currentItem){
        return set.contains(currentItem);
    }*/

    public Transaction getConditionalTransaction(String id){
        if (conditionalTransaction == null) {
            conditionalTransaction = new Transaction(id);
        }
        return conditionalTransaction;
    }

    public void setConditionalTransaction(Transaction conditionalTransaction) {
        this.conditionalTransaction = conditionalTransaction;
    }

    public MiningModelElement getItemIdList(int index){
        return this.getElement(index);
    }

    @Override
    protected String propertiesToString() {
        return ", " + set;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }


}
