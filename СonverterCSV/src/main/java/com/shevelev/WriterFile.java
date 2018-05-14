package com.shevelev;

import java.io.IOException;
import java.util.Collection;

public interface WriterFile {

    void write(String[] headers, Collection<Integer> row) throws IOException;
}
