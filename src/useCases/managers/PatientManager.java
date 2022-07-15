package useCases.managers;

import dataBundles.ContactDataBundle;
import dataBundles.PatientDataBundle;
import database.DataMapperGateway;
import database.*;
import entities.Contact;
import entities.Patient;
import entities.User;

public class PatientManager {
    DataMapperGateway<Patient> patientDatabase;
    GenericUserManagerMethods<Patient> patientMethods;
    PatientManager patientManager;
    DataMapperGateway<Contact> contactDatabase;


    /**
     * @param patientDatabase database storing all the patients.
     * @param contactDatabase database storing all the contacts.
     */
    public PatientManager(DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Contact> contactDatabase) {
        this.patientDatabase = patientDatabase;
        this.patientMethods = new GenericUserManagerMethods<>(patientDatabase);
        this.contactDatabase = contactDatabase;
    }

    /**
     * Creates a new Patient object and stores it in the database, returns PatientDataBundle.
     * @param username          String new username
     * @param password          String new password
     * @param contactDataBundle ContactDataBundle which includes contact info of the user. Cannot be null.
     * @param healthNumber      Int Health number of the patient being created.
     * @return PatientDataBundle which includes information of the patient.
     */
    public PatientDataBundle createPatient(String username, String password, ContactDataBundle contactDataBundle,
                                           String healthNumber) {
        Integer contactId = contactDatabase.add(contactDataBundleToContactEntity(contactDataBundle));
        Patient patient = new Patient(username, password, contactId, healthNumber);
        patientDatabase.add(patient);
        return new PatientDataBundle(patient.getId(), patient);
    }

    /**
     * Changes the patient's password. If the patient does not exist in the database, do nothing.
     * @param userId      Int userId of the user trying to change the password.
     * @param newPassword String new password for the user.
     */
    public void changeUserPassword(Integer userId, String newPassword) {
        patientMethods.changePassword(userId, newPassword);
    }

    /**
     * Deletes the patient from the database. If the patient does not exist in the database, do nothing.
     * @param userId Int userId of the user being deleted.
     */
    public void deletePatient(Integer userId) {
        patientMethods.deleteUser(userId);
    }

    /**
     * Returns Patient Object with the given Id. If the patient does not exist in the database, return null.
     * @param userId Int userId of the user requested.
     * @return Patient object or null if patient doesn't exist in patient database.
     */
    public Patient getPatient(Integer userId) {
        return patientMethods.getUser(userId);
    }

    private Contact contactDataBundleToContactEntity(ContactDataBundle contactDataBundle) {
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
