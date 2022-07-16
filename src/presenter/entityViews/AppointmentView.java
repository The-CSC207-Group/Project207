package presenter.entityViews;

import dataBundles.AppointmentData;
import entities.TimeBlock;

public class AppointmentView extends EntityView<AppointmentData> {

    @Override
    public String view(AppointmentData item) {
        // TODO not a fan of plus notation, use StringBuilder
        TimeBlock timeBlock = item.getTimeBlock();
        return "Appointment starting at " + timeBlock.getStartTime() + " and ending at " + timeBlock.getEndTime()
                + ".";
    }
}
