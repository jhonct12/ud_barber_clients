package ud.barberClients.service;

import ud.barberClients.model.HistoricalZone;
import ud.barberClients.model.keys.KeyHistoricalZone;
import ud.barberClients.utils.dates.Dates;
import ud.barberClients.utils.exceptions.ConflictException;
import ud.barberClients.utils.exceptions.NotFoundException;
import ud.barberClients.utils.nameReserved.NameReserved;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class HistoricalZoneService {
    @PersistenceContext
    EntityManager entityManager;

    public HistoricalZoneService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public HistoricalZone historicalZoneByKey(KeyHistoricalZone keyHistoricalZone) {
        HistoricalZone historicalZone = entityManager.find(HistoricalZone.class, keyHistoricalZone);
        if (historicalZone == null){
            return null;
        }else{
            return new HistoricalZone(historicalZone);
        }
    }

    public Boolean historicalZoneByKeyWithTry(KeyHistoricalZone keyHistoricalZone) throws ConflictException {
        HistoricalZone HistoricalZone = entityManager.find(HistoricalZone.class, keyHistoricalZone);
        if (HistoricalZone != null){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.COMPANY_ID, keyHistoricalZone.getCompanyId());
            reason.put(NameReserved.DOCUMENT, keyHistoricalZone.getDocument());
            reason.put(NameReserved.INITIAL_DATE, keyHistoricalZone.getInitialDate().toString());
            reason.put(NameReserved.AREA_ID, keyHistoricalZone.getAreaId().toString());
            reason.put(NameReserved.ZONE_ID, keyHistoricalZone.getZoneId().toString());
            throw new ConflictException(NameReserved.CROSS_DATES, reason);
        }else{
            return true;
        }
    }

    public List<HistoricalZone> historicalZoneByComIniFinDate(String companyId, String document, Long initialDate, Integer areaId, Integer zoneId, Long finalDate){
        TypedQuery<HistoricalZone> q = entityManager.createNamedQuery(HistoricalZone.FIND_BY_COM_INI_FIN_DATE, HistoricalZone.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setParameter(NameReserved.DOCUMENT, document)
                .setParameter(NameReserved.INITIAL_DATE, initialDate)
                .setParameter(NameReserved.AREA_ID, areaId)
                .setParameter(NameReserved.ZONE_ID, zoneId)
                .setParameter(NameReserved.FINAL_DATE, finalDate);

        List<HistoricalZone> response = new ArrayList<>();
        for (HistoricalZone historicalZone : q.getResultList()) {
            response.add(new HistoricalZone(historicalZone));
        }
        return response;
    }

    public Boolean verifyOverlapHistoricalZone(String companyId, String document, Long initialDate, Integer areaId, Integer zoneId, Long finalDate) throws ConflictException {
        List<HistoricalZone> historicalZoneList = historicalZoneByComIniFinDate(companyId, document, initialDate, areaId, zoneId, finalDate);
        if (historicalZoneList.isEmpty()){
            return true;
        }else{
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.COMPANY_ID, companyId);
            reason.put(NameReserved.DOCUMENT, document);
            reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
            reason.put(NameReserved.AREA_ID, areaId.toString());
            reason.put(NameReserved.ZONE_ID, zoneId.toString());
            reason.put(NameReserved.FINAL_DATE, finalDate.toString());
            throw new ConflictException(NameReserved.SERVER_OVERLAP, reason);
        }
    }

    public Boolean verifyOverlapHistoricalZoneWithoutRealInitialDate(String companyId, String document, Long initialDate, Integer areaId, Integer zoneId, Long finalDate, Long realInitialDate) throws ConflictException {
        List<HistoricalZone> historicalZoneList = historicalZoneByComIniFinDate(companyId, document, initialDate, areaId, zoneId, finalDate);
        List<HistoricalZone> historicalZoneListFiltered = historicalZoneList.stream().filter(item -> !(item.getInitialDate().equals(realInitialDate))).collect(Collectors.toList());

        if (historicalZoneListFiltered.isEmpty()){
            return true;
        }else{
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.COMPANY_ID, companyId);
            reason.put(NameReserved.DOCUMENT, document);
            reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
            reason.put(NameReserved.AREA_ID, areaId.toString());
            reason.put(NameReserved.ZONE_ID, zoneId.toString());
            reason.put(NameReserved.FINAL_DATE, finalDate.toString());
            throw new ConflictException(NameReserved.SERVER_OVERLAP, reason);
        }
    }

    //1- cross
    //2- try
    //3- overlap
    //4- overlap without realInitialDate
    public void verificationOptions(HistoricalZone historicalZone, Long realInitialDate, ArrayList<Integer> option) throws ConflictException {
        for (Integer integer : option) {
            if (integer.equals(1)) {
                Dates.verifyCrossDates(historicalZone.getInitialDate(), historicalZone.getFinalDate());
            } else if (integer.equals(2)) {
                historicalZoneByKeyWithTry(new KeyHistoricalZone(historicalZone.getCompanyId(), historicalZone.getDocument(), historicalZone.getInitialDate(), historicalZone.getAreaId(), historicalZone.getZoneId()));
            } else if (integer.equals(3)) {
                verifyOverlapHistoricalZone(historicalZone.getCompanyId(), historicalZone.getDocument(), historicalZone.getInitialDate(), historicalZone.getAreaId(),historicalZone.getZoneId(), historicalZone.getFinalDate());
            } else if (integer.equals(4)) {
                verifyOverlapHistoricalZoneWithoutRealInitialDate(historicalZone.getCompanyId(), historicalZone.getDocument(), historicalZone.getInitialDate(), historicalZone.getAreaId(), historicalZone.getZoneId(), historicalZone.getFinalDate(), realInitialDate);
            }

        }
    }

    // default 1,2,3
    public HistoricalZone createHistoricalZone(HistoricalZone historicalZone) throws ConflictException {
        verificationOptions(historicalZone, null, new ArrayList<>(Arrays.asList(1,2,3)));
        try {
            entityManager.persist(historicalZone);
            return historicalZone;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }

    public HistoricalZone updateHistoricalZone(Long initialDate,HistoricalZone historicalZone) throws NotFoundException, ConflictException {
        verificationOptions(historicalZone, initialDate, new ArrayList<>(Arrays.asList(1,2,4)));
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, historicalZone.getCompanyId());
        reason.put(NameReserved.DOCUMENT, historicalZone.getDocument());
        reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
        reason.put(NameReserved.AREA_ID, historicalZone.getAreaId().toString());
        reason.put(NameReserved.ZONE_ID, historicalZone.getZoneId().toString());

        HistoricalZone updateHistoricalZone = historicalZoneByKey(new KeyHistoricalZone(historicalZone.getCompanyId(), historicalZone.getDocument(), initialDate, historicalZone.getAreaId(), historicalZone.getZoneId()));
        if (updateHistoricalZone == null) {
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        } else {

            if (historicalZone.getFinalDate() != null) updateHistoricalZone.setFinalDate(historicalZone.getFinalDate());

            if ((initialDate.equals(historicalZone.getInitialDate()))){
                try{
                    entityManager.merge(updateHistoricalZone);
                }catch (Exception e){
                    throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
                }
            }else{
                deleteHistoricalZone(updateHistoricalZone.getCompanyId(), updateHistoricalZone.getDocument(), updateHistoricalZone.getInitialDate(), updateHistoricalZone.getAreaId(), updateHistoricalZone.getZoneId());
                updateHistoricalZone.setInitialDate(historicalZone.getInitialDate());
                createHistoricalZone(updateHistoricalZone);
            }
            return updateHistoricalZone;
        }
    }

    public HistoricalZone deleteHistoricalZone(String companyId, String document, Long initialDate, Integer areaId, Integer zoneId ) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, companyId);
        reason.put(NameReserved.DOCUMENT, document);
        reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
        reason.put(NameReserved.AREA_ID, areaId.toString());
        reason.put(NameReserved.ZONE_ID, zoneId.toString());
        HistoricalZone historicalZone = historicalZoneByKey(new KeyHistoricalZone(companyId, document, initialDate, areaId, zoneId));
        if (historicalZone != null){
            try{
                entityManager.remove(historicalZone);
                return historicalZone;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_DELETE, reason);
            }
        }else{
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        }
    }

}
