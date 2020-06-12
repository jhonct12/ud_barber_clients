package ud.barberClients.model;

public class UserShift {

    private String companyId;
    private String document;
    private Integer companyShiftId;
    private Integer userShiftId;

    private Long initialDate;
    private Long finalDate;
    private Integer areaId;
    private Integer zoneId;
    private Integer shopId;
    private String days;

    public UserShift(){
    }

    public UserShift(String companyId, String document, Integer companyShiftId,Integer userShiftId, Integer sequenceNumber, Long initialDate, Long finalDate, Integer areaId, Integer zoneId, Integer shopId, String days) {
        this.companyId = companyId;
        this.document = document;
        this.companyShiftId = companyShiftId;
        this.userShiftId = userShiftId;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.areaId = areaId;
        this.zoneId = zoneId;
        this.shopId = shopId;
        this.days = days;
    }

    public UserShift(UserShift userShift) {
        this.companyId = userShift.getCompanyId();
        this.document = userShift.getDocument();
        this.companyShiftId = userShift.getCompanyShiftId();
        this.userShiftId = userShift.getUserShiftId();
        this.initialDate = userShift.getInitialDate();
        this.finalDate = userShift.getFinalDate();
        this.areaId = userShift.getAreaId();
        this.zoneId = userShift.getZoneId();
        this.shopId = userShift.getShopId();
        this.days = userShift.getDays();
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

    public Integer getUserShiftId() {
        return userShiftId;
    }

    public void setUserShiftId(Integer userShiftId) {
        this.userShiftId = userShiftId;
    }

    public Integer getCompanyShiftId() {
        return companyShiftId;
    }

    public void setCompanyShiftId(Integer companyShiftId) {
        this.companyShiftId = companyShiftId;
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
