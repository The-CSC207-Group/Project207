package useCases.managers;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import database.DataMapperGateway;
import database.Database;
import entities.Availability;
import entities.Clinic;
import entities.Contact;
import entities.Doctor;

import java.util.Optional;

public class DoctorManager extends UserManager<Doctor> {

    DataMapperGateway<Doctor> doctorDatabase;
    DataMapperGateway<Contact> contactDatabase;
    Database database;
    PatientManager patientManager;

    /***
     * Initialize the doctor and contact databases.
     * @param database The entire database.
     */
    public DoctorManager(Database database) {
        super(database.getDoctorDatabase(), database);
        this.doctorDatabase = database.getDoctorDatabase();
        this.contactDatabase = database.getContactDatabase();
        this.database = database;
        this.patientManager = new PatientManager(database);
    }

    /***
     * Creates and doctor and adds to the database.
     * @param username username of new account, should not exist in database.
     * @param password password of new account.
     * @return doctor data if sign in successful, if username exists in the database, return null.
     */
    public DoctorData createDoctor(String username, String password) {
        Doctor doctor = new Doctor(username, password, newContactInDatabase());
        database.getClinic().getClinicHours().forEach(doctor::addAvailability);
        doctorDatabase.add(doctor);
        return new DoctorData(doctor);

    }

    private DoctorData toDoctorData(Doctor doctor) {
        if (doctor == null) {
            return null;
        } else {
            return new DoctorData(doctor);
        }
    }

    @Override
    public DoctorData signIn(String userName, String password) {
        return toDoctorData(signInHelper(userName, password));
    }

    @Override
    public DoctorData getUserData(String username) {
        return getUserHelper(username).map(DoctorData::new).orElse(null);
    }
}

