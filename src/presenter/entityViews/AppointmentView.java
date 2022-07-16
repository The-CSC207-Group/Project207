package presenter.entityViews;

import entities.Appointment;
import entities.TimeBlock;

import java.util.List;


public class AppointmentView {

    public String view(Appointment item) {
        // TODO not a fan of plus notation, use StringBuilder
        TimeBlock timeBlock = item.getTimeBlock();
        return "Appointment starting at " + timeBlock.getStartTime() + " and ending at " + timeBlock.getEndTime()
                + ".";
    }

    public String viewFromList(List<Appointment> items) {
        // I'm sure similar code to this will be used throughout the entity views
        // TODO if the need arises, abstract this to a helper or common super class
        StringBuilder appendedOutput = new StringBuilder("");
        for (int i = 0; i < items.size(); i++) {
            if (i != items.size() - 1) {
                appendedOutput.append(view(items.get(i)));
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }
}
