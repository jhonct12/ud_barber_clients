package ud.barberClients.model;

import ud.barberClients.model.keys.KeyArea;

import javax.persistence.*;

@Entity
@IdClass(KeyArea.class)
@Table(name = "area")
@NamedQueries({
        @NamedQuery(name = Area.FIND_BY_COMPANY_ID, query = "SELECT a FROM Area a where a.companyId = :companyId order by a.areaId desc"),
        @NamedQuery(name = Area.FIND_BY_COM_AREA_ID_AREA_CODE, query = "SELECT a FROM Area a where a.companyId = :companyId and a.areaId = :areaId and a.codeArea = :areaCode")
})
public class Area {

    public static final String FIND_BY_COMPANY_ID = "HistoricalArea.findByCompanyId";
    public static final String FIND_BY_COM_AREA_ID_AREA_CODE = "HistoricalArea.findByComAreaIdAreaCode";

    @Id
    private String companyId;
    @Id
    private Integer areaId;

    private String codeArea;

    private String descriptionArea;

    public Area() {
    }

    public Area(String companyId, Integer areaId, String codeArea, String descriptionArea) {
        this.companyId = companyId;
        this.areaId = areaId;
        this.codeArea = codeArea;
        this.descriptionArea = descriptionArea;
    }

    public Area(Area area) {
        this.companyId = area.getCompanyId();
        this.areaId = area.getAreaId();
        this.codeArea = area.getCompanyId();
        this.descriptionArea = area.getDescriptionArea();
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

    public String getCodeArea() {
        return codeArea;
    }

    public void setCodeArea(String areaCode) {
        this.codeArea = areaCode;
    }

    public String getDescriptionArea() {
        return descriptionArea;
    }

    public void setDescriptionArea(String description) {
        this.descriptionArea = description;
    }
}
