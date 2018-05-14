package org.eltech.ddm.associationrules.newFpgTest.nir;

import org.eltech.ddm.associationrules.nir.newfpg.fpg.FPGAlgorithm;
import org.eltech.ddm.associationrules.nir.newfpg.fpg.FPGModel;
import org.eltech.ddm.associationrules.nir.newfpg.fpg.FPGSettings;
import org.eltech.ddm.environment.ConcurrentCSTExecutionEnvironment;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Before;
import org.junit.Test;

public class NirFPGSequentialAlgorithmTest extends NirModelTest {
    protected MiningAlgorithm algorithm;
    protected EMiningAlgorithmSettings algorithmSettings;

    private static final int HANDLERS_NUMBER = 8;

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

        EMiningBuildTask buildTask = createBuildTask(inputData.getFileName());


        model = (FPGModel) buildTask.execute();
        System.out.println("___________");
    }

    private EMiningBuildTask createBuildTask(String targetFile) throws MiningException {
        algorithm = new FPGAlgorithm(miningSettings);
        ConcurrentCSTExecutionEnvironment environment = new ConcurrentCSTExecutionEnvironment(targetFile, HANDLERS_NUMBER);

        EMiningBuildTask buildTask = new EMiningBuildTask();
        buildTask.setMiningAlgorithm(algorithm);
        buildTask.setMiningSettings(miningSettings);
        buildTask.setExecutionEnvironment(environment);
        return buildTask;
    }
}
