package useCases;

import dataBundles.DoctorData;
import dataBundles.TimeBlockData;
import database.DataMapperGateway;
import database.Database;
import entities.Doctor;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        Doctor doctor = new Doctor(username, password);
        // Commented code is pending implementation in phase 2
//        database.getClinic().getClinicHours().forEach(doctor::addAvailability);
        if (doctorDatabase.add(doctor) != null) {
            doctor.setContactInfoId(newContactInDatabase());
            return new DoctorData(doctor);
        }
        return null;
    }

    /**
     * Creates and returns a data of the doctor associated with the login details passed in.
     * @param userName String - the username of the doctor that wants to sign in.
     * @return PatientData - the data of the doctor that wants to sign in.
     */
    @Override
    public DoctorData signIn(String userName, String password) {
        return toDoctorData(signInHelper(userName, password));
    }

    /**
     * Creates and returns a data of the doctor associated with the username passed in.
     * @param username String - username of the specified user.
     * @return PatientData - data of the doctor associated with the username passed in.
     */
    @Override
    public DoctorData getUserData(String username) {
        return getUserHelper(username).map(DoctorData::new)
                .orElse(null);
    }

    private DoctorData toDoctorData(Doctor doctor) {
        if (doctor == null) {
            return null;
        } else {
            return new DoctorData(doctor);
        }
    }

    /**
     * Returns all absences associated with a doctor. Assumes doctorData is associated with a doctor in the database.
     * @param doctorData DoctorData - data bundle associated with the doctor.
     * @return ArrayList<TimeBlockData> - arraylist of doctor absences.
     */
    public ArrayList<TimeBlockData> getAbsence(DoctorData doctorData){
        Doctor doctor = doctorDatabase.get(doctorData.getId());
        return doctor.getAbsence().stream().map(TimeBlockData::new).collect(Collectors.toCollection(ArrayList::new));
    }

}

