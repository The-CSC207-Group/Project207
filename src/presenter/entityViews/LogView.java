package presenter.entityViews;

import dataBundles.LogData;

public class LogView extends EntityView<LogData> {

    @Override
    public String viewFull(LogData item) {
        return "Time: " + item.getTime() + ", Message: " + item.getMessage();
    }
}
