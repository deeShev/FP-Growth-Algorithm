package org.eltech.ddm.miningcore.algorithms;

import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningFunctionSettings;

/**
 *
 * @author push_king
 */
public abstract class MiningAlgorithm {

	/**
	 * This specifies the logical data specification and specific parameters for
	 * the mining task.
	 */
	protected EMiningFunctionSettings miningSettings;

	/**
	 * This is sequence of block of this mining algorithm
	 */
	protected MiningSequence blocks;

	public MiningSequence getStepSequence() {
		return blocks;
	}


	public MiningAlgorithm(EMiningFunctionSettings miningSettings) throws MiningException {
		//this();
		this.miningSettings = miningSettings;
		initBlocks();
	}

	public abstract EMiningModel createModel(MiningInputStream inputStream) throws MiningException;

	public EMiningModel initModel(MiningInputStream inputStream) throws MiningException{
		EMiningModel resultModel = createModel(inputStream);
		resultModel.setNumberVectors(inputStream.getVectorsNumber());
		resultModel.initModel();

		return resultModel;
	}

	protected abstract void initBlocks() throws MiningException;


	public EMiningModel buildModel(MiningInputStream inputStream) throws MiningException
	{
		if( blocks == null )
			initBlocks();

		EMiningModel resultModel = initModel(inputStream);

		resultModel = runAlgorithm(inputStream, resultModel);

		return resultModel;
	}

	protected EMiningModel runAlgorithm(MiningInputStream inputStream, EMiningModel model) throws MiningException {

		EMiningModel resultModel = blocks.run(inputStream, model);

		return resultModel;

	}

	public MiningFunctionSettings getMiningSettings() {
		return miningSettings;
	}


}