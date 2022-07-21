package presenter.entityViews;

import dataBundles.AppointmentData;
import dataBundles.TimeBlockData;
import database.Database;
import entities.TimeBlock;

/**
 * The Appointment entity's view.
 */
public class AppointmentView extends EntityView<AppointmentData> {

    /**
     * @param item AppointmentData bundle to view.
     * @return String representing item's full appointment view.
     */
    @Override
    public String viewFull(AppointmentData item) {
        TimeBlockData timeBlock = item.getTimeBlock();
        Database database = new Database();
        String doctorUsername = database.getDoctorDatabase().get(item.getDoctorId()).getUsername();
        return "Appointment starting at " + timeBlock.getStartTime() + " and ending at " + timeBlock.getEndTime()
                + "with Dr." + doctorUsername + ".";
    }
}
