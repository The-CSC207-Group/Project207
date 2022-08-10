package database;

import entities.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * The Database class is responsible for keeping relations between many DataMapperGateways.
 * It also acts as a facade to each DataMapperGateway interface.
 */
public class Database {

    private final DataMapperGateway<Patient> patientDatabase;
    private final DataMapperGateway<Doctor> doctorDatabase;
    private final DataMapperGateway<Secretary> secretaryDatabase;
    private final DataMapperGateway<Admin> adminDatabase;
    private final DataMapperGateway<Prescription> prescriptionDatabase;
    private final DataMapperGateway<Report> reportDatabase;
    private final DataMapperGateway<Appointment> appointmentDatabase;
    private final DataMapperGateway<Log> logDatabase;
    private final DataMapperGateway<Contact> contactDatabase;
    private final DataMapperGateway<Clinic> clinicDatabase;
    private final List<DataMapperGateway<?>> databaseList;

    /**
     * No args constructor to default the database location to CWD/database
     */
    public Database() {
        this("database");
    }

    /**
     * Constructor used to initialize Database with a give folder name.
     *
     * @param dataDirectoryName Folder name.
     */
    public Database(String dataDirectoryName) {
        File userFolder = new File("./" + dataDirectoryName + "/user");
        KeyDelegator userKeyDelegator = new KeyDelegator("getUsername");
        patientDatabase = new JsonDatabase<>(Patient.class, userKeyDelegator, userFolder);
        doctorDatabase = new JsonDatabase<>(Doctor.class, userKeyDelegator, userFolder);
        secretaryDatabase = new JsonDatabase<>(Secretary.class, userKeyDelegator, userFolder);
        adminDatabase = new JsonDatabase<>(Admin.class, userKeyDelegator, userFolder);

        File noteFolder = new File("./" + dataDirectoryName + "/note");
        KeyDelegator noteKeyDelegator = new KeyDelegator();
        prescriptionDatabase = new JsonDatabase<>(Prescription.class, noteKeyDelegator, noteFolder);
        reportDatabase = new JsonDatabase<>(Report.class, noteKeyDelegator, noteFolder);

        File rootFolder = new File("./" + dataDirectoryName);
        appointmentDatabase = new JsonDatabase<>(Appointment.class, rootFolder);
        logDatabase = new JsonDatabase<>(Log.class, rootFolder);
        contactDatabase = new JsonDatabase<>(Contact.class, rootFolder);
        clinicDatabase = new JsonDatabase<>(Clinic.class, rootFolder);

        databaseList = Arrays.asList(patientDatabase, doctorDatabase, secretaryDatabase, adminDatabase,
                prescriptionDatabase, reportDatabase, appointmentDatabase, logDatabase, contactDatabase, clinicDatabase);
    }

    /**
     * Patient database access.
     *
     * @return a DataMapperGateway of type Patient.
     */
    public DataMapperGateway<Patient> getPatientDatabase() {
        return patientDatabase;
    }

    /**
     * Doctor database access.
     *
     * @return a DataMapperGateway of type Doctor.
     */
    public DataMapperGateway<Doctor> getDoctorDatabase() {
        return doctorDatabase;
    }

    /**
     * Secretary database access.
     *
     * @return a DataMapperGateway of type Secretary.
     */
    public DataMapperGateway<Secretary> getSecretaryDatabase() {
        return secretaryDatabase;
    }

    /**
     * Admin database access.
     *
     * @return a DataMapperGateway of type Admin.
     */
    public DataMapperGateway<Admin> getAdminDatabase() {
        return adminDatabase;
    }

    /**
     * Prescription database access.
     *
     * @return a DataMapperGateway of type Prescription.
     */
    public DataMapperGateway<Prescription> getPrescriptionDatabase() {
        return prescriptionDatabase;
    }

    /**
     * Report database access.
     *
     * @return a DataMapperGateway of type Report.
     */
    public DataMapperGateway<Report> getReportDatabase() {
        return reportDatabase;
    }

    /**
     * Appointment database access.
     *
     * @return a DataMapperGateway of type Appointment.
     */
    public DataMapperGateway<Appointment> getAppointmentDatabase() {
        return appointmentDatabase;
    }

    /**
     * Log database access.
     *
     * @return a DataMapperGateway of type Log.
     */
    public DataMapperGateway<Log> getLogDatabase() {
        return logDatabase;
    }

    /**
     * Contact database access.
     *
     * @return a DataMapperGateway of type Contact.
     */
    public DataMapperGateway<Contact> getContactDatabase() {
        return contactDatabase;
    }

    /**
     * Get the current Clinic
     *
     * @return Clinic, or null if not clinic has been set.
     */
    public Clinic getClinic() {
        Optional<Clinic> clinic = clinicDatabase.stream().findFirst();
        return clinic.orElse(null);
    }

    /**
     * Set the current Clinic
     *
     * @param clinic the Clinic entity to set as the current clinic.
     */
    public void setClinic(Clinic clinic) {
        clinicDatabase.getAllIds().forEach(clinicDatabase::remove);
        clinicDatabase.add(clinic);
    }

    /**
     * Saves all tables/databases.
     */
    public void save() {
        for (DataMapperGateway<?> database : databaseList) {
            database.save();
        }
    }

}
