package ru.gb.timesheet.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Employee;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.EmployeeService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.findById(id)
                .map(value -> ResponseEntity
                .status(HttpStatus.OK)
                .body(value)).orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getTimesheetsByEmployeeId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(employeeService.getTimesheets(id));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll());
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        employee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
