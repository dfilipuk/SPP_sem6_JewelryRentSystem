package by.bsuir.spp.jewelryrentsystem.service.impl.document.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class PdfBuilder {
    public void buildDocument(OutputStream outputStream, String[] header, List<List<String>> data,
                             String watermark, boolean isPortrait) throws IOException {
        Document document = null;
        Rectangle documentRectangle;
        WatermarkPageEvent watermarkPageEvent;

        if (isPortrait) {
            documentRectangle = PageSize.A4;
            watermarkPageEvent = new WatermarkPageEvent(watermark, 300, 400, 50);
        } else {
            documentRectangle = PageSize.A4.rotate();
            watermarkPageEvent = new WatermarkPageEvent(watermark, 430, 300, 35);
        }

        try {
            document = new Document(documentRectangle, 36, 36, 54, 36);
            PdfWriter pdfWriter = PdfWriter.getInstance(document, outputStream);
            pdfWriter.setPageEvent(watermarkPageEvent);
            pdfWriter.setEncryption(null, null,  ~(PdfWriter.ALLOW_COPY), PdfWriter.STANDARD_ENCRYPTION_128);
            document.open();

            PdfPTable table = new PdfPTable(header.length);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            Font font = FontFactory.getFont(FontFactory.TIMES);
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPadding(5);

            for (String element : header) {
                cell.setPhrase(new Phrase(element, font));
                table.addCell(cell);
            }

            for (List<String> row : data) {
                for (String column : row) {
                    table.addCell(column);
                }
            }

            document.add(table);
        } catch (DocumentException e) {
            throw new IOException(e.getMessage());
        } finally {
            if (document != null && document.isOpen()) {
                document.close();
            }
        }
    }
}
