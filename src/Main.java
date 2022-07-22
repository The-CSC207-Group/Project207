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
        Database database = new Database();

        // Bootstrap database with default clinic and admin
        database.setClinic(new Clinic("", "", "", ZoneId.of("US/Eastern"),
                new ArrayList<>(List.of(new Availability(DayOfWeek.of(1), LocalTime.of(8, 30),
                                LocalTime.of(17, 0)),
                        new Availability(DayOfWeek.of(2), LocalTime.of(8, 30),
                                LocalTime.of(17, 0)),
                        new Availability(DayOfWeek.of(3), LocalTime.of(8, 30),
                                LocalTime.of(17, 0)),
                        new Availability(DayOfWeek.of(4), LocalTime.of(8, 30),
                                LocalTime.of(17, 0)),
                        new Availability(DayOfWeek.of(5), LocalTime.of(8, 30),
                                LocalTime.of(17, 0)),
                        new Availability(DayOfWeek.of(6), LocalTime.of(10, 0),
                                LocalTime.of(16, 0)),
                        new Availability(DayOfWeek.of(7), LocalTime.of(10, 0),
                                LocalTime.of(16, 0))))));
        AdminManager adminManager = new AdminManager(database);
        adminManager.createAdmin("root", "root");
        database.save();

        Context c = new Context(database);
        c.run();
    }
}
