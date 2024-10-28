package ru.gb.timesheet.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.ProjectService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectService.findById(id)
                .map(value -> ResponseEntity
                .status(HttpStatus.OK)
                .body(value)).orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getTimesheetsByProjectId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(projectService.getTimesheets(id));
        }
        catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.findAll());
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        project = projectService.createProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
