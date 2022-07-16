package useCases.managers;

import database.DataMapperGateway;
import entities.User;

public class UserManager<T extends User> {
    DataMapperGateway<T> database;

    T getUser(Integer id){
      return database.get(id);
    }
}
