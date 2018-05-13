package org.eltech.ddm.cluster.actors.messages;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.algorithms.StepSequence;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.io.Serializable;

public class ExecuteJob implements Serializable {
    private final Step step;
    private final EMiningModel miningModel;
    private final MiningInputStream miningInputStream;

    public ExecuteJob(MiningInputStream inputData, EMiningModel model, Step step) {
        miningInputStream = inputData;
        miningModel = model;
       this.step = step;
    }

    public Step getStep() {
        return step;
    }

    public EMiningModel getMiningModel() {
        return miningModel;
    }

    public MiningInputStream getMiningInputStream() {
        return miningInputStream;
    }
}


