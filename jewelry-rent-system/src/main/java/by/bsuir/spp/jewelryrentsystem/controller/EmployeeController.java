package by.bsuir.spp.jewelryrentsystem.controller;

import by.bsuir.spp.jewelryrentsystem.dto.EmployeeDto;
import by.bsuir.spp.jewelryrentsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/list")
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping(value = "/page-list")
    public List<EmployeeDto> getAllEmployeesPageable(@RequestParam(value = "page") int pageNumber,
                                              @RequestParam(value = "page-size") int pageSize) {
        return employeeService.getAllEmployeesPageable(pageNumber, pageSize);
    }

    @GetMapping(value = "/list-pages-amo")
    public long getPagesAmount(@RequestParam(value = "page-size") long pageSize) {
        return employeeService.getEmployeesListPagesAmount(pageSize);
    }

    @GetMapping(value = "/get")
    public EmployeeDto getEmployee(@RequestParam(value = "id") long id) {
        return employeeService.getEmployeeById(id);
    }

    @PostMapping(value = "/delete")
    public void deleteEmployee(@RequestParam(value = "id") long id) {
        employeeService.deleteEmployeeById(id);
    }

    @PostMapping(value = "/create")
    public void createEmployee(@Validated(EmployeeDto.Create.class) @ModelAttribute EmployeeDto employee) {
        employeeService.createEmployee(employee);
    }

    @PostMapping(value = "/update")
    public void updateEmployee(@Validated(EmployeeDto.Update.class) @ModelAttribute EmployeeDto employee) {
        employeeService.updateEmployee(employee);
    }
}
