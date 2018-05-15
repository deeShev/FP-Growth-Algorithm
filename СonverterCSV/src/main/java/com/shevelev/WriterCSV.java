package com.shevelev;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WriterCSV implements WriterFile {
    private boolean firstCame = true;
    private char delimiter;
    private String csvFileNameToResources;
    private int previousID = -1;
    private int count;

    private  Map<String, String[]> preparationHeaderMap;
    private final String[] preparationHeaders = {"transID", "itemID", "lastStatus", "whenCreated"};
    private Map<String, Integer> orderInRows;
    private Map<String,Column> compoundCSV;

    public WriterCSV(char delimiter, String csvFileNameToResources) {
        this.delimiter = delimiter;
        this.csvFileNameToResources = csvFileNameToResources;
        orderInRows = new HashMap<>();
        compoundCSV = new HashMap<>();

        preparationHeaderMap = new HashMap<>();
        preparationHeaderMap.put(preparationHeaders[0], new String[]{"incidentGroup_guid", "info"});
        preparationHeaderMap.put(preparationHeaders[1], new String[]{"zoneName", "rootZoneName", "typeName"});
        preparationHeaderMap.put(preparationHeaders[2], new String[]{"lastStatus"});
        preparationHeaderMap.put(preparationHeaders[3], new String[]{"whenCreated"});
    }

    @Override
    public void write(String[] headers, Collection<Integer> rows) throws IOException {
        CsvWriterSettings settings = new CsvWriterSettings();
        // when writing, nulls are printed using the empty value (defaults to "").
        // Here we configure the writer to print ? to describe null values.
        settings.setNullValue("?");

        // if the value is not null, but is empty (e.g. ""), the writer will can be configured to
        // print some default representation for a non-null/empty value
        settings.setEmptyValue("!");

        // Encloses all records within quotes even when they are not required.
        settings.setQuoteAllFields(true);

        settings.getFormat().setDelimiter(delimiter);

        // Sets the file headers (used for selection, these values won't be written automatically)
        settings.setHeaders(headers);


        CsvWriter writer = new CsvWriter(new FileWriter("CONV" + "_" + csvFileNameToResources, true), settings);
        if (firstCame) {
            for (int i = 0; i < headers.length; i++) {
                orderInRows.put(headers[i], i);
            }
            writer.writeHeaders();
        }

        preparationCSV(rows);
        firstCame = false;

        writer.writeRow(rows);

        writer.close();
    }

    public void preparationCSV(Collection<Integer> row) throws IOException {
        CsvWriterSettings preparationSettings = new CsvWriterSettings();
        // when writing, nulls are printed using the empty value (defaults to "").
        // Here we configure the writer to print ? to describe null values.
        preparationSettings.setNullValue("?");

        // if the value is not null, but is empty (e.g. ""), the writer will can be configured to
        // print some default representation for a non-null/empty value
        preparationSettings.setEmptyValue("!");

        // Encloses all records within quotes even when they are not required.
        preparationSettings.setQuoteAllFields(true);

        preparationSettings.getFormat().setDelimiter(delimiter);

        // Sets the file headers (used for selection, these values won't be written automatically)
        preparationSettings.setHeaders(preparationHeaders);

        CsvWriter writer = new CsvWriter(new FileWriter("PREP" + "_" + csvFileNameToResources, true), preparationSettings);

        if (firstCame) {
            writer.writeHeaders();
        }
        writer.writeRow(transducerCSV((List<Integer>) row));

        writer.close();
    }

    private List<Integer> transducerCSV(List<Integer> rows) {
            List<Integer> transducerList = new ArrayList<>();
        StringBuilder tmpRow;

        for (int i = 0; i < preparationHeaders.length; i++){
            tmpRow = new StringBuilder();
            String [] compoundRow = preparationHeaderMap.get(preparationHeaders[i]);

            for (int j = 0; j < compoundRow.length; j++){
                
                Column column  = compoundCSV.get(preparationHeaders[i]);

                if (column == null) {
                    column = new Column(preparationHeaders[i]);
                    compoundCSV.put(preparationHeaders[i], column);
                    column.setSubstituteRows(null);
                }
                
                
                int indexInRows = orderInRows.get(compoundRow[j]);

                if (i == 1){
                    if (j == 0){
                        if (previousID == transducerList.get(0)){
                            tmpRow.append(count).append("_").append(rows.get(indexInRows));
                            count += 1;
                        }else {
                            if (previousID == -1){
                                previousID = 0;
                                tmpRow.append(count).append("_").append(rows.get(indexInRows));
                                count += 1;
                            }else {
                                count = 0;
                                tmpRow.append(count).append("_").append(rows.get(indexInRows));
                                count += 1;
                            }
                        }
                    } else {
                        tmpRow.append("_").append(rows.get(indexInRows));
                    }
                }else {
                    if (i == 2){
                        double count = rows.get(indexInRows);
                        tmpRow.append(count);
                    }else {
                        if (j == 0) {
                            tmpRow.append(rows.get(indexInRows));
                        } else {
                            tmpRow.append("_").append(rows.get(indexInRows));
                        }
                    }
                }



                if (j == compoundRow.length - 1){
                    if (i == preparationHeaders.length -1){
                        previousID = transducerList.get(0);
                    }
                    if (column.getCompoundSubstituteRows().containsKey(String.valueOf(tmpRow))){
                        if (i == 2){
                            column.getCompoundSubstituteRows().put(String.valueOf(tmpRow), (int) Double.parseDouble(String.valueOf(tmpRow)));
                            transducerList.add((int) Double.parseDouble(String.valueOf(tmpRow)));
                        }else {
                            transducerList.add(column.getCompoundSubstituteRows().get(String.valueOf(tmpRow)));
                        }
                    }else {
                        if (i == 2){
                            column.getCompoundSubstituteRows().put(String.valueOf(tmpRow), (int) Double.parseDouble(String.valueOf(tmpRow)));
                            transducerList.add((int) Double.parseDouble(String.valueOf(tmpRow)));
                        }else {
                            if (i == 0){
                                if (previousID == column.getCount()){
                                    transducerList.add(column.getCount());
                                }else {
                                    column.getCompoundSubstituteRows().put(String.valueOf(tmpRow), column.getCount());
                                    transducerList.add(column.getCount());
                                    column.setCount(column.getCount() + 1);
                                }
                            }else {
                                column.getCompoundSubstituteRows().put(String.valueOf(tmpRow), column.getCount());
                                transducerList.add(column.getCount());
                                column.setCount(column.getCount() + 1);
                            }
                        }
                    }
                }
            }
        }
        
        return transducerList;
    }


}
