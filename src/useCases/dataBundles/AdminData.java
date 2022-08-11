package useCases.dataBundles;

import entities.Admin;

/**
 * Wrapper class for admin entity.
 */
public class AdminData extends UserData<Admin> {

    /**
     * Constructor for adminData data bundle.
     *
     * @param admin Admin - admin entity.
     */
    public AdminData(Admin admin) {
        super(admin);
    }

}
