package dataBundles;

import entities.User;

import java.util.ArrayList;

public abstract class UserDataBundle<T extends User> extends DataBundle{
    T user;

    public UserDataBundle(T user){
        super(user);
        this.user = user;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public Integer getContact() {
        return user.getContactInfoId();
    }

    public ArrayList<Integer> getLogs() {
        return user.getLogIds();
    }



}
