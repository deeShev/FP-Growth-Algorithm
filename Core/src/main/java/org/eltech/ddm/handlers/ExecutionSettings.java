/**
 *
 */
package org.eltech.ddm.handlers;

import java.io.Serializable;
import java.util.ArrayList;

import org.eltech.ddm.inputdata.DataSplitType;
import org.eltech.ddm.inputdata.MiningInputStream;

/**
 *
 *  @author Ivan Holod
 */
public class ExecutionSettings implements Serializable, Cloneable
{
	private MiningInputStream dataSet;

	/**
	 * This is number of computing processors (handlers) on which the parallel algorithm will be executed 
	 */
	protected int numberHandlers = 1;
	
	/**
	 * This is type of system fulfilling the parallel algorithm execution 
	 */
	protected HandlerType systemType;

	/** Name of handler */
	protected String nameHandler;
	
	public int getNumberHandlers() {
		return numberHandlers;
	}

	public void setNumberHandlers(int numberHandlers) {
		this.numberHandlers = numberHandlers;
	}

	public HandlerType getSystemType() {
		return systemType;
	}

	public void setSystemType(HandlerType systemType) {
		this.systemType = systemType;
	}


	public String getNameHandler() {
		return nameHandler;
	}

	public void setNameHandler(String name) {
		this.nameHandler = name;
	}

	/**
	 * ������������
	 */
	public Object clone() {
		ExecutionSettings o = null;
		try {
			o = (ExecutionSettings)super.clone();
		} catch(CloneNotSupportedException e) {
			System.err.println(this.getClass().toString() + " can't be cloned");
		}
		o.systemType = systemType;

		o.nameHandler = nameHandler;

		o.numberHandlers = numberHandlers;

		return o;
	}


	public MiningInputStream getDataSet() {
		return dataSet;
	}

	public void setDataSet(MiningInputStream dataSet) {
		this.dataSet = dataSet;
	}
}
