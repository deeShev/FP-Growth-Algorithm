package com.shevelev;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Column {
    private  int count = 0;
    private String name;
    private Map<String,Integer> substituteRows;

    private Map<Integer, String> compoundSubstituteRows;

    public Column(String name) {
        this.name = name;
        this.substituteRows = new HashMap<>();
        this.compoundSubstituteRows = new HashMap<>();
    }

    public  int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Map<String, Integer> getSubstituteRows() {
        return substituteRows;
    }

    public Map<Integer, String> getCompoundSubstituteRows() {
        return compoundSubstituteRows;
    }

    public void setSubstituteRows(Map<String, Integer> substituteRows) {
        this.substituteRows = substituteRows;
    }

    public void setCompoundSubstituteRows(Map<Integer, String> compoundSubstituteRows) {
        this.compoundSubstituteRows = compoundSubstituteRows;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Column))return false;

        Column column = (Column) obj;
        return column.count == count && column.name.equals(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count,name);
    }
}
