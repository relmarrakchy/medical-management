package com.example.cabinet.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Doctor {
    @Id
    private long id;
    private String name;
    private String speciality;
    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    List<Appointment> appointments;
}
