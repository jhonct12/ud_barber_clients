package ud.barberClients.model;

import ud.barberClients.model.keys.KeyShop;
import ud.barberClients.model.keys.KeyZone;

import javax.persistence.*;

@Entity
@IdClass(KeyShop.class)
@Table(name = "shop")
@NamedQueries({
        @NamedQuery(name = Shop.FIND_BY_COMPANY_AREA_ID_ZONE_ID_SHOP_ID, query = "SELECT s FROM Shop s WHERE s.companyId = :companyId AND s.areaId = :areaId AND s.zoneId = :zoneId AND s.shopId = :shopId order by  s.sequenceNumber desc"),
        @NamedQuery(name = Shop.FIND_BY_COMPANY_AREA_ID_ZONE_ID, query = "SELECT s FROM Shop s WHERE s.companyId = :companyId AND s.areaId = :areaId AND s.zoneId = :zoneId order by  s.shopId desc")
})
public class Shop {

    public static final String FIND_BY_COMPANY_AREA_ID_ZONE_ID_SHOP_ID = "Zone.findByCompanyAreaIdZoneIdShopId";
    public static final String FIND_BY_COMPANY_AREA_ID_ZONE_ID = "Zone.findByCompanyAreaIdZoneId";


    @Id
    private String companyId;
    @Id
    private Integer areaId;
    @Id
    private Integer zoneId;
    @Id
    private Integer shopId;
    @Id
    private Integer sequenceNumber;

    private String codeShop;
    private String descriptionShop;
    private Double gpsLatitude;
    private Double gpgLongitude;
    private Integer gpsError;
    private Integer shopParentId;

    public Shop() {
    }

    public Shop(String companyId, Integer areaId, Integer zoneId, Integer shopId, Integer sequenceNumber, String codeShop, String descriptionShop, Double gpsLatitude, Double gpgLongitude, Integer gpsError, Integer shopParentId) {
        this.companyId = companyId;
        this.areaId = areaId;
        this.zoneId = zoneId;
        this.shopId = shopId;
        this.sequenceNumber = sequenceNumber;
        this.codeShop = codeShop;
        this.descriptionShop = descriptionShop;
        this.gpsLatitude = gpsLatitude;
        this.gpgLongitude = gpgLongitude;
        this.gpsError = gpsError;
        this.shopParentId = shopParentId;
    }

    public Shop(Shop shop) {
        this.companyId = shop.getCompanyId();
        this.areaId = shop.getAreaId();
        this.zoneId = shop.getZoneId();
        this.shopId = shop.getShopId();
        this.sequenceNumber = shop.getSequenceNumber();
        this.codeShop = shop.getCodeShop();
        this.descriptionShop = shop.getDescriptionShop();
        this.gpsLatitude = shop.getGpsLatitude();
        this.gpgLongitude = shop.getGpgLongitude();
        this.gpsError = shop.getGpsError();
        this.shopParentId = shop.getShopParentId();
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

    public String getCodeShop() {
        return codeShop;
    }

    public void setCodeShop(String codeShop) {
        this.codeShop = codeShop;
    }

    public String getDescriptionShop() {
        return descriptionShop;
    }

    public void setDescriptionShop(String descriptionShop) {
        this.descriptionShop = descriptionShop;
    }

    public Double getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(Double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public Double getGpgLongitude() {
        return gpgLongitude;
    }

    public void setGpgLongitude(Double gpgLongitude) {
        this.gpgLongitude = gpgLongitude;
    }

    public Integer getGpsError() {
        return gpsError;
    }

    public void setGpsError(Integer gpsError) {
        this.gpsError = gpsError;
    }

    public Integer getShopParentId() {
        return shopParentId;
    }

    public void setShopParentId(Integer shopParentId) {
        this.shopParentId = shopParentId;
    }
}
