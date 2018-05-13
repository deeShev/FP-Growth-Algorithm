package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.associationrules.fpg.HeaderTable;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.List;

/**
 * Created by denis on 08.12.17.
 */
public class FormHeaderTableStep extends Step{
    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public FormHeaderTableStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;

        List<HeaderTable> headerTableList = fpgModel.getHeaderTableList();
        HeaderTable headerTable = headerTableList.get(fpgModel.getCurrentHeaderTable());
        String itemIDInCurrentHT =  headerTable.getItemID();

        Item itemInSortList = fpgModel.getSortedItemsList().get(fpgModel.getCurrentItemInSortedList());
        String itemIDInSortList = itemInSortList.getItemID();
        int supportItemInSortList = itemInSortList.getSupportCount();

        HeaderTable newHT = new HeaderTable(itemIDInSortList,supportItemInSortList);

        if (headerTableList.contains(newHT)){
            if (itemIDInCurrentHT.equals(itemIDInSortList)) {
                headerTable.setSupportItem(supportItemInSortList);
            }
        }else {
            headerTableList.add(newHT);
        }

        return fpgModel;
    }
}
