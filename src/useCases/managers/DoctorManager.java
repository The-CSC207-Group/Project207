package useCases.managers;

import dataBundles.ContactDataBundle;
import dataBundles.DoctorDataBundle;
import database.DataMapperGateway;
import entities.Contact;
import entities.Doctor;

public class DoctorManager{

    DataMapperGateway<Doctor> doctorDatabase;
    GenericUserManagerMethods<Doctor> doctorMethods;
    DataMapperGateway<Contact> contactDatabase;


    public DoctorManager(DataMapperGateway<Doctor> doctorDatabase, DataMapperGateway<Contact> contactDatabase){
        this.doctorDatabase = doctorDatabase;
        this.doctorMethods = new GenericUserManagerMethods<>(doctorDatabase);
    }

    public DoctorDataBundle createDoctor(String username, String password, ContactDataBundle contactDataBundle){
        Integer contactId = contactDatabase.add(contactDataBundleToContactEntity(contactDataBundle));
        Doctor doctor = new Doctor(username, password, contactId);
        return new DoctorDataBundle(doctor.getId(), doctor);

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

    private Contact contactDataBundleToContactEntity(ContactDataBundle contactDataBundle){
        return new Contact(contactDataBundle.getName(),
                contactDataBundle.getEmail(),
                contactDataBundle.getPhoneNumber(),
                contactDataBundle.getAddress(),
                contactDataBundle.getBirthday(),
                contactDataBundle.getEmergencyContactName(),
                contactDataBundle.getEmergencyContactEmail(),
                contactDataBundle.getEmergencyContactPhoneNumber(),
                contactDataBundle.getEmergencyRelationship());
    }


}

