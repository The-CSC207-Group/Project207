package useCases.managers;

import dataBundles.PrescriptionDataBundle;
import database.DataMapperGateway;
import entities.Prescription;
import utilities.DatabaseQueryUtility;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Stream;

public class PrescriptionManager {
    DataMapperGateway<Prescription> prescriptionsDatabase;

    DatabaseQueryUtility databaseUtilities = new DatabaseQueryUtility();

    /**
     * Initializes Prescription Manager with the prescription database.
     * @param prescriptionsDatabase database the prescriptionManager will access.
     */
    public PrescriptionManager(DataMapperGateway<Prescription> prescriptionsDatabase){
        this.prescriptionsDatabase = prescriptionsDatabase;
    }

    /**
     *
     * @param idUser the id associated with the patient in the database. Should not be null.
     * @return An array list of PrescriptionDataBundles for each prescription in the database belonging to the
     * user that is active.
     */
    public ArrayList<PrescriptionDataBundle> getPatientActivePrescriptionDataByUserId(Integer idUser) {
        Stream<PrescriptionDataBundle> dataBundleStream = databaseUtilities.getAllItems(prescriptionsDatabase).
                filter(x -> !isExpiredPrescription(x)).
                filter(x -> isPatientsPrescription(x, idUser)).
                map(x -> new PrescriptionDataBundle(x.getId(), x));
        return databaseUtilities.toArrayList(dataBundleStream);
    }
    /**
     *
     * @param idUser the id associated with the patient in the database. Should not be null.
     * @return An array list of PrescriptionDataBundles for each prescription in the database belonging to the
     * user.
     */
    public ArrayList<PrescriptionDataBundle> getPatientAllPrescriptionDataByUserId(Integer idUser) {
        Stream<PrescriptionDataBundle> dataBundleStream = databaseUtilities.getAllItems(prescriptionsDatabase).
                filter(x -> isPatientsPrescription(x, idUser)).
                map(x -> new PrescriptionDataBundle(x.getId(), x));
        return databaseUtilities.toArrayList(dataBundleStream);
    }


    /**
     * Adds a prescription to the prescription database given the info provided. Assumes all info given is valid and
     * exists in their associated database if applicable.
     * @param dateNoted date the prescription was created
     * @param header header of the prescription, info such as title
     * @param body body of the prescription, all notes relating to the prescription
     * @param idPatient id of the patient the prescription was assigned to.
     * @param idDoctor id of the doctor who gave out the prescription.
     * @param expiryDate date the prescription expires
     * @return PrescriptionDataBundle object corresponding to the prescription.
     */
    public PrescriptionDataBundle createPrescription(ZonedDateTime dateNoted, String header, String body, int idPatient, int idDoctor,
                                                     ZonedDateTime expiryDate){
        Prescription prescription = new Prescription(dateNoted, header, body, idPatient, idDoctor, expiryDate);
        prescriptionsDatabase.add(prescription);
        return new PrescriptionDataBundle(prescription.getId(), prescription);
    }

    /**
     * Removes prescription with given id from the prescription database. If it does not exist, nothing happens
     * @param prescriptionId id of the prescription to be removed.
     */
    public void removePrescription(Integer prescriptionId){
        prescriptionsDatabase.remove(prescriptionId);
    }

    private boolean isExpiredPrescription(Prescription prescription){
        return prescription.getExpiryDate().toLocalDateTime().isBefore(LocalDateTime.now());
    }
    private boolean isPatientsPrescription(Prescription prescription, Integer idUser){
        return prescription.getPatientID() == idUser;
    }

}
