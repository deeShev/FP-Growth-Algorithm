package com.shevelev;

import com.univocity.parsers.csv.CsvParserSettings;

import java.io.IOException;

public class Converter {
    private char delimiter;

    public static void main(String[] args) throws IOException {
        char semicolon = 59;
        Converter converter = new Converter(semicolon);
        ConverterFile converterFile = new ConverterCSV("after_transformation3.csv");
        WriterCSV writerCSV = new WriterCSV(semicolon,"after_transformation3.csv");
        converterFile.start(converter.settigsCSV(), writerCSV);

    }

    public Converter(char delimiter){
        this.delimiter = delimiter;
    }


    public CsvParserSettings settigsCSV(){
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
