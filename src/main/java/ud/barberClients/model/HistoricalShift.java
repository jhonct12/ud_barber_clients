package ud.barberClients.model;

public class HistoricalShift {

    private String companyId;
    private String document;
    private Long initialDate;

    private Long finalDate;
    private Integer areaId;
    private Integer zoneId;
    private Integer shopId;

    public HistoricalShift(){
    }

    public HistoricalShift(String companyId, String document, Long initialDate, Long finalDate, Integer areaId, Integer zoneId, Integer shopId) {
        this.companyId = companyId;
        this.document = document;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.areaId = areaId;
        this.zoneId = zoneId;
        this.shopId = shopId;
    }

    public HistoricalShift(HistoricalShift historicalShift) {
        this.companyId = historicalShift.getCompanyId();
        this.document = historicalShift.getDocument();
        this.initialDate = historicalShift.getInitialDate();
        this.finalDate = historicalShift.getFinalDate();
        this.areaId = historicalShift.getAreaId();
        this.zoneId = historicalShift.getZoneId();
        this.shopId = historicalShift.getShopId();
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

    public Long getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Long finalDate) {
        this.finalDate = finalDate;
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
