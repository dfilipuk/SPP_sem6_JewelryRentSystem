package by.bsuir.spp.jewelryrentsystem.service.impl.document.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class WatermarkPageEvent extends PdfPageEventHelper {
    private final Font WATERMARK_FONT = new Font(Font.FontFamily.HELVETICA, 32, Font.BOLD, new GrayColor(0.85f));
    private final Font PAGE_NUMBER_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private final String PAGE_NUMBER_PHRASE = "Page %d";

    private String watermarkPhrase;
    private final int watermarkX;
    private final int watermarkY;
    private final int watermarkRotation;

    public WatermarkPageEvent(String watermarkPhrase, int x, int y, int rotation) {
        this.watermarkPhrase = watermarkPhrase;
        watermarkX = x;
        watermarkY = y;
        watermarkRotation = rotation;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContentUnder(),
                Element.ALIGN_CENTER, new Phrase(watermarkPhrase, WATERMARK_FONT), watermarkX, watermarkY, watermarkRotation);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT,
                new Phrase(String.format(PAGE_NUMBER_PHRASE, document.getPageNumber()), PAGE_NUMBER_FONT),
                36, 20, 0);
    }
}
