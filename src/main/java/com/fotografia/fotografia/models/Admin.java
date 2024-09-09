package com.fotografia.fotografia.models;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
 @NoArgsConstructor
@Entity
@Table(name = "admin")
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true, name = "username", nullable = false)
    private String username; 

    @Column(name = "password", nullable = false)
    private String password;

    private String role;
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Gallery> images = new ArrayList<>();
  
    } 
    
  
  
    

