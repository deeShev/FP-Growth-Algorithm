package org.eltech.ddm.classification.ruleset.onerule;


import org.eltech.ddm.classification.ruleset.RuleSetModelTest;
import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.thread.MultiThreadedExecutionEnvironment;
import org.eltech.ddm.handlers.thread.ThreadSettings;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Before;

import static org.junit.Assert.fail;

public class OneRuleCountAlgorithmHorParallelTest extends RuleSetModelTest {

	private final int NUMBER_HANDLERS = 2;

	protected MiningAlgorithm algorithm;

	protected EMiningAlgorithmSettings algorithmSettings;	
	
	@Before
	public void setUp() throws Exception {
		algorithmSettings = new EMiningAlgorithmSettings();
		// Create and tuning algorithm settings
		algorithmSettings.setName(OneRuleCountAlgorithmHorParallel.class.getSimpleName());
		algorithmSettings.setClassname(OneRuleCountAlgorithmHorParallel.class.getName());
		algorithmSettings.setAlgorithm("1R");
	}

	@org.junit.Test
	public void test4WeatherNominal() {
		try {
			algorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
			
			setInputData4WeatherNominal();
			setMiningSettings4WeatherNominal(algorithmSettings);
			
			EMiningBuildTask buildTask = createBuidTask();

			model = (OneRuleMiningModel) buildTask.execute();
			
	        verifyModel4WeatherNominal();
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}
	
	private EMiningBuildTask createBuidTask() throws MiningException{
		ExecutionSettings executionSettings = new ThreadSettings();
		executionSettings.setDataSet(inputData);
		//executionSettings.setNumberHandlers(2);


		MiningAlgorithm algorithm = new OneRuleCountAlgorithmHorParallel(miningSettings);
		MultiThreadedExecutionEnvironment environment = new MultiThreadedExecutionEnvironment(executionSettings, algorithm);
		algorithmSettings.setEnvironment(environment);


		EMiningBuildTask buildTask = new EMiningBuildTask();
		buildTask.setInputStream(inputData);
		buildTask.setMiningAlgorithm(algorithm); 
		buildTask.setMiningSettings(miningSettings);
		buildTask.setExecutionEnvironment(environment);
		
		return buildTask;
	}


}
