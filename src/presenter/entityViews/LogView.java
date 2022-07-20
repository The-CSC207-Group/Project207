package presenter.entityViews;

import dataBundles.LogData;

/**
 * The Log entity's view.
 */
public class LogView extends EntityView<LogData> {

    /**
     * @param item The log data bundle to view.
     * @return Returns item's full log view.
     */
    @Override
    public String viewFull(LogData item) {
        return "Time: " + item.getTime() + ", Message: " + item.getMessage();
    }
}
