package ud.barberClients.model;

import ud.barberClients.model.keys.KeyFaceCut;

import javax.persistence.*;

@Entity
@IdClass(KeyFaceCut.class)
@Table(name = "faceCut")
@NamedQueries({
        @NamedQuery(name = FaceCut.FIND_BY_COMPANY_ID_AREA_ID_ZONE_ID_SHOP_ID_DOCUMENT, query = "SELECT f FROM FaceCut f where f.companyId = :companyId and f.areaId = :areaId and f.zoneId = :zoneId and f.shopId = :shopId and f.document = :document order by f.faceCutId desc"),
})
public class FaceCut {

    public static final String FIND_BY_COMPANY_ID_AREA_ID_ZONE_ID_SHOP_ID_DOCUMENT = "FaceCut.findByCompanyId";

    private String companyId;
    private Integer areaId;
    private Integer zoneId;
    private Integer shopId;
    private String document;
    private Integer faceCutId;

    private String title;
    private String description;
    private String pay;
    private Integer durationTime;

    public FaceCut(){
    }

    public FaceCut(String companyId, Integer areaId, Integer zoneId, Integer shopId, String document, Integer faceCutId, String title, String description, String pay, Integer durationTime) {
        this.companyId = companyId;
        this.areaId = areaId;
        this.zoneId = zoneId;
        this.shopId = shopId;
        this.document = document;
        this.faceCutId = faceCutId;
        this.title = title;
        this.description = description;
        this.pay = pay;
        this.durationTime = durationTime;
    }

    public FaceCut(FaceCut faceCut) {
        this.companyId = faceCut.getCompanyId();
        this.areaId = faceCut.getAreaId();
        this.zoneId = faceCut.getZoneId();
        this.shopId = faceCut.getShopId();
        this.document = faceCut.getDocument();
        this.faceCutId = faceCut.getFaceCutId();
        this.title = faceCut.getTitle();
        this.description = faceCut.getDescription();
        this.pay = faceCut.getPay();
        this.durationTime = faceCut.getDurationTime();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public Integer getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {
        this.durationTime = durationTime;
    }
}
