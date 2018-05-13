package org.eltech.ddm.handlers.thread;

import org.eltech.ddm.handlers.ParallelExecutionException;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.ExecutionHandler;
import org.eltech.ddm.miningcore.algorithms.MemoryType;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Adapter for thread. One adapter is created for one thread
 *
 * @author Alexey Nakidkin
 *
 */
public class ThreadExecutionHandler extends ExecutionHandler implements Cloneable {

	ExecutorService service;

	Future future;

	private EMiningModel resultModel;

	/**
	 *  This is  memory type of the system which will be fulfilling the execution of the parallel algorithm
	 */
	protected MemoryType memoryType = MemoryType.distributed;

	/**
	 * @param settings - settings for adapter
	 */
	ThreadExecutionHandler(ThreadSettings settings, ExecutorService service) {
		super(settings);
		memoryType = settings.getMemoryType();
		this.service = service;
	}

	@Override
	/**
	 * Start thread
	 */
	public void start (final MiningBlock block, final EMiningModel model) throws MiningException {
		if (block == null)
			throw new ParallelExecutionException("Handler has not sequens of block");

		future = service.submit(new Runnable(){
			@Override
			public void run() {
				try {
					resultModel = call(block, model);
				} catch (MiningException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} );
	}

	@Override
	/**
	 * Waiting finish of thread execution
	 */
	public EMiningModel getModel() throws ParallelExecutionException {
			while (!future.isDone()){};
			return resultModel;
	}


	public Object clone() {
		ThreadExecutionHandler o = null;
		o = (ThreadExecutionHandler) super.clone();

		return o;
	}
}
