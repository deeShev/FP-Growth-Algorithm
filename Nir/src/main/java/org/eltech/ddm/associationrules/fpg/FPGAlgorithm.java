package org.eltech.ddm.associationrules.fpg;

import org.eltech.ddm.associationrules.AssociationRulesMiningModel;
import org.eltech.ddm.associationrules.fpg.steps.*;
import org.eltech.ddm.associationrules.step.IsManyPrefixPathDecision;
import org.eltech.ddm.associationrules.step.IsOnePrefixPathDecision;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.*;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class FPGAlgorithm extends MiningAlgorithm {

    public FPGAlgorithm(EMiningFunctionSettings miningSettings) throws MiningException {
        super(miningSettings);
    }

    @Override
    public EMiningModel createModel() throws MiningException {
        EMiningModel resultModel = new FPGModel(miningSettings);

        return resultModel;
    }

    @Override
    public MiningSequence getSequenceAlgorithm() throws MiningException {
        MiningLoopVectors loopVectors = new MiningLoopVectors(miningSettings,
                new FormTransaction(miningSettings));
        loopVectors.addListenerExecute(new BlockExecuteTimingListner());

        MiningLoopElement loop1 = new MiningLoopElement(miningSettings, AssociationRulesMiningModel.INDEX_ELEMENT_ITEM,
                new FormListOfItems(miningSettings));
        loop1.addListenerExecute(new BlockExecuteTimingListner());

        MiningLoopElement loop2 = new MiningLoopElement(miningSettings, FPGModel.INDEX_SORTED_ITEM_LIST,
                new FormHeaderTable(miningSettings));
        loop2.addListenerExecute(new BlockExecuteTimingListner());

        MiningLoopElement loop3 = new MiningLoopElement(miningSettings, AssociationRulesMiningModel.INDEX_TRANSACTION_LIST,
                new MiningLoopElement(miningSettings, FPGModel.INDEX_SORTED_ITEM_LIST,
                        new SortTransaction(miningSettings)),
                new MiningLoopElement(miningSettings, AssociationRulesMiningModel.INDEX_CURRENT_TRANSACTION_ITEM,
                        new MiningLoopElement(miningSettings, FPGModel.INDEX_HEADER_TABLE,
                                new FormFPTree(miningSettings))));
        loop3.addListenerExecute(new BlockExecuteTimingListner());

        MiningLoopElement loop4 = new MiningLoopElement(miningSettings, FPGModel.INDEX_HEADER_TABLE,
                new FormConditionalPatternBase(miningSettings),
                new MiningLoopElement(miningSettings, FPGModel.INDEX_CONDITIONAL_LIST,
                        new IsManyPrefixPathDecision(miningSettings,
                                new FormConditionalLargeFPTreeAndFormItemSets(miningSettings)),
                        new IsOnePrefixPathDecision(miningSettings,
                                new FormConditionalFPTreeAndFormItemSets(miningSettings))));
        loop4.addListenerExecute(new BlockExecuteTimingListner());


        MiningSequence  blocks = new MiningSequence(miningSettings,
                loopVectors,
                loop1,
                loop2,
                loop3,
                loop4
        );
        blocks.addListenerExecute(new BlockExecuteTimingListner());
        return blocks;
    }

    @Override
    public MiningSequence getCentralizedParallelAlgorithm() throws MiningException {
        return getHorDistributedAlgorithm();
    }

    @Override
    public MiningSequence getHorDistributedAlgorithm() throws MiningException {
        MiningParallel loopVectors = new MiningParallel(miningSettings, MemoryType.distributed,
                new MiningLoopVectors(miningSettings,
                        //new FormTransaction(miningSettings)));
                        new FormTransaction1(miningSettings)));
        loopVectors.addListenerExecute(new BlockExecuteTimingListner());

        MiningLoopElement loop1 = new MiningLoopElement(miningSettings, AssociationRulesMiningModel.INDEX_ELEMENT_ITEM,
                new FormListOfItems(miningSettings));
        loop1.addListenerExecute(new BlockExecuteTimingListner());

        MiningLoopElement loop2 = new MiningLoopElement(miningSettings, FPGModel.INDEX_SORTED_ITEM_LIST,
                new FormHeaderTable(miningSettings));
        loop2.addListenerExecute(new BlockExecuteTimingListner());

        MiningLoopElement loop3 = new MiningLoopElement(miningSettings, AssociationRulesMiningModel.INDEX_TRANSACTION_LIST,
                new MiningLoopElement(miningSettings, FPGModel.INDEX_SORTED_ITEM_LIST,
                        new SortTransaction(miningSettings)),
                new MiningLoopElement(miningSettings, AssociationRulesMiningModel.INDEX_CURRENT_TRANSACTION_ITEM,
                        new MiningLoopElement(miningSettings, FPGModel.INDEX_HEADER_TABLE,
                                new FormFPTree(miningSettings))));
        loop3.addListenerExecute(new BlockExecuteTimingListner());

        MiningLoopElement loop4 = new MiningLoopElement(miningSettings, FPGModel.INDEX_HEADER_TABLE,
                new FormConditionalPatternBase(miningSettings),
                new MiningLoopElement(miningSettings, FPGModel.INDEX_CONDITIONAL_LIST,
                        new IsManyPrefixPathDecision(miningSettings,
                                new FormConditionalLargeFPTreeAndFormItemSets(miningSettings)),
                        new IsOnePrefixPathDecision(miningSettings,
                                new FormConditionalFPTreeAndFormItemSets(miningSettings))));
        loop4.addListenerExecute(new BlockExecuteTimingListner());


        MiningSequence blocks = new MiningSequence(miningSettings,
                loopVectors,
                loop1,
                loop2,
                loop3,
                loop4
        );
        blocks.addListenerExecute(new BlockExecuteTimingListner());
        return blocks;
    }

    @Override
    public MiningSequence getVerDistributedAlgorithm() throws MiningException {
        return null;
    }
}
