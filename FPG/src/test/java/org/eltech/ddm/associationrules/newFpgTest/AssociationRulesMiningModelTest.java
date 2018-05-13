package org.eltech.ddm.associationrules.newFpgTest;

import org.eltech.ddm.associationrules.newfpg.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.newfpg.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.inputdata.file.MiningArffStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;

public class AssociationRulesMiningModelTest {
    protected MiningInputStream inputData;
    protected AssociationRulesFunctionSettings miningSettings;
    protected FPGModel model;

    protected void setInputData() throws MiningException {
        //inputData = new MiningArffStream("../data/arff/association/transact_small.arff");
        //inputData = new MiningArffStream("/home/denis/Programs/iiholod-dxelopes4students-01a8bcabadfc/data/arff/association/T_20000.arff");
        inputData = new MiningArffStream("/home/denis/Programs/fpg-dxelopes/data/arff/association/transact_small.arff");
       /* CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(true);
        settings.setNormalizeLineEndingsWithinQuotes(true);
        settings.setReadInputOnSeparateThread(false);
        inputData = new MiningCsvStream("before_transformation3.csv",settings);*/
    }

    protected void setMiningSettings(EMiningAlgorithmSettings algorithmSettings) throws MiningException {
        ELogicalData logicalData = inputData.getLogicalData();

        miningSettings = new AssociationRulesFunctionSettings(logicalData);

        miningSettings.setTransactionIDsArributeName("transactId");
        miningSettings.setItemIDsArributeName("itemId");
        miningSettings.setMinSupport(0.1);
        miningSettings.setAlgorithmSettings(algorithmSettings);
    }

}
