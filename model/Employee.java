package ru.gb.timesheet.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long employeeId;

    private String firstName;

    private String lastName;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "project_employee",
            joinColumns = @JoinColumn(name = "employee_id",
                    referencedColumnName = "employeeId"),
            inverseJoinColumns = @JoinColumn(name = "project_id",
                    referencedColumnName = "projectId"))
    private Set<Project> projects;
}
