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


    public AdminManager(DataMapperGateway<Admin> adminDatabase, DataMapperGateway<Contact> contactDatabase){
        this.adminDatabase = adminDatabase;
        this.adminMethods = new GenericUserManagerMethods<>(adminDatabase);
        this.contactDatabase = contactDatabase;
    }
    public AdminDataBundle createAdmin(String username, String password, ContactDataBundle contactDataBundle){
        Integer contactId = contactDatabase.add(contactDataBundleToContactEntity(contactDataBundle));
        Admin admin = new Admin(username, password, contactId);
        return new AdminDataBundle(admin.getId(), admin);
    }
    public void changeUserPassword(Integer userId, String newPassword){
        adminMethods.changePassword(userId, newPassword);
    }
    public void deleteAdminUser(Integer userId){
        adminMethods.deleteUser(userId);
    }

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
