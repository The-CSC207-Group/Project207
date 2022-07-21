import controllers.Context;
import database.Database;
import entities.Admin;
import entities.Availability;
import entities.Clinic;
import entities.TimeBlock;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        DataMapperGateway<User> userDatabase = new UserJsonDatabase();
        Database database = new Database();
        database.setClinic(new Clinic("", "", "", ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30), LocalTime.of(17, 0))))));
        Context c = new Context(database);
        c.run();
        database.getAdminDatabase().add(new Admin("root", "root"));
        database.save();
    }
}
