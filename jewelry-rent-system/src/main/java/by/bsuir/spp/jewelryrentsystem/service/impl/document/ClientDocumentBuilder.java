package by.bsuir.spp.jewelryrentsystem.service.impl.document;

import by.bsuir.spp.jewelryrentsystem.model.Client;
import by.bsuir.spp.jewelryrentsystem.repository.ClientRepository;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.csv.CsvBuilder;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.excel.ExcelBuilder;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.pdf.PdfBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("client_documents")
public class ClientDocumentBuilder extends AbstractDocumentBuilder {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientDocumentBuilder(ClientRepository clientRepository, ExcelBuilder excelBuilder,
                                 CsvBuilder csvBuilder, PdfBuilder pdfBuilder) {
        super(excelBuilder, csvBuilder, pdfBuilder);
        this.clientRepository = clientRepository;
    }

    @Override
    protected String getDocumentName() {
        return "Clients";
    }

    @Override
    protected String[] getDocumentHeader() {
        return new String[] { "Name", "Surname", "Second name", "Passport number", "Address", "Telephone" };
    }

    @Override
    protected int getExcelColumnWidth() {
        return 13;
    }

    @Override
    protected boolean isPdfPortrait() {
        return true;
    }

    @Override
    protected List<List<String>> getDocumentData(Object[] args) {
        List<Client> clients = clientRepository.findAll();
        List<List<String>> data = new ArrayList<>();

        for(Client client : clients) {
            List<String> item = new ArrayList<>();
            item.add(client.getName());
            item.add(client.getSurname());
            item.add(client.getSecondName());
            item.add(client.getPassportNumber());
            item.add(client.getAddress());
            item.add(client.getTelephone());
            data.add(item);
        }

        return data;
    }
}
