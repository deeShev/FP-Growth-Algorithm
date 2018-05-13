package org.eltech.ddm.associationrules.newfpg.fpg.steps;

import org.eltech.ddm.associationrules.newfpg.Item;
import org.eltech.ddm.associationrules.newfpg.Transaction;
import org.eltech.ddm.associationrules.newfpg.TransactionList;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGModel;
import org.eltech.ddm.associationrules.newfpg.fpg.HeaderTable;
import org.eltech.ddm.associationrules.newfpg.fpg.Node;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class FormFPTree extends MiningBlock {
    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public FormFPTree(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;

        TransactionList transactions = fpgModel.getTransactionList();
        Transaction currentTransaction = (Transaction) transactions.getElement(fpgModel.getCurrentIndexTransaction());
        Item itemInTransac = (Item) currentTransaction.getItemIdList(fpgModel.getCurrentIndexItemInTransaction());
        HeaderTable headerTable = (HeaderTable) fpgModel.getHeaderTable().getElement(fpgModel.getCurrentIndexHeaderTable());

        if (itemInTransac.getID().equals(headerTable.getID())) {
            int currentItemInTransac = fpgModel.getCurrentIndexItemInTransaction();
            List<MiningModelElement> currentTransactionSet = currentTransaction.getSet();
            Node node = (Node) fpgModel.getFPGTree();
            node.addNode(0, currentItemInTransac, currentTransactionSet, headerTable);
        }

        return fpgModel;
    }
}
