package ud.barberClients.model;

import ud.barberClients.model.keys.KeyHistoricalShop;

import javax.persistence.*;

@Entity
@IdClass(KeyHistoricalShop.class)
@Table(name = "historicalShop")
@NamedQueries({
        @NamedQuery(name = HistoricalShop.FIND_BY_COM_INI_FIN_DATE, query = "SELECT h FROM HistoricalShop h where h.companyId = :companyId and h.document = :document and h.areaId = :areaId and h.zoneId = :zoneId and h.shopId = :shopId and h.initialDate <= :finalDate and h.finalDate >= :initialDate")
})
public class HistoricalShop {

    public static final String FIND_BY_COM_INI_FIN_DATE = "historicalShop.findByComIniFinDate";

    @Id
    private String companyId;
    @Id
    private String document;
    @Id
    private Long initialDate;
    @Id
    private Integer areaId;
    @Id
    private Integer zoneId;
    @Id
    private Integer shopId;

    private Long finalDate;

    public HistoricalShop() {
    }

    public HistoricalShop(String companyId, String document, Long initialDate, Integer areaId, Integer zoneId, Integer shopId, Long finalDate) {
        this.companyId = companyId;
        this.document = document;
        this.initialDate = initialDate;
        this.areaId = areaId;
        this.zoneId = zoneId;
        this.shopId = shopId;
        this.finalDate = finalDate;
    }

    public HistoricalShop(HistoricalShop historicalShop) {
        this.companyId = historicalShop.getCompanyId();
        this.document = historicalShop.getDocument();
        this.initialDate = historicalShop.getInitialDate();
        this.areaId = historicalShop.getAreaId();
        this.zoneId = historicalShop.getZoneId();
        this.shopId = historicalShop.getShopId();
        this.finalDate = historicalShop.getFinalDate();
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

    public Long getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Long finalDate) {
        this.finalDate = finalDate;
    }
}
