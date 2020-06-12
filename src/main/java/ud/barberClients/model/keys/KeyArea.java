package ud.barberClients.model.keys;

public class KeyArea {

    private String companyId;
    private Integer areaId;

    public KeyArea() {
    }

    public KeyArea(String companyId, Integer areaId) {
        this.companyId = companyId;
        this.areaId = areaId;
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
}
