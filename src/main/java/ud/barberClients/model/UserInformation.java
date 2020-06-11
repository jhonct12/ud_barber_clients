package ud.barberClients.model;

import javax.persistence.*;

@Entity
@Table(name = "userInformation")
@NamedQueries({
        @NamedQuery(name = UserInformation.FIND_ALL, query = "SELECT u FROM UserInformation u")
})
public class UserInformation {

    public static final String FIND_ALL = "UserInformation.findAll";

    @Id
    private String document;
    private String documentType;
    private String name;
    private String surname;
    private String location;
    private String address;
    private Long birthDate;
    private String genderType;
    private String bloodType;
    private String email;
    private String telephone;
    private String deviceId;

    public UserInformation() {
    }

    public UserInformation(String document, String documentType, String name, String surname, String location, String address, Long birthDate, String genderType, String bloodType, String email, String telephone, String deviceId) {
        this.document = document;
        this.documentType = documentType;
        this.name = name;
        this.surname = surname;
        this.location = location;
        this.address = address;
        this.birthDate = birthDate;
        this.genderType = genderType;
        this.bloodType = bloodType;
        this.email = email;
        this.telephone = telephone;
        this.deviceId = deviceId;
    }

    public UserInformation(UserInformation userInformation) {
        this.document = userInformation.getDocument();
        this.documentType = userInformation.getDocumentType();
        this.name = userInformation.getName();
        this.surname = userInformation.getSurname();
        this.location = userInformation.getLocation();
        this.address = userInformation.getAddress();
        this.birthDate = userInformation.getBirthDate();
        this.genderType = userInformation.getGenderType();
        this.bloodType = userInformation.getBloodType();
        this.email = userInformation.getEmail();
        this.telephone = userInformation.getTelephone();
        this.deviceId = userInformation.getDeviceId();
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
    }

    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
