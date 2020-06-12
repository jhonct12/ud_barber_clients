package ud.barberClients.model;

import ud.barberClients.model.keys.KeyZone;

import javax.persistence.*;

@Entity
@IdClass(KeyZone.class)
@Table(name = "zone")
@NamedQueries({
        @NamedQuery(name = Zone.FIND_BY_COMPANY_AREA_ID_ZONE_ID, query = "SELECT z FROM Zone z WHERE z.companyId = :companyId AND z.areaId = :areaId AND z.zoneId = :zoneId order by  z.sequenceNumber desc"),
        @NamedQuery(name = Zone.FIND_BY_COMPANY_AREA_ID, query = "SELECT z FROM Zone z WHERE z.companyId = :companyId AND z.areaId = :areaId order by  z.zoneId desc")
})
public class Zone {

    public static final String FIND_BY_COMPANY_AREA_ID_ZONE_ID = "Zone.findByCompanyAreaIdZoneId";
    public static final String FIND_BY_COMPANY_AREA_ID = "Zone.findByCompanyAreaId";

    @Id
    private String companyId;
    @Id
    private Integer areaId;
    @Id
    private Integer zoneId;
    @Id
    private Integer sequenceNumber;

    private String codeZone;
    private String descriptionZone;
    private Integer zoneParentId;

    public Zone() {
    }

    public Zone(String companyId, Integer areaId, Integer zoneId, Integer sequenceNumber, String codeZone, String descriptionZone, Integer zoneParentId) {
        this.companyId = companyId;
        this.areaId = areaId;
        this.zoneId = zoneId;
        this.sequenceNumber = sequenceNumber;
        this.codeZone = codeZone;
        this.descriptionZone = descriptionZone;
        this.zoneParentId = zoneParentId;
    }

    public Zone(Zone zone) {
        this.companyId = zone.getCompanyId();
        this.areaId = zone.getAreaId();
        this.zoneId = zone.getZoneId();
        this.sequenceNumber = zone.getSequenceNumber();
        this.codeZone = zone.getCodeZone();
        this.descriptionZone = zone.getDescriptionZone();
        this.zoneParentId = zone.getZoneParentId();
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

    public String getCodeZone() {
        return codeZone;
    }

    public void setCodeZone(String codeZone) {
        this.codeZone = codeZone;
    }

    public String getDescriptionZone() {
        return descriptionZone;
    }

    public void setDescriptionZone(String descriptionZone) {
        this.descriptionZone = descriptionZone;
    }

    public Integer getZoneParentId() {
        return zoneParentId;
    }

    public void setZoneParentId(Integer zoneParentId) {
        this.zoneParentId = zoneParentId;
    }
}
