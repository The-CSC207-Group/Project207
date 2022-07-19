package presenter.response;

import java.time.ZonedDateTime;
import java.util.Objects;


public final class PrescriptionDetails {
    private final String header;
    private final String body;
    private final ZonedDateTime expiryDate;

    public PrescriptionDetails(String header, String body, ZonedDateTime expiryDate) {
        this.header = header;
        this.body = body;
        this.expiryDate = expiryDate;
    }

    public String header() {
        return header;
    }

    public String body() {
        return body;
    }

    public ZonedDateTime expiryDate() {
        return expiryDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PrescriptionDetails) obj;
        return Objects.equals(this.header, that.header) &&
                Objects.equals(this.body, that.body) &&
                Objects.equals(this.expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, body, expiryDate);
    }

    @Override
    public String toString() {
        return "PrescriptionDetails[" +
                "header=" + header + ", " +
                "body=" + body + ", " +
                "expiryDate=" + expiryDate + ']';
    }


}
