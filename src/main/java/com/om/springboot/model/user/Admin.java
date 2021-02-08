package com.om.springboot.model.user;

import com.om.springboot.model.audit.DateAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "admin")
public class Admin extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String name;

    @NotEmpty
    @Size(min = 1, max = 15)
    private String mobile;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String company;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String email;

    private Long userId;
}
