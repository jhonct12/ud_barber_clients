package ud.barberClients.model.keys;

public class KeyHistoricalFaceCut {


    private Integer faceCutId;
    private String documentSelected;
    private Long initialDate;

    public KeyHistoricalFaceCut() {
    }

    public KeyHistoricalFaceCut(Integer faceCutId, String documentSelected, Long initialDate) {
        this.faceCutId = faceCutId;
        this.documentSelected = documentSelected;
        this.initialDate = initialDate;
    }

    public Integer getFaceCutId() {
        return faceCutId;
    }

    public void setFaceCutId(Integer faceCutId) {
        this.faceCutId = faceCutId;
    }

    public String getDocumentSelected() {
        return documentSelected;
    }

    public void setDocumentSelected(String documentSelected) {
        this.documentSelected = documentSelected;
    }

    public Long getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Long initialDate) {
        this.initialDate = initialDate;
    }
}
