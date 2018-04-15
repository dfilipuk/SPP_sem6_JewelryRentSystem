package by.bsuir.spp.jewelryrentsystem.service.impl.document;

import by.bsuir.spp.jewelryrentsystem.model.Jewelry;
import by.bsuir.spp.jewelryrentsystem.model.Material;
import by.bsuir.spp.jewelryrentsystem.repository.JewelryRepository;
import by.bsuir.spp.jewelryrentsystem.repository.MaterialRepository;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.csv.CsvBuilder;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.excel.ExcelBuilder;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.pdf.PdfBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("jewelry_documents")
public class JewelryDocumentBuilder extends AbstractDocumentBuilder {
    public final Long MATERIAL_ID_DEFAULT_VALUE = 0L;

    private final JewelryRepository jewelryRepository;
    private final MaterialRepository materialRepository;

    @Autowired
    public JewelryDocumentBuilder(JewelryRepository jewelryRepository, MaterialRepository materialRepository,
                                   ExcelBuilder excelBuilder, CsvBuilder csvBuilder, PdfBuilder pdfBuilder) {
        super(excelBuilder, csvBuilder, pdfBuilder);
        this.jewelryRepository = jewelryRepository;
        this.materialRepository = materialRepository;
    }

    @Override
    protected String getDocumentName() {
        return "Jewelries";
    }

    @Override
    protected String[] getDocumentHeader() {
        return new String[] { "Name", "Producer", "Type", "Weight", "Materials", "Cost per day", "Status", "Days rental", "Branch address" };
    }

    @Override
    protected int getExcelColumnWidth() {
        return 12;
    }

    @Override
    protected boolean isPdfPortrait() {
        return false;
    }

    @Override
    protected List<List<String>> getDocumentData(Object[] args) {
        List<List<String>> data = new ArrayList<>();

        if (args.length < 1) {
            throw new UnprocessableEntityException(ARGS_ERROR_MESSAGE);
        }

        if (!(args[0] instanceof Long)) {
            throw new UnprocessableEntityException(ARGS_ERROR_MESSAGE);
        }

        Long materialId = (Long)args[0];
        List<Jewelry> jewelries;

        if (materialId.longValue() == MATERIAL_ID_DEFAULT_VALUE.longValue()) {
            jewelries = jewelryRepository.findAll();
        } else {
            Material material = materialRepository.findOne(materialId);

            if (material == null) {
                return data;
            }

            jewelries = jewelryRepository.findByMaterials(material);
        }

        for (Jewelry jewelry : jewelries) {
            List<String> item = new ArrayList<>();
            item.add(jewelry.getName());
            item.add(jewelry.getProducer());
            item.add(jewelry.getType());
            item.add(String.format("%.2f", jewelry.getWeight()));
            item.add(getAllMaterialsForJewelry(jewelry));
            item.add(String.format("%.2f", jewelry.getCostPerDay()));
            item.add(jewelry.getStatus());
            item.add(String.format("%d", jewelry.getDaysRental()));
            item.add(jewelry.getBranch().getAddress());
            data.add(item);
        }

        return data;
    }

    private String getAllMaterialsForJewelry(Jewelry jewelry) {
        if (jewelry.getMaterials().isEmpty()) {
            return "None";
        }

        StringBuilder result = new StringBuilder();

        for (Material material : jewelry.getMaterials()) {
            result.append(String.format("%s, ", material.getName()));
        }

        return result.toString();
    }
}
