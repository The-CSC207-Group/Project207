package useCases.managers;

import dataBundles.PatientData;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.Patient;

import java.util.Optional;

public class PatientManager extends UserManager<Patient> {
    DataMapperGateway<Patient> patientDatabase;
    DataMapperGateway<Contact> contactDatabase;


    /**
     *
     */
    public PatientManager(Database database) {
        super(database.getPatientDatabase(), database);
        this.patientDatabase = database.getPatientDatabase();
        this.contactDatabase = database.getContactDatabase();
    }

    /**
     * Creates a new Patient object and stores it in the database, returns PatientDataBundle.
     *
     * @param username String new username
     * @param password String new password
     * @return PatientDataBundle which includes information of the patient.
     */
    public PatientData createPatient(String username, String password) {
        Patient patient = new Patient(username, password);
        patientDatabase.add(patient);
        return new PatientData(patient);
    }

    private PatientData toPatientData(Patient patient) {
        if (patient == null) {
            return null;
        } else {
            return new PatientData(patient);
        }
    }

    @Override
    public PatientData signIn(String userName, String password) {
        return toPatientData(signInHelper(userName, password));

    }

    @Override
    public PatientData getUserData(String username) {
        return getUserHelper(username).map(x -> new PatientData(x)).orElse(null);
    }
}
