package ud.barberClients.model.keys;

public class KeyHistoricalArea {

    private String companyId;
    private String document;
    private Long initialDate;
    private Integer areaId;

    public KeyHistoricalArea() {
    }

    public KeyHistoricalArea(String companyId, String document, Long initialDate, Integer areaId) {
        this.companyId = companyId;
        this.document = document;
        this.initialDate = initialDate;
        this.areaId = areaId;
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

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer sequenceNumber) {
        this.areaId = sequenceNumber;
    }
}
