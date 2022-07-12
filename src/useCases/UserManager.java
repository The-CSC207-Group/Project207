package useCases;

import database.DataMapperGateway;
import entities.*;

import java.util.HashSet;


public class UserManager {

    DataMapperGateway<Patient> patientDatabase;
    DataMapperGateway<Doctor> doctorDatabase;
    DataMapperGateway<Secretary> secretaryDatabase;
    DataMapperGateway<Admin> adminDatabase;

    public boolean createPatient(String username, String password, int contactInfo, String healthNumber){
        Patient patient = new Patient(username, password, contactInfo, healthNumber);
        patientDatabase.add(patient);
        HashSet<Integer> all_ids  = patientDatabase.getAllIds();
        return all_ids.contains(patient.getId());
    }

    public boolean createDoctor(String username, String password, int contactInfo){
        Doctor doctor = new Doctor(username, password, contactInfo);
        doctorDatabase.add(doctor);
        HashSet<Integer> all_ids = doctorDatabase.getAllIds();
        return all_ids.contains(doctor.getId());
    }

    public boolean createSecretary(String username, String password, int contactInfo){
        Secretary secretary = new Secretary(username, password, contactInfo);
        secretaryDatabase.add(secretary);
        HashSet<Integer> all_ids = secretaryDatabase.getAllIds();
        return all_ids.contains(secretary.getId());
    }

    public boolean createAdmin(String username, String password, int contactInfo){
        Admin admin = new Admin(username, password, contactInfo);
        adminDatabase.add(admin);
        HashSet<Integer> all_ids = adminDatabase.getAllIds();
        return all_ids.contains(admin.getId());
    }
}
