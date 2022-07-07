package entities;

public class Secretary extends User {

    public Secretary(String username, String password, Contact contactInfo) {
        super(username, password, "secretary", contactInfo);
    }

}
