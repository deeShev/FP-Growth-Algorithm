package org.eltech.ddm.associationrules.newFpgTest;

import org.eltech.ddm.associationrules.newfpg.fpg.FPGAlgorithm;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGModel;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGSettings;
import org.eltech.ddm.environment.ConcurrencyExecutionEnvironment;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SequentialAlgorithmTest extends AssociationRulesMiningModelTest {
    protected MiningAlgorithm algorithm;

    protected EMiningAlgorithmSettings algorithmSettings;

    @Before
    public void setUp() {
        algorithmSettings =  new FPGSettings();
        algorithmSettings.setName("FPG");
        algorithmSettings.setClassname("org.eltech.ddm.associationrules.newfpg.fpg.FPGAlgorithm");
    }


    @Test
    public void test() throws MiningException {
        setInputData();
        setMiningSettings(algorithmSettings);

        EMiningBuildTask buildTask = createBuildTask();
        model = (FPGModel) buildTask.execute();
        System.out.println("-----------------------------");
        Assert.assertEquals(3, model.getItemSets().size());
    }

    private EMiningBuildTask createBuildTask() throws MiningException {
        algorithm = new FPGAlgorithm(miningSettings);
        ConcurrencyExecutionEnvironment environment = new ConcurrencyExecutionEnvironment(inputData);

        EMiningBuildTask buildTask = new EMiningBuildTask();
        buildTask.setMiningAlgorithm(algorithm);
        buildTask.setMiningSettings(miningSettings);
        buildTask.setExecutionEnvironment(environment);

        return buildTask;
    }
}
