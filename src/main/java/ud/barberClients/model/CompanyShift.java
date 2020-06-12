package ud.barberClients.model;

import javax.persistence.Id;

public class CompanyShift {

    @Id
    private String companyId;
    @Id
    private Integer areaId;
    @Id
    private Integer companyShiftId;

    private String title;
    private String description;
    private Long initialDate;
    private Long finalDate;

    private Integer minInitialDateBefore;
    private Integer minInitialDateAfter;
    private Integer minFinalDateBefore;
    private Integer minFinalDateAfter;
    private Integer cantHourDay;

    public CompanyShift(){
    }

    public CompanyShift(String companyId, Integer areaId, Integer companyShiftId, String title, String description, Long initialDate, Long finalDate, Integer minInitialDateBefore, Integer minInitialDateAfter, Integer minFinalDateBefore, Integer minFinalDateAfter, Integer cantHourDay) {
        this.companyId = companyId;
        this.areaId = areaId;
        this.companyShiftId = companyShiftId;
        this.title = title;
        this.description = description;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.minInitialDateBefore = minInitialDateBefore;
        this.minInitialDateAfter = minInitialDateAfter;
        this.minFinalDateBefore = minFinalDateBefore;
        this.minFinalDateAfter = minFinalDateAfter;
        this.cantHourDay = cantHourDay;
    }

    public CompanyShift(CompanyShift companyShift) {
        this.companyId = companyShift.getCompanyId();
        this.areaId = companyShift.getAreaId();
        this.companyShiftId = companyShift.getCompanyShiftId();
        this.title = companyShift.getTitle();
        this.description = companyShift.getDescription();
        this.initialDate = companyShift.getInitialDate();
        this.finalDate = companyShift.getFinalDate();
        this.minInitialDateBefore = companyShift.getMinInitialDateBefore();
        this.minInitialDateAfter = companyShift.getMinInitialDateAfter();
        this.minFinalDateBefore = companyShift.getMinFinalDateBefore();
        this.minFinalDateAfter = companyShift.getMinFinalDateAfter();
        this.cantHourDay = companyShift.getCantHourDay();
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

    public Integer getMinInitialDateBefore() {
        return minInitialDateBefore;
    }

    public void setMinInitialDateBefore(Integer minInitialDateBefore) {
        this.minInitialDateBefore = minInitialDateBefore;
    }

    public Integer getMinInitialDateAfter() {
        return minInitialDateAfter;
    }

    public void setMinInitialDateAfter(Integer minInitialDateAfter) {
        this.minInitialDateAfter = minInitialDateAfter;
    }

    public Integer getMinFinalDateBefore() {
        return minFinalDateBefore;
    }

    public void setMinFinalDateBefore(Integer minFinalDateBefore) {
        this.minFinalDateBefore = minFinalDateBefore;
    }

    public Integer getMinFinalDateAfter() {
        return minFinalDateAfter;
    }

    public void setMinFinalDateAfter(Integer minFinalDateAfter) {
        this.minFinalDateAfter = minFinalDateAfter;
    }

    public Integer getCantHourDay() {
        return cantHourDay;
    }

    public void setCantHourDay(Integer cantHourDay) {
        this.cantHourDay = cantHourDay;
    }
}
