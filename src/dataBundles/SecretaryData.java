package dataBundles;

import entities.Secretary;

import java.util.ArrayList;

public class SecretaryData extends DataBundle{
    private final Secretary secretary;

    public SecretaryData(Integer id, Secretary secretary) {
        super(id);
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
}
