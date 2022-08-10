package presenters.entityViews;

import dataBundles.TimeBlockData;

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
        return "This time block's start time is " + item.getStartDateTime() + ".";
    }

    /**
     * @param item TimeBlockData bundle to view.
     * @return String representing the time block's end time as a view.
     */
    public String viewEndTime(TimeBlockData item) {
        return "This time block's end time is " + item.getEndDateTime() + ".";
    }

}
