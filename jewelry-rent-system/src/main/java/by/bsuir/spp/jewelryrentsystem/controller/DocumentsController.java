package by.bsuir.spp.jewelryrentsystem.controller;

import by.bsuir.spp.jewelryrentsystem.service.DocumentBuilder;
import by.bsuir.spp.jewelryrentsystem.service.exception.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping(value = "/document")
public class DocumentsController {
    private final String ERROR_MESSAGE = "Unable to generate document";
    private final String EXCEL_CONTENT_TYPE = "application/vnd.ms-excel";
    private final String CSV_CONTENT_TYPE = "text/csv";
    private final String PDF_CONTENT_TYPE = "application/pdf";

    private final DocumentBuilder clientDocumentBuilder;

    @Autowired
    public DocumentsController(@Qualifier("client_documents") DocumentBuilder clientDocumentBuilder) {
        this.clientDocumentBuilder = clientDocumentBuilder;
    }

    @GetMapping(value = "/client-excel")
    public void getExcel(HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setContentType(EXCEL_CONTENT_TYPE);
            clientDocumentBuilder.generateExcelDocument(httpServletResponse.getOutputStream());
        } catch (IOException e) {
            throw new InternalServerErrorException(ERROR_MESSAGE);
        }
    }

    @GetMapping(value = "/client-csv")
    public void getCsv(HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setContentType(CSV_CONTENT_TYPE);
            clientDocumentBuilder.generateCsvDocument(httpServletResponse.getOutputStream());
        } catch (IOException e) {
            throw new InternalServerErrorException(ERROR_MESSAGE);
        }
    }

    @GetMapping(value = "/client-pdf")
    public void getPdf(HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setContentType(PDF_CONTENT_TYPE);
            clientDocumentBuilder.generatePdfDocument(httpServletResponse.getOutputStream());
        } catch (IOException e) {
            throw new InternalServerErrorException(ERROR_MESSAGE);
        }
    }
}
