package ud.barberClients.model.keys;

public class KeyUserShift {

    private String companyId;
    private String document;
    private Integer companyShiftId;
    private Integer userShiftId;

    public KeyUserShift(){
    }

    public KeyUserShift(String companyId, String document, Integer companyShiftId, Integer userShiftId) {
        this.companyId = companyId;
        this.document = document;
        this.companyShiftId = companyShiftId;
        this.userShiftId = userShiftId;
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

    public Integer getCompanyShiftId() {
        return companyShiftId;
    }

    public void setCompanyShiftId(Integer companyShiftId) {
        this.companyShiftId = companyShiftId;
    }

    public Integer getUserShiftId() {
        return userShiftId;
    }

    public void setUserShiftId(Integer userShiftId) {
        this.userShiftId = userShiftId;
    }
}
