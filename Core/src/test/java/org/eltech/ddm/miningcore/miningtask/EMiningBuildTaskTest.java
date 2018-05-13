package org.eltech.ddm.miningcore.miningtask;

import org.eltech.ddm.handlers.ExecutionEnvironment;
import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.inputdata.MiningArrayStream;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningfunctionsettings.DataProcessingStrategy;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningFunction;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningModelProcessingStrategy;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EMiningBuildTaskTest {
	EMiningFunctionSettings funSettings;
	MiningAlgorithm algorithm;
	ExecutionEnvironment environment;
	ExecutionSettings envSettings;
	EMiningAlgorithmSettings algSettings;
	
	@Before
	public void setUp() throws Exception {
		funSettings = new EMiningFunctionSettings(null) {
			@Override
			public MiningFunction getMiningFunction() {
				// TODO Auto-generated method stub
				return MiningFunction.classification;
			}
		};
		
		algorithm = new MiningAlgorithm(funSettings) {
			@Override
			public void initBlocks() throws MiningException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public EMiningModel createModel(MiningInputStream inputStream)
					throws MiningException {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		envSettings = new ExecutionSettings();
		
		environment = new ExecutionEnvironment(envSettings) {
			@Override
			protected void initEnvironment() throws ParallelExecutionException {
			}
		};
		
		algSettings = new EMiningAlgorithmSettings();
		funSettings.setAlgorithmSettings(algSettings);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testVerify() {
		
		EMiningBuildTask buildTask = new EMiningBuildTask();
		boolean condition = buildTask.verify();
		Assert.assertFalse(condition);
		
		buildTask.setInputStream(new MiningArrayStream());
		condition = buildTask.verify();
		Assert.assertFalse(condition);
		
		buildTask.setMiningAlgorithm(algorithm); 
		condition = buildTask.verify();
		Assert.assertFalse(condition);
		
		buildTask.setMiningSettings(funSettings);
		condition = buildTask.verify();
		Assert.assertFalse(condition);
		
		buildTask.setExecutionEnvironment(environment);
		condition = buildTask.verify();
		Assert.assertTrue(condition);		

 		algSettings.setDataProcessingStrategy(DataProcessingStrategy.SingleDataSet );
		algSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
		condition = buildTask.verify();
		Assert.assertFalse(condition);	

		algSettings.setDataProcessingStrategy(DataProcessingStrategy.SingleDataSet );
		algSettings.setModelProcessingStrategy(MiningModelProcessingStrategy.SingleMiningModel);
		condition = buildTask.verify();
		Assert.assertTrue(condition);	
		
	}

}
