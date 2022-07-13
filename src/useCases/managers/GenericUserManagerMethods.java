package useCases.managers;

import database.DataMapperGateway;
import entities.User;

public class GenericUserManagerMethods<T extends User> {
    DataMapperGateway<T> database;

    public GenericUserManagerMethods(DataMapperGateway<T> database){
        this.database = database;
    }

    public void changePassword(Integer IDUser, String password){
        database.get(IDUser).setPassword(password);
    }

    public void deleteUser(Integer idUser){
        database.remove(idUser);
    }

    public T getUser(Integer idUser){
        return database.get(idUser);
    }


}
