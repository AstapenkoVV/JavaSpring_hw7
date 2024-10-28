package ru.gb.timesheet.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Employee;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.TimesheetService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/timesheets")
public class TimesheetController {

    private final TimesheetService service;
    private final ProjectController projectController;
    private final EmployeeController employeeController;

    public TimesheetController(TimesheetService service, ProjectController projectController, EmployeeController employeeController) {
        this.service = service;
        this.projectController = projectController;
        this.employeeController = employeeController;
    }

    @GetMapping("/{id}") // получить все
    public ResponseEntity<Timesheet> getTimesheet(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping // получить все
    public ResponseEntity<List<Timesheet>> getAll(
            @RequestParam(required = false) LocalDate createdAtBefore,
            @RequestParam(required = false) LocalDate createdAtAfter
    ){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(createdAtBefore, createdAtAfter));
    }

//    @GetMapping("/filter")
//    public ResponseEntity<List<Timesheet>> filterByDate(@RequestParam(required = false) LocalDate createdAtBefore,
//                                                        @RequestParam(required = false) LocalDate createdAtAfter) {
//        return ResponseEntity.status(HttpStatus.FOUND).body(service.filterByDate(createdAtBefore, createdAtAfter));
//    }

    @PostMapping // создание нового ресурса
    public ResponseEntity<Timesheet> createTimesheet(@RequestBody Timesheet timesheet, @PathVariable("projectId") Long projectId) {
        if (projectController.getProjectById(projectId).getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            timesheet.setProjectId(projectId);
            timesheet = service.createTimesheet(timesheet);
            return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimesheet(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
