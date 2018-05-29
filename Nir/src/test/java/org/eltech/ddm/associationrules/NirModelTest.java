package org.eltech.ddm.associationrules;

import com.univocity.parsers.csv.CsvParserSettings;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;

public class NirModelTest {
    protected MiningCsvStream inputData;
    protected AssociationRulesFunctionSettings miningSettings;
    protected FPGModel model;

    protected void setInputData() throws MiningException {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(true);
        inputData = new MiningCsvStream("CONV_after_transformation3.csv", settings, false);
    }


    protected void setMiningSettings(EMiningAlgorithmSettings algorithmSettings) throws MiningException {
        ELogicalData logicalData = inputData.getLogicalData();

        miningSettings = new AssociationRulesFunctionSettings(logicalData);

        miningSettings.setTransactionIDsArributeName("incidentGroup_guid");
        miningSettings.setItemIDsArributeName("typeName");
        miningSettings.setStatus("lastStatus");
        miningSettings.setRootZoneName("rootZoneName");
        miningSettings.setZoneName("zoneName");
        miningSettings.setNumberTS("info");
        miningSettings.setTime("value_????? ??????? ??");
        miningSettings.setMinSupport(0.1);
        miningSettings.setAlgorithmSettings(algorithmSettings);
    }

}
