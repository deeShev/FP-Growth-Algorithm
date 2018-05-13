package com.shevelev;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

public class Converter {
    private  Map<String,Column> substitute;
    private String csvFileNameToResources;
    private char delimiter;

    public static void main(String[] args) throws IOException, URISyntaxException {
        char semicolon = 59;
        Converter converter = new Converter("after_transformation3.csv",semicolon);
        converter.startConverter();
    }

    public Converter(String csvFileNameToResources, char delimiter){
        this.csvFileNameToResources = csvFileNameToResources;
        this.delimiter = delimiter;
        substitute = new HashMap<>();
    }

    public  void startConverter() throws IOException, URISyntaxException {
        CsvParserSettings settings = new CsvParserSettings();

        //delimiter (default ,): value used to separate individual fields in the input.
        settings.getFormat().setDelimiter(delimiter);

        //You can configure the parser to automatically detect what line separator sequence is in the input
        settings.setLineSeparatorDetectionEnabled(true);

        // Let's consider the first parsed row as the headers of each column in the file.
        settings.setHeaderExtractionEnabled(true);

        //Configures the parser to replace line separators,
        // specified in Format.getLineSeparator() by the normalized line separator character specified
        // in Format.getNormalizedNewline(), even on quoted values. This is enabled by default and is used to ensure data be read on any
        // platform without introducing unwanted blank lines.
        settings.setNormalizeLineEndingsWithinQuotes(true);

        // A RowListProcessor stores each parsed row in a List.
        RowListProcessor rowListProcessor = new RowListProcessor();
        settings.setProcessor(rowListProcessor);


        CsvParser parser = new CsvParser(settings);
        parser.parse(getReader(csvFileNameToResources));

        String[] headers = rowListProcessor.getHeaders();
        List<String[]> rows = rowListProcessor.getRows();

        boolean firstCame = false;

        for (String[] currentRow : rows){
            //Integer[] tmpRow = new Integer[currentRow.length];
            List<Object> tmpRow = new ArrayList<>();

            for (int i  = 0; i < headers.length; i++){

                String currentHeader = headers[i];

                Column column  = substitute.get(currentHeader);

                if (column == null) {
                    column = new Column(currentHeader);
                    substitute.put(currentHeader, column);
                }


                Integer row = column.getSubstituteRows().get(currentRow[i]);

                if (row == null){
                    if (column.getName().equals("lastStatus")){
                        double count = Double.parseDouble(currentRow[i]);
                        column.getSubstituteRows().put(currentRow[i], (int) count);
                        tmpRow.add((int) count);
                    }else {
                        column.getSubstituteRows().put(currentRow[i], column.getCount());
                        //tmpRow[i] = column.getCount();
                        tmpRow.add(column.getCount());
                        column.setCount(column.getCount() + 1);
                    }
                }else {
                    //tmpRow[i] = row;
                    tmpRow.add(row);
                }
            }
            writingCSV(headers,tmpRow, firstCame);
            firstCame = true;
        }
    }

    private void writingCSV(String[] headers, Collection<Object> row, boolean firstCame) throws IOException, URISyntaxException {

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
        if (!firstCame){
            writer.writeHeaders();
        }

        writer.writeRow(row);

        writer.close();
    }

    private  Reader getReader(String relativePath)  {
        try {
            return new InputStreamReader(Converter.class.getClassLoader().getResourceAsStream(relativePath), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Unable to read input", e);
        }
    }

}
