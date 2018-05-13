package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.Item;
import org.eltech.ddm.associationrules.fpg.ConditionalTable;
import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.associationrules.fpg.HeaderTable;
import org.eltech.ddm.associationrules.fpg.Node;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 11.12.17.
 */
public class FormConditionalPatternBaseStep extends Step {
    /**
     * Constructor of algorithm's step for all or part of input data
     *
     * @param settings - settings for build model
     */
    public FormConditionalPatternBaseStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    @Override
    protected EMiningModel execute(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = ((FPGModel) model);
        HeaderTable headerTable = fpgModel.getHeaderTableList().get(fpgModel.getCurrentHeaderTable());
        String itemID = headerTable.getItemID();
        Node node = headerTable.getNode();


        int support = node.getSupportItem();
        List<List<Item>> prefixPath = new ArrayList<>();
        prefixPath = getListPrefix(node, support, prefixPath);
        if (prefixPath.isEmpty()){
            ConditionalTable conditionalTable = new ConditionalTable(itemID, null);
            //fpgModel.setConditionalTableList(conditionalTable);
        }else {
            ConditionalTable conditionalTable = new ConditionalTable(itemID, prefixPath);
            fpgModel.setConditionalTableList(conditionalTable);
        }
        return fpgModel;
    }


    private  List<List<Item>> getListPrefix(Node node, int support, List<List<Item>> prefixPath) {
        if (node != null) {
            if (node.getParent().getItemID() != null) {
                List<Item> items = new ArrayList<>();
                items = getPrefix(node, support, items);
                prefixPath.add(items);
            }
            Node nextNode = node.getLinkNext();
            if (nextNode != null) {
                support = nextNode.getSupportItem();
                getListPrefix(nextNode, support, prefixPath);
            }
        }
        return prefixPath;
    }

    private List<Item> getPrefix(Node node, int support, List<Item> items) {
        if (node.getParent().getItemID() != null) {
            Node parentNode = node.getParent();
            int parentSupport = 0;
            if (parentNode.getSupportItem() != 0) {
                parentSupport = support;
            }
            String parentItemID = parentNode.getItemID();
            Item item = new Item(parentItemID);
            item.setSupportCount(parentSupport);
            items.add(item);
            getPrefix(parentNode, parentSupport, items);
        }
        return items;
    }
}
