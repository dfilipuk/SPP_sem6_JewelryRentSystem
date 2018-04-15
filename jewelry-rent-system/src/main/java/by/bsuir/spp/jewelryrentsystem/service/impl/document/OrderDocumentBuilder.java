package by.bsuir.spp.jewelryrentsystem.service.impl.document;

import by.bsuir.spp.jewelryrentsystem.model.Order;
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

@Service("order_documents")
public class OrderDocumentBuilder extends AbstractDocumentBuilder {
    public final String DATE_DEFAULT_VALUE = "";

    private final String INPUT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final OrderRepository orderRepository;

    @Autowired
    public OrderDocumentBuilder(OrderRepository orderRepository, ExcelBuilder excelBuilder,
                                CsvBuilder csvBuilder, PdfBuilder pdfBuilder) {
        super(excelBuilder, csvBuilder, pdfBuilder);
        this.orderRepository = orderRepository;
    }

    @Override
    protected String getDocumentName() {
        return "Orders";
    }

    @Override
    protected String[] getDocumentHeader() {
        return new String[] { "Rent date", "Cost", "Days rent", "Jewelry name", "Seller surname", "Client surname", "Client passport number" };
    }

    @Override
    protected int getExcelColumnWidth() {
        return 15;
    }

    @Override
    protected boolean isPdfPortrait() {
        return false;
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

        String startDateStr = (String)args[0];
        String endDateStr = (String)args[1];
        List<Order> orders;

        if (startDateStr.equals(DATE_DEFAULT_VALUE) && endDateStr.equals(DATE_DEFAULT_VALUE)) {
            orders = orderRepository.findAll();
        } else {
            try {
                startDate = formatter.parse(String.format("%s 00:00:00", startDateStr));
                endDate = formatter.parse(String.format("%s 23:59:59", endDateStr));
            } catch (ParseException e) {
                throw new UnprocessableEntityException(ARGS_ERROR_MESSAGE);
            }

            if (!startDate.before(endDate)) {
                throw new UnprocessableEntityException(ARGS_ERROR_MESSAGE);
            }

            orders = orderRepository.findByRentDateBetween(startDate, endDate);
        }

        for (Order order : orders) {
            List<String> item = new ArrayList<>();
            item.add(formatter.format(order.getRentDate()));
            item.add(String.format("%.2f", order.getCost()));
            item.add(String.format("%d", order.getDaysRent()));
            item.add(order.getJewelry().getName());
            item.add(order.getEmployee().getSurname());
            item.add(order.getClient().getSurname());
            item.add(order.getClient().getPassportNumber());
            data.add(item);
        }

        return data;
    }
}
