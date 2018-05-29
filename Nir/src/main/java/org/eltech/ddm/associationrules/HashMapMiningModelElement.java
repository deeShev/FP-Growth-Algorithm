package org.eltech.ddm.associationrules;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapMiningModelElement extends MiningModelElement {

    private HashMap<String, Integer> map;

    public HashMapMiningModelElement(String id) {
        super(id);
        map = new HashMap<>();
    }

    @Override
    protected String propertiesToString() {
        return "";
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }

    public MiningModelElement getElement(String key) {
        if (map.containsKey(key)) {
            return super.getElement(map.get(key));
        }
        return null;
    }

    @Override
    protected synchronized void add(MiningModelElement element) {
        super.add(element);
        map.put(element.getID(), super.size() - 1);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public synchronized void put(String key, MiningModelElement value) {
        super.add(value);
        map.put(key, super.size() - 1);
    }

    public HashMap<String, Integer> getEntry() {
        return map;
    }

    public List<MiningModelElement> getSet() {
        return super.set;
    }

    public void setSet(List<MiningModelElement> newSet) {
        map = new HashMap<>();
        for (int i = 0; i < newSet.size(); i++) {
            map.put(newSet.get(i).getID(), i);
        }
        super.set = newSet;
    }

    @Override
    protected synchronized void remove(int index) {
        map.remove(set.get(index).getID());
        super.remove(index);

    }

    @Override
    public Object clone() {
        HashMapMiningModelElement clone = (HashMapMiningModelElement)super.clone();
        if(set != null){
            clone.set = new ArrayList<>();
            for(MiningModelElement element : set)
                clone.set.add((MiningModelElement)element.clone());
        }
        if (map !=null){
            clone.map = new HashMap<>();
            for (String key : map.keySet()) {
                Integer cloneValue = map.get(key);
                clone.map.put(key, cloneValue);
            }
        }

        return clone;
    }
}
