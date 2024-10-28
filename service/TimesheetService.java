package ru.gb.timesheet.service;

import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service // то же самое что и Component
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final ProjectRepository projectRepository;

    public TimesheetService(TimesheetRepository repository, ProjectRepository projectRepository) {
        this.timesheetRepository = repository;
        this.projectRepository = projectRepository;
    }

    public Optional<Timesheet> findById(Long id) {
        // select * from timesheets where id = $id
        return timesheetRepository.findById(id);
    }

    public List<Timesheet> findAll() {
        return findAll(null, null);
    }

    public List<Timesheet> findAll(LocalDate createdAtBefore, LocalDate createdAtAfter) {
//        throw new UnsupportedOperationException();
        return timesheetRepository.findAll();
    }

    public Timesheet createTimesheet(Timesheet timesheet) {
        if(Objects.isNull(timesheet.getProjectId())){
            throw new IllegalArgumentException("projectId must not be null");
        }

        if(projectRepository.findById(timesheet.getProjectId()).isEmpty()){
            throw new NoSuchElementException("Project with id " + timesheet.getProjectId() + " does not exists");
        }

        timesheet.setCreatedAt(LocalDate.now());
        return timesheetRepository.save(timesheet);
    }


    public void delete(Long id) {
        timesheetRepository.deleteById(id);
    }
//    public List<Timesheet> filterByDate(LocalDate createdAtBefore, LocalDate createdAtAfter) {
//        return timesheetRepository.filterByDate();
//    }
}