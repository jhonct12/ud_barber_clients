package ud.barberClients.model.keys;

public class KeyCompanySettings {

    private String companyId;
    private Integer areaId;
    private Integer zoneId;
    private Integer shopId;

    public KeyCompanySettings() {
    }

    public KeyCompanySettings(String companyId, Integer areaId, Integer zoneId, Integer shopId) {
        this.companyId = companyId;
        this.areaId = areaId;
        this.zoneId = zoneId;
        this.shopId = shopId;
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
}
