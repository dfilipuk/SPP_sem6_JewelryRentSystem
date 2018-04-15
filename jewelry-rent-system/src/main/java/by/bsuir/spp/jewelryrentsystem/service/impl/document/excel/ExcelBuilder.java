package by.bsuir.spp.jewelryrentsystem.service.impl.document.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExcelBuilder {
    public void buildDocument(OutputStream outputStream, String name, String[] header, List<List<String>> data,
                              int columnWidth) throws IOException {
        try (Workbook book = new HSSFWorkbook()) {
            Sheet sheet = book.createSheet(name);
            CellStyle headerStyle = book.createCellStyle();
            CellStyle contentStyle = book.createCellStyle();
            Font headerFont = book.createFont();
            Font contentFont = book.createFont();

            headerFont.setFontName("Arial");
            contentFont.setFontName("Arial");
            headerFont.setBold(true);
            contentFont.setBold(false);
            headerStyle.setWrapText(true);
            contentStyle.setWrapText(true);
            headerStyle.setVerticalAlignment(VerticalAlignment.TOP);
            contentStyle.setVerticalAlignment(VerticalAlignment.TOP);
            headerStyle.setFont(headerFont);
            contentStyle.setFont(contentFont);
            sheet.setDefaultColumnWidth(columnWidth);

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < header.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(header[i]);
                cell.setCellStyle(headerStyle);
            }

            int currentRowIndex = 1;
            for (List<String> row : data) {
                int currentColumnIndex = 0;
                Row currentRow = sheet.createRow(currentRowIndex++);

                for (String column : row) {
                    currentRow.createCell(currentColumnIndex).setCellValue(column);
                    currentRow.getCell(currentColumnIndex).setCellStyle(contentStyle);
                    currentColumnIndex++;
                }
            }

            book.write(outputStream);
        }
    }
}
