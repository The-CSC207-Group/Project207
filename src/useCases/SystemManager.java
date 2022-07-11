package useCases;

import database.DataMapperGateway;
import entities.*;

import java.util.HashSet;

public class SystemManager{

    private final DataMapperGateway<User> database;


    public SystemManager(DataMapperGateway<User> database) {
        this.database = database;
    }


    /**
     *
     * @param userID username of the user to check the type
     * @return string of the user type
     */
    public String userType(int userID){
        return database.get(userID).getClass().getName();
    }

    /**
     *
     * @param username new username
     * @param password new password
     * @param contactInfo contact info of user created
     * @return true if account has been created, false if account failed to create
     */
    public boolean createPatient(String username, String password, int contactInfo, String healthNumber) {
        Patient patient = new Patient(username, password, contactInfo, healthNumber);
        database.add(patient);
        HashSet<Integer> all_ids  = database.getAllIds();
        return all_ids.contains(patient.getId());
    }

    /**
     *
     * @param userID username of the user trying to sign in
     * @param password password of user trying to sign in
     * @return boolean, true if user can sign in, false if not
     */
    // need to have a method that checks if user is banned
    public boolean canSignIn(Integer userID, String password){
        User user = database.get(userID);
        return user != null && user.comparePassword(password);
    }


    /**
     *
     * @param userID userID
     * @return boolean, true if user is admin, false if anything else
     */
    public boolean isUserAdmin(int userID){
        return userType(userID).equals("Admin");
    }


}