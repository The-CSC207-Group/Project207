package useCases.managers;

import dataBundles.UserDataBundle;
import database.DataMapperGateway;
import entities.User;

public abstract class UserManager<T extends User> {
    DataMapperGateway<T> database;

    public UserManager(DataMapperGateway<T> database){
        this.database = database;
    }

    public boolean changeUserPassword(UserDataBundle<T> dataBundle, String password){
        T user = database.get(dataBundle.getId());
        if (user != null) {
            user.setPassword(password);
            return true;
        }
        return false;
    }
    public Boolean deleteUser(String username){
        T user = getUser(username);
        if (user != null){
            database.remove(user.getId());
            return true;
        }
        return false;
    }



    public T getUser(String username){
        return database.getByCondition(x -> x.getUsername().equals(username));
    }
    public Boolean doesUserExist(String username){
        return database.getByCondition(x -> x.getUsername().equals(username)) != null;
    }
    public T signIn(String username, String password){
        T user = getUser(username);
        if (user == null){return null;}
        if (user.comparePassword(password)){return user;}
        return null;
    }





}
