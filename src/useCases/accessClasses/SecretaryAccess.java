package useCases.accessClasses;

import dataBundles.*;
import database.DataMapperGateway;
import entities.*;
import useCases.managers.*;

import java.util.ArrayList;

public class SecretaryAccess {
    PrescriptionManager prescriptionManager;
    PatientManager patientManager;

    DoctorManager doctorManager;

    SecretaryManager secretaryManager;

    DataMapperGateway<Contact> contactDatabase;

    LogManager logManager;

    public SecretaryAccess(DataMapperGateway<Prescription> prescriptionDatabase,
                           DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Doctor>
                           doctorDatabase, DataMapperGateway<Secretary> secretaryDatabase,
                           DataMapperGateway<Log> logDatabase) {
        this.prescriptionManager = new PrescriptionManager(prescriptionDatabase);
        this.patientManager = new PatientManager(patientDatabase);
        this.doctorManager = new DoctorManager(doctorDatabase);
        this.secretaryManager = new SecretaryManager(secretaryDatabase);
        this.logManager = new LogManager(logDatabase);
    }
    public void signOut(){

    }
    public void deleteCurrentUser(Integer iDUser){
        secretaryManager.deleteSecretary(iDUser);
    }
    public void deletePatient(Integer iDUser){
        patientManager.deletePatient(iDUser);
    }
    public PatientDataBundle createPatient(String username, String password, ContactDataBundle contactDataBundle,
                                           String healthNumber){
        Integer contactId = contactDatabase.add(contactDataBundleToContactEntity(contactDataBundle));
        Integer patientId = patientManager.createPatient(username, password, contactId, healthNumber);
        return new PatientDataBundle(patientId, patientManager.getPatient(patientId));
    }
    public DoctorDataBundle createDoctor (String username, String password, ContactDataBundle contactDataBundle){
        Integer contactId = contactDatabase.add(contactDataBundleToContactEntity(contactDataBundle));
        Integer doctorId = doctorManager.createDoctor(username, password, contactId);
        return new DoctorDataBundle(doctorId, doctorManager.getDoctor(doctorId));
    }
    private Contact contactDataBundleToContactEntity(ContactDataBundle contactDataBundle){
        return new Contact(contactDataBundle.getName(),
                contactDataBundle.getEmail(),
                contactDataBundle.getPhoneNumber(),
                contactDataBundle.getAddress(),
                contactDataBundle.getBirthday(),
                contactDataBundle.getEmergencyContactName(),
                contactDataBundle.getEmergencyContactEmail(),
                contactDataBundle.getEmergencyContactPhoneNumber(),
                contactDataBundle.getEmergencyRelationship());
    }
    public ArrayList<PrescriptionDataBundle> getActivePatientPrescriptions(Integer iDUser){
        return prescriptionManager.getPatientActivePrescriptionDataByUserId(iDUser);
    }
    public ArrayList<PrescriptionDataBundle> getAllPatientPrescriptions(Integer iDUser){
        return prescriptionManager.getPatientAllPrescriptionDataByUserId(iDUser);
    }
    public void changePatientPassword(Integer iDUser, String newPassword){
        patientManager.changeUserPassword(iDUser, newPassword);
    }
    public void displaySchedule(){
        //TO BE IMPLEMENTED
    }

    public ArrayList<LogDataBundle> getLogs(Integer userId){
        if (patientManager.getPatient(userId) != null){return logManager.getLogDataBundlesFromLogIDs(patientManager.getPatient(userId).getLogs());}
        if (secretaryManager.getSecretary(userId) != null){return logManager.getLogDataBundlesFromLogIDs(secretaryManager.getSecretary(userId).getLogs());}
        return null;
    }
}
