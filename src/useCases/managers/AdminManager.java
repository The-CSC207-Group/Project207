package useCases.managers;

import dataBundles.AdminData;
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
        super(database.getAdminDatabase(), database);
        this.adminDatabase = database.getAdminDatabase();
        this.contactDatabase = database.getContactDatabase();
    }

    /***
     * Creates and admin and adds to the database.
     * @param username username of new account, should not exist in database.
     * @param password password of new account.
     * @return Admin data if sign in successful, if username exists in the database, return null.
     */
    public AdminData createAdmin(String username, String password){
        Admin admin = new Admin(username, password, newContactInDatabase());
        adminDatabase.add(admin);
        return new AdminData(admin);
    }

    /**
     * Creates and returns a data bundle of the admin associated with the login details passed in.
     * @param userName String - the username of the admin that wants to sign in.
     * @return AdminData - the data bundle of the admin that wants to sign in.
     */
    @Override
    public AdminData signIn(String userName, String password) {
        return toAdminData(signInHelper(userName, password));
    }

    /**
     * Creates and returns a data bundle of the admin associated with the username passed in.
     * @param username String - username of the specified user.
     * @return AdminData - data bundle of the admin associated with the username passed in.
     */
    @Override
    public AdminData getUserData(String username) {
       return getUserHelper(username).map(AdminData::new).orElse(null);
    }

    private AdminData toAdminData(Admin admin){
        if (admin == null){
            return null;
        } else {
            return new AdminData(admin);
        }
    }

}
