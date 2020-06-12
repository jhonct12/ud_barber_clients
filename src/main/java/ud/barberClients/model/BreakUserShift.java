package ud.barberClients.model;

public class BreakUserShift {

    private Integer userShiftId;
    private Long initialDate;

    private Long finalDate;

    public BreakUserShift(){
    }

    public BreakUserShift(Integer userShiftId, Long initialDate, Long finalDate) {
        this.userShiftId = userShiftId;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
    }


    public BreakUserShift(BreakUserShift breakUserShift) {
        this.userShiftId = breakUserShift.getUserShiftId();
        this.initialDate = breakUserShift.getInitialDate();
        this.finalDate = breakUserShift.getFinalDate();
    }


    public Integer getUserShiftId() {
        return userShiftId;
    }

    public void setUserShiftId(Integer userShiftId) {
        this.userShiftId = userShiftId;
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
}
