package presenter.entityViews;

import dataBundles.TimeBlockData;

import java.util.List;

/**
 * The TimeBlock entity's view.
 */
public class TimeBlockView extends EntityView<TimeBlockData> {

    /**
     * @param item TimeBlockData bundle to view.
     * @return String representing item's full time block view.
     */
    @Override
    public String viewFull(TimeBlockData item) {
        return viewStartTime(item) + "\n"
                + viewEndTime(item);
    }

    /**
     * @param item TimeBlockData bundle to view.
     * @return String representing the time block's start time as a view.
     */
    public String viewStartTime(TimeBlockData item) {
        return "This time block's start time is " + item.getStartTime() + ".";
    }
    /**
     * @param item TimeBlockData bundle to view.
     * @return String representing the time block's end time as a view.
     */
    public String viewEndTime(TimeBlockData item) {
        return "This time block's end time is " + item.getEndTime() + ".";
    }

}
