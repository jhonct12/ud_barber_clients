package ud.barberClients.service;

import ud.barberClients.model.HistoricalUserSession;
import ud.barberClients.model.HistoricalContract;
import ud.barberClients.model.UserSession;
import ud.barberClients.model.keys.KeyUserSession;
import ud.barberClients.utils.cipher.Cipher;
import ud.barberClients.utils.dates.Dates;
import ud.barberClients.utils.exceptions.ConflictException;
import ud.barberClients.utils.exceptions.NotFoundException;
import ud.barberClients.utils.nameReserved.NameReserved;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;

@Stateless
public class UserSessionService {
    @PersistenceContext
    EntityManager entityManager;

    public UserSessionService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public UserSession userSessionByKey(KeyUserSession keyUserSession) {
        UserSession userSession = entityManager.find(UserSession.class, keyUserSession);
        if (userSession == null){
            return null;
        }else{
            return new UserSession(userSession);
        }
    }

    public Boolean userSessionByKeyTry(KeyUserSession keyUserSession) throws ConflictException {
        UserSession userSession = userSessionByKey(keyUserSession);
        if (userSession != null){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.COMPANY_ID, keyUserSession.getCompanyId());
            reason.put(NameReserved.DOCUMENT, keyUserSession.getDocument());
            reason.put(NameReserved.USERNAME, keyUserSession.getUsername());
            throw new ConflictException(NameReserved.SERVER_FIND, reason);
        }else{
            return true;
        }
    }

    public UserSession userByUsername(String username) throws NotFoundException {
        TypedQuery<UserSession> q = entityManager.createNamedQuery(UserSession.FIND_BY_USERNAME, UserSession.class)
                .setParameter(NameReserved.USERNAME, username)
                .setMaxResults(1);

            UserSession userSession = q.getSingleResult();
            if (userSession != null){
                return userSession;
            }else{
                Map<String, String> reason = new HashMap<>();
                reason.put(NameReserved.USERNAME, username);
                throw new NotFoundException(NameReserved.SERVER_FIND, reason);
            }
    }

    public Boolean userByUsernameTry(String username) throws ConflictException {
        TypedQuery<UserSession> q = entityManager.createNamedQuery(UserSession.FIND_BY_USERNAME, UserSession.class)
                .setParameter(NameReserved.USERNAME, username)
                .setMaxResults(1);

        UserSession userSession = q.getSingleResult();
        if (userSession != null){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.USERNAME, username);
            throw new ConflictException(NameReserved.SERVER_FIND, reason);
        }else{
            return true;
        }
    }


    //1- key
    //2- username
    public void verificationOptions(UserSession userSession, String username, ArrayList<Integer> option) throws ConflictException {
        for (Integer integer : option) {
            if (integer.equals(1)){
                userSessionByKeyTry(new KeyUserSession(userSession.getCompanyId(), userSession.getDocument(), userSession.getUsername()));
            }else if(integer.equals(2)){
                userByUsernameTry(username);
            }
        }
    }

    public UserSession createUserSession(UserSession userSession, ArrayList<Integer> option) throws ConflictException {
        if (!option.isEmpty()){
            verificationOptions(null, userSession.getUsername(), option);
        }
        userSession.setPassword(Cipher.encryptString(userSession.getPassword()));
        try{
            entityManager.persist(userSession);
            return userSession;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }

    public UserSession updateUserSession(UserSession userSession) throws NotFoundException, ConflictException {
        verificationOptions(null, userSession.getUsername(), new ArrayList<>(Arrays.asList(2)));
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, userSession.getCompanyId());
        reason.put(NameReserved.DOCUMENT, userSession.getDocument());
        reason.put(NameReserved.USERNAME, userSession.getUsername());
        KeyUserSession key = new KeyUserSession(userSession.getCompanyId(), userSession.getDocument(), userSession.getUsername());
        UserSession updateUserSession = userSessionByKey(key);
        if (updateUserSession == null) {
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        } else {
            if (userSession.getStatus() != null) updateUserSession.setStatus(userSession.getStatus());
            if (userSession.getRoleType() != null) updateUserSession.setRoleType(userSession.getRoleType());
            if (userSession.getPassword() != null) updateUserSession.setPassword(userSession.getPassword());
            try{
                entityManager.merge(updateUserSession);
                return updateUserSession;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
            }
        }
    }

    public UserSession LogInUserSession(String username, String password, String deviceId) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        UserSession userSession  = userByUsername(username);
        String decryptPass = Cipher.decryptString(password);
        String decryptPassDb = Cipher.decryptString(userSession.getPassword());
        if (!decryptPass.equals(decryptPassDb)) {
            reason.put(NameReserved.PASSWORD, decryptPass);
            throw new NotFoundException(NameReserved.SERVER_NO_FOUND, reason);
        }

        HistoricalContractService  historicalContractService = new HistoricalContractService();
        Long currentDate = Dates.currentDateInEpoch();
        List<HistoricalContract> historicalContractList = historicalContractService.historicalContractByComIniFinDate(userSession.getCompanyId(), userSession.getDocument(), currentDate, currentDate);
        if (historicalContractList.isEmpty() || !historicalContractList.get(0).getRetirementType().equals("0")) {
            reason.put(NameReserved.SERVER_CURRENT_DATE, currentDate.toString());
            throw new ConflictException(NameReserved.SERVER_OVERLAP, reason);
        } else {
            // Create the object of the historical
            createHistoricalUserSession(userSession.getCompanyId(), userSession.getDocument(), userSession.getUsername(), deviceId);
            return userSession;
        }
    }


    public UserSession LogOutUserSession(String language, String username, String password, String deviceId) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        UserSession userSession = userByUsername(username);
        String decryptPass = Cipher.decryptString(password);
        String decryptPassDb = Cipher.decryptString(userSession.getPassword());
        if (!decryptPass.equals(decryptPassDb)) {
            reason.put(NameReserved.PASSWORD, decryptPass);
            throw new NotFoundException(NameReserved.SERVER_NO_FOUND, reason);
        }
        updateCloseHistoricalHandleUser(userSession.getCompanyId(), userSession.getDocument(), userSession.getUsername(), deviceId);
        return userSession;
    }

    public HistoricalUserSession createHistoricalUserSession(String companyId, String document, String username, String deviceId) throws ConflictException {
        Long actualFullDate = Dates.currentDateInEpoch();
        HistoricalUserSession historicalUserSession = new HistoricalUserSession();
        historicalUserSession.setCompanyId(companyId);
        historicalUserSession.setDocument(document);
        historicalUserSession.setUsername(username);
        historicalUserSession.setInitialDate(actualFullDate);
        historicalUserSession.setFinalDate(0L);
        historicalUserSession.setDeviceId(deviceId);
        try{
            entityManager.persist(historicalUserSession);
            return historicalUserSession;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }


    public void updateCloseHistoricalHandleUser(String companyId, String document, String username, String deviceId) throws ConflictException {
        List<HistoricalUserSession> historicalUserSessionList = historicalUserSessionByComDocUserDevFinZero(companyId, document, username, deviceId);
        if (!historicalUserSessionList.isEmpty()) {
            HistoricalUserSession historicalUserSession = historicalUserSessionList.get(0);
            try {
                entityManager.merge(historicalUserSession);
            }catch (Exception e){
                Map<String, String> reason = new HashMap<>();
                reason.put(NameReserved.COMPANY_ID, companyId);
                reason.put(NameReserved.DOCUMENT, document);
                reason.put(NameReserved.USERNAME, username);
                reason.put(NameReserved.DEVICE_ID, deviceId);
                throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
            }
        }
    }

    public List<HistoricalUserSession> historicalUserSessionByComDocUserDevFinZero(String companyId, String document, String username, String deviceId) {
        TypedQuery<HistoricalUserSession> q = entityManager.createNamedQuery(HistoricalUserSession.FIND_BY_COMPANY_DOCUMENT_USERNAME_DEVICE_ID_FINAL_DATE_ZERO, HistoricalUserSession.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setParameter(NameReserved.DOCUMENT, document)
                .setParameter(NameReserved.USERNAME, username)
                .setParameter(NameReserved.DEVICE_ID, deviceId);

        List<HistoricalUserSession> response = new ArrayList<>();
        for (HistoricalUserSession historicalUserSession : q.getResultList()) {
            response.add(new HistoricalUserSession(historicalUserSession));
        }
        return response;
    }
}
