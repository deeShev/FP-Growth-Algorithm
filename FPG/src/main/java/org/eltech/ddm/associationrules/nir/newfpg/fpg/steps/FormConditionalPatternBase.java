package org.eltech.ddm.associationrules.nir.newfpg.fpg.steps;

import org.eltech.ddm.associationrules.nir.newfpg.fpg.FPGConditionalTable;
import org.eltech.ddm.associationrules.nir.newfpg.fpg.FPGModel;
import org.eltech.ddm.associationrules.nir.newfpg.fpg.HeaderTable;
import org.eltech.ddm.associationrules.nir.newfpg.fpg.Node;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class FormConditionalPatternBase extends MiningBlock {
    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public FormConditionalPatternBase(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;
        HeaderTable currentHeaderTable = (HeaderTable) fpgModel.getHeaderTable().getElement(fpgModel.getCurrentIndexHeaderTable());
        Node node = currentHeaderTable.getNode();

        FPGConditionalTable fpgConditionalTable = new FPGConditionalTable(currentHeaderTable.getID());
        fpgModel.getListPrefix(node,fpgConditionalTable);

        if (!fpgConditionalTable.getPrefixPath().isEmpty()){
            fpgModel.addConditionalTable(fpgConditionalTable);
        }

        return fpgModel;
    }


}