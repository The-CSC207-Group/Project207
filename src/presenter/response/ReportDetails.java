package presenter.response;

import java.util.Objects;

/**
 * A report's details as a response.
 */
public final class ReportDetails {

    private final String header;
    private final String body;

    /**
     * Creates an instance of ReportDetails.
     * @param header String representing this report's header.
     * @param body String representing this report's body.
     */
    public ReportDetails(String header, String body) {
        this.header = header;
        this.body = body;
    }

    /**
     * @return String representing this report's header.
     */
    public String header() {
        return header;
    }

    /**
     * @return String representing this report's body.
     */
    public String body() {
        return body;
    }

    /**
     * @param obj Object being compared.
     * @return boolean indicating whether obj is "equal to" this instance of ReportDetails.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ReportDetails) obj;
        return Objects.equals(this.header, that.header) &&
                Objects.equals(this.body, that.body);
    }
    /**
     * @return int representing the hash code value for this instance of ReportDetails.
     */
    @Override
    public int hashCode() {
        return Objects.hash(header, body);
    }

    /**
     * @return String representing the string representation of this instance of ReportDetails.
     */
    @Override
    public String toString() {
        return "ReportDetails[" +
                "header=" + header + ", " +
                "body=" + body + ']';
    }

}
