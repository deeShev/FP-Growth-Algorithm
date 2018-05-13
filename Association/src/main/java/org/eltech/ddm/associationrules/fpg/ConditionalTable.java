package org.eltech.ddm.associationrules.fpg;

import org.eltech.ddm.associationrules.Item;

import java.util.List;

/**
 * Created by denis on 11.12.17.
 */
public class ConditionalTable {
    String suffix = null;
    List<List<Item>> prefixPath = null;
    ConditionalFPTree conditionalFPTree = null;

    public ConditionalTable(String suffix, List<List<Item>> prefixPath) {
        this.suffix = suffix;
        this.prefixPath = prefixPath;
    }

    public ConditionalTable(String suffix, List<List<Item>> prefixPath,ConditionalFPTree conditionalFPTree) {
        this.suffix = suffix;
        this.prefixPath = prefixPath;
        this.conditionalFPTree = conditionalFPTree;
    }
    public List<List<Item>> getPrefixPath() {
        return prefixPath;
    }

    public ConditionalFPTree getConditionalFPTree() {
        return conditionalFPTree;
    }

    public void setConditionalFPTree(ConditionalFPTree conditionalFPTree) {
        this.conditionalFPTree = conditionalFPTree;
    }

    public String getSuffix() {
        return suffix;
    }

    @Override
    public String toString() {
        return "Suffix: " + suffix + " PrefixPath: " + prefixPath.size();
    }
}
