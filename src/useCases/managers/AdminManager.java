package useCases.managers;

import database.DataMapperGateway;
import entities.Admin;
import entities.Patient;

public class AdminManager{
    DataMapperGateway<Admin> adminDatabase;
    GenericUserManagerMethods<Admin> adminMethods;

    public AdminManager(DataMapperGateway<Admin> adminDatabase){
        this.adminDatabase = adminDatabase;
        this.adminMethods = new GenericUserManagerMethods<>(adminDatabase);
    }
    public Integer createAdmin(String username, String password, int contactInfo){
        Admin admin = new Admin(username, password, contactInfo);
        return adminDatabase.add(admin);
    }
    public void changeUserPassword(Integer userId, String newPassword){
        adminMethods.changePassword(userId, newPassword);
    }
    public void deleteAdminUser(Integer idUser){
        adminMethods.deleteUser(idUser);
    }

    public Admin getAdmin(Integer idUser){
        return adminMethods.getUser(idUser);
    }


}
