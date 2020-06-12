package ud.barberClients.model.keys;

public class KeyBreakUserShift {

    private Integer userShiftId;
    private Long initialDate;

    public KeyBreakUserShift(){
    }

    public KeyBreakUserShift(Integer userShiftId, Long initialDate) {
        this.userShiftId = userShiftId;
        this.initialDate = initialDate;
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
}
