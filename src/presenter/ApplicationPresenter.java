/*
 *
 *
 *
 * DEPRECATED: THIS FILE WILL BE REMOVED AFTER ALL USAGES ARE GONE.
 *
 * DEPRECATED: THIS FILE WILL BE REMOVED AFTER ALL USAGES ARE GONE.
 *
 * DEPRECATED: THIS FILE WILL BE REMOVED AFTER ALL USAGES ARE GONE.
 *
 * DEPRECATED: THIS FILE WILL BE REMOVED AFTER ALL USAGES ARE GONE.
 *
 * DEPRECATED: THIS FILE WILL BE REMOVED AFTER ALL USAGES ARE GONE.
 *
 * DEPRECATED: THIS FILE WILL BE REMOVED AFTER ALL USAGES ARE GONE.
 *
 * DEPRECATED: THIS FILE WILL BE REMOVED AFTER ALL USAGES ARE GONE.
 *
 * DEPRECATED: THIS FILE WILL BE REMOVED AFTER ALL USAGES ARE GONE.
 *
 *
 *
 *
 *
 */

package presenter;

import java.util.HashMap;
import java.util.List;


public interface ApplicationPresenter {

    void infoMessage(String message);

    void successMessage(String message);

    void warningMessage(String message);

    void errorMessage(String message);

    HashMap<String, String> promptPopup(List<String> fields);

    String promptPopup(String prompt);
}
