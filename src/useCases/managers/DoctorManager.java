package useCases.managers;

import dataBundles.DoctorData;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.Doctor;

public class DoctorManager extends UserManager<Doctor> {

    DataMapperGateway<Doctor> doctorDatabase;
    DataMapperGateway<Contact> contactDatabase;
    Database database;
    PatientManager patientManager;

    /***
     * Initializes the doctor manager.
     * @param database Database - collection of all entity databases in the program.
     */
    public DoctorManager(Database database) {
        super(database.getDoctorDatabase(), database);
        this.doctorDatabase = database.getDoctorDatabase();
        this.contactDatabase = database.getContactDatabase();
        this.database = database;
        this.patientManager = new PatientManager(database);
    }

    /***
     * Creates a doctor data bundle associated with the login details passed in and adds the doctor entity to
     * the database.
     * @param username String - username of new account, should not exist in database.
     * @param password String - password of new account.
     * @return DoctorData - associated with the login details passed in if username exists in the database,
     * otherwise returns null.
     */
    public DoctorData createDoctor(String username, String password) {
        Doctor doctor = new Doctor(username, password, newContactInDatabase());
        database.getClinic().getClinicHours().forEach(doctor::addAvailability);
        doctorDatabase.add(doctor);
        return new DoctorData(doctor);
    }

    /**
     * Creates and returns a data bundle of the doctor associated with the login details passed in.
     * @param userName String - the username of the doctor that wants to sign in.
     * @return PatientData - the data bundle of the doctor that wants to sign in.
     */
    @Override
    public DoctorData signIn(String userName, String password) {
        return toDoctorData(signInHelper(userName, password));
    }

    /**
     * Creates and returns a data bundle of the doctor associated with the username passed in.
     * @param username String - username of the specified user.
     * @return PatientData - data bundle of the doctor associated with the username passed in.
     */
    @Override
    public DoctorData getUserData(String username) {
        return getUserHelper(username).map(DoctorData::new).orElse(null);
    }

    private DoctorData toDoctorData(Doctor doctor) {
        if (doctor == null) {
            return null;
        } else {
            return new DoctorData(doctor);
        }
    }

}

