package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.Transaction;
import org.eltech.ddm.associationrules.TransactionList;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.associationrules.fpg.HeaderTable;
import org.eltech.ddm.associationrules.fpg.Node;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.List;

/**
 * Created by denis on 08.12.17.
 */
public class FormFPTreeStep extends Step {
    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public FormFPTreeStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;
        TransactionList transactions = fpgModel.getTransactionList();
        Transaction transaction = transactions.getTransaction(fpgModel.getCurrentTransaction());

        String itemID = transaction.getItemIDList().get(fpgModel.getCurrentItem());
        HeaderTable headerTable = fpgModel.getHeaderTableList().get(fpgModel.getCurrentHeaderTable());

        if (transaction.getTID().equals(transactions.get(0).getTID()) && fpgModel.getCurrentItem() == 0 && fpgModel.getCurrentHeaderTable() == 0) {
            Node root = new Node();
            fpgModel.setFpTree(root);
        }

        if (itemID.equals(headerTable.getItemID())) {
            int currentItem = fpgModel.getCurrentItem();
            List<String> currentTransaction = transaction.getItemIDList();
            fpgModel.getFpTree().addNode(0, currentItem, currentTransaction, 1, headerTable);
        }

        return fpgModel;
    }
}
