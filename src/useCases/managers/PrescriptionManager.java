package useCases.managers;

import dataBundles.PrescriptionDataBundle;
import database.DataMapperGateway;
import entities.Prescription;
import useCases.query.NoteQueryConditions.IsUsersNote;
import useCases.query.Query;
import useCases.query.QueryCondition;
import useCases.query.prescriptionQueryConditions.IsActivePrescription;
import utilities.DatabaseQueryUtility;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrescriptionManager {
    DataMapperGateway<Prescription> prescriptionsDatabase;

    DatabaseQueryUtility databaseUtilities = new DatabaseQueryUtility();
    public PrescriptionManager(DataMapperGateway<Prescription> prescriptionsDatabase){
        this.prescriptionsDatabase = prescriptionsDatabase;
    }
    private Stream<Prescription> getAllPerscriptions(){
        return prescriptionsDatabase.getAllIds().stream()
                .map(x -> prescriptionsDatabase.get(x));
    }

    public ArrayList<PrescriptionDataBundle> getPatientActivePrescriptionDataByUserId(Integer userId) {
         Stream<PrescriptionDataBundle> dataBundleStream = databaseUtilities.getAllItems(prescriptionsDatabase).
                filter(x -> !isExpiredPrescription(x)).
                filter(x -> isPatientsPrescription(x, userId)).
                map(x -> new PrescriptionDataBundle(x.getId(), x));
         return databaseUtilities.toArrayList(dataBundleStream);
    }
    public ArrayList<PrescriptionDataBundle> getPatientAllPrescriptionDataByUserId(Integer userId) {
        Stream<PrescriptionDataBundle> dataBundleStream = databaseUtilities.getAllItems(prescriptionsDatabase).
                filter(x -> isPatientsPrescription(x, userId)).
                map(x -> new PrescriptionDataBundle(x.getId(), x));
        return databaseUtilities.toArrayList(dataBundleStream);
    }



    public PrescriptionDataBundle createPrescription(ZonedDateTime dateNoted, String header, String body, int patientID, int doctorID,
                                   ZonedDateTime expiryDate){
        Prescription prescription = new Prescription(dateNoted, header, body, patientID, doctorID, expiryDate);
        prescriptionsDatabase.add(prescription);
        return new PrescriptionDataBundle(prescription.getId(), prescription);
    }
    public void removePrescription(Integer prescriptionId){
        prescriptionsDatabase.remove(prescriptionId);
    }
    private boolean isExpiredPrescription(Prescription prescription){
        return prescription.getExpiryDate().toLocalDateTime().isBefore(LocalDateTime.now());
    }
    private boolean isPatientsPrescription(Prescription prescription, Integer userId){
        return prescription.getPatientID() == userId;
    }

}
