package presenter.entityViews;

import dataBundles.ContactData;

public class ContactView extends EntityView<ContactData> {
    // TODO refactor this

    @Override
    public String viewFull(ContactData item) {
        return viewName(item) + ", "
                + viewEmail(item) + ", "
                + viewPhoneNumber(item) + ", "
                + viewAddress(item) + ", "
                + viewBirthday(item) + ", "
                + viewEmergencyContactName(item) + ", "
                + viewEmergencyContactEmail(item) + ", "
                + viewEmergencyContactPhoneNumber(item) + ", "
                + viewEmergencyRelationship(item);
    }

    public String viewName(ContactData item) {
        return getDefaultString(item.getName(), "unnamed");
    }

    public String viewEmail(ContactData item) {
        return "Email: " + getDefaultStringNA(item.getEmail());
    }

    public String viewPhoneNumber(ContactData item) {
        return "Phone Number: " + getDefaultStringNA("N/A");
    }

    public String viewAddress(ContactData item) {
        return "Address: " + getDefaultStringNA(item.getAddress());
    }

    public String viewBirthday(ContactData item) {
        return "Date of Birth: " + getDefaultStringNA(item.getBirthday().toString());
    }

    public String viewEmergencyContactName(ContactData item) {
        return "Emergency Contact Name: " + getDefaultStringNA(item.getEmergencyContactName());
    }

    public String viewEmergencyContactEmail(ContactData item) {
        return "Emergency Contact Email: " + getDefaultStringNA(item.getEmergencyContactEmail());
    }

    public String viewEmergencyContactPhoneNumber(ContactData item) {
        return "Emergency Contact Phone Number: " + getDefaultStringNA(item.getEmergencyContactPhoneNumber());
    }

    public String viewEmergencyRelationship(ContactData item) {
        return "Emergency Contact Relationship: " + getDefaultStringNA(item.getEmergencyRelationship());
    }

}
