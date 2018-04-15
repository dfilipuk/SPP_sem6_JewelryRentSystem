package by.bsuir.spp.jewelryrentsystem.service.impl.document;

import by.bsuir.spp.jewelryrentsystem.service.DocumentBuilder;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.csv.CsvBuilder;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.excel.ExcelBuilder;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.pdf.PdfBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public abstract class AbstractDocumentBuilder implements DocumentBuilder {
    protected final String ARGS_ERROR_MESSAGE = "Invalid arguments";

    private final String PDF_WATER_MARK = "Evtushenko Tatyana, Filipuk Dmitry, Akulchik Vladimir";
    private final ExcelBuilder excelBuilder;
    private final CsvBuilder csvBuilder;
    private final PdfBuilder pdfBuilder;

    @Autowired
    public AbstractDocumentBuilder(ExcelBuilder excelBuilder, CsvBuilder csvBuilder, PdfBuilder pdfBuilder) {
        this.excelBuilder = excelBuilder;
        this.csvBuilder = csvBuilder;
        this.pdfBuilder = pdfBuilder;
    }

    protected abstract String getDocumentName();
    protected abstract String[] getDocumentHeader();
    protected abstract List<List<String>> getDocumentData(Object[] args);
    protected abstract int getExcelColumnWidth();
    protected abstract boolean isPdfPortrait();

    @Override
    public void generateExcelDocument(OutputStream outputStream, Object... args) throws IOException {
        excelBuilder.buildDocument(outputStream, getDocumentName(), getDocumentHeader(), getDocumentData(args),
                getExcelColumnWidth());
    }

    @Override
    public void generateCsvDocument(OutputStream outputStream, Object... args) throws IOException {
        csvBuilder.buildDocument(outputStream, getDocumentHeader(), getDocumentData(args));
    }

    @Override
    public void generatePdfDocument(OutputStream outputStream, Object... args) throws IOException {
        pdfBuilder.buildDocument(outputStream, getDocumentHeader(), getDocumentData(args), PDF_WATER_MARK, isPdfPortrait());
    }
}
