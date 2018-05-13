package org.eltech.ddm.associationrules.newfpg.fpg;

import org.eltech.ddm.associationrules.newfpg.AssociationRulesMiningModel;
import org.eltech.ddm.associationrules.newfpg.Item;
import org.eltech.ddm.associationrules.newfpg.ItemSet;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class FPGModel extends AssociationRulesMiningModel {
    static private final int SORTED_ITEM_LIST = 5;
    static private final int HEADER_TABLE = 6;
    static private final int NODE = 7;
    static private final int CONDITIONAL_LIST = 8;


    static public final int[] INDEX_SORTED_ITEM_LIST = {SORTED_ITEM_LIST};
    static public final int[] INDEX_HEADER_TABLE = {HEADER_TABLE};
    static public final int[] INDEX_CONDITIONAL_LIST = {CONDITIONAL_LIST};


    public FPGModel(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
        sets.add(SORTED_ITEM_LIST, new FPGSortedItemList("Sorted list of frequent items"));
        sets.add(HEADER_TABLE, new MiningModelElement("HeaderTable") {
            @Override
            protected String propertiesToString() {
                return ", size=" + size();
            }

            @Override
            public void merge(List<MiningModelElement> elements) throws MiningException {

            }
        });
        sets.add(NODE, new Node("Node"));
        sets.add(CONDITIONAL_LIST, new MiningModelElement("Conditional list") {
            @Override
            protected String propertiesToString() {
                return ", size=" + size();
            }

            @Override
            public void merge(List<MiningModelElement> elements) throws MiningException {

            }
        });
    }

    public MiningModelElement getSortedList() throws MiningException {
        return getElement(index(SORTED_ITEM_LIST));
    }

    public int getCurrentIndexSortedList() throws MiningException {
        return getCurrentElementIndex(index(SORTED_ITEM_LIST));
    }

    public MiningModelElement getHeaderTable() throws MiningException {
        return getElement(index(HEADER_TABLE));
    }

    public int getCurrentIndexHeaderTable() throws MiningException {
        return getCurrentElementIndex(index(INDEX_HEADER_TABLE));
    }


    public MiningModelElement getFPGTree() throws MiningException {
        return getElement(index(NODE));
    }


    public void addItemInHeaderTable(HeaderTable currentHeaderTable) throws MiningException {
        addElement(INDEX_HEADER_TABLE, currentHeaderTable);
    }


    public MiningModelElement getConditionalList() throws MiningException {
        return getElement(index(CONDITIONAL_LIST));
    }

    public int getCurrentIndexConditionalList() throws MiningException {
        return getCurrentElementIndex(index(INDEX_CONDITIONAL_LIST));
    }

    public void addConditionalTable(FPGConditionalTable conditionalTable) throws MiningException {
        addElement(INDEX_CONDITIONAL_LIST, conditionalTable);
    }

    public int getMinSupport(double support) throws MiningException {
        return (int) Math.ceil(getTransactionList().size() * support);
    }

    public void getListPrefix(Node node, FPGConditionalTable conditionalTable) {
        if (node != null) {
            if (node.getParent().getCount() != 0 && !node.getParent().getID().equals("Node") && node.getParent().getID() != null && node.getParent().getParent() != null) {
                PrefixPath prefixPath = new PrefixPath(null, node.getCount());
                getPrefix(node, node.getCount(), prefixPath);
                conditionalTable.addPrefixPath(prefixPath);
            }
            Node nextNode = node.getLinkNext();
            if (nextNode != null) {
                getListPrefix(nextNode, conditionalTable);
            }
        }
    }

    private void getPrefix(Node node, int count, PrefixPath prefixPath) {
        if (node.getParent().getCount() != 0  && !node.getParent().getID().equals("Node") && node.getParent().getID() != null && node.getParent().getParent() != null) {
            Node parentNode = node.getParent();
            Item item = new Item(parentNode.getID(), count);
            prefixPath.addItemInPP(item);
            if (parentNode.getParent() != null) {
                getPrefix(parentNode, count, prefixPath);
            }
        }
    }


    public void getFrequentItemSets(ItemSet suffix, FPGConditionalTable conditionalTable, int support) throws MiningException {
        if (conditionalTable.getPrefixPath().size() == 1) {
            Node root = conditionalTable.getConditionalFPTree();
            Node child = (Node) root.getChild().get(0);
            int deep = getDeepNode(child);
            int count = 0;
            runnerToTree(child, suffix, 0, support, count, deep);
        }

    }


    private void runnerToTree(Node currChild, ItemSet suffix, int index, int support, int count, int deep) throws MiningException {
        if (currChild.getCount() >= support) {
            String currentItem = currChild.getID();
            int supportItem = currChild.getCount();
            Item item = new Item(currentItem, supportItem);

            String itemSetID = suffix.getID() + ", " + currentItem;
            ItemSet currentItemSet = new ItemSet(itemSetID, supportItem, suffix.getItemIDList());
            currentItemSet.addInSet(item);

            addItemSets(currentItemSet);

            count++;
            if (count < deep) {
                Node child = (Node) currChild.getChild().get(index);
                runnerToTree(child, currentItemSet, index, support, count, deep);
                runnerToTree(child, suffix, index, support, count, deep);
            }
        }
    }


    private int getDeepNode(Node currNode) {
        int deep = 1;
        while (currNode.getChild() != null) {
            deep++;
            currNode = (Node) currNode.getChild().get(0);
        }
        return deep;
    }
}
