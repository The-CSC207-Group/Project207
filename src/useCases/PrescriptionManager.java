package useCases;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.PrescriptionData;
import database.DataMapperGateway;
import database.Database;
import entities.Prescription;
import utilities.DatabaseQueryUtility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Use case class meant for handling prescriptions.
 */
public class PrescriptionManager {

    private final DataMapperGateway<Prescription> prescriptionsDatabase;
    private final DatabaseQueryUtility databaseUtilities = new DatabaseQueryUtility();

    /***
     * Initializes Prescription Manager with the prescription database.
     * @param database The collection of databases.
     */
    public PrescriptionManager(Database database) {
        this.prescriptionsDatabase = database.getPrescriptionDatabase();
    }

    /**
     * Get all PrescriptionData associated with a Patient.
     *
     * @param patient PatientData - data associated with a patient in the database.
     * @return Arraylist of all patient's PrescriptionData, whether the prescription is active or not.
     */
    public ArrayList<PrescriptionData> getAllPrescriptions(PatientData patient) {
        return databaseUtilities.toArrayList(
                getAll(patient)
                        .map(PrescriptionData::new)
        );
    }

    /**
     * Get all PrescriptionData associated with a Patient.
     *
     * @param patientData PatientData - data associated with a patient in the database.
     * @return ArrayList of PrescriptionData - list of user's prescriptions.
     */
    public ArrayList<PrescriptionData> getAllActivePrescriptions(PatientData patientData) {
        return databaseUtilities.toArrayList(
                getAll(patientData)
                        .filter(p -> !isExpiredPrescription(p))
                        .map(PrescriptionData::new)
        );
    }

    /**
     * Adds a prescription to the prescription database given the info provided. Assumes all info given is valid and
     * exists in their associated database if applicable.
     *
     * @param header      String - header of the prescription, info such as name of prescription.
     * @param body        String - body of the prescription, all notes relating to the prescription.
     * @param patientData PatientData - data pertaining to a specific patient in the database.
     * @param doctorData  DoctorData - data pertaining to a specific doctor in the database.
     * @param expiryDate  LocalDate - date the prescription expires.
     * @return PrescriptionData - prescription data that stores the information passed in.
     */
    public PrescriptionData createPrescription(String header, String body, PatientData patientData,
                                               DoctorData doctorData, LocalDate expiryDate) {
        Prescription prescription = new Prescription(header, body, patientData.getId(), doctorData.getId(), expiryDate);
        prescriptionsDatabase.add(prescription);
        return new PrescriptionData(prescription);
    }

    /**
     * Removes prescription from the prescription database. If it does not exist, nothing happens.
     *
     * @param prescriptionData PrescriptionData - data pertaining to a prescription in the prescription database.
     */
    public void removePrescription(PrescriptionData prescriptionData) {
        prescriptionsDatabase.remove(prescriptionData.getPrescriptionId());
    }

    private boolean isExpiredPrescription(Prescription prescription) {
        return prescription.getExpiryDate().isBefore(LocalDate.now());
    }

    private Stream<Prescription> getAll(PatientData patient) {
        return prescriptionsDatabase.stream()
                .filter(p -> p.getPatientId().equals(patient.getId()));
    }

}
