package presenter.response;

import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A prescription's details as a response.
 */
public final class PrescriptionDetails {

    private final String header;
    private final String body;
    private final ZonedDateTime expiryDate;

    /**
     * Creates an instance of PrescriptionDetails.
     * @param header String representing this prescription's header.
     * @param body String representing this prescription's body.
     * @param expiryDate ZonedDateTime representing this prescription's expiry date.
     */
    public PrescriptionDetails(String header, String body, ZonedDateTime expiryDate) {
        this.header = header;
        this.body = body;
        this.expiryDate = expiryDate;
    }

    /**
     * @return String representing this prescription's header.
     */
    public String header() {
        return header;
    }

    /**
     * @return String representing this prescription's body.
     */
    public String body() {
        return body;
    }

    /**
     * @return ZonedDateTime representing this prescription's expiry date.
     */
    public ZonedDateTime expiryDate() {
        return expiryDate;
    }

    /**
     * @param obj Object being compared.
     * @return boolean indicating whether obj is "equal to" this instance of PrescriptionDetails.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PrescriptionDetails) obj;
        return Objects.equals(this.header, that.header) &&
                Objects.equals(this.body, that.body) &&
                Objects.equals(this.expiryDate, that.expiryDate);
    }

    /**
     * @return int representing the hash code value for this instance of PrescriptionDetails.
     */
    @Override
    public int hashCode() {
        return Objects.hash(header, body, expiryDate);
    }

    /**
     * @return String representation of this instance of PrescriptionDetails.
     */
    @Override
    public String toString() {
        return "PrescriptionDetails[" +
                "header=" + header + ", " +
                "body=" + body + ", " +
                "expiryDate=" + expiryDate + ']';
    }

}
