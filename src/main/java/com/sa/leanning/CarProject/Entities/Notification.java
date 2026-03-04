package com.sa.leanning.CarProject.Entities;

import com.sa.leanning.CarProject.config.security.Audit.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notifications")
public class Notification extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name", nullable=false)
    private String firstName;

    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(nullable=false)
    private String email;

    @Column(name="mobile_no")
    private String mobileNo;

    @Column(columnDefinition="TEXT", nullable=false)
    private String message;

    @Column(name="read_status", nullable=false)
    private boolean readStatus = false;
}