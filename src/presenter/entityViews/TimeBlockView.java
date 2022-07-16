package presenter.entityViews;

import dataBundles.TimeBlockData;

import java.util.List;

public class TimeBlockView extends EntityView<TimeBlockData> {

    @Override
    public String viewFull(TimeBlockData item) {
        return viewStartTime(item) + "\n"
                + viewEndTime(item);
    }

    public String viewStartTime(TimeBlockData item) {
        return "This time block's start time is " + item.getStartTime() + ".";
    }

    public String viewEndTime(TimeBlockData item) {
        return "This time block's end time is " + item.getEndTime() + ".";
    }

    public String viewStartTimeFromList(List<TimeBlockData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewStartTime(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }

    public String viewEndTimeFromList(List<TimeBlockData> items) {
        StringBuilder appendedOutput = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            appendedOutput.append(viewEndTime(items.get(i)));
            if (i != items.size() - 1) {
                appendedOutput.append("\n");
            }
        }
        return appendedOutput.toString();
    }
}
