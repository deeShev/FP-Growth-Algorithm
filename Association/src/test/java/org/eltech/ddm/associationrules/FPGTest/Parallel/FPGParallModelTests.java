package org.eltech.ddm.associationrules.FPGTest.Parallel;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.fpg.FPGParallelModel;
import org.eltech.ddm.associationrules.fpg.FPGSettings;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public abstract class FPGParallModelTests {
    protected abstract FPGParallelModel buildModel(AssociationRulesFunctionSettings miningSettings, MiningInputStream inputData) throws MiningException;

    @Test
    public void ParallTest() throws MiningException, FileNotFoundException, UnsupportedEncodingException {
        System.out.println("FPGAlg");

        //MiningInputStream miningInputStream = new MiningArffStream("../data/arff/association/T_200.arff");
        //MiningInputStream miningInputStream = new MiningArffStream("../data/arff/association/T_2000.arff");
        MiningInputStream miningInputStream = new MiningArffStream("../data/arff/association/T_20000.arff");

        ELogicalData logicalData = miningInputStream.getLogicalData();

        FPGSettings algorithmSettings = new FPGSettings();

        AssociationRulesFunctionSettings miningSettings = new AssociationRulesFunctionSettings(logicalData);
        miningSettings.setTransactionIDsArributeName("transactId");
        miningSettings.setItemIDsArributeName("itemId");
        miningSettings.setMinSupport(0.1);
        miningSettings.setAlgorithmSettings(algorithmSettings);

        FPGParallelModel miningModel = buildModel(miningSettings, miningInputStream);

        int result = 0;
        for (List<ItemSet> itemSets : miningModel.getResult()){
            result += itemSets.size();
        }
        //Assert.assertEquals(56, result);
        Assert.assertEquals(55, result);

    }


    /*@Test
    public void testTransactSmallData() throws MiningException, FileNotFoundException, UnsupportedEncodingException {
        System.out.println("FPGAlg");

        MiningInputStream miningInputStream = new MiningArffStream("../data/arff/association/transact_small.arff");
        ELogicalData logicalData = miningInputStream.getLogicalData();

        FPGSettings algorithmSettings = new FPGSettings();

        AssociationRulesFunctionSettings miningSettings = new AssociationRulesFunctionSettings(logicalData);
        miningSettings.setTransactionIDsArributeName("transactId");
        miningSettings.setItemIDsArributeName("itemId");
        miningSettings.setMinSupport(0.6);
        miningSettings.setAlgorithmSettings(algorithmSettings);

        FPGParallelModel miningModel = buildModel(miningSettings, miningInputStream);

        System.out.println("AssociationRuleSet:  \n" + miningModel.getAssociationRuleSet());

        Assert.assertEquals(2, miningModel.getAssociationRuleSet().size());
        // verify first rule
        Assert.assertEquals(true, miningModel.getAssociationRuleSet().contains(
                new AssociationRule(new ItemSet(new Item("5")), new ItemSet(new Item("2")), 0.0, 0.0)));
        Assert.assertEquals(true, miningModel.getAssociationRuleSet().contains(
                new AssociationRule(new ItemSet(new Item("2")), new ItemSet(new Item("5")), 0.0, 0.0)));
    }*/
}
