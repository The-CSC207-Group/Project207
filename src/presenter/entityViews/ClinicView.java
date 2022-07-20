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
        String clinicName = getDefaultStringNA(item.getClinicName());
        return "This clinic's name is " + clinicName + ".";
    }

    public String viewAddress(ClinicData item) {
        String address = getDefaultStringNA(item.getAddress());
        return "This clinic's address is " + address + ".";
    }

    public String viewTimeZone(ClinicData item) {
        String timeZone = getDefaultStringNA(item.getTimeZone().toString());
        return "This clinic's time zone is " + timeZone + ".";
    }

    public String viewClinicHours(ClinicData item) {
        String startTime = getDefaultStringNA(item.getClinicHours().getStartTime().toString());
        String endTime = getDefaultStringNA(item.getClinicHours().getEndTime().toString());
        return "This clinic is open from " + startTime + "to" + endTime + ".";
    }

    public String viewPhoneNumber(ClinicData item) {
        String phoneNumber = getDefaultStringNA(item.getPhoneNumber().toString());
        return "This clinic's phone number is " + phoneNumber + ".";
    }
}
