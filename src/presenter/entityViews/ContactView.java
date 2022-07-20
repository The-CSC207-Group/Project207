package presenter.entityViews;

import dataBundles.ContactData;

/**
 * The Contact entity's view.
 */
public class ContactView extends EntityView<ContactData> {

    /**
     * @param item The contact data bundle to view.
     * @return Returns item's full contact views.
     */
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

    /**
     * @param item The contact data bundle to view.
     * @return Returns the contact's name as a view.
     */
    public String viewName(ContactData item) {
        return getDefaultString(item.getName(), "unnamed");
    }

    /**
     * @param item The contact data bundle to view.
     * @return Returns the contact's email as a view.
     */
    public String viewEmail(ContactData item) {
        return "Email: " + getDefaultStringNA(item.getEmail());
    }

    /**
     * @param item The contact data bundle to view.
     * @return Returns the contact's phone number as a view.
     */
    public String viewPhoneNumber(ContactData item) {
        return "Phone Number: " + getDefaultStringNA("N/A");
    }

    /**
     * @param item The contact data bundle to view.
     * @return Returns the contact's address as a view.
     */
    public String viewAddress(ContactData item) {
        return "Address: " + getDefaultStringNA(item.getAddress());
    }

    /**
     * @param item The contact data bundle to view.
     * @return Returns the contact's birthday as a view.
     */
    public String viewBirthday(ContactData item) {
        return "Date of Birth: " + getDefaultStringNA(item.getBirthday().toString());
    }

    /**
     * @param item The contact data bundle to view.
     * @return Returns the emergency contact's name as a view.
     */
    public String viewEmergencyContactName(ContactData item) {
        return "Emergency Contact Name: " + getDefaultStringNA(item.getEmergencyContactName());
    }

    /**
     * @param item The contact data bundle to view.
     * @return Returns the emergency contact's email as a view.
     */
    public String viewEmergencyContactEmail(ContactData item) {
        return "Emergency Contact Email: " + getDefaultStringNA(item.getEmergencyContactEmail());
    }

    /**
     * @param item The contact data bundle to view.
     * @return Returns the emergency contact's phone number as a view.
     */
    public String viewEmergencyContactPhoneNumber(ContactData item) {
        return "Emergency Contact Phone Number: " + getDefaultStringNA(item.getEmergencyContactPhoneNumber());
    }

    /**
     * @param item The contact data bundle to view.
     * @return Returns the emergency contact's relationship to item's contact as a view.
     */
    public String viewEmergencyRelationship(ContactData item) {
        return "Emergency Contact Relationship: " + getDefaultStringNA(item.getEmergencyRelationship());
    }
}
