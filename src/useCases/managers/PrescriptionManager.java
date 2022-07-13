package useCases.managers;

import dataBundles.PrescriptionDataBundle;
import database.DataMapperGateway;
import entities.Prescription;
import useCases.query.NoteQueryConditions.IsUsersNote;
import useCases.query.Query;
import useCases.query.QueryCondition;
import useCases.query.prescriptionQueryConditions.IsActivePrescription;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class PrescriptionManager {
    DataMapperGateway<Prescription> prescriptionsDatabase;
    public PrescriptionManager(DataMapperGateway<Prescription> prescriptionsDatabase){
        this.prescriptionsDatabase = prescriptionsDatabase;
    }

    public ArrayList<PrescriptionDataBundle> getPatientActivePrescriptionDataByUserId(Integer userId) {
        return prescriptionsDatabase.getAllIds().stream().
                filter(x -> !isExpiredPrescription(prescriptionsDatabase.get(x))).
                filter(x -> isPatientsPrescription(prescriptionsDatabase.get(x), userId)).
                map(x -> new PrescriptionDataBundle(x, prescriptionsDatabase.get(x))).
                collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<PrescriptionDataBundle> getPatientAllPrescriptionDataByUserId(Integer userId) {
        return prescriptionsDatabase.getAllIds().stream().
                filter(x -> isPatientsPrescription(prescriptionsDatabase.get(x), userId)).
                map(x -> new PrescriptionDataBundle(x, prescriptionsDatabase.get(x))).
                collect(Collectors.toCollection(ArrayList::new));
    }



    public Integer createPrescription(ZonedDateTime dateNoted, String header, String body, int patientID, int doctorID,
                                   ZonedDateTime expiryDate){
        Prescription prescription = new Prescription(dateNoted, header, body, patientID, doctorID, expiryDate);
        return prescriptionsDatabase.add(prescription);
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
