package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.ItemSets;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.associationrules.fpg.HeaderTable;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.List;

public class AddItemSetSize1Step extends Step {
    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public AddItemSetSize1Step(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = ((FPGModel)model);
        HeaderTable headerTable = fpgModel.getHeaderTableList().get(fpgModel.getCurrentHeaderTable());
        String itemID = headerTable.getItemID();
        Item item = fpgModel.getItem(itemID);
        List<ItemSets> result = fpgModel.getResult();
        ItemSets oneItemSets;
        if (result.size() == 0){
            oneItemSets = new ItemSets();
            result.add(oneItemSets);
        }else {
            oneItemSets = result.get(0);
        }
        ItemSet itemSet = new ItemSet(item);
        itemSet.setSupportCount(headerTable.getSupportItem());
        oneItemSets.add(itemSet);

        return fpgModel;
    }
}
