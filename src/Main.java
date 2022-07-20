import controllers.Context;
import database.Database;
import entities.Admin;
import entities.Availability;
import entities.Clinic;
import entities.TimeBlock;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Main {
    public static void main(String[] args) {
//        DataMapperGateway<User> userDatabase = new UserJsonDatabase();
        Database database = new Database();
        database.setClinic(new Clinic("", "", "", ZoneId.of("US/Eastern"),
                new TimeBlock(ZonedDateTime.of(1, 2, 3, 4, 5, 6, 7,
                        ZoneId.of("US/Eastern")), ZonedDateTime.of(1, 2, 3, 4,
                        5, 6, 7, ZoneId.of("US/Eastern")))));
        Context c = new Context(database);
        c.run();
        database.getAdminDatabase().add(new Admin("root", "root"));
        database.save();
    }
}
