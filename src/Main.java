import controllers.Context;
import database.DataMapperGateway;
import database.Database;
import database.JsonDatabase;
import entities.Log;
import entities.User;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        DataMapperGateway<User> userDatabase = new UserJsonDatabase();
//        Context c = new Context(userDatabase);
//        c.run();
        Database database = new Database();
    }
}
