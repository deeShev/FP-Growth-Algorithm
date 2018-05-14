package org.eltech.ddm.associationrules.newfpg.fpg.steps;

import org.eltech.ddm.associationrules.newfpg.*;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.DataMiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class FormTransaction extends DataMiningBlock {
    private final String transactionIDsAttributeName;
    private final String itemIDsAttributeName;

    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public FormTransaction(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
        itemIDsAttributeName = ((AssociationRulesFunctionSettings) settings).getItemIDsAttributeName();
        transactionIDsAttributeName = ((AssociationRulesFunctionSettings) settings).getTransactionIDsAttributeName();
    }

    @Override
    protected EMiningModel execute(MiningInputStream dataSet, EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;
        MiningVector miningVector  = dataSet.getVector(fpgModel.getCurrentVectorIndex());
        String transId = miningVector.getValueCategory(transactionIDsAttributeName).getName();
        String itemId = miningVector.getValueCategory(itemIDsAttributeName).getName();

        Item item = createItem(itemId,  fpgModel);
        createTransaction(transId, item, fpgModel);

        return fpgModel;
    }

    private void createTransaction(String transId, Item item, FPGModel fpgModel) throws MiningException {
        TransactionList transactions = fpgModel.getTransactionList();
        Transaction transaction;
        //if (transactions.containsTransaction(transId)) {
        if (transactions.containsKey(transId)){
            transaction = transactions.getTransaction(transId);
        } else {
            transaction = new Transaction(transId);
            transactions.addTransaction(transaction);
        }

        //if (!transaction.contains(item)) {
        if (!transaction.containsKey(item.getID())){
            transaction.addElementInTransaction(item);
            item.setCount(item.getCount() + 1);
        }
    }

    private Item createItem(String itemId, FPGModel fpgModel) throws MiningException {
        //int indexItemToSet = fpgModel.getItemToSet(itemId);
        Item item;
        //if (indexItemToSet == -1){
        if (!fpgModel.getItem().containsKey(itemId)){
            item = new Item(itemId);
            fpgModel.addItem(AssociationRulesMiningModel.INDEX_ELEMENT_ITEM,item);
            return item;
        }else {
            item = (Item) fpgModel.getItem().getElement(itemId);
            return item;
        }
    }
}
