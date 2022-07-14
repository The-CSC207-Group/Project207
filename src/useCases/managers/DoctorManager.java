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
        doctorDatabase.add(doctor);
        return new DoctorDataBundle(doctor.getId(), doctor);

    }

    public void changeUserPassword(Integer userId, String newPassword){
        doctorMethods.changePassword(userId, newPassword);
    }
    public void deleteDoctor(Integer userId){
        doctorMethods.deleteUser(userId);
    }
    public Doctor getDoctor(Integer userId){
        return doctorMethods.getUser(userId);
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

