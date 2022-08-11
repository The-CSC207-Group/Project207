package presenters.entityViews;

import useCases.dataBundles.AvailabilityData;
import utilities.DayOfWeekUtils;

/**
 * The Availability entity's view.
 */
public class AvailabilityView extends EntityView<AvailabilityData> {
    private final DayOfWeekUtils dayOfWeekUtils = new DayOfWeekUtils();

    /**
     * @param item AvailabilityData  to view.
     * @return String representing item's full availability view.
     */
    @Override
    public String viewFull(AvailabilityData item) {
        return dayOfWeekUtils.dayOfWeekToString(item.getDayOfWeek()) + ": " +
                item.getStartTime() + " to " + item.getEndTime();
    }

}
