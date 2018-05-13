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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 08.12.17.
 */
public class SortTransactionStep extends Step {
    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public SortTransactionStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;
        TransactionList transactions = fpgModel.getTransactionList();
        Transaction transaction = transactions.getTransaction(fpgModel.getCurrentTransaction());
        List<String> transactionList = transaction.getItemIDList();

        List<Item> sortedItemsList = fpgModel.getSortedItemsList();
        Item item = sortedItemsList.get(fpgModel.getCurrentItemInSortedList());
        String itemIDInSortList = item.getItemID();

        List<String> sortTrans = fpgModel.getConditionalTransList();
        if (transactionList.contains(itemIDInSortList)) {
            sortTrans.add(itemIDInSortList);
        }

        if (sortedItemsList.size()-1 == fpgModel.getCurrentItemInSortedList()){
            transaction.setItemIDList(sortTrans);
            sortTrans = new ArrayList<>();
            fpgModel.setConditionalTransList(sortTrans);
        }
        return fpgModel;
    }
}
