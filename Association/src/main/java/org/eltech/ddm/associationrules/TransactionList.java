package org.eltech.ddm.associationrules;

import java.util.ArrayList;
import java.util.Collection;

public class TransactionList extends ArrayList<Transaction> {
	private static final long serialVersionUID = 1L;

	//private TransactionList root;
	//private ItemSets inBetweenItemsets = new ItemSets();

	public TransactionList() {

	}

	public TransactionList(Collection<Transaction> collection) {
		super(collection);
	}


	public void print() {
		System.out.println("TID | Items");
		for(Transaction transaction : this) {
			System.out.println("  " + transaction.getTID() + " | " + transaction.getItemIDList());
		}
	}

	public boolean containsTransaction(String tid) {
		for(Transaction transaction : this) {
			if(transaction.getTID().equals(tid)) {
				return true;
			}
		}
		return false;
	}

	public Transaction getTransaction(int index) {
		if(size() > index)
			return get(index);
		else
			return null;
	}

	public Transaction getTransaction(String tid) {
		for(Transaction transaction : this) {
			if(transaction.getTID().equals(tid)) {
				return transaction;
			}
		}
		return null;
	}
}
