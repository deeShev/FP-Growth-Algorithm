package org.eltech.ddm.associationrules.nir.newfpg;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class AssociationRule extends MiningModelElement {
    private ItemSet antecedent;
    private ItemSet consequent;
    private double support;
    private double confidence;

    public AssociationRule(String id) {
        super(id);
    }

    public AssociationRule(ItemSet antecedent, ItemSet consequent, double support,
                           double confidence){
        super("1");
        this.antecedent = antecedent;
        this.consequent = consequent;
        this.support = support;
        this.confidence = confidence;

    }


    @Override
    protected String propertiesToString() {
        return " ";
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }

    public ItemSet getAntecedent() {
        return antecedent;
    }

    public void setAntecedent(ItemSet antecedent) {
        this.antecedent = antecedent;
    }

    public ItemSet getConsequent() {
        return consequent;
    }

    public void setConsequent(ItemSet consequent) {
        this.consequent = consequent;
    }

    public double getSupport() {
        return support;
    }

    public void setSupport(double support) {
        this.support = support;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}
