package org.eltech.ddm.associationrules.newFpgTest;

import org.eltech.ddm.associationrules.newfpg.fpg.FPGAlgorithm;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGModel;
import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FPGParallelHor extends AssociationRulesMiningModelTest {
    private final int NUMBER_HANDLERS = 4;

    protected MiningAlgorithm algorithm;

    protected EMiningAlgorithmSettings algorithmSettings;

    @Before
    public void setUp() throws Exception {
        algorithmSettings = new EMiningAlgorithmSettings();
        algorithmSettings.setName("FPGParallel");
        algorithmSettings.setClassname("org.eltech.ddm.associationrules.newfpg.fpg.FPGAlgorithmHorParallel");
    }

    @Test
    public void test() throws MiningException {
        setInputData();
        setMiningSettings(algorithmSettings);

        EMiningBuildTask buildTask = createBuidTask();

        model = (FPGModel) buildTask.execute();

        System.out.println("-----------------------------");
        Assert.assertEquals(3, model.getItemSets().size());


    }

    private EMiningBuildTask createBuidTask() throws MiningException {
        algorithm = new FPGAlgorithm(miningSettings);
        ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(NUMBER_HANDLERS, inputData);

        EMiningBuildTask buildTask = new EMiningBuildTask();
        buildTask.setMiningAlgorithm(algorithm);
        buildTask.setMiningSettings(miningSettings);
        buildTask.setExecutionEnvironment(environment);

        return buildTask;
    }
}
