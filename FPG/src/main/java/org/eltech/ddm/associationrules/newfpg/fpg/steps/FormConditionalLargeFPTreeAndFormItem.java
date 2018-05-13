package org.eltech.ddm.associationrules.newfpg.fpg.steps;

import org.eltech.ddm.associationrules.newfpg.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.newfpg.Item;
import org.eltech.ddm.associationrules.newfpg.ItemSet;
import org.eltech.ddm.associationrules.newfpg.fpg.*;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningBlock;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FormConditionalLargeFPTreeAndFormItem extends MiningBlock {
    private final double support;
    private int supportInt = 0;

    /**
     * Constructor of algorithm's calculation step (not using data set)
     *
     * @param settings - settings for build model
     */
    public FormConditionalLargeFPTreeAndFormItem(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
        support = ((AssociationRulesFunctionSettings) settings).getMinSupport();
    }

    @Override
    protected EMiningModel execute(EMiningModel model) throws MiningException {
        FPGModel fpgModel = (FPGModel) model;
        HeaderTable currentHeaderTable = (HeaderTable) fpgModel.getHeaderTable().getElement(fpgModel.getCurrentIndexHeaderTable());
        FPGConditionalTable currentConditionalTable = (FPGConditionalTable) fpgModel.getConditionalList().getElement(fpgModel.getCurrentIndexConditionalList());
        supportInt = fpgModel.getMinSupport(support);

        if (currentHeaderTable.getID().equals(currentConditionalTable.getID())) {
            List<MiningModelElement> prefixPaths = currentConditionalTable.getPrefixPath();
            List<HeaderTable> conditionalH_T = new ArrayList<>();
            formHeaderTable(conditionalH_T, prefixPaths);

            if (!conditionalH_T.isEmpty()) {
                String itemID = currentHeaderTable.getID();
                int countItem = currentHeaderTable.getCount();
                Node conditionalTree = new Node(itemID, countItem, prefixPaths, conditionalH_T);
                currentConditionalTable.setConditionalFPTree(conditionalTree);
                Item item = new Item(currentConditionalTable.getID(), conditionalTree.getCount());
                ItemSet suffix = new ItemSet(item.getID(), item);
                getBigItemSets(suffix, conditionalH_T, fpgModel);
            }
        }

        return fpgModel;
    }


    private void getBigItemSets(ItemSet suffix, List<HeaderTable> conditionalHeaderTableList, FPGModel fpgModel) throws MiningException {

        List<FPGConditionalTable> conditionalTableList = new ArrayList<>();

        for (HeaderTable headerTable : conditionalHeaderTableList) {
            FPGConditionalTable fpgConditionalTable = new FPGConditionalTable(headerTable.getID());
            fpgModel.getListPrefix(headerTable.getNode(), fpgConditionalTable);
            conditionalTableList.add(fpgConditionalTable);
            Item currItem = new Item(headerTable.getID(), headerTable.getCount());
            String itemSetID = suffix.getID() + ", " + currItem.getID();
            ItemSet newSuffix = new ItemSet(itemSetID, headerTable.getCount(), suffix.getItemIDList());
            newSuffix.addInSet(currItem);

            fpgModel.addItemSets(newSuffix);
            Node currentConditionalFPTree;

            if (fpgConditionalTable.getPrefixPath().size() == 1) {
                currentConditionalFPTree = new Node(headerTable.getID(), headerTable.getCount(), fpgConditionalTable.getPrefixPath(), null);
                fpgConditionalTable.setConditionalFPTree(currentConditionalFPTree);
                fpgModel.getFrequentItemSets(newSuffix, fpgConditionalTable, supportInt);

            } else {
                if (fpgConditionalTable.getPrefixPath().size() > 1) {
                    List<HeaderTable> newHeaderTableList = new ArrayList<>();
                    formHeaderTable(newHeaderTableList, fpgConditionalTable.getPrefixPath());

                    newHeaderTableList.remove(headerTable);
                    currentConditionalFPTree = new Node(headerTable.getID(), headerTable.getCount(), fpgConditionalTable.getPrefixPath(), newHeaderTableList);
                    fpgConditionalTable.setConditionalFPTree(currentConditionalFPTree);
                    getBigItemSets(newSuffix, newHeaderTableList, fpgModel);
                }
            }
        }
    }

    private void formHeaderTable(List<HeaderTable> conditionalHeaderTableList, List<MiningModelElement> prefixPaths) {
        for (MiningModelElement prefixPath1 : prefixPaths) {
            PrefixPath prefixPath = (PrefixPath) prefixPath1;

            for (int j = 0; j < prefixPath.size(); j++) {
                Item currentItem = (Item) prefixPath.getElement(j);

                HeaderTable headerTable = new HeaderTable(currentItem.getID(), currentItem.getCount());
                if (conditionalHeaderTableList.contains(headerTable)) {
                    for (HeaderTable currentHT : conditionalHeaderTableList) {
                        if (currentHT.equals(headerTable)) {
                            int support = currentHT.getCount() + currentItem.getCount();
                            currentHT.setCount(support);
                        }
                    }
                } else {
                    conditionalHeaderTableList.add(headerTable);
                }
            }
        }
        removeSmallItemInHTAndSort(conditionalHeaderTableList);
    }

    private void removeSmallItemInHTAndSort(List<HeaderTable> conditionalHeaderTableList) {
        for (int i = conditionalHeaderTableList.size() - 1; i > -1; i--) {
            HeaderTable currentHT = conditionalHeaderTableList.get(i);
            if (currentHT.getCount() < supportInt) {
                conditionalHeaderTableList.remove(currentHT);
            }
        }

        if (!conditionalHeaderTableList.isEmpty()) {
            conditionalHeaderTableList.sort(new Comparator<HeaderTable>() {
                @Override
                public int compare(HeaderTable o1, HeaderTable o2) {
                    if (o1.getCount() == o2.getCount()) {
                        return o1.getID().compareTo(o2.getID());
                    } else {
                        return o2.compareTo(o1);
                    }
                }
            });
        }
    }
}
