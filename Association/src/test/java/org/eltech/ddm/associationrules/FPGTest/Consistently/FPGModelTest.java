package org.eltech.ddm.associationrules.FPGTest.Consistently;

import org.eltech.ddm.associationrules.AssociationRule;
import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.ItemSet;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.associationrules.fpg.FPGSettings;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Created by denis on 04.12.17.
 */
public abstract class FPGModelTest {

    protected abstract FPGModel buildModel(AssociationRulesFunctionSettings miningSettings, MiningInputStream inputData) throws MiningException;

    @Test
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

        FPGModel miningModel = buildModel(miningSettings, miningInputStream);

        System.out.println("AssociationRuleSet:  \n" + miningModel.getAssociationRuleSet());

        Assert.assertEquals(2, miningModel.getAssociationRuleSet().size());
        // verify first rule
        Assert.assertEquals(true, miningModel.getAssociationRuleSet().contains(
                new AssociationRule(new ItemSet(new Item("5")), new ItemSet(new Item("2")), 0.0, 0.0)));
        Assert.assertEquals(true, miningModel.getAssociationRuleSet().contains(
                new AssociationRule(new ItemSet(new Item("2")), new ItemSet(new Item("5")), 0.0, 0.0)));
    }

    @Test
    public void testTransactData() throws MiningException, FileNotFoundException, UnsupportedEncodingException {
        MiningInputStream miningInputStream = new MiningArffStream("../data/arff/association/transact.arff");
        ELogicalData logicalData = miningInputStream.getLogicalData();

        FPGSettings algorithmSettings = new FPGSettings();

        AssociationRulesFunctionSettings miningSettings = new AssociationRulesFunctionSettings(logicalData);
        miningSettings.setTransactionIDsArributeName("transactId");
        miningSettings.setItemIDsArributeName("itemId");
        miningSettings.setMinSupport(0.5);
        miningSettings.setAlgorithmSettings(algorithmSettings);
        miningSettings.verify();

        FPGModel miningModel = buildModel(miningSettings, miningInputStream);

        System.out.println("AssociationRuleSet  \n" + miningModel.getAssociationRuleSet());

        Assert.assertEquals(2, miningModel.getAssociationRuleSet().size());
        // verify first rule
        Assert.assertEquals(true, miningModel.getAssociationRuleSet().contains(
                new AssociationRule(new ItemSet(new Item("3")), new ItemSet(new Item("1")), 0.0, 0.0)));
        Assert.assertEquals(true, miningModel.getAssociationRuleSet().contains(
                new AssociationRule(new ItemSet(new Item("1")), new ItemSet(new Item("3")), 0.0, 0.0)));
    }
}
