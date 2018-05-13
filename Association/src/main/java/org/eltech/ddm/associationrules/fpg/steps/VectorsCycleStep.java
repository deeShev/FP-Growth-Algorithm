package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.CycleStep;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.io.Serializable;

public class VectorsCycleStep extends CycleStep implements Serializable {

    public VectorsCycleStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    public VectorsCycleStep(EMiningFunctionSettings settings, Step... steps) throws MiningException {
        super(settings, steps);
    }

    @Override
    protected EMiningModel initLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
        //setStateParameter(model, EMiningModel.NAME_CURRENT_VECTOR, 0);
        FPGModel fpgModel = (FPGModel)model;

        fpgModel.setCurrentVector(fpgModel.getStartIndex());

        return fpgModel;
    }

    @Override
    protected boolean conditionLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
        //return  (Integer)getStateParameter(model, EMiningModel.NAME_CURRENT_VECTOR) < inputData.getVectorsNumber();
        FPGModel fpgModel = (FPGModel) model;
        return fpgModel.getCurrentVector() < fpgModel.getLastIndex();
    }

    @Override
    protected EMiningModel beforeIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
        //inputData.move(((EMiningModel)model).getCurrentVector());

        return model;
    }

    @Override
    protected EMiningModel afterIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
        //setStateParameter(model, EMiningModel.NAME_CURRENT_VECTOR, (Integer)getStateParameter(model, EMiningModel.NAME_CURRENT_VECTOR) +1 );
        model.setCurrentVector(model.getCurrentVector() + 1);

        return model;
    }

}
