package org.eltech.ddm.associationrules.newFpgTest;

import org.eltech.ddm.associationrules.newfpg.fpg.FPGAlgorithmHorParallel;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGModel;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGSettings;
import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.thread.MultiThreadedExecutionEnvironment;
import org.eltech.ddm.handlers.thread.ThreadSettings;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Test;

public class FPGParallelHor extends AssociationRulesMiningModelTest {
    private final int NUMBER_HANDLERS = 4;

    protected FPGSettings miningAlgorithmSettings;
    protected MiningAlgorithm algorithm;


    @Test
    public void test() throws MiningException {
        setInputData();
        miningAlgorithmSettings = new FPGSettings();
        setMiningSettings(miningAlgorithmSettings);

        miningAlgorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
        EMiningBuildTask buildTask = createBuidTask();

        model = (FPGModel) buildTask.execute();
        System.out.println(model);

    }

    private EMiningBuildTask createBuidTask() throws MiningException{
        ExecutionSettings executionSettings = new ThreadSettings();
        executionSettings.setDataSet(inputData);
        executionSettings.setNumberHandlers(NUMBER_HANDLERS);


        algorithm = new FPGAlgorithmHorParallel(miningSettings);
        MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings, algorithm);
        miningAlgorithmSettings.setEnvironment(environment);


        EMiningBuildTask buildTask = new EMiningBuildTask();
        buildTask.setInputStream(inputData);
        buildTask.setMiningAlgorithm(algorithm);
        buildTask.setMiningSettings(miningSettings);
        buildTask.setExecutionEnvironment(environment);

        return buildTask;
    }
}
