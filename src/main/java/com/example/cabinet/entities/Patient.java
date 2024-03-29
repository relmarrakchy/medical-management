package com.example.cabinet.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Patient {
    @Id
    private long id;
    private String name;
    private Date birthday;
    private boolean diseased;
    private int score;
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    List<Appointment> appointments;
}
