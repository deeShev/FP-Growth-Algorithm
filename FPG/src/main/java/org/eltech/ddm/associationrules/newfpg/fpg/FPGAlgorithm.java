package org.eltech.ddm.associationrules.newfpg.fpg;

import org.eltech.ddm.associationrules.newfpg.AssociationRulesMiningModel;
import org.eltech.ddm.associationrules.newfpg.fpg.steps.*;
import org.eltech.ddm.associationrules.newfpg.step.IsManyPrefixPathDecision;
import org.eltech.ddm.associationrules.newfpg.step.IsOnePrefixPathDecision;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.*;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class FPGAlgorithm extends MiningAlgorithm {

    public FPGAlgorithm(EMiningFunctionSettings miningSettings) throws MiningException {
        super(miningSettings);
    }

    @Override
    public EMiningModel createModel(MiningInputStream inputStream) throws MiningException {
        EMiningModel resultModel = new FPGModel(miningSettings);

        return resultModel;
    }

    @Override
    protected void initBlocks() throws MiningException {
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
                                new FormConditionalLargeFPTreeAndFormItem(miningSettings)),
                        new IsOnePrefixPathDecision(miningSettings,
                                new FormConditionalFPTreeAndFormItem(miningSettings))));
        loop4.addListenerExecute(new BlockExecuteTimingListner());


        blocks = new MiningSequence(miningSettings,
                loopVectors,
                loop1,
                loop2,
                loop3,
                loop4
        );
        blocks.addListenerExecute(new BlockExecuteTimingListner());
    }
}
