package org.eltech.ddm.associationrules.nir.newfpg.fpg;

import org.eltech.ddm.associationrules.nir.newfpg.Item;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class Node extends MiningModelElement {
    private int count = 0;
    private Node parent = null;
    private Node linkNext = null;


    public Node(String id) {
        super(id);
        set = new ArrayList<>();
    }

    public Node(String id, int count) {
        super(id);
        this.count = count;
        set = new ArrayList<>();
    }

    public Node(String id, int count, Node parent) {
        super(id);
        this.count = count;
        this.parent = parent;
    }

    public Node(String id, int count, List<MiningModelElement> prefixPaths, List<HeaderTable> headerTables) {
        super(id);
        this.count = count;

        formConditionalNode(prefixPaths, headerTables);
    }

    public List<MiningModelElement> getChild() {
        if (set !=null){
            return set;
        }else return null;
    }

    public int getCount() {
        return count;
    }

    public Node getParent() {
        return parent;
    }

    public Node getLinkNext() {
        return linkNext;
    }

    @Override
    protected String propertiesToString() {
        return ", count = " + count;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }

    public void formConditionalNode(List<MiningModelElement> prefixPaths, List<HeaderTable> headerTables) {
        if (prefixPaths.size() < 2) {
            PrefixPath prefixPath = (PrefixPath) prefixPaths.get(0);
            List<MiningModelElement> path = prefixPath.getPrefixPath();
            createConditionalTree(0, path, null);
        } else {
            for (MiningModelElement prefixPath1 : prefixPaths) {
                PrefixPath prefixPath = (PrefixPath) prefixPath1;
                List<MiningModelElement> path = prefixPath.getPrefixPath();
                createConditionalTree(0, path, headerTables);
            }
        }
    }


    private void createConditionalTree(int start, List<MiningModelElement> path, List<HeaderTable> headerTables) {

        if (set == null) {
            set = new ArrayList<>();
        }

        Item item = (Item) path.get(start);
        int count = item.getCount();
        Node child;
        if (set.isEmpty()) {
            child = new Node(item.getID(), count,  this);
            set.add(child);
            child.addToConditionalHeaderTable(headerTables);
            if (++start < path.size()) {
                child.createConditionalTree(start, path, headerTables);
            }
        } else {
            if (set.contains(item)) {
                child = (Node) set.get(getIndexChild(start, path));
                child.count = child.getCount() + item.getCount();
                if (++start < path.size()) {
                    child.createConditionalTree(start, path, headerTables);
                }
            } else {
                child = new Node(item.getID(), count,  this);
                set.add(child);
                child.addToConditionalHeaderTable(headerTables);
                if (++start < path.size()) {
                    child.createConditionalTree(start, path, headerTables);
                }
            }
        }
    }

    private void addToConditionalHeaderTable(List<HeaderTable> headerTables) {
        if (headerTables != null) {
            for (HeaderTable currentHeaderTable : headerTables) {
                if (currentHeaderTable.getID().equals(this.getID())) {
                    this.addToHeaderTable(currentHeaderTable);
                    break;
                }
            }
        }
    }

    public void addNode(int startTrans, int endTrans,
                        List<MiningModelElement> itemList, HeaderTable headerTable) {

        int count = 1;
        if (set == null) {
            set = new ArrayList<>();
        }

        if (set.contains(itemList.get(startTrans))) {
            Node child = (Node) set.get(getIndexChild(startTrans, itemList));
            if (child.getID().equals(itemList.get(startTrans).getID())) {
                if (endTrans == itemList.size() - 1) {
                    child.count += count;
                }
                if (startTrans++ < endTrans) {
                    child.addNode(startTrans, endTrans, itemList, headerTable);
                }
            }
        } else {
            appendNode(startTrans, itemList, headerTable);
        }
    }

    private Integer getIndexChild(int startTrans, List<MiningModelElement> itemList) {
        Integer foundIndex = null;
        for (int i = 0; i < set.size(); i++) {
            if (set.get(i).equals(itemList.get(startTrans))) {
                foundIndex = i;
                break;
            }
        }
        return foundIndex;
    }

    private void appendNode(int startTrans, List<MiningModelElement> itemList, HeaderTable headerTable) {
        int count = 1;

        if (startTrans < itemList.size() - 1 && itemList.size() > 1) {
            count = 0;
        }

        Item item = (Item) itemList.get(startTrans);
        Node newChild = new Node(item.getID(), count,  this);
        newChild.addToHeaderTable(headerTable);
        set.add(newChild);
    }

    private void addToHeaderTable(HeaderTable headerTable) {
        linkNext = headerTable.getNode();
        headerTable.setNode(this);
    }
}
