package useCases.managers;

import dataBundles.DoctorData;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.Doctor;

public class DoctorManager extends UserManager<Doctor> {

    DataMapperGateway<Doctor> doctorDatabase;
    DataMapperGateway<Contact> contactDatabase;

    /***
     * Initialize the doctor and contact databases.
     * @param database The entire database.
     */
    public DoctorManager(Database database) {
        super(database.getDoctorDatabase());
        this.doctorDatabase = database.getDoctorDatabase();
        this.contactDatabase = database.getContactDatabase();
    }

    public DoctorData createDoctor(String username, String password) {
        Doctor doctor = new Doctor(username, password);
        doctorDatabase.add(doctor);
        return new DoctorData(doctor);

    }
    public DoctorData toDoctorData(Doctor doctor) {
        if (doctor == null) {
            return new DoctorData(doctor);
        }
    }
}

