package entities;

public class Admin extends User {

    public Admin(String username, String password, Contact contactInfo) {
        super(username, password, "admin", contactInfo);
    }
}
