package dataBundles;

import entities.User;

import java.util.ArrayList;

public abstract class UserData<T extends User> extends DataBundle {
    T user;

    public UserData(T user){
        super(user);
        this.user = user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public Integer getContact() {
        return user.getContactInfoId();
    }

}
