package entities;

public class Secretary extends User {

    // constructors

    public Secretary(String username, String password, Contact contactInfo) {
        super(username, password, "secretary", contactInfo);
    }

    // methods

}
