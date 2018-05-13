package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.fpg.ConditionalFPTree;
import org.eltech.ddm.associationrules.fpg.ConditionalTable;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.associationrules.fpg.HeaderTable;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.List;

/**
 * Created by denis on 12.12.17.
 */
public class FormConditionalFPTreeAndFormItemStepStep extends Step {
    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public FormConditionalFPTreeAndFormItemStepStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = ((FPGModel) model);
        HeaderTable headerTable = fpgModel.getHeaderTableList().get(fpgModel.getCurrentHeaderTable());
        String itemID = headerTable.getItemID();

        ConditionalTable conditionalTable = fpgModel.getConditionalTableList().get(fpgModel.getCurrentConditionalTableList());
        List<List<Item>> prefixPath = conditionalTable.getPrefixPath();
        if (itemID.equals(conditionalTable.getSuffix())) {
            if (!prefixPath.isEmpty()) {
                ConditionalFPTree conditionalFPTree = new ConditionalFPTree(prefixPath, false, null);
                conditionalTable.setConditionalFPTree(conditionalFPTree);
                Item item = fpgModel.getItem(itemID);
                ItemSet suffix = new ItemSet(item);
                conditionalFPTree.getFerqItemSet(suffix, fpgModel, conditionalFPTree, prefixPath, fpgModel.getMinSupport());
            }
        }
        return fpgModel;
    }
}
