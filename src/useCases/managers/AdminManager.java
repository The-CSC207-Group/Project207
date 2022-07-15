package useCases.managers;

import dataBundles.AdminDataBundle;
import dataBundles.ContactDataBundle;
import database.DataMapperGateway;
import entities.Admin;
import entities.Contact;
import entities.Patient;

public class AdminManager{
    DataMapperGateway<Admin> adminDatabase;
    GenericUserManagerMethods<Admin> adminMethods;
    DataMapperGateway<Contact> contactDatabase;

    /**
     * Initialize the admin and contact databases.
     * @param adminDatabase DataMapperGateway<Admin>
     * @param contactDatabase DataMapperGateway<Contact>
     */
    public AdminManager(DataMapperGateway<Admin> adminDatabase, DataMapperGateway<Contact> contactDatabase){
        this.adminDatabase = adminDatabase;
        this.adminMethods = new GenericUserManagerMethods<>(adminDatabase);
        this.contactDatabase = contactDatabase;
    }
    public AdminDataBundle createAdmin(String username, String password, ContactDataBundle contactDataBundle){
        Integer contactId = contactDatabase.add(contactDataBundleToContactEntity(contactDataBundle));
        Admin admin = new Admin(username, password, contactId);
        adminDatabase.add(admin);
        return new AdminDataBundle(admin.getId(), admin);
    }

    /**
     * Changes the Admin's password. If the admin does not exist in the database, do nothing.
     * @param userId      Int userId of the user trying to change the password.
     * @param newPassword String new password for the user.
     */
    public void changeUserPassword(Integer userId, String newPassword){
        adminMethods.changePassword(userId, newPassword);
    }

    /**
     * Deletes the admin from the database. If the admin does not exist in the database, do nothing.
     * @param userId Int userId of the user being deleted.
     */
    public void deleteAdminUser(Integer userId){
        adminMethods.deleteUser(userId);
    }

    /**
     * Returns admin object with the given id. If the admin does not exist in the database, return null.
     * @param userId Int userId of the user requested.
     * @return Admin object or null if doctor doesn't exist in doctor database.
     */
    public Admin getAdmin(Integer userId){
        return adminMethods.getUser(userId);
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

}
