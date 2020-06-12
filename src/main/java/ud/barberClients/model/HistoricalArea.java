package ud.barberClients.model;

import ud.barberClients.model.keys.KeyHistoricalArea;

import javax.persistence.*;

@Entity
@IdClass(KeyHistoricalArea.class)
@Table(name = "historicalArea")
@NamedQueries({
        @NamedQuery(name = HistoricalArea.FIND_BY_COM_INI_FIN_DATE, query = "SELECT h FROM HistoricalArea h where h.companyId = :companyId and h.document = :document and h.areaId = :areaId and h.initialDate <= :finalDate and h.finalDate >= :initialDate")
})
public class HistoricalArea {

    public static final String FIND_BY_COM_INI_FIN_DATE = "HistoricalArea.findByComIniFinDate";

    @Id
    private String companyId;
    @Id
    private String document;
    @Id
    private Long initialDate;
    @Id
    private Integer areaId;

    private Long finalDate;

    public HistoricalArea() {
    }

    public HistoricalArea(String companyId, String document, Long initialDate, Long finalDate, Integer areaId) {
        this.companyId = companyId;
        this.document = document;
        this.initialDate = initialDate;
        this.areaId = areaId;
        this.finalDate = finalDate;
    }

    public HistoricalArea(HistoricalArea historicalArea) {
        this.companyId = historicalArea.getCompanyId();
        this.document = historicalArea.getDocument();
        this.initialDate = historicalArea.getInitialDate();
        this.finalDate = historicalArea.getFinalDate();
        this.areaId = historicalArea.getAreaId();
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

    public Long getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Long finalDate) {
        this.finalDate = finalDate;
    }

}
