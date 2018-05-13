package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.AssociationRule;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.ItemSets;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.List;

public class GenerateAssosiationRuleStep extends Step {
    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public GenerateAssosiationRuleStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = ((FPGModel)model);
        if(fpgModel.getCurrentResult() == 0) {
            return fpgModel;
        }

        ItemSets currentItemSetList = fpgModel.getResult().get(fpgModel.getCurrentResult());
        ItemSet itemSet = currentItemSetList.get(fpgModel.getCurrentItemSet());
        String itemId = itemSet.getItemIDList().get(fpgModel.getCurrentItem());

        ItemSet a = new ItemSet(itemSet.getItemIDList());
        a.getItemIDList().remove(itemId);
        //int confidence = itemSet.getSupportCount() / a.getSupportCount();
        /*if (confidence >= minConfidence) {*/
            ItemSet c = new ItemSet(itemSet.getItemIDList());
            c.getItemIDList().removeAll(a.getItemIDList());
            AssociationRule rule = new AssociationRule(a, c, fpgModel.getMinSupport(), 0);
            List<AssociationRule> associationRuleSet = fpgModel.getAssociationRuleSet();
            if (!associationRuleSet.contains(rule)) {
                associationRuleSet.add(rule);
            }
        //}
        return fpgModel;
    }
}
