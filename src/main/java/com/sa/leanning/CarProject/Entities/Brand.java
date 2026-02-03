package com.sa.leanning.CarProject.Entities;

import com.sa.leanning.CarProject.config.security.Audit.Auditable;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="brands")
public class Brand extends Auditable{
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Id
        private Long id;
        private String name;
       
}
