package presenter.entityViews;

import dataBundles.AppointmentData;
import database.Database;
import entities.TimeBlock;

public class AppointmentView extends EntityView<AppointmentData> {

    @Override
    public String viewFull(AppointmentData item) {
        TimeBlock timeBlock = item.getTimeBlock();
        Database database = new Database();
        String doctorUsername = database.getDoctorDatabase().get(item.getDoctorId()).getUsername();
        return "Appointment starting at " + timeBlock.getStartTime() + " and ending at " + timeBlock.getEndTime()
                + "with Dr." + doctorUsername + ".";
    }
}
