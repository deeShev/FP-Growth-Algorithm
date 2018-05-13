package org.eltech.ddm.classification.naivebayes.steps;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.classification.naivebayes.NaiveBayesModel;
import org.eltech.ddm.classification.naivebayes.TargetValueCount;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class FindProbabilityOfTargetValue extends MiningBlock {
	private final ELogicalAttribute targetAttr;

	public FindProbabilityOfTargetValue(EMiningFunctionSettings settings)
															throws MiningException {
		super(settings);

		targetAttr = ((ClassificationFunctionSettings)settings).getTarget();
	}


	protected EMiningModel execute(MiningInputStream data, EMiningModel model) throws MiningException {

		MiningVector mv = data.getVector(model.getCurrentVectorIndex());

		//System.out.println("Thread-" + Thread.currentThread().getName() + " id vectror = " + mv.getIndex() + " vector " + mv);

		int indexValueTarg = (int)mv.getValue(targetAttr.getName());


		TargetValueCount tvc = ((NaiveBayesModel)model).getOutputTargetValueCount(indexValueTarg);
		tvc.incCount();

		//System.out.println("Thread-" + Thread.currentThread().getName() + " id vectror = " + mv.getIndex() + " =" + tvc);

		return model;
	}

}
