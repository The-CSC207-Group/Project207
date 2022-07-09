package useCases;

import dataBundles.PrescriptionDataBundle;
import database.DataMapperGateway;
import entities.Prescription;
import useCases.query.NoteQueryConditions.IsUsersNote;
import useCases.query.Query;
import useCases.query.QueryCondition;
import useCases.query.prescriptionQueryConditions.IsActivePrescription;

import java.time.ZonedDateTime;
import java.util.*;

public class PrescriptionManager {
    DataMapperGateway<Prescription> prescriptionsDatabase;
    public PrescriptionManager(DataMapperGateway<Prescription> prescriptionsDatabase){
        this.prescriptionsDatabase = prescriptionsDatabase;
    }

    //    public ArrayList<PrescriptionDataBundle> getPatientDataByUserId(String userId){
//        HashSet<Integer> prescriptionIds = prescriptionsDatabase.getAllIds();
//        ArrayList<PrescriptionDataBundle> res = new ArrayList<>();
//        for (Integer prescriptionId : prescriptionIds) {
//            Prescription currPrescription = prescriptionsDatabase.get(prescriptionId);
//            String prescriptionUserID = currPrescription.getPatient().getUsername();
//
//            if (!isPatientWithId(userId, prescriptionUserID)) {return null;}
//            if (!isActivePrescription(currPrescription)) {return null;}
//
//            res.add(new PrescriptionDataBundle(currPrescription));
//        }
//        return res;
//    }
    public ArrayList<PrescriptionDataBundle> getPatientActivePrescriptionDataByUserId(Integer userId) {
        ArrayList<QueryCondition<Prescription>> conditions = new ArrayList<>();
        conditions.add(new IsActivePrescription<>(true));
        conditions.add(new IsUsersNote<>(userId, true));
        return getPrescriptionDataBundles(conditions);
    }
    public ArrayList<PrescriptionDataBundle> getPatientAllPrescriptionDataByUserId(Integer userId) {
        ArrayList<QueryCondition<Prescription>> conditions = new ArrayList<>();
        conditions.add(new IsUsersNote<>(userId, true));
        conditions.add(new IsActivePrescription<>(false));
        return getPrescriptionDataBundles(conditions);
    }

    private ArrayList<PrescriptionDataBundle> getPrescriptionDataBundles(ArrayList<QueryCondition<Prescription>> conditions) {
        ArrayList<Prescription> queryResults = new Query<Prescription>().returnAllMeetingConditions(
                prescriptionsDatabase, conditions);
        ArrayList<PrescriptionDataBundle> res = new ArrayList<>();
        for (Prescription prescription: queryResults){
            res.add(new PrescriptionDataBundle(prescription));
        }
        return res;
    }


    public void createPrescription(ZonedDateTime dateNoted, String header, String body, int patientID, int doctorID,
                                   ZonedDateTime expiryDate){
        Prescription prescription = new Prescription(dateNoted, header, body, patientID, doctorID, expiryDate);
        prescriptionsDatabase.add(prescription);
    }
    public void removePrescription(Integer prescriptionId){
        prescriptionsDatabase.remove(prescriptionId);
    }

}
