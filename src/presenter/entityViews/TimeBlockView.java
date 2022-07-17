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

}
