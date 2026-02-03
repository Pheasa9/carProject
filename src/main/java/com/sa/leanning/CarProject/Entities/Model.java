package com.sa.leanning.CarProject.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name= "models")
public class Model {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
	private Long id;
    
   
	private String name;
	@JoinColumn(name= "brandId")
	@ManyToOne
	private Brand brand;
	
}
