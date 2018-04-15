package by.bsuir.spp.jewelryrentsystem.controller;

import by.bsuir.spp.jewelryrentsystem.service.DocumentBuilder;
import by.bsuir.spp.jewelryrentsystem.service.exception.InternalServerErrorException;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.EmployeeDocumentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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
    private final EmployeeDocumentBuilder employeeDocumentBuilder;

    @Autowired
    public DocumentsController(@Qualifier("client_documents") DocumentBuilder clientDocumentBuilder,
                               @Qualifier("employee_documents") EmployeeDocumentBuilder employeeDocumentBuilder) {
        this.clientDocumentBuilder = clientDocumentBuilder;
        this.employeeDocumentBuilder = employeeDocumentBuilder;
    }

    @GetMapping(value = "/client-excel")
    public void getClientExcel(HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setContentType(EXCEL_CONTENT_TYPE);
            clientDocumentBuilder.generateExcelDocument(httpServletResponse.getOutputStream());
        } catch (IOException e) {
            throw new InternalServerErrorException(ERROR_MESSAGE);
        }
    }

    @GetMapping(value = "/client-csv")
    public void getClientCsv(HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setContentType(CSV_CONTENT_TYPE);
            clientDocumentBuilder.generateCsvDocument(httpServletResponse.getOutputStream());
        } catch (IOException e) {
            throw new InternalServerErrorException(ERROR_MESSAGE);
        }
    }

    @GetMapping(value = "/client-pdf")
    public void getClientPdf(HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setContentType(PDF_CONTENT_TYPE);
            clientDocumentBuilder.generatePdfDocument(httpServletResponse.getOutputStream());
        } catch (IOException e) {
            throw new InternalServerErrorException(ERROR_MESSAGE);
        }
    }

    @GetMapping(value = "/employee-excel")
    public void getEmployeeExcel(HttpServletResponse httpServletResponse,
                               @RequestParam(value = "branch-id", defaultValue = "0") Long branchId) {
        try {
            httpServletResponse.setContentType(EXCEL_CONTENT_TYPE);
            employeeDocumentBuilder.generateExcelDocument(httpServletResponse.getOutputStream(), branchId);
        } catch (IOException e) {
            throw new InternalServerErrorException(ERROR_MESSAGE);
        }
    }

    @GetMapping(value = "/employee-csv")
    public void getEmployeeCsv(HttpServletResponse httpServletResponse,
                               @RequestParam(value = "branch-id", defaultValue = "0") Long branchId) {
        try {
            httpServletResponse.setContentType(CSV_CONTENT_TYPE);
            employeeDocumentBuilder.generateCsvDocument(httpServletResponse.getOutputStream(), branchId);
        } catch (IOException e) {
            throw new InternalServerErrorException(ERROR_MESSAGE);
        }
    }

    @GetMapping(value = "/employee-pdf")
    public void getEmployeePdf(HttpServletResponse httpServletResponse,
            @RequestParam(value = "branch-id", defaultValue = "0") Long branchId) {
        try {
            httpServletResponse.setContentType(PDF_CONTENT_TYPE);
            employeeDocumentBuilder.generatePdfDocument(httpServletResponse.getOutputStream(), branchId);
        } catch (IOException e) {
            throw new InternalServerErrorException(ERROR_MESSAGE);
        }
    }
}
