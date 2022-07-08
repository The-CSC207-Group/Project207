package entities;

public class Admin extends User {


    public Admin(int id, String username, String password, int contactInfo) {
        super(id, username, password, "admin", contactInfo);
    }

}
