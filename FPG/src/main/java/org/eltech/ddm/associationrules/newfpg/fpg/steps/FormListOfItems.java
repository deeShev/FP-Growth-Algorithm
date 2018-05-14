package org.eltech.ddm.associationrules.newfpg.fpg.steps;

import org.eltech.ddm.associationrules.newfpg.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.newfpg.Item;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGModel;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGSortedItemList;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class FormListOfItems extends MiningBlock {
    private final double support;


    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public  FormListOfItems(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
        support = ((AssociationRulesFunctionSettings) settings).getMinSupport();

    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;
        int minSupport = fpgModel.getMinSupport(support);
        int currentIndexItem = fpgModel.getCurrentIndexItem();
        Item currentItem = (Item) fpgModel.getItem().getElement(currentIndexItem);

        if (currentItem.getCount() >= minSupport) {
            FPGSortedItemList fpgSIL = (FPGSortedItemList) fpgModel.getSortedList();
            fpgSIL.addItemInSortedList(currentItem);
        }

        return fpgModel;
    }
}
