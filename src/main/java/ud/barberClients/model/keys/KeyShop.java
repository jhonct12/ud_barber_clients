package ud.barberClients.model.keys;

public class KeyShop {

    private String companyId;
    private Integer areaId;
    private Integer zoneId;
    private Integer shopId;
    private Integer sequenceNumber;

    public KeyShop() {
    }

    public KeyShop(String companyId, Integer areaId, Integer zoneId, Integer shopId, Integer sequenceNumber) {
        this.companyId = companyId;
        this.areaId = areaId;
        this.zoneId = zoneId;
        this.shopId = shopId;
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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
