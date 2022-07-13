package useCases.managers;

import database.DataMapperGateway;
import entities.Admin;
import entities.Doctor;

import javax.xml.crypto.Data;

public class DoctorManager {

    DataMapperGateway<Doctor> doctorDatabase;
    GenericUserManagerUtilities<Doctor> doctorUtilities;


    public DoctorManager(DataMapperGateway<Doctor> doctorDatabase){
        this.doctorDatabase = doctorDatabase;
        this.doctorUtilities = new GenericUserManagerUtilities<>(doctorDatabase);
    }

    public boolean createDoctor(String username, String password, int contactInfo){
        Doctor doctor = new Doctor(username, password, contactInfo);
        Integer user_id = doctorDatabase.add(doctor);
        return user_id != null;
    }

    public void changeUserPassword(Integer IDUser, String newPassword){
        doctorUtilities.changePassword(IDUser, newPassword);
    }
}
