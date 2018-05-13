package org.eltech.ddm.associationrules.nir.newfpg;

import org.eltech.ddm.associationrules.HashMapMiningModelElement;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class TransactionList extends HashMapMiningModelElement {

    public TransactionList(String id) {
        super(id);
        set = new ArrayList<>();
    }

    @Override
    protected String propertiesToString() {
        return ", size=" + size();
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }

    public void replaceTransaction(int index, Transaction conditionalTransaction){
        replace(index, conditionalTransaction);
    }

    public Transaction getTransaction(String tid) {
        if (containsKey(tid)){
            return (Transaction) getElement(tid);
        }
        /*for (int i = 0; i < size(); i++){
            Transaction transaction = (Transaction) getElement(i);
            if (transaction.getID().equals(tid)){
                return transaction;
            }
        }*/
        return null;
    }

    public void remove(int index){
        super.remove(index);
    }

   public void addTransaction(MiningModelElement element){
       add(element);
   }

    /*public boolean containsTransaction(String tid) {
       for (int i = 0; i < size(); i++){
           Transaction transaction = (Transaction)getElement(i);
           if (transaction.getID().equals(tid)){
               return true;
           }
       }
        return false;
    }*/
}
