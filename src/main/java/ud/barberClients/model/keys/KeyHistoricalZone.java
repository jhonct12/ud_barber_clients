package ud.barberClients.model.keys;

public class KeyHistoricalZone {

    private String companyId;
    private String document;
    private Long initialDate;
    private Integer areaId;
    private Integer zoneId;

    public KeyHistoricalZone() {
    }

    public KeyHistoricalZone(String companyId, String document, Long initialDate, Integer areaId, Integer zoneId) {
        this.companyId = companyId;
        this.document = document;
        this.initialDate = initialDate;
        this.areaId = areaId;
        this.zoneId = zoneId;
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

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }
}
