package org.eltech.ddm.associationrules.nir.newfpg.fpg;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class FPGConditionalTable extends MiningModelElement {
    private Node conditionalFPTree = null;

    public FPGConditionalTable(String id) {
        super(id);
        set = new ArrayList<>();
    }

    public Node getConditionalFPTree() {
        return conditionalFPTree;
    }

    public void setConditionalFPTree(Node conditionalFPTree) {
        this.conditionalFPTree = conditionalFPTree;
    }

    public void addPrefixPath(MiningModelElement prefixPath){
        set.add(prefixPath);
    }


    public List<MiningModelElement> getPrefixPath(){
        return set;
    }

    @Override
    protected String propertiesToString() {
        return " , prefixPath=" + set + ", conditional FPTree=" + conditionalFPTree;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }
}
