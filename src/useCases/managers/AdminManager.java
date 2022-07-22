package useCases.managers;

import dataBundles.AdminData;
import database.DataMapperGateway;
import database.Database;
import entities.Admin;
import entities.Contact;

/**
 * Use case class for handling operations and data of admin users.
 */
public class AdminManager extends UserManager<Admin>{

    private final DataMapperGateway<Admin> adminDatabase;

    /***
     * Initialize the admin and contact databases.
     * @param database The entire database.
     */
    public AdminManager(Database database){
        super(database.getAdminDatabase(), database);
        this.adminDatabase = database.getAdminDatabase();
    }

    /***
     * Creates and admin and adds to the database.
     * @param username username of new account, should not exist in database.
     * @param password password of new account.
     * @return Admin data if sign in successful, if username exists in the database, return null.
     */
    public AdminData createAdmin(String username, String password){
        Admin admin = new Admin(username, password);
        if (adminDatabase.add(admin) != null) {
            admin.setContactInfoId(newContactInDatabase());
            return new AdminData(admin);
        }
        return null;
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
