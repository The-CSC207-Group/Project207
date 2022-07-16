package presenter.entityViews;

import dataBundles.LogData;

public class LogView extends EntityView<LogData> {

    @Override
    public String view(LogData item) {
        return "Time: " + item.getTime() + ", Message: " + item.getMessage();
    }
}
