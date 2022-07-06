import controllers.ApplicationController;
import controllers.Controller;
import database.DataMapperGateway;
import database.UserJsonDatabase;
import entities.User;

public class Main {
    public static void main(String[] args) {
        DataMapperGateway<User> userDatabase = new UserJsonDatabase();
        Controller c = new Controller(userDatabase);

    }
}
