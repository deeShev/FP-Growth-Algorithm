package org.eltech.ddm.associationrules.fpg;

import java.util.HashMap;
import java.util.List;

/**
 * Created by denis on 08.12.17.
 */
public class Node implements Cloneable{
    protected String itemID = null;
    private int supportItem = 0;
    private Node parent = null;
    private Node linkNext = null;
    private HashMap<String, Node> children = null;

    public Node() {
    }

    private Node(String itemID, int supportItem, Node parent) {
        this.itemID = itemID;
        this.supportItem = supportItem;
        this.parent = parent;
    }

    public String getItemID() {
        return itemID;
    }

    public int getSupportItem() {
        return supportItem;
    }

    public Node getParent() {
        return parent;
    }

    public Node getLinkNext() {
        return linkNext;
    }

    @Override
    public String toString() {
        return "ItemId: " + itemID;
    }

    public void addNode(int startTrans, int endTrans, List<String> itemList, int support, HeaderTable headerTable) {
        if (children == null) {
            children = new HashMap<>();
        }

        if (children.containsKey(itemList.get(startTrans))) {
            Node child = children.get(itemList.get(startTrans));
            if (endTrans == itemList.size() - 1) {
                child.supportItem += support;
            }
            if (startTrans++ < endTrans) {
                child.addNode(startTrans, endTrans, itemList, support, headerTable);
            }
        } else {
            appendNode(startTrans, itemList, support, headerTable);
        }

    }

    private void appendNode(int startTrans, List<String> itemList, int support, HeaderTable headerTable) {
        if (startTrans < itemList.size() - 1 && itemList.size() > 1) {
            support = 0;
        }
        String item = itemList.get(startTrans);

        Node newChild = new Node(item, support, Integer.parseInt(item) < 0 ? null : this);

        newChild.addToHeaderTable(headerTable);
        children.put(item, newChild);
    }

    private void addToHeaderTable(HeaderTable headerTable) {
        linkNext = headerTable.getNode();
        headerTable.setNode(this);
    }

    @Override
    protected Object clone() {
        Node cloned = new Node();
        cloned.supportItem = supportItem;
        cloned.itemID = itemID;
        cloned.linkNext = (Node) linkNext.clone();
        cloned.parent = (Node) parent.clone();
        cloned.children = new HashMap<>(children.size());
        for (String key : children.keySet()) {
            Node clone = (Node) children.get(key).clone();
            cloned.children.put(key,clone);
        }
        return cloned;
    }
}
