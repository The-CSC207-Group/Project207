package entities;

public class Secretary extends User {

    public Secretary(String username, String password, Integer contactInfoId) {
        super(username, password, contactInfoId);
    }

    public Secretary(String username, String password){
        super(username, password, new Contact().getId());
    }

}
