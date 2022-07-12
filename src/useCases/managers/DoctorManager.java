package useCases.managers;

import database.DataMapperGateway;
import entities.Doctor;

public class DoctorManager {

    DataMapperGateway<Doctor> doctorDatabase;

    public boolean createDoctor(String username, String password, int contactInfo){
        Doctor doctor = new Doctor(username, password, contactInfo);
        Integer user_id = doctorDatabase.add(doctor);
        return user_id != null;
    }
}
