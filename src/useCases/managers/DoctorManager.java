package useCases.managers;

import dataBundles.ContactData;
import dataBundles.DoctorData;
import database.DataMapperGateway;
import entities.Contact;
import entities.Doctor;
import entities.User;

public class DoctorManager extends UserManager<Doctor> {

    DataMapperGateway<Doctor> doctorDatabase;
    DataMapperGateway<Contact> contactDatabase;

    /**
     * Initialize the doctor and contact databases.
     * @param doctorDatabase DataMapperGateway<Doctor>
     * @param contactDatabase DataMapperGateway<Contact>
     */
    public DoctorManager(DataMapperGateway<Doctor> doctorDatabase, DataMapperGateway<Contact> contactDatabase) {
        super(doctorDatabase);
        this.doctorDatabase = doctorDatabase;
        this.contactDatabase = contactDatabase;
    }

    public DoctorData createDoctor(String username, String password) {
        Doctor doctor = new Doctor(username, password);
        doctorDatabase.add(doctor);
        return new DoctorData(doctor);

    }
}

