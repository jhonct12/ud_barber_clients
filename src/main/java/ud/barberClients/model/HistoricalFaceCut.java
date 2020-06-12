package ud.barberClients.model;

public class HistoricalFaceCut {

    private Integer faceCutId;
    private String documentSelected;
    private Long initialDate;

    private Long finalDate;
    private String document;
    private String listFaceCut;

    private String deviceId;
    private Double latitude;
    private Double longitude;
    private String totalPay;

    public HistoricalFaceCut(){
    }


    public HistoricalFaceCut(Integer faceCutId, String documentSelected, Long initialDate, Long finalDate, String document, String listFaceCut, String deviceId, Double latitude, Double longitude, String totalPay) {
        this.faceCutId = faceCutId;
        this.documentSelected = documentSelected;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.document = document;
        this.listFaceCut = listFaceCut;
        this.deviceId = deviceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.totalPay = totalPay;
    }

    public HistoricalFaceCut(HistoricalFaceCut historicalFaceCut) {
        this.faceCutId = historicalFaceCut.getFaceCutId();
        this.documentSelected = historicalFaceCut.getDocumentSelected();
        this.initialDate = historicalFaceCut.getInitialDate();
        this.finalDate = historicalFaceCut.getFinalDate();
        this.document = historicalFaceCut.getDocument();
        this.listFaceCut = historicalFaceCut.getListFaceCut();
        this.deviceId = historicalFaceCut.getDeviceId();
        this.latitude = historicalFaceCut.getLatitude();
        this.longitude = historicalFaceCut.getLongitude();
        this.totalPay = historicalFaceCut.getTotalPay();
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

    public Long getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Long finalDate) {
        this.finalDate = finalDate;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getListFaceCut() {
        return listFaceCut;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setListFaceCut(String listFaceCut) {
        this.listFaceCut = listFaceCut;
    }

    public String getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(String totalPay) {
        this.totalPay = totalPay;
    }
}
