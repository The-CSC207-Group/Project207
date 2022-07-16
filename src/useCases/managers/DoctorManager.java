package useCases.managers;

import dataBundles.ContactDataBundle;
import dataBundles.DoctorDataBundle;
import database.DataMapperGateway;
import entities.Contact;
import entities.Doctor;

public class DoctorManager {

    DataMapperGateway<Doctor> doctorDatabase;
    GenericUserManagerMethods<Doctor> doctorMethods;
    DataMapperGateway<Contact> contactDatabase;

    /**
     * Initialize the doctor and contact databases.
     * @param doctorDatabase DataMapperGateway<Doctor>
     * @param contactDatabase DataMapperGateway<Contact>
     */
    public DoctorManager(DataMapperGateway<Doctor> doctorDatabase, DataMapperGateway<Contact> contactDatabase) {
        this.doctorDatabase = doctorDatabase;
        this.doctorMethods = new GenericUserManagerMethods<>(doctorDatabase);
        this.contactDatabase = contactDatabase;
    }

    public DoctorDataBundle createDoctor(String username, String password, ContactDataBundle contactDataBundle) {
        Integer contactId = contactDatabase.add(contactDataBundleToContactEntity(contactDataBundle));
        Doctor doctor = new Doctor(username, password, contactId);
        doctorDatabase.add(doctor);
        return new DoctorDataBundle(doctor.getId(), doctor);

    }

    /**
     * Changes the doctor's password. If the doctor does not exist in the database, do nothing.
     * @param userId      Int userId of the user trying to change the password.
     * @param newPassword String new password for the user.
     */
    public void changeUserPassword(Integer userId, String newPassword) {
        doctorMethods.changePassword(userId, newPassword);
    }

    /**
     * Deletes the doctor from the database. If the doctor does not exist in the database, do nothing.
     * @param userId Int userId of the user being deleted.
     */
    public void deleteDoctor(Integer userId) {
        doctorMethods.deleteUser(userId);
    }

    /**
     * Returns doctor object with the given id. If the doctor does not exist in the database, return null.
     * @param userId Int userId of the user requested.
     * @return Doctor object or null if doctor doesn't exist in doctor database.
     */
    public Doctor getDoctor(Integer userId) {
        return doctorMethods.getUser(userId);
    }

    private Contact contactDataBundleToContactEntity(ContactDataBundle contactDataBundle) {
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
    public boolean doesDoctorExist(String username){
        return doctorDatabase.getAllIds().stream().map(x -> doctorDatabase.get(x))
                .anyMatch(x -> x.getUsername().equals(username));
    }


}

