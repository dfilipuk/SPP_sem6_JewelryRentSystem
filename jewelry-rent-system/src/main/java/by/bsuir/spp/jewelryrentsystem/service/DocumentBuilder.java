package by.bsuir.spp.jewelryrentsystem.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public interface DocumentBuilder {
    void generateExcelDocument(OutputStream outputStream, Object... args) throws IOException;
    void generateCsvDocument(OutputStream outputStream, Object... args) throws IOException;
    void generatePdfDocument(OutputStream outputStream, Object... args) throws IOException;
}
