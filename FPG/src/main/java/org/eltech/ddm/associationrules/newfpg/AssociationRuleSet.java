package org.eltech.ddm.associationrules.newfpg;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.List;

public class AssociationRuleSet extends MiningModelElement {

    public AssociationRuleSet(String id) {
        super(id);
        set = new ArrayList<>();
    }
    

    @Override
    protected String propertiesToString() {
        return " " + set;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }

    public void addAssociationRule(MiningModelElement miningModelElement){
        add(miningModelElement);
    }

    public boolean contains(Object o) {
        boolean contain = false;
        for (int i = 0; i < size(); i++){
            if (getElement(i).equals(o)){
                contain = true;
                break;
            }else {
                contain = false;
            }
        }
        return contain;
    }
}
