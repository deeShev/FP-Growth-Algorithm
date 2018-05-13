package org.eltech.ddm.associationrules.newfpg.fpg;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class HeaderTable extends MiningModelElement implements Comparable<HeaderTable>{
    private int count;
    private Node node = null;

    public HeaderTable(String id, int count) {
        super(id);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    protected String propertiesToString() {
        return ", count=" + count + ", node=" + node;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }

    @Override
    public int compareTo(HeaderTable o) {
        int result;
        result = Integer.compare(count, o.count);
        if (result != 0) return result;
        return result;
    }

}
