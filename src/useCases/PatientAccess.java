package useCases;

public class PatientAccess {
    private DataMapperGateway<Patient> patientDatabase;
    AppointmentManager appointmentManager;

    PrescriptionManager prescriptionManager;
    public PatientAccess(DataMapperGateway<Patient> database, AppointmentManager appointmentManager) {
        this.patientDatabase = database;
        this.appointmentManager = appointmentManager;
    }

    public void signOut(){

    }
    public void deleteCurrentUser() {

    }
    public void changeCurrentUserPassword(String newPassword){

    }
    public ArrayList<Prescription> getActivePrescriptions(Patient patient){

    }
    public ArrayList<Prescription> getPrescriptionsHistory(Patient patient){

    }
    public ArrayList<Appointments> getAppointments(Patient patient){

    }
}
