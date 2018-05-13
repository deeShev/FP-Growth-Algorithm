package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.Transaction;
import org.eltech.ddm.associationrules.TransactionList;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.List;

public class FormListOfItemsStep extends Step {
    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public FormListOfItemsStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = ((FPGModel) model);

        TransactionList transactions = fpgModel.getTransactionList();
        Transaction transaction = transactions.getTransaction(fpgModel.getCurrentTransaction());

        String itemID = transaction.getItemIDList().get(fpgModel.getCurrentItem());
        Item itemInTrans = fpgModel.getItem(itemID);

        List<Item> sortedItemsList = fpgModel.getSortedItemsList();
        Item itemInSortList = fpgModel.getSortedItemsList().get(fpgModel.getCurrentItemInSortedList());
        int indexItemInSortList = sortedItemsList.indexOf(itemInSortList);

        if (itemInTrans.getSupportCount() >= fpgModel.getMinSupport()) {
            int comparison = itemInSortList.getItemID().compareTo(itemInTrans.getItemID());
            if (sortedItemsList.contains(itemInTrans)) {
                if (itemInSortList.equals(itemInTrans)) {
                    itemInSortList.setSupportCount(itemInTrans.getSupportCount());
                } else {
                    int indexItemTransInSortList = sortedItemsList.indexOf(itemInTrans);
                    if (itemInTrans.getSupportCount() == itemInSortList.getSupportCount()) {
                        if (comparison < 0) {
                            sortedItemsList.remove(indexItemTransInSortList);
                            sortedItemsList.add(indexItemInSortList, itemInTrans);
                        }
                    } else if (itemInTrans.getSupportCount() > itemInSortList.getSupportCount()) {
                        if (indexItemTransInSortList > indexItemInSortList) {
                            sortedItemsList.remove(indexItemTransInSortList);
                            sortedItemsList.add(indexItemInSortList, itemInTrans);
                        }
                    } else {
                        sortedItemsList.remove(indexItemTransInSortList);
                        sortedItemsList.add(indexItemInSortList, itemInTrans);
                    }
                }
            } else {
                if (comparison > 0) {
                    sortedItemsList.add(indexItemInSortList, itemInTrans);
                } else {
                    indexItemInSortList += 1;
                    sortedItemsList.add(indexItemInSortList, itemInTrans);
                }
            }
        }
        return fpgModel;
    }
}
