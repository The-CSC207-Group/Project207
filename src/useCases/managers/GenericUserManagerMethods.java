package useCases.managers;

import database.DataMapperGateway;
import entities.User;

public class GenericUserManagerMethods<T extends User> {
    DataMapperGateway<T> database;

    public GenericUserManagerMethods(DataMapperGateway<T> database){
        this.database = database;
    }

    public void changePassword(Integer userId, String password){
        database.get(userId).setPassword(password);
    }

    public void deleteUser(Integer userId){
        database.remove(userId);
    }

    public T getUser(Integer userId){
        return database.get(userId);
    }


}
