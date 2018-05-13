package org.eltech.ddm.associationrules.fpg;

/**
 * Created by denis on 08.12.17.
 */
public class HeaderTable implements Comparable<HeaderTable> , Cloneable{
    private String itemID;
    private int supportItem;
    private Node node = null;

    public HeaderTable(){

    }

    public HeaderTable(String itemID, int supportItem) {
        this.itemID = itemID;
        this.supportItem = supportItem;
    }

    @Override
    public int compareTo(HeaderTable o) {
        int result;
        result = Integer.compare(supportItem,o.supportItem);
        if (result != 0) return result;
        return result;
    }

    public String getItemID() {
        return itemID;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int getSupportItem() {
        return supportItem;
    }

    public void setSupportItem(int supportItem) {
        this.supportItem = supportItem;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        HeaderTable other = (HeaderTable) obj;
        if (this.itemID != other.itemID){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemID == null) ? 0 : itemID.hashCode());
        return  result;
    }

    @Override
    public String toString() {
        return "ItemID: " + itemID + " sup: " + supportItem;
    }

    @Override
    protected Object clone() {
        HeaderTable cloned = new HeaderTable();
        cloned.supportItem = supportItem;
        cloned.itemID = itemID;
        cloned.node = (Node) node.clone();

        return cloned;
    }
}


