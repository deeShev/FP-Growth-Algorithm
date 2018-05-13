/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.eltech.ddm.classification.naivebayes;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.algorithms.*;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.classification.steps.DecisionCurrentAttributIsNotTarget;

/**
 *
 * @author Ivan Kholod
 */
public class NaiveBayesParallelAlgorithm extends NaiveBayesAlgorithm  {


	public NaiveBayesParallelAlgorithm(
			EMiningFunctionSettings miningSettings) throws MiningException {
		super(miningSettings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EMiningModel createModel(MiningInputStream inputStream) throws MiningException {
		EMiningModel resultModel = new NaiveBayesModel((EMiningFunctionSettings) miningSettings);

//		((EMiningModel) resultModel).setVectorCount(inputStream.getVectorsNumber());

		return resultModel;
	}

	/**
	 * Initialization of Naive Bayes algorithm's block.
	 * 
	 */
	@Override
	protected void initBlocks() throws MiningException {
//
//		MiningLoopElement vectorsCycle = new MiningLoopElement(miningSettings, EMiningModel.INDEX_VECTOR_SET,
//				new DecisionCurrentAttributIsNotTarget(miningSettings,
//						new FindProbabilityOfAttributeValue(miningSettings)));
//		//attrsCycle.addListenerExecute(new BlockExecuteTimingListner());
//
//		MiningLoopElement attrsCycle = new MiningLoopElement(miningSettings, EMiningModel.INDEX_VECTOR_SET, vectorsCycle);
//
//		MiningParallel attrsCycleParallel = new MiningParallel(miningSettings, attrsCycle);
//		attrsCycleParallel.addListenerExecute(new ParallelBlockExecuteTimingListner());
//
//		MiningLoopElement targVectorsCycle = new MiningLoopElement(miningSettings, EMiningModel.INDEX_VECTOR_SET,
//				new FindProbabilityOfTargetValue(miningSettings));
//		targVectorsCycle.addListenerExecute(new BlockExecuteTimingListner());
//
//		blocks = new MiningSequence(miningSettings, attrsCycleParallel, targVectorsCycle);
//		blocks.addListenerExecute(new BlockExecuteTimingListner());
	}
}
