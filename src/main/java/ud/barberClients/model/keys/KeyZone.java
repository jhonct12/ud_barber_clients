package ud.barberClients.model.keys;

public class KeyZone {

    private String companyId;
    private Integer areaId;
    private Integer zoneId;
    private Integer sequenceNumber;

    public KeyZone() {
    }

    public KeyZone(String companyId, Integer areaId, Integer zoneId, Integer sequenceNumber) {
        this.companyId = companyId;
        this.areaId = areaId;
        this.zoneId = zoneId;
        this.sequenceNumber = sequenceNumber;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
