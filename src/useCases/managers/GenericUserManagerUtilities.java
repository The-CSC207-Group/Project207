package useCases.managers;

import database.DataMapperGateway;
import entities.User;

public class GenericUserManagerUtilities<T extends User> {
    DataMapperGateway<T> database;

    public GenericUserManagerUtilities(DataMapperGateway<T> database){
        this.database = database;
    }

    public void changePassword(Integer IDUser, String password){
        database.get(IDUser).setPassword(password);
    }

}
