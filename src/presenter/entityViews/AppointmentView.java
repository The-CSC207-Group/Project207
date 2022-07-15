package presenter.entityViews;

import entities.Appointment;
import entities.TimeBlock;

import java.util.List;

public class AppointmentView implements EntityListView<Appointment> {
    private void view(Appointment item) {
        TimeBlock timeBlock = item.getTimeBlock();
        System.out.println("Appointment starting at " + timeBlock.getStartTime() + " and ending at " +
                timeBlock.getEndTime() + ".");
    }

    @Override
    public void viewFromList(List<Appointment> items) {
        for (Appointment item : items) {
            view(item);
        }
    }
}
