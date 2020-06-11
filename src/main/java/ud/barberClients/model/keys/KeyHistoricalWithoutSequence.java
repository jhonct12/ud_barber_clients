package ud.barberClients.model.keys;

public class KeyHistoricalWithoutSequence {

    private String companyId;
    private String document;
    private Long initialDate;

    public KeyHistoricalWithoutSequence() {
    }

    public KeyHistoricalWithoutSequence(String companyId, String document, Long initialDate) {
        this.companyId = companyId;
        this.document = document;
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

    public Long getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Long initialDate) {
        this.initialDate = initialDate;
    }
}
