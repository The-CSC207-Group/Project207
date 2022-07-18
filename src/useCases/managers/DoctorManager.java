package useCases.managers;

import dataBundles.DoctorData;
import dataBundles.PatientData;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.Doctor;

import java.util.Optional;

public class DoctorManager extends UserManager<Doctor> {

    DataMapperGateway<Doctor> doctorDatabase;
    DataMapperGateway<Contact> contactDatabase;
    PatientManager patientManager;

    /***
     * Initialize the doctor and contact databases.
     * @param database The entire database.
     */
    public DoctorManager(Database database) {
        super(database.getDoctorDatabase());
        this.doctorDatabase = database.getDoctorDatabase();
        this.contactDatabase = database.getContactDatabase();
        this.patientManager = new PatientManager(database);
    }

    public DoctorData createDoctor(String username, String password) {
        Doctor doctor = new Doctor(username, password);
        doctorDatabase.add(doctor);
        return new DoctorData(doctor);

    }

    private DoctorData toDoctorData(Doctor doctor) {
        if (doctor == null) {
            return null;
        } else {
            return new DoctorData(doctor);
        }
    }

    @Override
    public DoctorData signIn(String userName, String password) {
        return toDoctorData(signInHelper(userName, password));
    }

    public Optional<PatientData> getPatient(String username){
        return Optional.ofNullable(patientManager.getUser(username))
                .map(PatientData::new);
    }
}

