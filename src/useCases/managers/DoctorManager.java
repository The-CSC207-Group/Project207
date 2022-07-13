package useCases.managers;

import database.DataMapperGateway;
import entities.Doctor;

import javax.xml.crypto.Data;

public class DoctorManager {

    DataMapperGateway<Doctor> doctorDatabase;

    public DoctorManager(DataMapperGateway<Doctor> doctorDatabase){
        this.doctorDatabase = doctorDatabase;
    }

    public boolean createDoctor(String username, String password, int contactInfo){
        Doctor doctor = new Doctor(username, password, contactInfo);
        Integer user_id = doctorDatabase.add(doctor);
        return user_id != null;
    }
}
