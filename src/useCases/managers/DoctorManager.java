package useCases.managers;

import database.DataMapperGateway;
import entities.Doctor;

public class DoctorManager {

    DataMapperGateway<Doctor> doctorDatabase;
    GenericUserManagerMethods<Doctor> doctorUtilities;


    public DoctorManager(DataMapperGateway<Doctor> doctorDatabase){
        this.doctorDatabase = doctorDatabase;
        this.doctorUtilities = new GenericUserManagerMethods<>(doctorDatabase);
    }

    public boolean createDoctor(String username, String password, int contactInfo){
        Doctor doctor = new Doctor(username, password, contactInfo);
        Integer user_id = doctorDatabase.add(doctor);
        return user_id != null;
    }

    public void changeUserPassword(Integer IDUser, String newPassword){
        doctorUtilities.changePassword(IDUser, newPassword);
    }
    public void deleteDoctor(Integer idUser){
        doctorUtilities.deleteUser(idUser);
    }
}
