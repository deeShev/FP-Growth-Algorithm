package org.eltech.ddm.associationrules.FPGTest.Consistently;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
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
import java.util.List;

public abstract class FPGBigModelTests {

    protected abstract FPGModel buildModel(AssociationRulesFunctionSettings miningSettings, MiningInputStream inputData) throws MiningException;

    @Test
    public void FPGTestT_200() throws MiningException, FileNotFoundException, UnsupportedEncodingException {
        System.out.println("FPGAlg");

        //MiningInputStream miningInputStream = new MiningArffStream("../data/arff/association/T_200.arff");
       MiningInputStream miningInputStream = new MiningArffStream("../data/arff/association/T_2000.arff");
        //MiningInputStream miningInputStream = new MiningArffStream("../data/arff/association/T_20000.arff");

        ELogicalData logicalData = miningInputStream.getLogicalData();

        FPGSettings algorithmSettings = new FPGSettings();

        AssociationRulesFunctionSettings miningSettings = new AssociationRulesFunctionSettings(logicalData);
        miningSettings.setTransactionIDsArributeName("transactId");
        miningSettings.setItemIDsArributeName("itemId");
        miningSettings.setMinSupport(0.1);
        miningSettings.setAlgorithmSettings(algorithmSettings);

        FPGModel miningModel = buildModel(miningSettings, miningInputStream);

        int result = 0;
        for (List<ItemSet> itemSets : miningModel.getResult()){
            result += itemSets.size();
        }
        //Assert.assertEquals(56, result);
        Assert.assertEquals(55, result);

    }
}
