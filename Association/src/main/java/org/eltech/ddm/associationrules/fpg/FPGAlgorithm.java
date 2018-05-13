package org.eltech.ddm.associationrules.fpg;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.fpg.steps.*;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.algorithms.StepExecuteTimingListner;
import org.eltech.ddm.miningcore.algorithms.StepSequence;
import org.eltech.ddm.miningcore.algorithms.VectorsCycleStep;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

/**
 * Created by denis on 03.12.17.
 */
public class FPGAlgorithm extends MiningAlgorithm {

    public FPGAlgorithm(EMiningFunctionSettings miningSettings) throws MiningException {
        super(miningSettings);
    }

    @Override
    public EMiningModel createModel(MiningInputStream inputStream) throws MiningException {
        FPGModel resultModel = new FPGModel((AssociationRulesFunctionSettings) miningSettings);
        //resultModel.setLastIndex(inputStream.getVectorsNumber());

        return resultModel;
    }

    @Override
    protected void initSteps() throws MiningException {
        VectorsCycleStep vcs = new VectorsCycleStep(miningSettings,
                new FormTransactionStep(miningSettings));
        vcs.addListenerExecute(new StepExecuteTimingListner());

        TransactionsCycleStep tcs1 =
                new TransactionsCycleStep(miningSettings,
                        new TransactionItemsCycleStep(miningSettings,
                                new Calculate1ItemSetSupportStep(miningSettings),
                                new SortedItemsListCycleStep(miningSettings,
                                        new FormListOfItemsStep(miningSettings),
                                        new HeaderTableCycleStep(miningSettings,
                                                new FormHeaderTableStep(miningSettings)))));
        tcs1.addListenerExecute(new StepExecuteTimingListner());

        SortItemListStep sils = new SortItemListStep(miningSettings);
        sils.addListenerExecute(new StepExecuteTimingListner());

        SortHeaderTableStep shts = new SortHeaderTableStep(miningSettings);
        shts.addListenerExecute(new StepExecuteTimingListner());

        TransactionsCycleStep tcs2 =
                new TransactionsCycleStep(miningSettings,
                        new SortedItemsListCycleStep(miningSettings,
                                new SortTransactionStep(miningSettings)),
                        new TransactionItemsCycleStep(miningSettings,
                                new HeaderTableCycleStep(miningSettings,
                                        new FormFPTreeStep(miningSettings))));
        tcs2.addListenerExecute(new StepExecuteTimingListner());

        BackHeaderTableCycleStep bhtcs =
                new BackHeaderTableCycleStep(miningSettings,
                        new FormConditionalPatternBaseStep(miningSettings),
                        new AddItemSetSize1Step(miningSettings),
                        new ConditionalTableListCycleStep(miningSettings,
                                new IsPrefixPathDecisionStep(miningSettings,
                                        new FormConditionalLargeFPTreeAndFormItemSetsStep(miningSettings)),
                                new IsPrefixPathExactlyOne(miningSettings,
                                        new FormConditionalFPTreeAndFormItemStepStep(miningSettings))));
        bhtcs.addListenerExecute(new StepExecuteTimingListner());

        ResultCycleStep rcs = new ResultCycleStep(miningSettings,
                new ItemSetListsCycleStep(miningSettings,
                        new ResultItemSetItemsCycleStep(miningSettings,
                                new GenerateAssosiationRuleStep(miningSettings))));
        rcs.addListenerExecute(new StepExecuteTimingListner());

        steps = new StepSequence(miningSettings,
                vcs,
                tcs1,
                sils,
                shts,
                tcs2,
                bhtcs,
                rcs);
        steps.addListenerExecute(new StepExecuteTimingListner());
    }
}
