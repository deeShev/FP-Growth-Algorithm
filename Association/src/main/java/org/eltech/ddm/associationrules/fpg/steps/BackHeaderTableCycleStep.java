package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.CycleStep;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * Created by denis on 11.12.17.
 */
public class BackHeaderTableCycleStep extends CycleStep{
    public BackHeaderTableCycleStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    public BackHeaderTableCycleStep(EMiningFunctionSettings settings, Step... steps) throws MiningException {
        super(settings, steps);
    }

    @Override
    protected EMiningModel initLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
        ((FPGModel)model).setCurrentHeaderTable(((FPGModel)model).getHeaderTableList().size()-1);
        return model;
    }

    @Override
    protected boolean conditionLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
        return ((FPGModel)model).getCurrentHeaderTable() > -1;
    }

    @Override
    protected EMiningModel beforeIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
        return model;
    }

    @Override
    protected EMiningModel afterIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
        ((FPGModel) model).setCurrentHeaderTable(((FPGModel) model).getCurrentHeaderTable() - 1);
        return model;
    }
}
