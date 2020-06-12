package ud.barberClients.model.keys;

public class KeyFaceCut {

    private String companyId;
    private Integer areaId;
    private Integer zoneId;
    private Integer shopId;
    private String document;
    private Integer faceCutId;

    public KeyFaceCut() {
    }

    public KeyFaceCut(String companyId, Integer areaId, Integer zoneId, Integer shopId, String document, Integer faceCutId) {
        this.companyId = companyId;
        this.areaId = areaId;
        this.zoneId = zoneId;
        this.shopId = shopId;
        this.document = document;
        this.faceCutId = faceCutId;
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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Integer getFaceCutId() {
        return faceCutId;
    }

    public void setFaceCutId(Integer faceCutId) {
        this.faceCutId = faceCutId;
    }
}
