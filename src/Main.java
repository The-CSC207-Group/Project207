import controllers.Context;
import database.Database;
import entities.Admin;
import entities.Availability;
import entities.Clinic;
import useCases.managers.AdminManager;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        DataMapperGateway<User> userDatabase = new UserJsonDatabase();
        Database database = new Database();
        database.setClinic(new Clinic("", "", "", ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30), LocalTime.of(17, 0))))));
        AdminManager adminManager = new AdminManager(database);
        adminManager.createAdmin("root", "root");
        database.save();
        Context c = new Context(database);
        c.run();
    }
}
