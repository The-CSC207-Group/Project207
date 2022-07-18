package useCases.managers;

import dataBundles.AdminData;
import dataBundles.ContactData;
import database.DataMapperGateway;
import database.Database;
import entities.Admin;
import entities.Contact;

public class AdminManager extends UserManager<Admin>{
    DataMapperGateway<Admin> adminDatabase;
    DataMapperGateway<Contact> contactDatabase;

    /***
     * Initialize the admin and contact databases.
     * @param database The entire database.
     */
    public AdminManager(Database database){
        super(database.getAdminDatabase());
        this.adminDatabase = database.getAdminDatabase();
        this.contactDatabase = database.getContactDatabase();
    }
    public AdminData createAdmin(String username, String password){
        Admin admin = new Admin(username, password);
        adminDatabase.add(admin);
        return new AdminData(admin);
    }
    
}
