package presenters.entityViews;

import entities.UniversalTimeBlock;

public class UniversalTimeBlockView extends EntityView<UniversalTimeBlock>{

    /**
     * @param item AppointmentData  to view.
     * @return String representing item's full appointment view.
     */

    @Override
    public String viewFull(UniversalTimeBlock item) {
        return "Appointment starting at " + item.startTime() + " and ending at " + item.endTime() + "on " + ".";
    }
}
