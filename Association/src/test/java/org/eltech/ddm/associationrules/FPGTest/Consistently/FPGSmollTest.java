package org.eltech.ddm.associationrules.FPGTest.Consistently;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.fpg.FPGAlgorithm;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;

/**
 * Created by denis on 04.12.17.
 */
public class FPGSmollTest extends FPGModelTest{
    @Override
    protected FPGModel buildModel(AssociationRulesFunctionSettings miningSettings, MiningInputStream inputData) throws MiningException {
        FPGAlgorithm algorithm = new FPGAlgorithm(miningSettings);
        FPGModel model = (FPGModel) algorithm.buildModel(inputData);

        System.out.println("calculation time [s]: " + algorithm.getTimeSpentToBuildModel());

        return model;
    }
}
