package org.eltech.ddm.associationrules.newfpg.fpg.steps;

import org.eltech.ddm.associationrules.newfpg.Item;
import org.eltech.ddm.associationrules.newfpg.Transaction;
import org.eltech.ddm.associationrules.newfpg.TransactionList;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGModel;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGSortedItemList;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class SortTransaction extends MiningBlock {
    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public SortTransaction(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;
        TransactionList  transactions = fpgModel.getTransactionList();
        Transaction currentTransaction = (Transaction) transactions.getElement(fpgModel.getCurrentIndexTransaction());

        FPGSortedItemList fpgSortedItemList = (FPGSortedItemList) fpgModel.getSortedList();
        Item itemInSortList = (Item) fpgSortedItemList.getElement(fpgModel.getCurrentIndexSortedList());

        Transaction conditionalTransaction = currentTransaction.getConditionalTransaction(currentTransaction.getID());

        //if (currentTransaction.contains(itemInSortList)){
        if (currentTransaction.containsKey(itemInSortList.getID())){
            conditionalTransaction.addElementInTransaction(itemInSortList);
            currentTransaction.setConditionalTransaction(conditionalTransaction);
        }

        if (fpgSortedItemList.size() - 1 == fpgModel.getCurrentIndexSortedList()){
            transactions.replaceTransaction(fpgModel.getCurrentIndexTransaction(),conditionalTransaction);
            currentTransaction.setConditionalTransaction(null);
        }

        return fpgModel;
    }
}
