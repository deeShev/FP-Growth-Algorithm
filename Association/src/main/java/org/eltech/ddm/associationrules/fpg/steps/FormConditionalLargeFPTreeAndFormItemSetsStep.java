package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.fpg.ConditionalFPTree;
import org.eltech.ddm.associationrules.fpg.ConditionalTable;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.associationrules.fpg.HeaderTable;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FormConditionalLargeFPTreeAndFormItemSetsStep extends Step {
    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public FormConditionalLargeFPTreeAndFormItemSetsStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = ((FPGModel) model);
        HeaderTable headerTable = fpgModel.getHeaderTableList().get(fpgModel.getCurrentHeaderTable());
        String itemID = headerTable.getItemID();

        ConditionalTable conditionalTable = fpgModel.getConditionalTableList().get(fpgModel.getCurrentConditionalTableList());
        String itemCT = conditionalTable.getSuffix();

        if (itemID.equals(itemCT)) {

            List<List<Item>> prefixPath = conditionalTable.getPrefixPath();
            List<HeaderTable> conditionalHeaderTableList = new ArrayList<>();

            formHeaderTable(conditionalHeaderTableList, prefixPath, fpgModel);
            fpgModel.setConditionalHeaderTableList(conditionalHeaderTableList);
            if (!conditionalHeaderTableList.isEmpty()) {
                ConditionalFPTree conditionalFPTree = new ConditionalFPTree(prefixPath, true, conditionalHeaderTableList);
                conditionalTable.setConditionalFPTree(conditionalFPTree);

                Item item = fpgModel.getItem(itemID);
                ItemSet suffix = new ItemSet(item);
                getItemSets(suffix, conditionalHeaderTableList, conditionalFPTree, fpgModel);
            }


        }
        return fpgModel;
    }

    private void getItemSets(ItemSet suffix, List<HeaderTable> conditionalHeaderTableList, ConditionalFPTree conditionalFPTree, FPGModel fpgModel) {
        List<ConditionalTable> conditionalTableList = new ArrayList<>();

        for (int i = conditionalHeaderTableList.size() - 1; i > -1; i--) {
            List<List<Item>> prefixPath = new ArrayList<>();

            HeaderTable currentHT = conditionalHeaderTableList.get(i);
            String currentHTItemID = currentHT.getItemID();

            ConditionalFPTree currentNode = (ConditionalFPTree) currentHT.getNode();
            int support = currentNode.getSupportItem();

            prefixPath = getListPrefix(currentNode, support, prefixPath);


            Item currentItem = fpgModel.getItem(currentHTItemID);
            ItemSet newSuffix = new ItemSet(suffix.getItemIDList());

            newSuffix.addItem(currentItem);
            newSuffix.setSupportCount(currentHT.getSupportItem());
            conditionalFPTree.addItemSet(fpgModel.getResult(), newSuffix);


            ConditionalFPTree currentConditionalFPTree;


            if (prefixPath.size() == 1) {
                currentConditionalFPTree = new ConditionalFPTree(prefixPath, false, conditionalHeaderTableList);

                ConditionalTable conditionalTable = new ConditionalTable(currentHTItemID, prefixPath, currentConditionalFPTree);
                conditionalTableList.add(conditionalTable);

                currentConditionalFPTree.getFerqItemSet(newSuffix, fpgModel, currentConditionalFPTree, prefixPath, fpgModel.getMinSupport());
            } else {
                List<HeaderTable> newHeaderTableList = new ArrayList<>();
                formHeaderTable(newHeaderTableList, prefixPath, fpgModel);

                newHeaderTableList.remove(currentHT);
                currentConditionalFPTree = new ConditionalFPTree(prefixPath, true, newHeaderTableList);
                getItemSets(newSuffix, newHeaderTableList, currentConditionalFPTree, fpgModel);
            }
        }
    }


    private void formHeaderTable(List<HeaderTable> conditionalHeaderTableList, List<List<Item>> prefixPath, FPGModel fpgModel) {
        for (List<Item> currentPrefixPath : prefixPath) {
            for (Item currentItem : currentPrefixPath) {
                HeaderTable headerTable = new HeaderTable(currentItem.getItemID(), currentItem.getSupportCount());
                if (conditionalHeaderTableList.contains(headerTable)) {
                    for (HeaderTable currentHT : conditionalHeaderTableList) {
                        if (currentHT.equals(headerTable)) {
                            int support = currentHT.getSupportItem() + currentItem.getSupportCount();
                            currentHT.setSupportItem(support);
                        }
                    }
                } else {
                    conditionalHeaderTableList.add(headerTable);
                }
            }
        }
        removeSmallItemInHTAnDSort(conditionalHeaderTableList, fpgModel);
    }

    private void removeSmallItemInHTAnDSort(List<HeaderTable> conditionalHeaderTableList, FPGModel fpgModel) {
        for (int i = conditionalHeaderTableList.size() - 1; i > -1; i--) {
            HeaderTable currentHT = conditionalHeaderTableList.get(i);
            if (currentHT.getSupportItem() < fpgModel.getMinSupport()) {
                conditionalHeaderTableList.remove(currentHT);
            }
        }

        if (!conditionalHeaderTableList.isEmpty()) {
            conditionalHeaderTableList.sort(new Comparator<HeaderTable>() {
                @Override
                public int compare(HeaderTable o1, HeaderTable o2) {
                    if (o1.getSupportItem() == o2.getSupportItem()) {
                        return o1.getItemID().compareTo(o2.getItemID());
                    } else {
                        return o2.compareTo(o1);
                    }
                }
            });
        }
    }


    private List<List<Item>> getListPrefix(ConditionalFPTree node, int support, List<List<Item>> prefixPath) {
        if (node != null) {
            if (node.getParent().getItemID() != null) {
                List<Item> items = new ArrayList<>();
                items = getPrefix(node, support, items);
                prefixPath.add(items);
            }
            ConditionalFPTree nextNode = node.getNext();
            if (nextNode != null) {
                support = nextNode.getSupportItem();
                getListPrefix(nextNode, support, prefixPath);
            }
        }
        return prefixPath;
    }

    private List<Item> getPrefix(ConditionalFPTree node, int support, List<Item> items) {
        if (node.getParent().getItemID() != null) {
            if (node.getItemID() != null) {
                ConditionalFPTree parentNode = node.getParent();
                int parentSupport = 0;
                if (parentNode.getSupportItem() != 0) {
                    parentSupport = support;
                }
                String parentItemID = parentNode.getItemID();
                Item item = new Item(parentItemID);
                item.setSupportCount(parentSupport);
                items.add(item);
                getPrefix(parentNode, parentSupport, items);
            }
        }
        return items;
    }
}
