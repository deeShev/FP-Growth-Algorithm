package org.eltech.ddm.associationrules.newFpgTest;

import org.eltech.ddm.associationrules.newfpg.Item;
import org.eltech.ddm.associationrules.newfpg.ItemSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ItemSetTest {
    ItemSet itemSet;

    @Before
    public void setUp() throws Exception {
        Item item1 = new Item("1");

        Item item3 = new Item("3");

        Item item5 = new Item("5");

        itemSet = new ItemSet("3");
        itemSet.addInSet(item1);
        itemSet.addInSet(item3);
        itemSet.addInSet(item5);
    }

    @Test
    public void testItemSet() {
        Assert.assertEquals("1", itemSet.getElement(0).getID());
        Assert.assertEquals("3", itemSet.getElement(1).getID());
        Assert.assertEquals("5", itemSet.getElement(2).getID());
    }
}
