package org.eltech.ddm.classification.ruleset.onerule;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.classification.ruleset.onerule.steps.*;
import org.eltech.ddm.classification.steps.DecisionCurrentAttributIsNotTarget;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.Operator;
import org.eltech.ddm.miningcore.algorithms.*;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElementPredicate;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElementRule;

public class OneRuleCountAlgorithm extends MiningAlgorithm {

	public OneRuleCountAlgorithm(EMiningFunctionSettings miningSettings)
			throws MiningException {
		super(miningSettings);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EMiningModel createModel(MiningInputStream inputStream) throws MiningException {
		EMiningModel resultModel = new OneRuleCountMiningModel(miningSettings);

		return resultModel;
	}

    /**
     * Initialization of 1R algorithm's block.
     */
	@Override
	protected void initBlocks() throws MiningException {

		int indexTargetAttr = miningSettings.getLogicalData().
				getAttributeIndex(((ClassificationFunctionSettings)miningSettings).getTarget());
//		MiningModelElementRule ruleNoTarget = new MiningModelElementRule(
//									new MiningModelElementPredicate(MiningModelElement.ID, Operator.NOT_EQUAL,
//											((ClassificationFunctionSettings)miningSettings).getTarget().getName()));
		int[] indexTargetValues =  {EMiningModel.ATTRIBUTE_SET, indexTargetAttr};

		blocks = new MiningSequence(miningSettings,
				new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
						//new MiningDecisionElement(miningSettings, ruleNoTarget,	EMiningModel.INDEX_CURRENT_ATTRIBUTE,
						new DecisionCurrentAttributIsNotTarget(miningSettings,
								new MiningLoopVectors(miningSettings,
										new IncrementCorrectVectorsCount(miningSettings)))),
				new MiningLoopElement(miningSettings, EMiningModel.INDEX_ATTRIBUTE_SET,
						new DecisionCurrentAttributIsNotTarget(miningSettings,
								new MiningLoopElement(miningSettings, EMiningModel.INDEX_CURRENT_ATTRIBUTE,
										new MiningLoopElement(miningSettings, indexTargetValues,
												 new SelectMaxCorrectVectorsCount(miningSettings)),
										new AddBestOneRule(miningSettings),
										new SummCorrectVectorsCount(miningSettings)),
								new SelectBestRuleSet(miningSettings),
								new RemoveAttributeRuleSet(miningSettings))
				)
		);

		blocks.addListenerExecute(new BlockExecuteTimingListner());
	}

}
