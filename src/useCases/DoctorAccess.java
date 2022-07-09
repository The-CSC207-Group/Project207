package useCases;

import database.DataMapperGateway;
import entities.Doctor;
import entities.Patient;

public class DoctorAccess {

    private DataMapperGateway<Doctor> doctorDatabase;
    private DataMapperGateway<Patient> patientDatabase;
    AppointmentManager appointmentManager;

    public DoctorAccess(DataMapperGateway<Doctor> doctorDatabase, DataMapperGateway<Patient> patientDatabase,
                         AppointmentManager appointmentManager){
        this.doctorDatabase = doctorDatabase;
        this.patientDatabase = patientDatabase;
        this.appointmentManager = appointmentManager;
    }
    public void signOut(){

    }


}
