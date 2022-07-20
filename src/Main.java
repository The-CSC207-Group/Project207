import controllers.Context;
import database.Database;
import entities.Admin;

public class Main {
    public static void main(String[] args) {
//        DataMapperGateway<User> userDatabase = new UserJsonDatabase();
        Database database = new Database();
        Context c = new Context(database);
        c.run();
//        database.getAdminDatabase().add(new Admin("root", "root"));
//        database.save();
    }
}
