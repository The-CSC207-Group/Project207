package useCases.managers;

import dataBundles.AdminData;
import dataBundles.ContactData;
import database.DataMapperGateway;
import entities.Admin;
import entities.Contact;

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
    public AdminData createAdmin(String username, String password, ContactData contactData){
        Integer contactId = contactDatabase.add(contactDataBundleToContactEntity(contactData));
        Admin admin = new Admin(username, password, contactId);
        adminDatabase.add(admin);
        return new AdminData(admin);
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

    private Contact contactDataBundleToContactEntity(ContactData contactData){
        return new Contact(contactData.getName(),
                contactData.getEmail(),
                contactData.getPhoneNumber(),
                contactData.getAddress(),
                contactData.getBirthday(),
                contactData.getEmergencyContactName(),
                contactData.getEmergencyContactEmail(),
                contactData.getEmergencyContactPhoneNumber(),
                contactData.getEmergencyRelationship());
    }

}
