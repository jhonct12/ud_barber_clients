package ud.barberClients.model.keys;

public class KeyCompanyShift {

    private String companyId;
    private Integer areaId;
    private Integer companyShiftId;

    public KeyCompanyShift() {
    }

    public KeyCompanyShift(String companyId, Integer areaId, Integer companyShiftId) {
        this.companyId = companyId;
        this.areaId = areaId;
        this.companyShiftId = companyShiftId;
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

    public Integer getCompanyShiftId() {
        return companyShiftId;
    }

    public void setCompanyShiftId(Integer companyShiftId) {
        this.companyShiftId = companyShiftId;
    }
}
