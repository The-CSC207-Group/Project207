package entities;

public class Secretary extends User {

    public Secretary(int id, String username, String password, int contactInfo) {
        super(id, username, password, "secretary", contactInfo);
    }

}
