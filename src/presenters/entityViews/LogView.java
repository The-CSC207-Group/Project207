package presenters.entityViews;

import dataBundles.LogData;

/**
 * The Log entity's view.
 */
public class LogView extends EntityView<LogData> {

    /**
     * @param item LogData bundle to view.
     * @return String representing item's full log view.
     */
    @Override
    public String viewFull(LogData item) {
        return "Time: " + item.getTime() + ", Message: " + item.getMessage();
    }

}
