package dataBundles;

import entities.Secretary;

import java.util.ArrayList;

public class SecretaryData {
    private final Secretary secretary;

    public SecretaryData(Secretary secretary) {
        this.secretary = secretary;
    }

    public String getUsername(){
        return secretary.getUsername();
    }

    public Integer getContact(){
        return secretary.getContactInfoId();
    }

    public ArrayList<Integer> getLogs(){
        return secretary.getLogIds();
    }

    public Integer getSecretaryId() {
        return secretary.getId();
    }
}