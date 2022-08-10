package dataBundles;

import entities.Secretary;

/**
 * Wrapper class for a secretary entity.
 */
public class SecretaryData extends UserData<Secretary> {

    /**
     * Constructor. Calls the UserData<Secretary> constructor which stores the secretary in the parent class and
     * allows for methods from UserData to be used.
     *
     * @param secretary Secretary - secretary to be stored.
     */
    public SecretaryData(Secretary secretary) {
        super(secretary);
    }

}
