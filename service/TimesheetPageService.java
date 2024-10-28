package ru.gb.timesheet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.page.TimesheetPageDto;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimesheetPageService {

    private final TimesheetService timesheetService;
    private final ProjectService projectService;
//  Вместо описания, можно использовать @RequiredArgsConstructor
//    public TimesheetPageController(TimesheetService timesheetService) {
//        this.timesheetService = timesheetService;
//    }

    public List<TimesheetPageDto> findAll() {
        return timesheetService.findAll().stream()
                .map(this::convert)
                .toList();
    }

    public Optional<TimesheetPageDto> findById(long id) {
        return timesheetService.findById(id)
                .map(this::convert);
    }

    public Optional<TimesheetPageDto> findProjectById(long id) {
        return timesheetService.findById(id)
                .map(this::convert);
    }

    private TimesheetPageDto convert(Timesheet timesheet) {
        Project project = projectService.findById(timesheet.getProjectId())
                .orElseThrow();

        TimesheetPageDto timesheetPageDto = new TimesheetPageDto();
        timesheetPageDto.setProjectId(String.valueOf(project.getProjectId()));
        timesheetPageDto.setProjectName(project.getProjectName());
        timesheetPageDto.setId(String.valueOf(timesheet.getId()));
        timesheetPageDto.setMinutes(String.valueOf(timesheet.getMinutes()));
        timesheetPageDto.setCreatedAt(timesheet.getCreatedAt().toString());
        return timesheetPageDto;
    }

}
