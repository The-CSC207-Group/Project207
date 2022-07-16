package useCases.managers;

import dataBundles.AdminData;
import dataBundles.ContactData;
import database.DataMapperGateway;
import entities.Admin;
import entities.Contact;

public class AdminManager extends UserManager<Admin>{
    DataMapperGateway<Admin> adminDatabase;
    GenericUserManagerMethods<Admin> adminMethods;
    DataMapperGateway<Contact> contactDatabase;

    /**
     * Initialize the admin and contact databases.
     * @param adminDatabase DataMapperGateway<Admin>
     * @param contactDatabase DataMapperGateway<Contact>
     */
    public AdminManager(DataMapperGateway<Admin> adminDatabase, DataMapperGateway<Contact> contactDatabase){
        super(adminDatabase);
        this.adminDatabase = adminDatabase;
        this.adminMethods = new GenericUserManagerMethods<>(adminDatabase);
        this.contactDatabase = contactDatabase;
    }
    public AdminData createAdmin(String username, String password){
        Admin admin = new Admin(username, password);
        adminDatabase.add(admin);
        return new AdminData(admin);
    }
    
}
