package useCases;

import dataBundles.DoctorData;
import database.DataMapperGateway;
import database.Database;
import entities.Doctor;

/**
 * Use case class for handling operations and data of doctor users.
 */
public class DoctorManager extends UserManager<Doctor> {

    private final DataMapperGateway<Doctor> doctorDatabase;

    /***
     * Initializes the doctor manager.
     * @param database Database - collection of all entity databases in the program.
     */
    public DoctorManager(Database database) {
        super(database.getDoctorDatabase(), database);
        this.doctorDatabase = database.getDoctorDatabase();
    }

    /***
     * Creates a doctor data associated with the login details passed in and adds the doctor entity to
     * the database.
     * @param username String - username of new account, should not exist in database.
     * @param password String - password of new account.
     * @return DoctorData - associated with the login details passed in if username exists in the database,
     * otherwise returns null.
     */
    public DoctorData createDoctor(String username, String password) {
        if (super.regexCheck(username, password)) {
            Doctor doctor = new Doctor(username, password);
            if (doctorDatabase.add(doctor) != null) {
                doctor.setContactInfoId(newContactInDatabase());
                return new DoctorData(doctor);
            } else {
                return null;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Creates and returns a data of the doctor associated with the login details passed in.
     *
     * @param userName String - the username of the doctor that wants to sign in.
     * @param password String - the password of the doctor that wants to sign in.
     * @return PatientData - the data of the doctor that wants to sign in.
     */
    public DoctorData signIn(String userName, String password) {
        return toDoctorData(signInHelper(userName, password));
    }

    /**
     * Creates and returns a data of the doctor associated with the username passed in.
     *
     * @param username String - username of the specified user.
     * @return PatientData - data of the doctor associated with the username passed in.
     */
    @Override
    public DoctorData getUserData(String username) {
        return getUserHelper(username).map(DoctorData::new)
                .orElse(null);
    }

    /**
     * Return a doctor's data by their id.
     *
     * @param id Integer - id of the doctor whose data will be retrieved.
     * @return DoctorData - data of the doctor associated with the id.
     */
    public DoctorData getUserData(Integer id) {
        Doctor doctor = doctorDatabase.get(id);
        if (doctor == null) {
            return null;
        }
        return new DoctorData(doctor);
    }

    private DoctorData toDoctorData(Doctor doctor) {
        if (doctor == null) {
            return null;
        } else {
            return new DoctorData(doctor);
        }
    }

}

