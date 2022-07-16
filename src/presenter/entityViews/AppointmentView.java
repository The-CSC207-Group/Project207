package presenter.entityViews;

import dataBundles.AppointmentData;
import entities.TimeBlock;

public class AppointmentView extends EntityView<AppointmentData> {

    @Override
    public String view(AppointmentData item) {
        TimeBlock timeBlock = item.getTimeBlock();
        return "Appointment starting at " + timeBlock.getStartTime() + " and ending at " + timeBlock.getEndTime()
                + ".";
    }
}
