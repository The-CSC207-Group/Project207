package useCases.managers;

import database.DataMapperGateway;
import entities.Admin;

public class AdminManager {
    DataMapperGateway<Admin> adminDatabase;
    GenericUserManagerMethods<Admin> adminUtilities;

    public AdminManager(DataMapperGateway<Admin> adminDatabase){
        this.adminDatabase = adminDatabase;
        this.adminUtilities = new GenericUserManagerMethods<>(adminDatabase);
    }
    public boolean createAdmin(String username, String password, int contactInfo){
        Admin admin = new Admin(username, password, contactInfo);
        Integer user_id = adminDatabase.add(admin);
        return user_id != null;
    }
    public void changeUserPassword(Integer userId, String newPassword){
        adminUtilities.changePassword(userId, newPassword);
    }
    public void deleteAdminUser(Integer idUser){
        adminUtilities.deleteUser(idUser);
    }
}
