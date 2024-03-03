package com.example.cabinet;

import com.example.cabinet.entities.Appointment;
import com.example.cabinet.entities.Doctor;
import com.example.cabinet.entities.Patient;
import com.example.cabinet.repository.AppointmentRepository;
import com.example.cabinet.repository.DoctorRepository;
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
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public void display_patients(List<Patient> patients) {
        for (Patient p: patients) {
            System.out.println("Patient : "
                    + "\n\tName : "+p.getName() +
                    "\n\tBirthday : "+p.getBirthday()
            );
            if (!p.getAppointments().isEmpty()) {
                System.out.println("\tAppointments : ");
                for (Appointment a : p.getAppointments()) {
                    System.out.println("\t\tWith Dr." + a.getDoctor().getName());
                }
            }
        }
    }

    public void display_doctors(List<Doctor> doctors) {
        for (Doctor d: doctors) {
            System.out.println("Doctor : "
                    + "\n\tName : "+d.getName() +
                    "\n\tSpeciality : "+d.getSpeciality()
            );
            if (!d.getAppointments().isEmpty()) {
                System.out.println("\tAppointments : ");
                for (Appointment a : d.getAppointments()) {
                    System.out.println("\t\tWith Mr./Mrs." + a.getPatient().getName());
                }
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(CabinetApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> bds = new ArrayList<>(List.of("1999-12-31", "1999-01-31", "1999-04-31"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long id = 1;

        //Adding records -- patients
        patientRepository.save(new Patient(1, "Reda ELMARRAKCHY", dateFormat.parse(bds.get(0)),true, 1, null));
        patientRepository.save(new Patient(2, "Mouad BOUFENZI", dateFormat.parse(bds.get(1)),true, 2, null));
        patientRepository.save(new Patient(3, "Houda MAMOUNI", dateFormat.parse(bds.get(2)),true, 2, null));

        //Adding records -- doctors
        doctorRepository.save(new Doctor(1, "Khalil TAZI", "Heart", null));
        doctorRepository.save(new Doctor(2, "Nabila BERRADA", "Head", null));
        doctorRepository.save(new Doctor(3, "Soufiane TALBI", "PSY", null));

        //Adding appointment
        long doctorId = 1;
        long patientId = 1;

        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (doctor != null && patient != null) {
            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointmentRepository.save(appointment);
        } else {
            System.err.println("Doctor or patient not found with the given IDs.");
        }



        System.out.println("Getting all the records : -----------------------------------------------");
        //Getting all the records
        List<Patient> patients = patientRepository.findAll();
        List<Doctor> doctors = doctorRepository.findAll();
        display_patients(patients);
        display_doctors(doctors);

        System.out.println("\nConsulting one record : -----------------------------------------------");
        //Consulting one record
        Patient theOne = new Patient();
        if (patientRepository.findById(id).isPresent()) {
            theOne = patientRepository.findById(id).get();

            System.out.println("Doctor : "
                    + "\n\tName : "+theOne.getName() +
                    "\n\tBirthday : "+theOne.getBirthday()
            );
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
        System.out.println("Doctor : "
                + "\n\tName : "+theOne.getName() +
                "\n\tBirthday : "+theOne.getBirthday()
        );

        System.out.println("\nDeleting a patient : -----------------------------------------------");
        long idToDelete = 2;
        patientRepository.deleteById(idToDelete);
        patients = patientRepository.findAll();
        display_patients(patients);
    }
}
