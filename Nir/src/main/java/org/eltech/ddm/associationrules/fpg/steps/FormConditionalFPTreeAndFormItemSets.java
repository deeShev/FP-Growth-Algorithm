package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.fpg.FPGConditionalTable;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.associationrules.fpg.HeaderTable;
import org.eltech.ddm.associationrules.fpg.Node;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class FormConditionalFPTreeAndFormItemSets extends MiningBlock {
    private final double support;
    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public FormConditionalFPTreeAndFormItemSets(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
        support = ((AssociationRulesFunctionSettings) settings).getMinSupport();
    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;
        HeaderTable currentHeaderTable = (HeaderTable) fpgModel.getHeaderTable().getElement(fpgModel.getCurrentIndexHeaderTable());
        List<HeaderTable> conditionalH_T = new ArrayList<>();
        conditionalH_T.add(currentHeaderTable);

        FPGConditionalTable currentConditionalTable = (FPGConditionalTable) fpgModel.getConditionalList().getElement(fpgModel.getCurrentIndexConditionalList());

        if (currentHeaderTable.getID().equals(currentConditionalTable.getID())){
            String itemID = currentHeaderTable.getID();
            int countItem = currentHeaderTable.getCount();
            int supportFPG = fpgModel.getMinSupport(support);

            List<MiningModelElement> prefixPaths = currentConditionalTable.getPrefixPath();
            Node conditionalTree = new Node(itemID, countItem, prefixPaths,conditionalH_T);
            currentConditionalTable.setConditionalFPTree(conditionalTree);
            Item item =  new Item(currentConditionalTable.getID(),((Node)conditionalTree.getChild().get(0)).getCount());
            ItemSet suffix = new ItemSet(item.getID(),item);

            fpgModel.getFrequentItemSets(suffix,currentConditionalTable,supportFPG);
        }

        return fpgModel;
    }
}
