package useCases;

import dataBundles.PatientData;
import database.DataMapperGateway;
import database.Database;
import entities.Patient;

import java.util.regex.Pattern;

/**
 * Use case class for handling operations and data of patient users.
 */
public class PatientManager extends UserManager<Patient> {

    private final DataMapperGateway<Patient> patientDatabase;

    /**
     * Initializes the patient manager.
     * @param database Database - collection of all entity databases in the program.
     */
    public PatientManager(Database database) {
        super(database.getPatientDatabase(), database);
        this.patientDatabase = database.getPatientDatabase();
    }

    /**
     * Creates a new patient and stores it in the database.
     * @param username String - username of the new patient, cannot exist in the database yet.
     * @param password String password for the user.
     * @return PatientData - data consisting of information for this patient,
     * null if username exists in database.
     */
    public PatientData createPatient(String username, String password) {
        Patient patient = new Patient(username, password);
        if (Pattern.matches("^p[a-zA-Z0-9]{5,}$", username) && patientDatabase.add(patient) != null) {
            patient.setContactInfoId(newContactInDatabase());
            return new PatientData(patient);
        }
        return null;
    }

    /**
     * Creates and returns a data of the patient associated with the login details passed in.
     * @param userName String - the username of the patient that wants to sign in.
     * @return PatientData - the data of the patient that wants to sign in.
     */
    @Override
    public PatientData signIn(String userName, String password) {
        return toPatientData(signInHelper(userName, password));

    }

    /**
     * Creates and returns a data of the patient associated with the username passed in.
     * @param username String - username of the specified user.
     * @return PatientData - data of the patient associated with the username passed in.
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
