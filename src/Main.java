import controllers.Context;
import database.Database;
import entities.Admin;
import entities.Clinic;

import java.time.ZoneId;

public class Main {
    public static void main(String[] args) {
//        DataMapperGateway<User> userDatabase = new UserJsonDatabase();
        Database database = new Database();
        database.setClinic(new Clinic("", "", "", ZoneId.of("US/Eastern"), null));
        Context c = new Context(database);
        c.run();
        database.getAdminDatabase().add(new Admin("root", "root"));
        database.save();
    }
}
