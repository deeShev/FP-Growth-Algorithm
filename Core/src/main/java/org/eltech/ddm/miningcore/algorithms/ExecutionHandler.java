package org.eltech.ddm.miningcore.algorithms;

import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * Basis class for parallel executer adapter
 *
 * @author Ivan Kholod
 *
 */
public abstract class ExecutionHandler implements Cloneable {//, Serializable {

	/**
	 * Executed step of mining algorithm
	 */
	//protected MiningBlock step;

	/**
	 * Settings for handler
	 */
	protected ExecutionSettings settings;

	protected ExecutionHandler(ExecutionSettings settings) {
		this.settings = settings;
	}

	public ExecutionSettings getExecutionSettings() {
		return settings;
	}

	/**
	 * Start execution of parallel branch
	 *
	 * @throws MiningException
	 */
	protected EMiningModel call(MiningBlock block, EMiningModel model) throws MiningException{
		return block.run(settings.getDataSet(), model);
	}


	public abstract void start(MiningBlock block, EMiningModel model) throws MiningException;

	/**
	 * Waiting finish of handler execution and getting result
	 *
	 * @throws ParallelExecutionException
	 */
	public abstract EMiningModel getModel() throws ParallelExecutionException;

	public Object clone() {
		ExecutionHandler o = null;
		try {
			o = (ExecutionHandler) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		if (settings != null)
			o.settings = (ExecutionSettings) settings.clone();

		return o;
	}

}