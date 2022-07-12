package database;

import entities.*;

import java.io.File;


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

    public Database() {
        this("database");
    }

    public Database(String dataDirectoryName) {
        File userFolder = new File("./" + dataDirectoryName + "/user");
        KeyDelegator userKeyDelegator = new KeyDelegator();
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
    }

    public DataMapperGateway<Patient> getPatientDatabase() {
        return patientDatabase;
    }

    public DataMapperGateway<Doctor> getDoctorDatabase() {
        return doctorDatabase;
    }

    public DataMapperGateway<Secretary> getSecretaryDatabase() {
        return secretaryDatabase;
    }

    public DataMapperGateway<Admin> getAdminDatabase() {
        return adminDatabase;
    }

    public DataMapperGateway<Prescription> getPrescriptionDatabase() {
        return prescriptionDatabase;
    }

    public DataMapperGateway<Report> getReportDatabase() {
        return reportDatabase;
    }

    public DataMapperGateway<Appointment> getAppointmentDatabase() {
        return appointmentDatabase;
    }

    public DataMapperGateway<Log> getLogDatabase() {
        return logDatabase;
    }

    public DataMapperGateway<Contact> getContactDatabase() {
        return contactDatabase;
    }

    public DataMapperGateway<Clinic> getClinicDatabase() {
        return clinicDatabase;
    }
}