package by.bsuir.spp.jewelryrentsystem.service.impl;

import by.bsuir.spp.jewelryrentsystem.dto.CreateActionResponseDto;
import by.bsuir.spp.jewelryrentsystem.model.Branch;
import by.bsuir.spp.jewelryrentsystem.model.Employee;
import by.bsuir.spp.jewelryrentsystem.dto.EmployeeDto;
import by.bsuir.spp.jewelryrentsystem.repository.BranchRepository;
import by.bsuir.spp.jewelryrentsystem.repository.EmployeeRepository;
import by.bsuir.spp.jewelryrentsystem.service.EmployeeService;
import by.bsuir.spp.jewelryrentsystem.service.PaginationService;
import by.bsuir.spp.jewelryrentsystem.service.exception.InternalServerErrorException;
import by.bsuir.spp.jewelryrentsystem.service.exception.NotFoundException;
import by.bsuir.spp.jewelryrentsystem.service.exception.UnprocessableEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final String SORT_COLUMN = "surname";

    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final PaginationService paginationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               BranchRepository branchRepository,
                               PaginationService paginationService,
                               PasswordEncoder passwordEncoder
    ) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.paginationService = paginationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees;

        try {
            Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));
            employees = employeeRepository.findAll(sort);
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return convertToDtoList(employees);
    }

    @Override
    public List<EmployeeDto> getAllEmployeesPageable(int page, int size) {
        List<Employee> employees;

        try {
            Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, SORT_COLUMN));
            Pageable pageable = new PageRequest(page, size, sort);
            employees = employeeRepository.findAll(pageable).getContent();
        } catch (Exception e) {
            throw new UnprocessableEntityException(e.getMessage());
        }

        return convertToDtoList(employees);
    }

    @Override
    public long getEmployeesListPagesAmount(long pageSize) {
        return paginationService.getPagesAmount(pageSize, employeeRepository.count());
    }

    @Override
    public EmployeeDto getEmployeeById(long id) {
        Employee employee = employeeRepository.findOne(id);

        if (employee == null) {
            throw new NotFoundException("Employee not found");
        }

        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getSurname(),
                employee.getSecondName(),
                employee.getSalary(),
                employee.getPosition(),
                employee.getLogin(),
                "",
                employee.getRole(),
                employee.getBranch() == null ? 0 : employee.getBranch().getId()
        );
    }

    @Override
    public void deleteEmployeeById(long id) {
        Employee employee = employeeRepository.findOne(id);

        if (employee == null) {
            throw new UnprocessableEntityException("Employee for delete not found");
        }

        if (!employee.getOrders().isEmpty()) {
            throw new UnprocessableEntityException("Unable to delete employee with associated orders");
        }

        try {
            employeeRepository.delete(employee);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public CreateActionResponseDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findFirstByLogin(employeeDto.getLogin());

        if (employee != null) {
            throw new UnprocessableEntityException("Employee with same login already exists");
        }

        employee = new Employee();
        saveEmployeeData(employee, employeeDto);
        return new CreateActionResponseDto(employee.getId());
    }

    @Override
    public void updateEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findOne(employeeDto.getId());

        if (employee == null) {
            throw new UnprocessableEntityException("Employee for update not found");
        }

        Employee existingEmployee = employeeRepository.findFirstByLogin(employeeDto.getLogin());

        if ((existingEmployee != null) && (employee.getId() != existingEmployee.getId())) {
            throw new UnprocessableEntityException("Employee with same login already exists");
        }

        saveEmployeeData(employee, employeeDto);
    }

    private void saveEmployeeData(Employee employee, EmployeeDto employeeDto) {
        Branch branch = branchRepository.findOne(employeeDto.getBranchId());

        if (branch == null) {
            throw new UnprocessableEntityException("Invalid branch id");
        }

        employee.setBranch(branch);
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employee.setSecondName(employeeDto.getSecondName());
        employee.setSalary(employeeDto.getSalary());
        employee.setPosition(employeeDto.getPosition());
        employee.setLogin(employeeDto.getLogin());
        employee.setRole(employeeDto.getRole());

        if (!employeeDto.getPassword().equals("")) {
            employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        }

        try {
            employeeRepository.saveAndFlush(employee);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private List<EmployeeDto> convertToDtoList(List<Employee> employees) {
        List<EmployeeDto> employeesDto = new ArrayList<>();

        for (Employee employee : employees) {
            employeesDto.add(new EmployeeDto(
                    employee.getId(),
                    employee.getName(),
                    employee.getSurname(),
                    employee.getSecondName(),
                    employee.getSalary(),
                    employee.getPosition(),
                    employee.getLogin(),
                    "",
                    employee.getRole(),
                    employee.getBranch().getId()
            ));
        }

        return employeesDto;
    }
}
