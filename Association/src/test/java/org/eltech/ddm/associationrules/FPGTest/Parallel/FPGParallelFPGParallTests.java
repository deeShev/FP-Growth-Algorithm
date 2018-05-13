package org.eltech.ddm.associationrules.FPGTest.Parallel;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.fpg.FPGParallelAlgorithm;
import org.eltech.ddm.associationrules.fpg.FPGParallelModel;
import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.thread.MemoryType;
import org.eltech.ddm.handlers.thread.MultiThreadedExecutionEnvironment;
import org.eltech.ddm.inputdata.DataSplitType;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.DataProcessingStrategy;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningModelProcessingStrategy;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;

public class FPGParallelFPGParallTests extends FPGParallModelTests {
    @Override
    protected FPGParallelModel buildModel(AssociationRulesFunctionSettings miningSettings, MiningInputStream inputData) throws MiningException {

        miningSettings.getAlgorithmSettings().setDataSplitType(DataSplitType.full);
        miningSettings.getAlgorithmSettings().setDataProcessingStrategy(DataProcessingStrategy.SingleDataSet);
        miningSettings.getAlgorithmSettings().setModelProcessingStrategy(MiningModelProcessingStrategy.SeparatedMiningModel);
        miningSettings.getAlgorithmSettings().setNumberHandlers(8);
        miningSettings.getAlgorithmSettings().setMemoryType(MemoryType.distributed);

        ExecutionSettings executionSettings = new ExecutionSettings();

        MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings);

        FPGParallelAlgorithm algorithm = new FPGParallelAlgorithm(miningSettings);

        EMiningBuildTask buildTask = new EMiningBuildTask();

        buildTask.setInputStream(inputData);
        buildTask.setMiningAlgorithm(algorithm);
        buildTask.setMiningSettings(miningSettings);
        buildTask.setExecutionEnvironment(environment);

        FPGParallelModel model = (FPGParallelModel) buildTask.execute();

        return model;
    }
}
