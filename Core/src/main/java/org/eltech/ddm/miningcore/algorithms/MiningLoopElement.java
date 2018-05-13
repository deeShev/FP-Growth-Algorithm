package org.eltech.ddm.miningcore.algorithms;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class MiningLoopElement extends MiningLoop {

	private final int[] indexSet;

	// Number of  mining model's elements are handled by the loop
	private final int startPositon;

	// Number of  mining model's elements are handled by the loop
	private final int countElement;

    public MiningLoopElement(EMiningFunctionSettings settings, int[] indexSet, MiningBlock... blocks) throws MiningException {
		super(settings);
		this.indexSet = indexSet;
		startPositon = 0;
		countElement = -1;
		iteration = new MiningSequence(settings, blocks);
	}

	/**
	 * Constructor for distributed execution of the loop
	 * @param settings - function mining settings
	 * @param indexSet - index parent mining model's element
	 * @param startPos - start position of the loop
	 * @param countElement - count of mining model's elements wich are handled by the loop
	 * @param block - iteration block
	 * @throws MiningException
	 */
	MiningLoopElement(EMiningFunctionSettings settings, int[] indexSet, int startPos, int countElement, MiningSequence block) throws MiningException {
		super(settings);
		this.indexSet = indexSet;
		startPositon = startPos;
		this.countElement = countElement;
		iteration = block;
	}

	@Override
	protected EMiningModel initLoop(EMiningModel model) throws MiningException {
		model.setCurrentElement(indexSet, startPositon);
    	return model;
	}

	@Override
	protected boolean conditionLoop(EMiningModel model) throws MiningException {
		if(countElement < 0)
    		return !model.currIsLastElement(indexSet);
		else {
			return ((model.getCurrentElementIndex(indexSet) - startPositon) < countElement);
		}
	}

	@Override
	protected EMiningModel beforeIteration(EMiningModel model) throws MiningException {
		return model;
	}

	@Override
	protected EMiningModel afterIteration(EMiningModel model) throws MiningException {
		model.nextCurrElement(indexSet);
		return model;
	}

	int[] getIndexSet() {
		return indexSet;
	}

}
