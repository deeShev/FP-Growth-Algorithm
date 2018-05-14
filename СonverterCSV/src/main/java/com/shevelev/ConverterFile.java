package com.shevelev;

import com.univocity.parsers.common.CommonParserSettings;

import java.io.IOException;

public interface ConverterFile {

    void start(CommonParserSettings settings, WriterFile writerFile) throws IOException;
}
