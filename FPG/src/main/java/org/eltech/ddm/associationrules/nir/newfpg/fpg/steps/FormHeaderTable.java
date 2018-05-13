package org.eltech.ddm.associationrules.nir.newfpg.fpg.steps;

import org.eltech.ddm.associationrules.nir.newfpg.Item;
import org.eltech.ddm.associationrules.nir.newfpg.fpg.FPGModel;
import org.eltech.ddm.associationrules.nir.newfpg.fpg.HeaderTable;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class FormHeaderTable extends MiningBlock {
    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public FormHeaderTable(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;
        int currentIndexItemInSortList = fpgModel.getCurrentIndexSortedList();

        Item item = (Item) fpgModel.getSortedList().getElement(currentIndexItemInSortList);
        HeaderTable headerTable = new HeaderTable(item.getID(),item.getCount());
        fpgModel.addItemInHeaderTable(headerTable);


        return fpgModel;
    }
}
