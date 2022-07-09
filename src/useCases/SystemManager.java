//package useCases;
//
//import database.DataMapperGateway;
//import entities.Log;
//import entities.User;
//
//
//public class SystemManager {
//
//    private final DataMapperGateway<User> database;
//
//    public SystemManager(DataMapperGateway<User> database) {
//        this.database = database;
//    }
//
//    /**
//     *
//     * @param username username of user to be checked
//     * @return true if the user exists in the database and is an admin, false otherwise.
//     */
//    public boolean isUserAdmin(String username) {
//        User user = database.get(username);
//        return user != null && user.isAdmin();
//    }
//
//    /**
//     *
//     * @param username username of user to be checked
//     * @param password password of user to be checked
//     * @return true if user exists in the database and password provided matches that of the user in the database. false
//     * if the user doesn't exist in the database or the password of the user in the database doesn't match the password
//     * given.
//     */
//    public boolean canSignIn(String username, String password) {
//        User user = database.get(username);
//        return user != null && user.comparePassword(password) && !user.isBanned();
//    }
//
//    /**
//     * Add a log to the user associated with the username provided.
//     * @param username username of user who needs logs updated
//     */
//    public void addUserLog(String username){
//        User user = database.get(username);
//        user.addLog(new Log(user.getUsername() + " logged in"));
//    }
//
//    /**
//     * Add new user to the database.
//     * @param username username
//     * @param password password
//     * @return Return false if user with same username already exists in database, true otherwise.
//     */
//    public boolean createUser(String username, String password) {
//        User newUser = new User(username, password, false);
//        return database.add(newUser);
//    }
//}
