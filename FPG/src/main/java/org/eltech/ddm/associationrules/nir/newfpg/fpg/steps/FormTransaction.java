package org.eltech.ddm.associationrules.nir.newfpg.fpg.steps;

import org.eltech.ddm.associationrules.nir.newfpg.*;
import org.eltech.ddm.associationrules.nir.newfpg.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.DataMiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class FormTransaction extends DataMiningBlock {
    private final String transactionIDsAttributeName;
    private final String itemIDsAttributeName;
    private final String status;

    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public FormTransaction(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
        itemIDsAttributeName =((AssociationRulesFunctionSettings) settings).getItemIDsAttributeName();
        transactionIDsAttributeName = ((AssociationRulesFunctionSettings) settings).getTransactionIDsAttributeName();
        status = ((AssociationRulesFunctionSettings) settings).getStatus();
    }

    @Override
    protected EMiningModel execute(MiningInputStream dataSet, EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;
        MiningVector miningVector = dataSet.getVector(fpgModel.getCurrentVectorIndex());


        String transId = String.valueOf(miningVector.getValue(transactionIDsAttributeName));
        String itemId = String.valueOf(miningVector.getValue(itemIDsAttributeName));
        String statusID = String.valueOf(miningVector.getValue(status));


        createTransaction(transId, itemId, statusID, fpgModel);

        return fpgModel;
    }

    private void createTransaction(String transId, String itemId, String status, FPGModel fpgModel) throws MiningException {
        TransactionList transactions = fpgModel.getTransactionList();
        Transaction transaction;
        if (transactions.containsKey(transId)) {
            transaction = transactions.getTransaction(transId);
        } else {
            transaction = new Transaction(transId);
            transactions.addTransaction(transaction);
        }

        if (transaction.isPositiveStatus()){
            checkStatus(transaction, status);
        }

        Item item = createItem(0 + "_" + itemId, transaction, fpgModel);

        if (!transaction.containsKey(item.getID())) {
            transaction.addElementInTransaction(item);
            item.setCount(item.getCount() + 1);
        }
    }

    private Item createItem(String itemId, Transaction transaction, FPGModel fpgModel) throws MiningException {
        Item item = new Item(itemId);

        if (!fpgModel.getItem().containsKey(itemId)) {
            if (transaction.size() < 1) {
                fpgModel.addItem(AssociationRulesMiningModel.INDEX_ELEMENT_ITEM, item);
            } else {
                item = (Item) transaction.getItemIdList(transaction.size() - 1);
                item = adapt(item,itemId);

                if (fpgModel.getItem().containsKey(item.getID())) {
                    item = (Item) fpgModel.getItem().getElement(item.getID());
                } else {
                    fpgModel.addItem(AssociationRulesMiningModel.INDEX_ELEMENT_ITEM, item);
                }
            }
            return item;
        } else {
            item = sequenceItemInTrans(itemId, transaction, fpgModel);
            return item;
        }
    }

    private Item sequenceItemInTrans(String itemId, Transaction transaction, FPGModel fpgModel) throws MiningException {
        Item item = (Item) fpgModel.getItem().getElement(itemId);
        if (transaction.containsKey(item.getID())) {
            item = (Item) transaction.getItemIdList(transaction.size() - 1);
            item = adapt(item, itemId);
            if (fpgModel.getItem().containsKey(item.getID())) {
                item = (Item) fpgModel.getItem().getElement(item.getID());
                return item;
            } else {
                fpgModel.addItem(AssociationRulesMiningModel.INDEX_ELEMENT_ITEM, item);
                return item;
            }
        } else {
            if (transaction.size() != 0) {
                item = (Item) transaction.getItemIdList(transaction.size() - 1);
                item = adapt(item, itemId);
                if (fpgModel.getItem().containsKey(item.getID())) {
                    item = (Item) fpgModel.getItem().getElement(item.getID());
                    return item;
                } else {
                    fpgModel.addItem(AssociationRulesMiningModel.INDEX_ELEMENT_ITEM, item);
                    return item;
                }
            }else {
                item = (Item) fpgModel.getItem().getElement(item.getID());
                return item;
            }
        }
    }

    private Item adapt(Item currentItem, String itemId) {
        String id = currentItem.getID();
        String[] order = id.split("_");
        int count = Integer.parseInt(order[0]);
        currentItem = new Item(++count + "_" + itemId.split("_")[1]);
        return currentItem;
    }

    private void checkStatus(Transaction transaction, String status){
        if ((int) Double.parseDouble(status) != 8){
            transaction.setPositiveStatus(false);
        }
    }


}
