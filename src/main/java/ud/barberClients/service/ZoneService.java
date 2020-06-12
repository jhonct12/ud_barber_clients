package ud.barberClients.service;

import ud.barberClients.model.Zone;
import ud.barberClients.model.keys.KeyZone;
import ud.barberClients.utils.exceptions.ConflictException;
import ud.barberClients.utils.exceptions.NotFoundException;
import ud.barberClients.utils.nameReserved.NameReserved;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class ZoneService {

    @PersistenceContext
    EntityManager entityManager;

    public ZoneService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public Zone zoneByKey(KeyZone keyZone) {
        Zone zone =  entityManager.find(Zone.class, keyZone);
        if (zone == null){
            return null;
        }else{
            return new Zone(zone);
        }
    }


    public List<Zone> zoneByCompanyIdAreaIdZoneId(String companyId, Integer areaId, Integer zoneId) {
        TypedQuery<Zone> q = entityManager.createNamedQuery(Zone.FIND_BY_COMPANY_AREA_ID_ZONE_ID, Zone.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setParameter(NameReserved.AREA_ID, areaId)
                .setParameter(NameReserved.ZONE_ID, zoneId);

        List<Zone> response = new ArrayList<>();
        for (Zone zone : q.getResultList()) {
            response.add(new Zone(zone));
        }
        return response;
    }

    public Zone maxZoneSequence(String companyId, Integer areaId, Integer zoneId) {
        TypedQuery<Zone> q = entityManager.createNamedQuery(Zone.FIND_BY_COMPANY_AREA_ID_ZONE_ID, Zone.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setParameter(NameReserved.AREA_ID, areaId)
                .setParameter(NameReserved.ZONE_ID, zoneId)
                .setMaxResults(1);

        Zone zone = q.getSingleResult();

        if (zone == null){
            return null;
        }else{
            return new Zone(zone);
        }
    }

    public Zone maxZoneId(String companyId, Integer areaId) {
        TypedQuery<Zone> q = entityManager.createNamedQuery(Zone.FIND_BY_COMPANY_AREA_ID, Zone.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setParameter(NameReserved.AREA_ID, areaId)
                .setMaxResults(1);

        Zone zone = q.getSingleResult();

        if (zone == null){
            return null;
        }else{
            return new Zone(zone);
        }
    }

    public Boolean verifySameCodeOrDescription(String companyId, Integer areaId, Integer zoneId, String codeZone, String descriptionZone) throws ConflictException {
        List<Zone> verifyData = zoneByCompanyIdAreaIdZoneId(companyId, areaId, zoneId).stream().filter( item ->
                item.getCodeZone().equals(codeZone) ||
                        item.getDescriptionZone().equals(descriptionZone)
        ).collect(Collectors.toList());

        if (verifyData.isEmpty()){
            return true;
        }else{
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, NameReserved.DATA);
            throw new ConflictException(NameReserved.SERVER_KEY_RESERVED, reason);
        }
    }

    public Zone createZone(Zone zone) throws ConflictException {
        verifySameCodeOrDescription(zone.getCompanyId(), zone.getAreaId(), zone.getZoneId(), zone.getCodeZone(), zone.getDescriptionZone());
        Zone auxZone = maxZoneSequence(zone.getCompanyId(), zone.getAreaId(), zone.getZoneId());
        Integer newSequence = auxZone == null ? 1 : auxZone.getSequenceNumber() + 1;
        Zone auxZoneTwo = maxZoneId(zone.getCompanyId(), zone.getAreaId());
        Integer newZoneId = auxZoneTwo == null ? 1 : auxZoneTwo.getZoneId() + 1;
        zone.setSequenceNumber(newSequence);
        zone.setZoneId(newZoneId);
        try{
            entityManager.persist(zone);
            return zone;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }

    public Zone updateZone(Zone zone) throws ConflictException {
        verifySameCodeOrDescription(zone.getCompanyId(), zone.getAreaId(), zone.getZoneId(), zone.getCodeZone(), zone.getDescriptionZone());
        Zone updateZone = zoneByKey(new KeyZone(zone.getCompanyId(), zone.getAreaId(), zone.getZoneId(), zone.getSequenceNumber()));
        if (updateZone == null) {
            return null;
        } else {
            if (zone.getCodeZone() != null) updateZone.setCodeZone(zone.getCodeZone());
            if (zone.getDescriptionZone() != null) updateZone.setDescriptionZone(zone.getDescriptionZone());
            if (zone.getZoneParentId() != null) updateZone.setZoneParentId(zone.getZoneParentId());
            try {
                entityManager.merge(updateZone);
                return updateZone;
            }catch (Exception e){
                Map<String, String> reason = new HashMap<>();
                reason.put(NameReserved.SERVER, NameReserved.DATA);
                throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
            }
        }
    }

    public Zone deleteZone(KeyZone keyZone) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        Zone deleteZone = zoneByKey(keyZone);
        if (deleteZone == null){
            reason.put(NameReserved.COMPANY_ID, keyZone.getCompanyId());
            reason.put(NameReserved.AREA_ID,  keyZone.getAreaId().toString());
            reason.put(NameReserved.ZONE_ID,  keyZone.getZoneId().toString());
            reason.put(NameReserved.SEQUENCE_NUMBER,  keyZone.getSequenceNumber().toString());
            throw new NotFoundException(NameReserved.SERVER_FIND, reason);
        }else{
            try{
                entityManager.remove(deleteZone);
                return deleteZone;
            }catch (Exception e){
                reason.put(NameReserved.SERVER,  NameReserved.DATA);
                throw new ConflictException(NameReserved.SERVER_DELETE, reason);
            }
        }
    }
}
