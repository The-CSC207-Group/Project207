package useCases.managers;

import dataBundles.UserData;
import database.DataMapperGateway;
import database.Database;
import entities.Contact;
import entities.User;

import java.util.Optional;

public abstract class UserManager<T extends User> {
    DataMapperGateway<T> typeTDatabase;
    DataMapperGateway<Contact> contactDatabase;

    LogManager logManager;
    public UserManager(DataMapperGateway<T> typeTDatabase, Database database){
        this.typeTDatabase = typeTDatabase;
        this.contactDatabase = database.getContactDatabase();
        this.logManager = new LogManager(database);
    }

    public boolean changeUserPassword(UserData<T> dataBundle, String password){
        T user = typeTDatabase.get(dataBundle.getId());
        if (user != null) {
            user.setPassword(password);
            return true;
        }
        return false;
    }
    public boolean changeUserPassword(String name, String password){
        T user = getUser(name);
        if (user != null) {
            user.setPassword(password);
            return true;
        }
        return false;
    }
    public Boolean deleteUser(String username){
        T user = getUser(username);
        if (user != null){
            typeTDatabase.remove(user.getId());
            contactDatabase.remove(user.getContactInfoId());
            return true;
        }
        return false;
    }
    public Boolean deleteUserByData(UserData<T> user){
        return deleteUser(user.getUsername());
    }




    public T getUser(String username){
        return typeTDatabase.getByCondition(x -> x.getUsername().equals(username));
    }
    public Boolean doesUserExist(String username){
        return typeTDatabase.getByCondition(x -> x.getUsername().equals(username)) != null;
    }
    protected T signInHelper(String username, String password){
        T user = getUser(username);
        if (user == null){return null;}
        if (user.comparePassword(password)){
            logManager.addLog("signed in", user.getId());
            return user;}
        return null;
    }
    public abstract UserData<T> signIn(String username, String password);
    public abstract UserData<T> getUserData(String username);
    protected Optional<T> getUserHelper(String username){
        return Optional.ofNullable(getUser(username));
    }
}
