package ud.barberClients.model.keys;

public class KeyHistoricalWithSequence {

    private String companyId;
    private String document;
    private Long initialDate;
    private Integer sequenceNumber;

    public KeyHistoricalWithSequence() {
    }

    public KeyHistoricalWithSequence(String companyId, String document, Long initialDate, Integer sequenceNumber) {
        this.companyId = companyId;
        this.document = document;
        this.initialDate = initialDate;
        this.sequenceNumber = sequenceNumber;
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

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
