package useCases.managers;

import database.DataMapperGateway;
import entities.Doctor;

public class DoctorManager{

    DataMapperGateway<Doctor> doctorDatabase;
    GenericUserManagerMethods<Doctor> doctorMethods;


    public DoctorManager(DataMapperGateway<Doctor> doctorDatabase){
        this.doctorDatabase = doctorDatabase;
        this.doctorMethods = new GenericUserManagerMethods<>(doctorDatabase);
    }

    public Integer createDoctor(String username, String password, int contactInfo){
        Doctor doctor = new Doctor(username, password, contactInfo);
        return doctorDatabase.add(doctor);

    }

    public void changeUserPassword(Integer IDUser, String newPassword){
        doctorMethods.changePassword(IDUser, newPassword);
    }
    public void deleteDoctor(Integer idUser){
        doctorMethods.deleteUser(idUser);
    }
    public Doctor getDoctor(Integer idUser){
        return doctorMethods.getUser(idUser);
    }

}
