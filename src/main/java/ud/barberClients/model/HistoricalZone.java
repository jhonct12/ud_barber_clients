package ud.barberClients.model;

import ud.barberClients.model.keys.KeyHistoricalWithSequence;
import ud.barberClients.model.keys.KeyHistoricalZone;

import javax.persistence.*;

@Entity
@IdClass(KeyHistoricalZone.class)
@Table(name = "historicalZone")
@NamedQueries({
        @NamedQuery(name = HistoricalZone.FIND_BY_COM_INI_FIN_DATE, query = "SELECT h FROM HistoricalZone h where h.companyId = :companyId and h.document = :document and h.areaId = :areaId and h.zoneId = :zoneId and h.initialDate <= :finalDate and h.finalDate >= :initialDate")
})
public class HistoricalZone {

    public static final String FIND_BY_COM_INI_FIN_DATE = "HistoricalZone.findByComIniFinDate";

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

    private Long finalDate;

    public HistoricalZone() {
    }

    public HistoricalZone(String companyId, String document, Long initialDate, Integer areaId, Integer zoneId, Long finalDate) {
        this.companyId = companyId;
        this.document = document;
        this.initialDate = initialDate;
        this.areaId = areaId;
        this.zoneId = zoneId;
        this.finalDate = finalDate;
    }

    public HistoricalZone(HistoricalZone historicalZone) {
        this.companyId = historicalZone.getCompanyId();
        this.document = historicalZone.getDocument();
        this.initialDate = historicalZone.getInitialDate();
        this.areaId = historicalZone.getAreaId();
        this.zoneId = historicalZone.getZoneId();
        this.finalDate = historicalZone.getFinalDate();
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

    public Long getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Long finalDate) {
        this.finalDate = finalDate;
    }

}
