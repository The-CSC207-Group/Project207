import controllers.ApplicationController;
import database.DataMapperGateway;
import database.UserJsonDatabase;
import entities.User;

public class Main {
    public static void main(String[] args) {
        DataMapperGateway<User> userDatabase = new UserJsonDatabase();
        new ApplicationController(userDatabase).run();
    }
}
