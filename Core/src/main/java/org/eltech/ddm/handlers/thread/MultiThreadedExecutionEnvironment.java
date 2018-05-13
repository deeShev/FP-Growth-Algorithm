package org.eltech.ddm.handlers.thread;

import org.eltech.ddm.handlers.ExecutionEnvironment;
import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MemoryType;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;

public class MultiThreadedExecutionEnvironment extends ExecutionEnvironment {

	public MultiThreadedExecutionEnvironment(ExecutionSettings settings, MiningAlgorithm algorithm) throws MiningException {
		super(settings, algorithm);
	}

	@Override
	protected void initEnvironment() throws ParallelExecutionException {
		executionHandlerFactory = new ThreadExecutionHandlerFactory(settings);
//		handlers = new ArrayList<ExecutionHandler>();
		mainHandler = getExecutionHandlerFactory().create(settings);
		templateHandler = getExecutionHandlerFactory().create(settings);
//		handlers.add(executionHandlerFactory.create(new ThreadSettings()));
//		for(int i = 0; i < settings.getNumberHandlers(); i++){
//			handlers.add(executionHandlerFactory.create(new ThreadSettings()));
//		}
	}

	public MemoryType getMemoryType() {
		return ((ThreadSettings)settings).getMemoryType();
	}





}
