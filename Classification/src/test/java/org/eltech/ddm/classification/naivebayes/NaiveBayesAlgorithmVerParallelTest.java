package org.eltech.ddm.classification.naivebayes;

import static org.junit.Assert.*;

import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.thread.MultiThreadedExecutionEnvironment;
import org.eltech.ddm.handlers.thread.ThreadSettings;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Before;

public class NaiveBayesAlgorithmVerParallelTest extends NaiveBayesModelTest {

	private final int NUMBER_HANDLERS = 2;

	protected EMiningAlgorithmSettings algorithmSettings;

	@Before
	public void setUp() throws Exception {

		algorithmSettings = new EMiningAlgorithmSettings();
		// Create and tuning algorithm settings
		algorithmSettings.setName(NaiveBayesAlgorithmVerParallel.class.getSimpleName());
		algorithmSettings.setClassname(NaiveBayesAlgorithmVerParallel.class.getName());
		algorithmSettings.setAlgorithm("Naive Bayes");
		algorithmSettings.setNumberHandlers(NUMBER_HANDLERS);
	}

	@org.junit.Test
	public void test4WeatherNominal() {
		try {
			setInputData4WeatherNominal();
			setMiningSettings4WeatherNominal(algorithmSettings);

			EMiningBuildTask buildTask = createBuidTask();

			model = (ClassificationMiningModel) buildTask.execute();

			verifyModel4WeatherNominal();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	private EMiningBuildTask createBuidTask() throws MiningException{
		ExecutionSettings executionSettings = new ThreadSettings();
		executionSettings.setDataSet(inputData);

		MiningAlgorithm algorithm = new NaiveBayesAlgorithmVerParallel(miningSettings);
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
