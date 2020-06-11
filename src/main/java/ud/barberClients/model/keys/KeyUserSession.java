package ud.barberClients.model.keys;

public class KeyUserSession {

    private String companyId;
    private String document;
    private String username;

    public KeyUserSession() {
    }

    public KeyUserSession(String companyId, String document, String username) {
        this.companyId = companyId;
        this.document = document;
        this.username = username;
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
}
