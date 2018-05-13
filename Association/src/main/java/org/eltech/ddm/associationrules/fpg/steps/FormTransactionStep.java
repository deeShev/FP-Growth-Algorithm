package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.Transaction;
import org.eltech.ddm.associationrules.TransactionList;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * Created by denis on 03.12.17.
 */
public class FormTransactionStep extends Step {

    private final String itemIDsAttributeName;
    private final String transactionIDsAttributeName;

    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public FormTransactionStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
        itemIDsAttributeName = settings.getLogicalData().getAttribute(((AssociationRulesFunctionSettings) settings).getItemIDsAttributeName()).getName();
        transactionIDsAttributeName = settings.getLogicalData().getAttribute(((AssociationRulesFunctionSettings) settings).getTransactionIDsAttributeName()).getName();
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;

        int currentVector = fpgModel.getCurrentVector();
        MiningVector vector = inputData.getVector(currentVector);
        String transId = (String) vector.getValueCategory(transactionIDsAttributeName).getValue();
        String itemId = (String) vector.getValueCategory(itemIDsAttributeName).getValue();


        Item item = createItem(itemId, fpgModel);
        createTransaction(transId, item, fpgModel);

        return fpgModel;
    }

    private void createTransaction(String transId, Item item, final FPGModel fpgModel) {
        TransactionList transactions = fpgModel.getTransactionList();
        Transaction transaction;
        if (transactions.containsTransaction(transId)) {
            transaction = transactions.getTransaction(transId);
        } else {
            transaction = new Transaction();
            transaction.setTID(transId);
            transactions.add(transaction);
        }
        int currentTransaction = transactions.indexOf(transaction);
        fpgModel.setCurrentTransaction(currentTransaction);

        if (!transaction.getItemIDList().contains(item.getItemID())) {
            transaction.getItemIDList().add(item.getItemID());
        }

    }

    private Item createItem(String itemId, FPGModel fpgModel) {
        Item item = fpgModel.getItem(itemId);
        if (item == null) {
            item = new Item(itemId);
            fpgModel.addItem(item);
        }
        return item;
    }
}
