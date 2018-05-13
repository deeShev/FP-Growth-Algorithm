package org.eltech.ddm.associationrules.newFpgTest.nir;

import org.eltech.ddm.associationrules.nir.newfpg.fpg.FPGAlgorithm;
import org.eltech.ddm.associationrules.nir.newfpg.fpg.FPGModel;
import org.eltech.ddm.associationrules.nir.newfpg.fpg.FPGSettings;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.junit.Test;

public class FPGSequential extends NirModelTest {
    protected MiningAlgorithm algorithm;



    @Test
    public void FPGSequentialTest() throws MiningException {
        setInputData();
        FPGSettings fpgSettings = new FPGSettings();
        setMiningSettings(fpgSettings);
        algorithm =  new FPGAlgorithm(miningSettings);
        model = (FPGModel) algorithm.buildModel(inputData);
        System.out.println(model);
    }
}
