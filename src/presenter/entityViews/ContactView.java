package presenter.entityViews;

import dataBundles.ContactData;

public class ContactView extends EntityView<ContactData> {

    @Override
    public String viewFull(ContactData item) {
        return viewName(item) + "\n"
                + viewEmail(item) + "\n"
                + viewPhoneNumber(item) + "\n"
                + viewAddress(item) + "\n"
                + viewBirthday(item) + "\n"
                + viewEmergencyContactName(item) + "\n"
                + viewEmergencyContactEmail(item) + "\n"
                + viewEmergencyContactPhoneNumber(item) + "\n"
                + viewEmergencyRelationship(item);
    }

    public String viewName(ContactData item) {
        return "Your name is " + item.getName() + ".";
    }

    public String viewEmail(ContactData item) {
        return "Your email is " + item.getEmail() + ".";
    }

    public String viewPhoneNumber(ContactData item) {
        return "Your phone number is " + item.getPhoneNumber() + ".";
    }

    public String viewAddress(ContactData item) {
        return "Your address is " + item.getAddress() + ".";
    }

    public String viewBirthday(ContactData item) {
        return "Your date of birth is " + item.getBirthday().toString() + ".";
    }

    public String viewEmergencyContactName(ContactData item) {
        return "Your emergency contact name is " + item.getEmergencyContactName() + ".";
    }

    public String viewEmergencyContactEmail(ContactData item) {
        return "Your emergency contact email is " + item.getEmergencyContactEmail() + ".";
    }

    public String viewEmergencyContactPhoneNumber(ContactData item) {
        return "Your emergency contact phone number is " + item.getEmergencyContactPhoneNumber() + ".";
    }

    public String viewEmergencyRelationship(ContactData item) {
        return "Your emergency contact is your " + item.getEmergencyRelationship() + ".";
    }

}
