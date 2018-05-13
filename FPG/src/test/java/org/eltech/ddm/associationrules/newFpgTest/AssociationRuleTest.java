package org.eltech.ddm.associationrules.newFpgTest;

import org.eltech.ddm.associationrules.newfpg.AssociationRule;
import org.eltech.ddm.associationrules.newfpg.AssociationRuleSet;
import org.eltech.ddm.associationrules.newfpg.Item;
import org.eltech.ddm.associationrules.newfpg.ItemSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AssociationRuleTest {
    AssociationRuleSet associationRuleSet;
    AssociationRule assRule;

    @Before
    public void setUp() throws Exception {

        Item item1 = new Item("1");
        Item item3 = new Item("3");
        Item item5 = new Item("5");
        ItemSet itemSetA = new ItemSet("3",item1, item3, item5);

        Item item2 = new Item("2");
        ItemSet itemSetC = new ItemSet(item2.getID(),item2);

        assRule = new AssociationRule(itemSetA, itemSetC, 0.75, 0.6);
        associationRuleSet = new AssociationRuleSet("2");
        associationRuleSet.addAssociationRule(assRule);
    }

    @Test
    public void testAssRule() {
        Assert.assertEquals(3, assRule.getAntecedent().size());
        Assert.assertEquals("1", assRule.getAntecedent().getElement(0).getID());
        Assert.assertEquals("3", assRule.getAntecedent().getElement(1).getID());
        Assert.assertEquals("5", assRule.getAntecedent().getElement(2).getID());

        Assert.assertEquals(1, assRule.getConsequent().size());
        Assert.assertEquals("2", assRule.getConsequent().getElement(0).getID());

    }
}
