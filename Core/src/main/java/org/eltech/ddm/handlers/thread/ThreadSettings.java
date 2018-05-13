package org.eltech.ddm.handlers.thread;

import org.eltech.ddm.handlers.ExecutionSettings;
import org.eltech.ddm.miningcore.algorithms.MemoryType;


/**
*
 * @author Ivan Kholod
 *
 */
public class ThreadSettings extends ExecutionSettings {

	/**
	 *  This is  memory type of the system which will be fulfilling the execution of the parallel algorithm
	 */
	protected MemoryType memoryType = MemoryType.distributed;

	public ThreadSettings() {
		super();
	}

	public MemoryType getMemoryType() {
		return memoryType;
	}

	public void setMemoryType(MemoryType memoryType) {
		this.memoryType = memoryType;
	}
}
