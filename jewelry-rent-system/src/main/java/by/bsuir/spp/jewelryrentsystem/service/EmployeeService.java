package by.bsuir.spp.jewelryrentsystem.service;

import by.bsuir.spp.jewelryrentsystem.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDto> getAllEmployees();
    List<EmployeeDto> getAllEmployeesPageable(int page, int size);
    long getEmployeesListPagesAmount(long pageSize);
    void deleteEmployeeById(long id);
    EmployeeDto getEmployeeById(long id);
    void createEmployee(EmployeeDto employeeDto);
    void updateEmployee(EmployeeDto employeeDto);
}
