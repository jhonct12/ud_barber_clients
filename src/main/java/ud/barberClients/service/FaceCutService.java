package ud.barberClients.service;

import ud.barberClients.model.FaceCut;
import ud.barberClients.model.keys.KeyFaceCut;
import ud.barberClients.utils.exceptions.ConflictException;
import ud.barberClients.utils.exceptions.NotFoundException;
import ud.barberClients.utils.nameReserved.NameReserved;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

@Stateless
public class FaceCutService {

    @PersistenceContext
    EntityManager entityManager;

    public FaceCutService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public FaceCut faceCutByKey(KeyFaceCut keyFaceCut) {
        FaceCut faceCut = entityManager.find(FaceCut.class, keyFaceCut);
        if (faceCut == null){
            return null;
        }else{
            return new FaceCut(faceCut);
        }
    }

    public Boolean faceCutByKeyTry(KeyFaceCut keyFaceCut) throws ConflictException {
        FaceCut faceCut = entityManager.find(FaceCut.class, keyFaceCut);
        if (faceCut != null){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, NameReserved.DATA);
            throw new ConflictException(NameReserved.SERVER_KEY, reason);
        }else{
            return true;
        }
    }
    public FaceCut maximumFaceCutId(String companyId, Integer areaId, Integer zoneId, Integer shopId, String document){
        TypedQuery<FaceCut> q = entityManager.createNamedQuery(FaceCut.FIND_BY_COMPANY_ID_AREA_ID_ZONE_ID_SHOP_ID_DOCUMENT, FaceCut.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setParameter(NameReserved.AREA_ID, areaId)
                .setParameter(NameReserved.ZONE_ID, zoneId)
                .setParameter(NameReserved.SHOP_ID, shopId)
                .setParameter(NameReserved.DOCUMENT, document)
                .setMaxResults(1);

        FaceCut faceCut = q.getSingleResult();
        if (faceCut == null){
            return null;
        }else{
            return new FaceCut(faceCut);
        }
    }


    public FaceCut createFaceCut(FaceCut faceCut) throws ConflictException {
        FaceCut auxFaceCut = maximumFaceCutId(faceCut.getCompanyId(), faceCut.getAreaId(), faceCut.getZoneId(), faceCut.getShopId(), faceCut.getDocument());
        Integer newFaceId = auxFaceCut == null ? 1 : auxFaceCut.getFaceCutId() + 1;
        faceCut.setFaceCutId(newFaceId);

        try {
            entityManager.persist(faceCut);
            return faceCut;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }

    public FaceCut updateFaceCut(FaceCut faceCut) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, faceCut.getCompanyId());
        reason.put(NameReserved.AREA_ID, faceCut.getAreaId().toString());
        reason.put(NameReserved.ZONE_ID, faceCut.getZoneId().toString());
        reason.put(NameReserved.SHOP_ID, faceCut.getShopId().toString());
        reason.put(NameReserved.DOCUMENT, faceCut.getDocument());
        reason.put(NameReserved.FACE_CUT_ID, faceCut.getFaceCutId().toString());

        FaceCut updateFaceCut = faceCutByKey(new KeyFaceCut(faceCut.getCompanyId(), faceCut.getAreaId(), faceCut.getZoneId(), faceCut.getShopId(), faceCut.getDocument(), faceCut.getFaceCutId()));
        if (updateFaceCut == null) {
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        } else {
            if (faceCut.getTitle() != null) updateFaceCut.setTitle(faceCut.getTitle());
            if (faceCut.getDescription() != null) updateFaceCut.setDescription(faceCut.getDescription());
            if (faceCut.getPay() != null) updateFaceCut.setPay(faceCut.getPay());
            if (faceCut.getDurationTime() != null) updateFaceCut.setDurationTime(faceCut.getDurationTime());
            try{
                entityManager.merge(updateFaceCut);
                return updateFaceCut;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
            }
        }
    }

    public FaceCut deleteFaceCut(KeyFaceCut keyFaceCut) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, keyFaceCut.getCompanyId());
        reason.put(NameReserved.AREA_ID, keyFaceCut.getAreaId().toString());
        reason.put(NameReserved.ZONE_ID, keyFaceCut.getZoneId().toString());
        reason.put(NameReserved.SHOP_ID, keyFaceCut.getShopId().toString());
        reason.put(NameReserved.DOCUMENT, keyFaceCut.getDocument());
        reason.put(NameReserved.FACE_CUT_ID, keyFaceCut.getFaceCutId().toString());

        FaceCut faceCut = faceCutByKey(keyFaceCut);
        if (faceCut != null){
            try{
                entityManager.remove(faceCut);
                return faceCut;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_DELETE, reason);
            }
        }else{
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        }
    }
}
