package by.bsuir.spp.jewelryrentsystem.service.impl.document;

import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.model.Order;
import by.bsuir.spp.jewelryrentsystem.repository.BranchRepository;
import by.bsuir.spp.jewelryrentsystem.repository.OrderRepository;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.csv.CsvBuilder;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.excel.ExcelBuilder;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.pdf.PdfBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("profit_documents")
public class ProfitDocumentBuilder extends AbstractDocumentBuilder {
    private final String INPUT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final String OUTPUT_DATE_FORMAT = "yyyy-MM-dd";

    private final OrderRepository orderRepository;
    private final BranchRepository branchRepository;

    @Autowired
    public ProfitDocumentBuilder(OrderRepository orderRepository, BranchRepository branchRepository,
                                 ExcelBuilder excelBuilder, CsvBuilder csvBuilder, PdfBuilder pdfBuilder) {
        super(excelBuilder, csvBuilder, pdfBuilder);
        this.orderRepository = orderRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    protected String getDocumentName() {
        return "Profit";
    }

    @Override
    protected String[] getDocumentHeader() {
        return new String[] { "Period start", "Period end", "Branch address", "Branch telephone", "Profit" };
    }

    @Override
    protected int getExcelColumnWidth() {
        return 15;
    }

    @Override
    protected boolean isPdfPortrait() {
        return true;
    }

    @Override
    protected List<List<String>> getDocumentData(Object[] args) {
        List<List<String>> data = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat(INPUT_DATE_FORMAT);
        Date startDate;
        Date endDate;

        if (args.length < 2) {
            throw new UnprocessableEntityException(ARGS_ERROR_MESSAGE);
        }

        if (!(args[0] instanceof String) || !(args[1] instanceof String)) {
            throw new UnprocessableEntityException(ARGS_ERROR_MESSAGE);
        }

        try {
            startDate = formatter.parse(String.format("%s 00:00:00", (String)args[0]));
            endDate = formatter.parse(String.format("%s 23:59:59", (String)args[1]));
        } catch (ParseException e) {
            throw new UnprocessableEntityException(ARGS_ERROR_MESSAGE);
        }

        if (!startDate.before(endDate)) {
            throw new UnprocessableEntityException(ARGS_ERROR_MESSAGE);
        }

        List<Branch> branches = branchRepository.findAll();

        if (branches.size() > 0) {
            Map<Long, Double> profit = new HashMap<>();

            for (Branch branch : branches) {
                profit.put(branch.getId(), 0D);
            }

            List<Order> orders = orderRepository.findByRentDateBetween(startDate, endDate);

            for (Order order : orders) {
                Double value = profit.get(order.getEmployee().getBranch().getId());

                if (value != null) {
                    Double newValue = value + order.getCost();
                    profit.replace(order.getEmployee().getBranch().getId(), newValue);
                }
            }

            for (Branch branch : branches) {
                List<String> item = new ArrayList<>();
                item.add(formatter.format(startDate));
                item.add(formatter.format(endDate));
                item.add(branch.getAddress());
                item.add(branch.getTelephone());
                item.add(String.format("%.2f", profit.get(branch.getId())));
                data.add(item);
            }
        }

        return data;
    }
}
