package com.sa.leanning.CarProject.Entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="roles")
public class Roles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
    @ManyToMany
   private  Set<Permission> permissions;
}
