package useCases.accessClasses;

import dataBundles.ContactDataBundle;
import dataBundles.DoctorDataBundle;
import dataBundles.PatientDataBundle;
import dataBundles.PrescriptionDataBundle;
import database.DataMapperGateway;
import entities.*;
import useCases.managers.DoctorManager;
import useCases.managers.PatientManager;
import useCases.managers.PrescriptionManager;
import useCases.managers.SecretaryManager;

import java.util.ArrayList;

public class SecretaryAccess {
    PrescriptionManager prescriptionManager;
    PatientManager patientManager;

    DoctorManager doctorManager;

    SecretaryManager secretaryManager;

    DataMapperGateway<Contact> contactDatabase;


    public SecretaryAccess(DataMapperGateway<Prescription> prescriptionDatabase,
                           DataMapperGateway<Patient> patientDatabase, DataMapperGateway<Doctor>
                           doctorDatabase, DataMapperGateway<Secretary> secretaryDatabase, DataMapperGateway<Contact>
                           contactDatabase) {
        this.prescriptionManager = new PrescriptionManager(prescriptionDatabase);
        this.patientManager = new PatientManager(patientDatabase, contactDatabase);
        this.doctorManager = new DoctorManager(doctorDatabase, contactDatabase);
        this.secretaryManager = new SecretaryManager(secretaryDatabase, contactDatabase);
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
        return patientManager.createPatient(username, password, contactDataBundle, healthNumber);
    }
    public DoctorDataBundle createDoctor (String username, String password, ContactDataBundle contactDataBundle){
        return doctorManager.createDoctor(username, password, contactDataBundle);
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
}
