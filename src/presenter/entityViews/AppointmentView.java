package presenter.entityViews;

import entities.Appointment;
import entities.TimeBlock;

import java.util.List;

public class AppointmentView implements EntityListView<Appointment> {
    private String view(Appointment item) {
        TimeBlock timeBlock = item.getTimeBlock();
        return "Appointment starting at " + timeBlock.getStartTime() + " and ending at " + timeBlock.getEndTime()
                + ".";
    }

    @Override
    public String viewFromList(List<Appointment> items) {
        StringBuilder appendedOutput = new StringBuilder("");
        for (Appointment item : items) {
            appendedOutput.append(view(item) + "\n");
        }
        return appendedOutput.toString();
    }
}
