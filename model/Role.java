package ru.gb.timesheet.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long roleId;

    @Column(name = "name")
    private String name;
}
