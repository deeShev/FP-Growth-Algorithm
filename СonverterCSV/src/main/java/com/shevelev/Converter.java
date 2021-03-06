package com.shevelev;

import com.univocity.parsers.csv.CsvParserSettings;

import java.io.IOException;

public class Converter {
    private char delimiter;

    public static void main(String[] args) throws IOException {
        char semicolon = 59;
        Converter converter = new Converter(semicolon);
        ConverterFile converterFile = new ConverterCSV("mmk_incident_csv.csv");
        WriterCSV writerCSV = new WriterCSV(semicolon,"mmk_incident_csv.csv");
        converterFile.start(converter.settingsCSV(), writerCSV);
        System.out.println("aa");
    }

    public Converter(char delimiter){
        this.delimiter = delimiter;
    }


    public CsvParserSettings settingsCSV(){
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

        return  settings;
    }

}
