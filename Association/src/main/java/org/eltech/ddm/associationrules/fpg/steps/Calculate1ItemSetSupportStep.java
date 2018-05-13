package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.Transaction;
import org.eltech.ddm.associationrules.TransactionList;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.associationrules.fpg.HeaderTable;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.List;

/**
 * Created by denis on 05.12.17.
 */
public class Calculate1ItemSetSupportStep extends Step {
    private final double support;

    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public Calculate1ItemSetSupportStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
        support = ((AssociationRulesFunctionSettings) settings).getMinSupport();
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;
        minSupport(fpgModel);

        TransactionList transactions = fpgModel.getTransactionList();
        Transaction transaction = transactions.getTransaction(fpgModel.getCurrentTransaction());

        String itemID = transaction.getItemIDList().get(fpgModel.getCurrentItem());
        Item item = fpgModel.getItem(itemID);
        item.setSupportCount(item.getSupportCount() + 1);
        List<Item> sortedItemsList = fpgModel.getSortedItemsList();
        if (fpgModel.getSortedItemsList().isEmpty()){
            if (item.getSupportCount() >= fpgModel.getMinSupport()){
                sortedItemsList.add(item);
            }
        }
        if (fpgModel.getHeaderTableList().isEmpty()){
            if (item.getSupportCount() >= fpgModel.getMinSupport()){
                HeaderTable headerTable = new HeaderTable(item.getItemID(), item.getSupportCount());
                fpgModel.getHeaderTableList().add(headerTable);
            }
        }

        return fpgModel;
    }

    private void minSupport(FPGModel fpgModel) {
        fpgModel.setMinSupport((int) Math.ceil(fpgModel.getTransactionCount() * support));
    }
}
