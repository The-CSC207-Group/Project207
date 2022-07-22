package presenter.entityViews;

import dataBundles.TimeBlockData;

import java.util.List;

/**
 * The TimeBlock entity's view.
 */
public class TimeBlockView extends EntityView<TimeBlockData> {

    /**
     * @param item The time block data to view.
     * @return Returns item's full time block view.
     */
    @Override
    public String viewFull(TimeBlockData item) {
        return viewStartTime(item) + "\n"
                + viewEndTime(item);
    }

    /**
     * @param item The time block data to view.
     * @return Returns the time block's start time as a view.
     */
    public String viewStartTime(TimeBlockData item) {
        return "This time block's start time is " + item.getStartTime() + ".";
    }
    /**
     * @param item The time block data to view.
     * @return Returns the time block's end time as a view.
     */
    public String viewEndTime(TimeBlockData item) {
        return "This time block's end time is " + item.getEndTime() + ".";
    }

}
