package org.eltech.ddm.associationrules.nir.newfpg;

import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningfunctionsettings.MiningFunction;

public class AssociationRulesFunctionSettings extends EMiningFunctionSettings {

    private final String TAG_NAME_ITEM_ID_ATTRIBUTE = "itemIDsAttributeName";

    private final String TAG_NAME_TRANSACTION_ID_ATTRIBUTE = "transactionIDsAttributeName";
    private final String TAG_NAME_STATUS = "status";

    /**
     * This specifies the minimum confidence value for each association rule to
     * be found.
     */
    private final String TAG_MIN_CONFIDIENCE = "minimumConfidence";

    /**
     * This is the maximum length of the antecedent and consequent item attributes
     * sizes.
     */
    private final String TAG_MAX_RULE_LENGTH = "maximumRuleLength";

    /**
     * This specifies the minimum support of each frequent itemset to be found.
     */
    private final String TAG_MIN_SUPPORT = "minimumSupport";

    public AssociationRulesFunctionSettings(ELogicalData ld) {
        super(ld);
        addTaggedValue(TAG_NAME_ITEM_ID_ATTRIBUTE, null, "String");
        addTaggedValue(TAG_NAME_TRANSACTION_ID_ATTRIBUTE, null, "String");
        addTaggedValue(TAG_NAME_STATUS, null, "String");
        addTaggedValue(TAG_MIN_CONFIDIENCE, null, "double");
        addTaggedValue(TAG_MAX_RULE_LENGTH, null, "int");
        addTaggedValue(TAG_MIN_SUPPORT, null, "double");
    }

    @Override
    public MiningFunction getMiningFunction() {
        return MiningFunction.associationRules;
    }

    public double getMinConfidence() {
        String v = getTaggedValue(TAG_MIN_CONFIDIENCE);
        if(v == null)
            return 0.1;
        else
            return Double.parseDouble(v);
    }

    public void setMinConfidence(double minimumConfidence) {
        setTaggedValue(TAG_MIN_CONFIDIENCE, String.valueOf(minimumConfidence));
    }

    public void setItemIDsArributeName(String itemIDsAttributeName) {
        setTaggedValue(TAG_NAME_ITEM_ID_ATTRIBUTE, String.valueOf(itemIDsAttributeName));
    }

    public void setTransactionIDsArributeName(String transactionIDsAttributeName) {
        setTaggedValue(TAG_NAME_TRANSACTION_ID_ATTRIBUTE, String.valueOf(transactionIDsAttributeName));
    }

    public String getItemIDsAttributeName() {
        String v = getTaggedValue(TAG_NAME_ITEM_ID_ATTRIBUTE);
        if(v == null)
            return "";
        else
            return v;
    }

    public String getTransactionIDsAttributeName() {
        String v = getTaggedValue(TAG_NAME_TRANSACTION_ID_ATTRIBUTE);
        if(v == null)
            return "";
        else
            return v;
    }

    public void setStatus(String status) {
        setTaggedValue(TAG_NAME_STATUS, String.valueOf(status));
    }



    public String getStatus() {
        String v = getTaggedValue(TAG_NAME_STATUS);
        if(v == null)
            return "";
        else
            return v;
    }

    public double getMinSupport() {
        String v = getTaggedValue(TAG_MIN_SUPPORT);
        if(v == null)
            return 0.1;
        else
            return Double.parseDouble(v);
    }

    public void setMinSupport(double minimumSupport) {
        setTaggedValue(TAG_MIN_SUPPORT, String.valueOf(minimumSupport));
    }
}
