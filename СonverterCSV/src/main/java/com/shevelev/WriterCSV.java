package com.shevelev;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

public class WriterCSV implements WriterFile {
    private boolean firstCame = true;
    private char delimiter;
    private String csvFileNameToResources;

    public WriterCSV(char delimiter, String csvFileNameToResources) {
        this.delimiter = delimiter;
        this.csvFileNameToResources = csvFileNameToResources;
    }

    @Override
    public void write(String[] headers, Collection<Integer> row) throws IOException {
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


        CsvWriter writer = new CsvWriter(new FileWriter("CONV" + "_" + csvFileNameToResources,true), settings);
        if (firstCame){
            writer.writeHeaders();
            firstCame = false;
        }

        writer.writeRow(row);

        writer.close();
    }

}
