package ud.barberClients.model.keys;

public class KeyHistoricalUserSession {

    private String companyId;
    private String document;
    private String username;
    private Long initialDate;

    public KeyHistoricalUserSession() {
    }

    public KeyHistoricalUserSession(String companyId, String document, String username, Long initialDate) {
        this.companyId = companyId;
        this.document = document;
        this.username = username;
        this.initialDate = initialDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Long initialDate) {
        this.initialDate = initialDate;
    }
}
