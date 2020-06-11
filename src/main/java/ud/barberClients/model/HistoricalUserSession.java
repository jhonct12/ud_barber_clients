package ud.barberClients.model;

import ud.barberClients.model.keys.KeyHistoricalUserSession;

import javax.persistence.*;

@Entity
@IdClass(KeyHistoricalUserSession.class)
@Table(name = "historicalUserSession")
@NamedQueries({
        @NamedQuery(name = HistoricalUserSession.FIND_ALL, query = "SELECT h FROM UserInformation u"),
        @NamedQuery(name = HistoricalUserSession.FIND_BY_USERNAME, query = "SELECT h FROM HistoricalUserSession h WHERE h.username = :username"),
        @NamedQuery(name = HistoricalUserSession.FIND_BY_COMPANY_DOCUMENT_USERNAME_DEVICE_ID_FINAL_DATE_ZERO, query = "SELECT h FROM HistoricalUserSession h WHERE h.companyId = :companyId AND " +
                "h.document = :document AND h.username = :username AND h.finalDate = 0 AND h.deviceId = :deviceId ORDER BY h.initialDate DESC"),
        @NamedQuery(name = HistoricalUserSession.FIND_BY_COMPANY_DOCUMENT_USERNAME, query = "SELECT h FROM HistoricalUserSession h WHERE " +
                "h.companyId = :companyId AND h.document = :document AND h.username = :username ORDER BY h.initialDate ASC")
})
public class HistoricalUserSession {

    public static final String FIND_ALL = "HistoricalUserSession.findAll";
    public static final String FIND_BY_USERNAME = "HistoricalUserSession.findByUsername";
    public static final String FIND_BY_COMPANY_DOCUMENT_USERNAME_DEVICE_ID_FINAL_DATE_ZERO = "HistoricalUserSession.findByCompanyDocumentUsernameDeviceIdFinalDateZero";
    public static final String FIND_BY_COMPANY_DOCUMENT_USERNAME = "HistoricalUserSession.findByCompanyDocumentUsername";

    @Id
    private String companyId;
    @Id
    private String document;
    @Id
    private String username;
    @Id
    private Long initialDate;

    private Long finalDate;
    private String deviceId;

    public HistoricalUserSession() {
    }

    public HistoricalUserSession(String companyId, String document, String username, Long initialDate, Long finalDate, String deviceId) {
        this.companyId = companyId;
        this.document = document;
        this.username = username;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.deviceId = deviceId;
    }
    public HistoricalUserSession(HistoricalUserSession historicalUserSession) {
        this.companyId = historicalUserSession.getCompanyId();
        this.document = historicalUserSession.getDocument();
        this.username = historicalUserSession.getUsername();
        this.initialDate = historicalUserSession.getInitialDate();
        this.finalDate = historicalUserSession.getFinalDate();
        this.deviceId = historicalUserSession.getDeviceId();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
