package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.DecisionStep;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.algorithms.StepSequence;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * Created by denis on 12.12.17.
 */
public class IsPrefixPathDecisionStep extends DecisionStep {
    public IsPrefixPathDecisionStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    public IsPrefixPathDecisionStep(EMiningFunctionSettings settings, Step... trueSteps) throws MiningException {
        super(settings, trueSteps);
    }

    public IsPrefixPathDecisionStep(EMiningFunctionSettings settings, StepSequence trueSteps, Step... falseSteps) throws MiningException {
        super(settings, trueSteps, falseSteps);
    }

    public IsPrefixPathDecisionStep(EMiningFunctionSettings settings, StepSequence trueSteps, StepSequence falseSteps) throws MiningException {
        super(settings, trueSteps, falseSteps);
    }

    @Override
    protected boolean condition(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = ((FPGModel)model);
        return fpgModel.getConditionalTableList().get(fpgModel.getCurrentConditionalTableList()).getPrefixPath().size() > 1;
    }
}
