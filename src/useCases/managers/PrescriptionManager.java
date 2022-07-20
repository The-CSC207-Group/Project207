package useCases.managers;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import dataBundles.PrescriptionData;
import dataBundles.UserData;
import database.DataMapperGateway;
import database.Database;
import entities.Prescription;
import utilities.DatabaseQueryUtility;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

public class PrescriptionManager {
    DataMapperGateway<Prescription> prescriptionsDatabase;

    DatabaseQueryUtility databaseUtilities = new DatabaseQueryUtility();

    /***
     * Initializes Prescription Manager with the prescription database.
     * @param database The entire database.
     */
    public PrescriptionManager(Database database){
        this.prescriptionsDatabase = database.getPrescriptionDatabase();
    }

    private Stream<Prescription> getAll(PatientData patient){
       return prescriptionsDatabase.stream()
                        .filter(p -> p.getPatientId().equals(patient.getId()));
    }
    public ArrayList<PrescriptionData> getAllPrescriptions(PatientData patient){
        return databaseUtilities.toArrayList(
                getAll(patient)
                        .map(PrescriptionData::new)
        );
    }
    public ArrayList<PrescriptionData> getAllActivePrescriptions(PatientData patientData){
        return databaseUtilities.toArrayList(
                getAll(patientData)
                        .filter(p -> !isExpiredPrescription(p))
                        .map(PrescriptionData::new)
        );
    }


    /**
     * Adds a prescription to the prescription database given the info provided. Assumes all info given is valid and
     * exists in their associated database if applicable.
     * @param header header of the prescription, info such as title
     * @param body body of the prescription, all notes relating to the prescription
     * @param patientData id of the patient the prescription was assigned to.
     * @param doctorData id of the doctor who gave out the prescription.
     * @param expiryDate date the prescription expires
     * @return PrescriptionDataBundle object corresponding to the prescription.
     */
    public PrescriptionData createPrescription(String header, String body, PatientData patientData, DoctorData doctorData,
                                               ZonedDateTime expiryDate){
        ZonedDateTime dateNoted = ZonedDateTime.now();
        Prescription prescription = new Prescription(header, body, patientData.getId(), doctorData.getId(), expiryDate);
        prescriptionsDatabase.add(prescription);
        return new PrescriptionData(prescription);
    }

    /**
     * Removes prescription with given id from the prescription database. If it does not exist, nothing happens
     * @param prescriptionData id of the prescription to be removed.
     */
    public void removePrescription(PrescriptionData prescriptionData){
        prescriptionsDatabase.remove(prescriptionData.getPrescriptionId());
    }

    private boolean isExpiredPrescription(Prescription prescription){
        return prescription.getExpiryDate().toLocalDateTime().isBefore(LocalDateTime.now());
    }

}
