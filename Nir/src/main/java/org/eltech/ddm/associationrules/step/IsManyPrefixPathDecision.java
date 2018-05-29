package org.eltech.ddm.associationrules.step;

import org.eltech.ddm.associationrules.fpg.FPGConditionalTable;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.algorithms.MiningDecision;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class IsManyPrefixPathDecision extends MiningDecision {

    public IsManyPrefixPathDecision(EMiningFunctionSettings settings, MiningBlock... trueBlocks) throws MiningException {
        super(settings, trueBlocks);
    }

    @Override
    protected boolean condition(EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;
        FPGConditionalTable fpgConditionalTable = (FPGConditionalTable) fpgModel.getConditionalList().getElement(fpgModel.getCurrentIndexConditionalList());
        return  fpgConditionalTable.getPrefixPath().size() > 1;
    }
}
