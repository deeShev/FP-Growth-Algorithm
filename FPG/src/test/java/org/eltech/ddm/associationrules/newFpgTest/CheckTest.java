package org.eltech.ddm.associationrules.newFpgTest;

import org.eltech.ddm.associationrules.newfpg.fpg.FPGAlgorithm;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGModel;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGSettings;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.junit.Assert;
import org.junit.Test;

public class CheckTest extends AssociationRulesMiningModelTest {
    protected MiningAlgorithm algorithm;



    @Test
    public void smollTest() throws MiningException {
        setInputData();
        FPGSettings fpgSettings = new FPGSettings();
        setMiningSettings(fpgSettings);
        algorithm =  new FPGAlgorithm(miningSettings);
        model = (FPGModel) algorithm.buildModel(inputData);
        System.out.println("-----------------------------");
        Assert.assertEquals(3, model.getItemSets().size());
    }
}
