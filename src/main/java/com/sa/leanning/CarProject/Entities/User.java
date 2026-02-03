package com.sa.leanning.CarProject.Entities;

import java.util.Set;

import com.sa.leanning.CarProject.config.security.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="users")
@Data
public class User {
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Id
	 private Long id;
     private String username;
     private String password;
     private String firstname;
     private String lastname;
    
     private boolean accountNonExpired;
 	 private boolean accountNonLocked; 
 	 private boolean credentialsNonExpired;
 	 private boolean enabled;
 	 @ManyToMany(fetch = FetchType.EAGER)
	 private Set<Roles> roles;
}
