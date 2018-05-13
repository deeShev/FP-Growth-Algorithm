package org.eltech.ddm.associationrules.fpg;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.ArrayList;
import java.util.List;

public class FPGParallelModel extends FPGModel{

    public FPGParallelModel(AssociationRulesFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    public void join(List<EMiningModel> joinModels) throws MiningException {
        super.join(joinModels);
    }

    @Override
    public ArrayList<EMiningModel> split(int handlerCount) throws MiningException {
        return super.split(handlerCount);
    }
}
