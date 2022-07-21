package useCases.managers;

import dataBundles.PatientData;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.Patient;

/**
 * Use case class for handling operations and data of patient users.
 */
public class PatientManager extends UserManager<Patient> {

    DataMapperGateway<Patient> patientDatabase;
    DataMapperGateway<Contact> contactDatabase;

    /**
     * Initializes the patient manager.
     * @param database Database - collection of all entity databases in the program.
     */
    public PatientManager(Database database) {
        super(database.getPatientDatabase(), database);
        this.patientDatabase = database.getPatientDatabase();
        this.contactDatabase = database.getContactDatabase();
    }

    /**
     * Creates a new patient and stores it in the database.
     * @param username String - username of the new patient, cannot exist in the database yet.
     * @param password String password for the user.
     * @return PatientData - data bundle consisting of information for this patient,
     * null if username exists in database.
     */
    public PatientData createPatient(String username, String password) {
        Patient patient = new Patient(username, password, newContactInDatabase());
        if (patientDatabase.add(patient) == null){return null;}
        return new PatientData(patient);
    }

    /**
     * Creates and returns a data bundle of the patient associated with the login details passed in.
     * @param userName String - the username of the patient that wants to sign in.
     * @return PatientData - the data bundle of the patient that wants to sign in.
     */
    @Override
    public PatientData signIn(String userName, String password) {
        return toPatientData(signInHelper(userName, password));

    }

    /**
     * Creates and returns a data bundle of the patient associated with the username passed in.
     * @param username String - username of the specified user.
     * @return PatientData - data bundle of the patient associated with the username passed in.
     */
    @Override
    public PatientData getUserData(String username) {
        return getUserHelper(username).map(PatientData::new).orElse(null);
    }

    private PatientData toPatientData(Patient patient) {
        if (patient == null) {
            return null;
        } else {
            return new PatientData(patient);
        }
    }

}
