package ud.barberClients.model;

import ud.barberClients.model.keys.KeyUserSession;

import javax.persistence.*;

@Entity
@IdClass(KeyUserSession.class)
@Table(name = "userSession")
@NamedQueries({
        @NamedQuery(name = UserSession.FIND_ALL, query = "SELECT u FROM UserSession u"),
        @NamedQuery(name = UserSession.FIND_BY_USERNAME_STATUS, query = "SELECT u FROM UserSession u WHERE u.username = :username AND u.status = :status"),
        @NamedQuery(name = UserSession.FIND_BY_USERNAME, query = "SELECT u FROM UserSession u WHERE u.username = :username"),
        @NamedQuery(name = UserSession.FIND_BY_COMPANY, query = "SELECT u FROM UserSession u WHERE u.companyId = :companyId"),
        @NamedQuery(name = UserSession.FIND_BY_DOCUMENT, query = "SELECT u FROM UserSession u WHERE u.document = :document")
})
public class UserSession {

    public static final String FIND_ALL = "UserSession.findAll";
    public static final String FIND_BY_USERNAME_STATUS = "UserSession.findByUsernameStatus";
    public static final String FIND_BY_USERNAME = "UserSession.findByUsername";
    public static final String FIND_BY_COMPANY = "UserSession.findByCompany";
    public static final String FIND_BY_DOCUMENT = "UserSession.findByDocument";

    @Id
    private String companyId;
    @Id
    private String document;
    @Id
    private String username;

    private String status;
    private String roleType;
    private String password;


    public UserSession() {
    }

    public UserSession(String companyId, String document, String username, String status, String roleType, String password) {
        this.companyId = companyId;
        this.document = document;
        this.username = username;
        this.status = status;
        this.roleType = roleType;
        this.password = password;
    }

    public UserSession(UserSession userSession) {
        this.companyId = userSession.getCompanyId();
        this.document = userSession.getDocument();
        this.username = userSession.getUsername();
        this.status = userSession.getStatus();
        this.roleType = userSession.getRoleType();
        this.password = userSession.getPassword();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
