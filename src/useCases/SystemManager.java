package useCases;

import database.DataMapperGateway;
import entities.*;

public class SystemManager{

    private final DataMapperGateway<User> database;


    public SystemManager(DataMapperGateway<User> database) {
        this.database = database;
    }


    /**
     *
     * @param username username of the user to check the type
     * @return string of the user type
     */
//    public String userType(int username){
//        User user = database.get(username);
//        return user.getType();
//    }

    /**
     *
     * @param username new username
     * @param password new password
     * @param contactInfo contact info of user created
     * @return true if account has been created, false if account failed to create
     */
    //  can we create any* user using this design? that would break the system?
    public boolean createPatient(String username, String password, int contactInfo, String healthNumber) {
        Patient patient = new Patient(username, password, contactInfo, healthNumber);
        database.add(patient);
//        HashSet<Integer> all_ids  = database.getAllIds();
//        return all_ids.contains(user.getUserID());
        return true;
    }

    /**
     *
     * @param username username of the user trying to sign in
     * @param password password of user trying to sign in
     * @return boolean, true if user can sign in, false if not
     */
    // need to have a method that checks if user is banned
    public boolean canSignIn(String username, String password){
//        User user = database.get(userID);
//        return user != null && user.comparePassword(password);
        return false;
    }

    /**
     *
     * @param userID userID
     * @return boolean, true if user is admin, false if anything else
     */
//    public boolean isUserAdmin(int userID){
//        return userType(userID).equals("admin");
//    }


}