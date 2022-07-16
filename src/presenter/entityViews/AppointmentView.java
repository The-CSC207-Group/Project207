package presenter.entityViews;

import entities.Appointment;
import entities.TimeBlock;

import java.util.List;


public class AppointmentView {
    public String view(Appointment item) {
        TimeBlock timeBlock = item.getTimeBlock();
        return "Appointment starting at " + timeBlock.getStartTime() + " and ending at " + timeBlock.getEndTime()
                + ".";
    }

    public String viewFromList(List<Appointment> items) {
        StringBuilder appendedOutput = new StringBuilder("");
        for (Appointment item : items) {
            appendedOutput.append(view(item)).append("\n");
        }
        return appendedOutput.toString();
    }
}
