package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.CycleStep;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * Created by denis on 05.12.17.
 */
public class TransactionsCycleStep extends CycleStep {

    public TransactionsCycleStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    public TransactionsCycleStep(EMiningFunctionSettings settings, Step... steps) throws MiningException {
        super(settings, steps);
    }

    @Override
    protected EMiningModel initLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel)model;
        if (fpgModel.getLastIndex() == -1) {
            fpgModel.setLastIndex(fpgModel.getTransactionList().size());
        }
        fpgModel.setCurrentTransaction(fpgModel.getStartIndex());

        return model;
    }

    @Override
    protected boolean conditionLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
        return ((FPGModel) model).getCurrentTransaction() <
                ((FPGModel) model).getLastIndex();
    }

    @Override
    protected EMiningModel beforeIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
        return model;
    }

    @Override
    protected EMiningModel afterIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
        ((FPGModel) model).setCurrentTransaction(((FPGModel) model).getCurrentTransaction() + 1);
        return model;
    }
}
