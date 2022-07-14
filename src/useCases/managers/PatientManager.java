package useCases.managers;

import dataBundles.ContactDataBundle;
import dataBundles.PatientDataBundle;
import database.DataMapperGateway;
import database.*;
import entities.Contact;
import entities.Patient;
import entities.User;

public class PatientManager{
    DataMapperGateway<Patient> patientDatabase;
    GenericUserManagerMethods<Patient> patientMethods;
    PatientManager patientManager;
    DataMapperGateway<Contact> contactDatabase;

    public PatientManager(DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Contact> contactDatabase){
        this.patientDatabase = patientDatabase;
        this.patientMethods = new GenericUserManagerMethods<>(patientDatabase);
        this.contactDatabase = contactDatabase;
    }

    public PatientDataBundle createPatient(String username, String password, ContactDataBundle contactDataBundle,
                                 String healthNumber) {
        Integer contactId = contactDatabase.add(contactDataBundleToContactEntity(contactDataBundle));
        Patient patient = new Patient(username, password, contactId, healthNumber);
        return new PatientDataBundle(patient.getId(), patient);
    }
    public void changeUserPassword(Integer userId, String newPassword){
        patientMethods.changePassword(userId, newPassword);
    }
    public void deletePatient(Integer userId){
        patientMethods.deleteUser(userId);
    }

    public Patient getPatient(Integer userId){
        return patientMethods.getUser(userId);
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
