package ud.barberClients.service;

import ud.barberClients.model.UserInformation;
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
public class UserInformationService {

    @PersistenceContext
    EntityManager entityManager;

    public UserInformationService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public UserInformation userInformationByKey(String document) {
        return entityManager.find(UserInformation.class, document);
    }

    public UserInformation createUserInformation(UserInformation userInformation) throws ConflictException {
        try {
            entityManager.persist(userInformation);
            return userInformation;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }

    public UserInformation updateUserInformation(UserInformation userInformation) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, userInformation.getDocument());

        UserInformation updateUserInformation = userInformationByKey(userInformation.getDocument());
        if (updateUserInformation == null) {
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        } else {
            if (userInformation.getDocumentType() != null) updateUserInformation.setDocumentType(userInformation.getDocumentType());
            if (userInformation.getName() != null) updateUserInformation.setName(userInformation.getName());
            if (userInformation.getSurname() != null) updateUserInformation.setSurname(userInformation.getSurname());
            if (userInformation.getLocation() != null) updateUserInformation.setLocation(userInformation.getLocation());
            if (userInformation.getAddress() != null) updateUserInformation.setAddress(userInformation.getAddress());
            if (userInformation.getBirthDate() != null) updateUserInformation.setBirthDate(userInformation.getBirthDate());
            if (userInformation.getGenderType() != null) updateUserInformation.setGenderType(userInformation.getGenderType());
            if (userInformation.getBloodType() != null) updateUserInformation.setBloodType(userInformation.getBloodType());
            if (userInformation.getEmail() != null) updateUserInformation.setEmail(userInformation.getEmail());
            if (userInformation.getTelephone() != null) updateUserInformation.setTelephone(userInformation.getTelephone());
            if (userInformation.getDeviceId() != null) updateUserInformation.setDeviceId(userInformation.getDeviceId());
            try{
                entityManager.merge(updateUserInformation);
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
            }
            return updateUserInformation;
        }
    }

    public UserInformation deleteUserInformation(String document) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.DOCUMENT, document);
        UserInformation userInformation = userInformationByKey(document);
        if (userInformation != null){
            try{
                entityManager.remove(userInformation);
                return userInformation;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_DELETE, reason);
            }
        }else{
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        }
    }
}
