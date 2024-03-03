package com.example.cabinet;

import com.example.cabinet.entities.Patient;
import com.example.cabinet.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class CabinetApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;

    public void display_patients(List<Patient> patients) {
        for (Patient p: patients) {
            System.out.println(p.toString());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(CabinetApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> bds = new ArrayList<>(List.of("1999-12-31", "1999-01-31", "1999-04-31"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //Adding records
        patientRepository.save(new Patient(1, "Reda ELMARRAKCHY", dateFormat.parse(bds.get(0)),true, 1));
        patientRepository.save(new Patient(2, "Mouad BOUFENZI", dateFormat.parse(bds.get(1)),true, 2));
        patientRepository.save(new Patient(3, "Houda MAMOUNI", dateFormat.parse(bds.get(2)),true, 2));

        System.out.println("Getting all the records : -----------------------------------------------");
        //Getting all the records
        List<Patient> patients = patientRepository.findAll();
        display_patients(patients);

        System.out.println("\nConsulting one record : -----------------------------------------------");
        //Consulting one record
        long id = 1;
        Patient theOne = new Patient();
        if (patientRepository.findById(id).isPresent()) {
            theOne = patientRepository.findById(id).get();

            System.out.println("The one : " + theOne.toString());
        } else {
            System.err.println("No record with that ID : " + id);
        }

        System.out.println("\nSearching a record : -----------------------------------------------");
        List<Patient> res = patientRepository.searchByScore(2);
        display_patients(res);

        System.out.println("\nUpdating patient : -----------------------------------------------");
        Date correctedBD = dateFormat.parse("1998-12-31");
        patientRepository.updateByBirthday(theOne.getId(), correctedBD);
        theOne = patientRepository.findById(id).get();
        System.out.println(theOne.toString());

        System.out.println("\nDeleting a patient : -----------------------------------------------");
        long idToDelete = 2;
        patientRepository.deleteById(idToDelete);
        patients = patientRepository.findAll();
        display_patients(patients);
    }
}
