package presenter.entityViews;

import dataBundles.ContactData;

/**
 * The Contact entity's view.
 */
public class ContactView extends EntityView<ContactData> {

    /**
     * @param item ContactData bundle to view.
     * @return String representing item's full contact view.
     */
    @Override
    public String viewFull(ContactData item) {
        return viewName(item) + ",\n"
                + viewEmail(item) + ",\n"
                + viewPhoneNumber(item) + ",\n"
                + viewAddress(item) + ",\n"
                + viewBirthday(item) + ",\n"
                + viewEmergencyContactName(item) + ",\n"
                + viewEmergencyContactEmail(item) + ",\n"
                + viewEmergencyContactPhoneNumber(item) + ",\n"
                + viewEmergencyRelationship(item);
    }

    /**
     * @param item ContactData bundle to view.
     * @return String representing the contact's name as a view.
     */
    public String viewName(ContactData item) {
        return getDefaultString(item.getName(), "unnamed");
    }

    /**
     * @param item ContactData bundle to view.
     * @return String representing the contact's email as a view.
     */
    public String viewEmail(ContactData item) {
        return "Email: " + getDefaultStringNA(item.getEmail());
    }

    /**
     * @param item ContactData bundle to view.
     * @return String representing the contact's phone number as a view.
     */
    public String viewPhoneNumber(ContactData item) {
        return "Phone Number: " + getDefaultStringNA("N/A");
    }

    /**
     * @param item ContactData bundle to view.
     * @return String representing the contact's address as a view.
     */
    public String viewAddress(ContactData item) {
        return "Address: " + getDefaultStringNA(item.getAddress());
    }

    /**
     * @param item ContactData bundle to view.
     * @return String representing the contact's birthday as a view.
     */
    public String viewBirthday(ContactData item) {
        String birthday;

        if (item.getBirthday() != null) {
            birthday = item.getBirthday().toString();
        } else {
            birthday = null;
        }

        return "Date of Birth: " + getDefaultStringNA(birthday);
    }

    /**
     * @param item ContactData bundle to view.
     * @return String representing the emergency contact's name as a view.
     */
    public String viewEmergencyContactName(ContactData item) {
        return "Emergency Contact Name: " + getDefaultStringNA(item.getEmergencyContactName());
    }

    /**
     * @param item ContactData bundle to view.
     * @return String representing the emergency contact's email as a view.
     */
    public String viewEmergencyContactEmail(ContactData item) {
        return "Emergency Contact Email: " + getDefaultStringNA(item.getEmergencyContactEmail());
    }

    /**
     * @param item ContactData bundle to view.
     * @return String representing the emergency contact's phone number as a view.
     */
    public String viewEmergencyContactPhoneNumber(ContactData item) {
        return "Emergency Contact Phone Number: " + getDefaultStringNA(item.getEmergencyContactPhoneNumber());
    }

    /**
     * @param item ContactData bundle to view.
     * @return String representing the emergency contact's relationship as a view.
     */
    public String viewEmergencyRelationship(ContactData item) {
        return "Emergency Contact Relationship: " + getDefaultStringNA(item.getEmergencyRelationship());
    }
}
