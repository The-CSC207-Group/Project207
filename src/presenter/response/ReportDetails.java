package presenter.response;

import java.util.Objects;

public final class ReportDetails {
    private final String header;
    private final String body;

    public ReportDetails(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String header() {
        return header;
    }

    public String body() {
        return body;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ReportDetails) obj;
        return Objects.equals(this.header, that.header) &&
                Objects.equals(this.body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, body);
    }

    @Override
    public String toString() {
        return "ReportDetails[" +
                "header=" + header + ", " +
                "body=" + body + ']';
    }
}
