/**
 *
 */
package org.eltech.ddm.miningcore.miningfunctionsettings;

import java.io.Serializable;

import javax.datamining.VerificationReport;

import org.eltech.ddm.handlers.ExecutionEnvironment;
import org.eltech.ddm.handlers.HandlerType;
import org.eltech.ddm.miningcore.algorithms.MemoryType;
import org.eltech.ddm.inputdata.DataSplitType;
import org.eltech.ddm.miningcore.NamedObject;
import org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings.MiningAlgorithmSettings;

/**
 *  CWM Class
 *
 *  A mining algorithm settings object captures the parameters associated with a particular
 *  algorithm. It allows a knowledgeable user to fine tune algorithm parameters. Generally,
 *  not all parameters must be specified, however, those specified are taken into account by
 *  the underlying data mining system.
 *
 *  Separating mining algorithm from mining function provides a natural and convenient
 *  separation for those users experienced with data mining algorithms and those only
 *  familiar with mining functions.
 *
 *  @author Ivan Holod
 */
public class EMiningAlgorithmSettings extends MiningAlgorithmSettings implements Serializable
//javax.datamining.base.AlgorithmSettings
{
	private static String algorithmsFileName = "config/algorithms.xml"; // ?

	//private MiningAlgorithmParameter[] inputAttribute;

	private String algorithm;

	private String classname;

	private String version;

	/**
	 * 	This is parameter for choosing of data processing strategy
	 */
	private DataProcessingStrategy dataProcessingStrategy = DataProcessingStrategy.SingleDataSet;

    /**
     * 	This is parameter the choice of model processing strategy
     */
	private MiningModelProcessingStrategy modelProcessingStrategy = MiningModelProcessingStrategy.SingleMiningModel;

	private DataSplitType dataSplitType = DataSplitType.full;

	/**
	 * This is number of computing processors (handlers) on which the parallel algorithm will be executed
	 */
	protected int numberHandlers = 1;

	/**
	 * This is type of system fulfilling the parallel algorithm execution
	 */
	protected HandlerType systemType;

	protected ExecutionEnvironment environment;

	/**
	 *  This is  memory type of the system which will be fulfilling the execution of the parallel algorithm
	 */
	protected MemoryType memoryType = MemoryType.distributed;

	/**
	 * Complete index of mining model's attributes that will be processed by concuarently.
	 */
	protected int[] parallelizedMiningModelSet;


	//	private ArrayList<HandlerSettings> handlersSettings = new ArrayList<HandlerSettings>();
//
//	// ?
//	private static int adapterCursor = 0;
//
//	private String[] dataFiles;
//
//	private SenderStep senderStep = null;
//
//	public SenderStep getSenderStep() {
//		return senderStep;
//	}
//
//	public void setActorSenderStep(SenderStep senderStep) {
//		this.senderStep = senderStep;
//	}
//
//	public void addHandler(HandlerSettings handlerSettings) {
//		this.handlersSettings.add(handlerSettings);
//	}
///*
//	public HandlerSettings getHandler(int index) {
//		return handlersSettings.getElement(index);
//	}
//*/
//	public HandlerSettings getHandler(boolean incrementCursor) {
//		if(incrementCursor) {
//			return handlersSettings.getElement(adapterCursor++);
//		}
//		else
//			return handlersSettings.getElement(adapterCursor);
//	}
//
//	public void setDataFiles(String[] dataFiles) {
//		this.dataFiles = dataFiles;
//	}
//
//	/**
//	 * @return the dataFiles
//	 */
//	public String[] getDataFiles() {
//		return dataFiles;
//	}


	/**
	 * Verifies if the settings are valid to some degree of correctness as specified by the vendor.
	 * Returns null if the settings object is valid. If the settings object is invalid, it returns
	 * an instance of VerificationReport, which contains a vendor specific explanation.
	 * @return
	 */
	public VerificationReport verify() {
		if((modelProcessingStrategy == MiningModelProcessingStrategy.SeparatedMiningModel) &&
			(parallelizedMiningModelSet == null))
			return null;

		return null;
	}

	/**
	 * Returns the type of mining algorithm specified for the algorithm settings.
	 * @return the algorithm
	 */
	public String getMiningAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm the algorithm to attributes
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * @return the class name
	 */
	public String getClassname() {
		return classname;
	}

	/**
	 * @param classname the classname to attributes
	 */
	public void setClassname(String classname) {
		this.classname = classname;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to attributes
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public String toString() {
		String s = String.valueOf(String.valueOf(getName())).concat(" (");
//		for (int i = 0; i < this.inputAttribute.length; i++) {
//			s = String.valueOf(String.valueOf(new StringBuffer(String
//					.valueOf(String.valueOf(s)))
//					.append(this.inputAttribute[i].getName()).append("=")
//					.append(this.inputAttribute[i].getValue()).append(" ")));
//		}
		s = String.valueOf(String.valueOf(s)).concat(")");
		return s;
	}

	//@Override
	public NamedObject getObjectType() {
		// TODO Auto-generated method stub
		return NamedObject.algorithmSettings;
	}


	public DataProcessingStrategy getDataProcessingStrategy() {
		return dataProcessingStrategy;
	}

	public void setDataProcessingStrategy(DataProcessingStrategy dataHandlingStrategy) {
		this.dataProcessingStrategy = dataHandlingStrategy;
	}

	public MiningModelProcessingStrategy getModelProcessingStrategy() {
		return modelProcessingStrategy;
	}

	public void setModelProcessingStrategy(MiningModelProcessingStrategy modelHandlingStrategy) {
		this.modelProcessingStrategy = modelHandlingStrategy;
	}

	public void setDataSplitType(DataSplitType type) {
		dataSplitType = type;
	}

	public DataSplitType getDataSplitType() {
		return dataSplitType;
	}

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

	public MemoryType getMemoryType() {
		return memoryType;
	}

	public void setMemoryType(MemoryType memoryType) {
		this.memoryType = memoryType;
	}


	public int[] getParallelizedMiningModelSet() {
		return parallelizedMiningModelSet;
	}

	public void setParallelizedMiningModelSet(int[] index) {
		parallelizedMiningModelSet = index;
	}


	public ExecutionEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(ExecutionEnvironment environment) {
		this.environment = environment;
	}
}
