package ud.barberClients.model;


import ud.barberClients.model.keys.KeyCompanySettings;

import javax.persistence.*;

@Entity
@IdClass(KeyCompanySettings.class)
@Table(name = "companySettings")
public class CompanySettings {

    private String companyId;
    private Integer areaId;
    private Integer zoneId;
    private Integer shopId;

    private Boolean allowFace;
    private Boolean allowFaceUserFinal;
    private Integer percentageFace;
    private Boolean allowLocation;
    private Boolean allowDeviceId;
    private Boolean allowRegisterFaceCut;
    private Boolean allowRemoveFaceCut;
    private Integer timeBeforeRemoveFaceCut;

    public CompanySettings(){
    }

    public CompanySettings(String companyId, Integer areaId, Integer zoneId, Integer shopId, Boolean allowFace, Boolean allowFaceUserFinal, Integer percentageFace, Boolean allowLocation, Boolean allowDeviceId, Boolean allowRegisterFaceCut, Boolean allowRemoveFaceCut, Integer timeBeforeRemoveFaceCut) {
        this.companyId = companyId;
        this.areaId = areaId;
        this.zoneId = zoneId;
        this.shopId = shopId;
        this.allowFace = allowFace;
        this.allowFaceUserFinal = allowFaceUserFinal;
        this.percentageFace = percentageFace;
        this.allowLocation = allowLocation;
        this.allowDeviceId = allowDeviceId;
        this.allowRegisterFaceCut = allowRegisterFaceCut;
        this.allowRemoveFaceCut = allowRemoveFaceCut;
        this.timeBeforeRemoveFaceCut = timeBeforeRemoveFaceCut;
    }

    public CompanySettings(CompanySettings companySettings) {
        this.companyId = companySettings.getCompanyId();
        this.areaId = companySettings.getAreaId();
        this.zoneId = companySettings.getZoneId();
        this.shopId = companySettings.getShopId();
        this.allowFace = companySettings.getAllowFace();
        this.allowFaceUserFinal = companySettings.getAllowFaceUserFinal();
        this.percentageFace = companySettings.getPercentageFace();
        this.allowLocation = companySettings.getAllowLocation();
        this.allowDeviceId = companySettings.getAllowDeviceId();
        this.allowRegisterFaceCut = companySettings.getAllowRegisterFaceCut();
        this.allowRemoveFaceCut = companySettings.getAllowRemoveFaceCut();
        this.timeBeforeRemoveFaceCut = companySettings.getTimeBeforeRemoveFaceCut();
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

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Boolean getAllowFace() {
        return allowFace;
    }

    public void setAllowFace(Boolean allowFace) {
        this.allowFace = allowFace;
    }

    public Boolean getAllowFaceUserFinal() {
        return allowFaceUserFinal;
    }

    public void setAllowFaceUserFinal(Boolean allowFaceUserFinal) {
        this.allowFaceUserFinal = allowFaceUserFinal;
    }

    public Integer getPercentageFace() {
        return percentageFace;
    }

    public void setPercentageFace(Integer percentageFace) {
        this.percentageFace = percentageFace;
    }

    public Boolean getAllowLocation() {
        return allowLocation;
    }

    public void setAllowLocation(Boolean allowLocation) {
        this.allowLocation = allowLocation;
    }

    public Boolean getAllowDeviceId() {
        return allowDeviceId;
    }

    public void setAllowDeviceId(Boolean allowDeviceId) {
        this.allowDeviceId = allowDeviceId;
    }

    public Boolean getAllowRegisterFaceCut() {
        return allowRegisterFaceCut;
    }

    public void setAllowRegisterFaceCut(Boolean allowRegisterTime) {
        this.allowRegisterFaceCut = allowRegisterTime;
    }

    public Boolean getAllowRemoveFaceCut() {
        return allowRemoveFaceCut;
    }

    public void setAllowRemoveFaceCut(Boolean allowRemoveFaceCut) {
        this.allowRemoveFaceCut = allowRemoveFaceCut;
    }

    public Integer getTimeBeforeRemoveFaceCut() {
        return timeBeforeRemoveFaceCut;
    }

    public void setTimeBeforeRemoveFaceCut(Integer timeBeforeRemoveFaceCut) {
        this.timeBeforeRemoveFaceCut = timeBeforeRemoveFaceCut;
    }
}
