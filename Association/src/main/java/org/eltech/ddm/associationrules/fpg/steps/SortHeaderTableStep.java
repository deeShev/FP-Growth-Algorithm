package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.associationrules.fpg.HeaderTable;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.Comparator;
import java.util.List;

public class SortHeaderTableStep extends Step {
    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public SortHeaderTableStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = ((FPGModel)model);
        List<HeaderTable> headerTableList = fpgModel.getHeaderTableList();
        headerTableList.sort(new Comparator<HeaderTable>() {
            @Override
            public int compare(HeaderTable o1, HeaderTable o2) {
                if (o1.getSupportItem() == o2.getSupportItem()){
                    return o1.getItemID().compareTo(o2.getItemID());
                }else{
                    return o2.compareTo(o1);
                }
            }
        });


        return fpgModel;
    }
}
