package presenter.entityViews;

import dataBundles.ClinicData;

public class ClinicView extends EntityView<ClinicData> {

    @Override
    public String viewFull(ClinicData item) {
        return viewClinicName(item) + "\n"
                + viewAddress(item) + "\n"
                + viewTimeZone(item) + "\n"
                + viewClinicHours(item) + "\n"
                + viewPhoneNumber(item);
    }

    public String viewClinicName(ClinicData item) {
        return "This clinic's name is " + item.getClinicName() + ".";
    }

    public String viewAddress(ClinicData item) {
        return "This clinic's address is " + item.getAddress() + ".";
    }

    public String viewTimeZone(ClinicData item) {
        return "This clinic's time zone is " + item.getTimeZone() + ".";
    }

    public String viewClinicHours(ClinicData item) {
        return "This clinic is open from " + item.getClinicHours().getStartTime() + "to"
                + item.getClinicHours().getEndTime() + ".";
    }

    public String viewPhoneNumber(ClinicData item) {
        return "This clinic's phone number is " + item.getPhoneNumber() + ".";
    }
}
