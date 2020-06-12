package ud.barberClients.service;

import ud.barberClients.model.CompanySettings;
import ud.barberClients.model.keys.KeyCompanySettings;
import ud.barberClients.utils.exceptions.ConflictException;
import ud.barberClients.utils.exceptions.NotFoundException;
import ud.barberClients.utils.nameReserved.NameReserved;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class CompanySettingsService {

    @PersistenceContext
    EntityManager entityManager;

    public CompanySettingsService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public CompanySettings companySettingsByKey(KeyCompanySettings keyCompanySettings) {
        CompanySettings companySettings = entityManager.find(CompanySettings.class, keyCompanySettings);
        if (companySettings == null){
            return null;
        }else{
            return new CompanySettings(companySettings);
        }
    }

    public Boolean companySettingsByKeyTry(KeyCompanySettings keyCompanySettings) throws ConflictException {
        CompanySettings companySettings = entityManager.find(CompanySettings.class, keyCompanySettings);
        if (companySettings != null){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, NameReserved.DATA);
            throw new ConflictException(NameReserved.SERVER_KEY, reason);
        }else{
            return true;
        }
    }

    public CompanySettings createCompanySettings(CompanySettings companySettings) throws ConflictException {
        companySettingsByKeyTry(new KeyCompanySettings(companySettings.getCompanyId(), companySettings.getAreaId(), companySettings.getZoneId(), companySettings.getShopId()));
        try {
            entityManager.persist(companySettings);
            return companySettings;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }

    public CompanySettings updateCompanySettings(CompanySettings companySettings) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, companySettings.getCompanyId());
        reason.put(NameReserved.AREA_ID, companySettings.getAreaId().toString());
        reason.put(NameReserved.ZONE_ID, companySettings.getZoneId().toString());
        reason.put(NameReserved.SHOP_ID, companySettings.getShopId().toString());

        CompanySettings updateCompanySettings = companySettingsByKey(new KeyCompanySettings(companySettings.getCompanyId(), companySettings.getAreaId(), companySettings.getZoneId(), companySettings.getShopId()));
        if (updateCompanySettings == null) {
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        } else {
            if (companySettings.getAllowFace() != null) updateCompanySettings.setAllowFace(companySettings.getAllowFace());
            if (companySettings.getAllowFaceUserFinal() != null) updateCompanySettings.setAllowFaceUserFinal(companySettings.getAllowFaceUserFinal());
            if (companySettings.getPercentageFace() != null) updateCompanySettings.setPercentageFace(companySettings.getPercentageFace());
            if (companySettings.getAllowLocation() != null) updateCompanySettings.setAllowLocation(companySettings.getAllowLocation());
            if (companySettings.getAllowDeviceId() != null) updateCompanySettings.setAllowDeviceId(companySettings.getAllowDeviceId());
            if (companySettings.getAllowRegisterFaceCut() != null) updateCompanySettings.setAllowRegisterFaceCut(companySettings.getAllowRegisterFaceCut());
            if (companySettings.getAllowRemoveFaceCut() != null) updateCompanySettings.setAllowRemoveFaceCut(companySettings.getAllowRemoveFaceCut());
            if (companySettings.getTimeBeforeRemoveFaceCut() != null) updateCompanySettings.setTimeBeforeRemoveFaceCut(companySettings.getTimeBeforeRemoveFaceCut());
            try{
                entityManager.merge(updateCompanySettings);
                return updateCompanySettings;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
            }
        }
    }

    public CompanySettings deleteCompanySettings(KeyCompanySettings keyCompanySettings) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, keyCompanySettings.getCompanyId());
        reason.put(NameReserved.AREA_ID, keyCompanySettings.getAreaId().toString());
        reason.put(NameReserved.ZONE_ID, keyCompanySettings.getZoneId().toString());
        reason.put(NameReserved.SHOP_ID, keyCompanySettings.getShopId().toString());

        CompanySettings companySettings = companySettingsByKey(keyCompanySettings);
        if (companySettings != null){
            try{
                entityManager.remove(companySettings);
                return companySettings;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_DELETE, reason);
            }
        }else{
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        }
    }
}
