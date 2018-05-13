package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.CycleStep;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * Created by denis on 08.12.17.
 */
public class SortedItemsListCycleStep extends CycleStep {
    public SortedItemsListCycleStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    public SortedItemsListCycleStep(EMiningFunctionSettings settings, Step... steps) throws MiningException {
        super(settings, steps);
    }

    @Override
    protected EMiningModel initLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
        ((FPGModel) model).setCurrentItemInSortedList(0);
        return model;
    }

    @Override
    protected boolean conditionLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
        return ((FPGModel) model).getCurrentItemInSortedList() < ((FPGModel) model).getSortedItemsList().size();
    }

    @Override
    protected EMiningModel beforeIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
        return model;
    }

    @Override
    protected EMiningModel afterIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
        ((FPGModel) model).setCurrentItemInSortedList(((FPGModel) model).getCurrentItemInSortedList() + 1);
        return model;
    }
}