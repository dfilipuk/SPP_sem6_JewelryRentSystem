package by.bsuir.spp.jewelryrentsystem.service.impl.document;

import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.model.Employee;
import by.bsuir.spp.jewelryrentsystem.repository.BranchRepository;
import by.bsuir.spp.jewelryrentsystem.repository.EmployeeRepository;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.csv.CsvBuilder;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.excel.ExcelBuilder;
import by.bsuir.spp.jewelryrentsystem.service.impl.document.pdf.PdfBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("employee_documents")
public class EmployeeDocumentBuilder extends AbstractDocumentBuilder {
    public final Long BRANCH_ID_DEFAULT_VALUE = 0L;

    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;

    @Autowired
    public EmployeeDocumentBuilder(EmployeeRepository employeeRepository, BranchRepository branchRepository,
                                   ExcelBuilder excelBuilder, CsvBuilder csvBuilder, PdfBuilder pdfBuilder) {
        super(excelBuilder, csvBuilder, pdfBuilder);
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    protected String getDocumentName() {
        return "Employees";
    }

    @Override
    protected String[] getDocumentHeader() {
        return new String[] { "Name", "Surname", "Second name", "Position", "Salary", "Branch address" };
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
        List<List<String>> data = new ArrayList<>();

        if (args.length < 1) {
            throw new UnprocessableEntityException(ARGS_ERROR_MESSAGE);
        }

        if (!(args[0] instanceof Long)) {
            throw new UnprocessableEntityException(ARGS_ERROR_MESSAGE);
        }

        Long branchId = (Long)args[0];
        List<Employee> employees;

        if (branchId.longValue() == BRANCH_ID_DEFAULT_VALUE.longValue()) {
            employees = employeeRepository.findAll();
        } else {
            Branch branch = branchRepository.findOne(branchId);

            if (branch == null) {
                return data;
            }

            employees = employeeRepository.findByBranch(branch);
        }

        for (Employee employee : employees) {
            List<String> item = new ArrayList<>();
            item.add(employee.getName());
            item.add(employee.getSurname());
            item.add(employee.getSecondName());
            item.add(employee.getPosition());
            item.add(String.format("%.2f", employee.getSalary()));
            item.add(employee.getBranch().getAddress());
            data.add(item);
        }

        return data;
    }
}
