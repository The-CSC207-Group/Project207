package useCases.managers;

import com.sun.javadoc.Doc;
import database.DataMapperGateway;
import entities.Doctor;
import entities.Patient;

public class DoctorManager {

    DataMapperGateway<Doctor> doctorDatabase;
    GenericUserManagerMethods<Doctor> doctorUtilities;


    public DoctorManager(DataMapperGateway<Doctor> doctorDatabase){
        this.doctorDatabase = doctorDatabase;
        this.doctorUtilities = new GenericUserManagerMethods<>(doctorDatabase);
    }

    public Integer createDoctor(String username, String password, int contactInfo){
        Doctor doctor = new Doctor(username, password, contactInfo);
        return doctorDatabase.add(doctor);

    }

    public void changeUserPassword(Integer IDUser, String newPassword){
        doctorUtilities.changePassword(IDUser, newPassword);
    }
    public void deleteDoctor(Integer idUser){
        doctorUtilities.deleteUser(idUser);
    }
    public Doctor getDoctor(Integer idUser){
        return doctorUtilities.getUser(idUser);
    }
}
