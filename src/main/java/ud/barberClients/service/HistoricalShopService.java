package ud.barberClients.service;


import ud.barberClients.model.HistoricalShop;
import ud.barberClients.model.keys.KeyHistoricalShop;
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
public class HistoricalShopService {
    @PersistenceContext
    EntityManager entityManager;

    public HistoricalShopService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public HistoricalShop historicalShopByKey(KeyHistoricalShop keyHistoricalShop) {
        HistoricalShop historicalShop = entityManager.find(HistoricalShop.class, keyHistoricalShop);
        if (historicalShop == null){
            return null;
        }else{
            return new HistoricalShop(historicalShop);
        }
    }

    public Boolean historicalShopByKeyWithTry(KeyHistoricalShop keyHistoricalShop) throws ConflictException {
        HistoricalShop historicalShop = entityManager.find(HistoricalShop.class, keyHistoricalShop);
        if (historicalShop != null){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.COMPANY_ID, keyHistoricalShop.getCompanyId());
            reason.put(NameReserved.DOCUMENT, keyHistoricalShop.getDocument());
            reason.put(NameReserved.INITIAL_DATE, keyHistoricalShop.getInitialDate().toString());
            reason.put(NameReserved.AREA_ID, keyHistoricalShop.getAreaId().toString());
            reason.put(NameReserved.ZONE_ID, keyHistoricalShop.getZoneId().toString());
            reason.put(NameReserved.SHOP_ID, keyHistoricalShop.getShopId().toString());
            throw new ConflictException(NameReserved.CROSS_DATES, reason);
        }else{
            return true;
        }
    }

    public List<HistoricalShop> historicalShopByComIniFinDate(String companyId, String document, Long initialDate, Integer areaId, Integer zoneId, Integer shopId, Long finalDate){
        TypedQuery<HistoricalShop> q = entityManager.createNamedQuery(HistoricalShop.FIND_BY_COM_INI_FIN_DATE, HistoricalShop.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setParameter(NameReserved.DOCUMENT, document)
                .setParameter(NameReserved.INITIAL_DATE, initialDate)
                .setParameter(NameReserved.AREA_ID, areaId)
                .setParameter(NameReserved.ZONE_ID, zoneId)
                .setParameter(NameReserved.SHOP_ID, shopId)
                .setParameter(NameReserved.FINAL_DATE, finalDate);

        List<HistoricalShop> response = new ArrayList<>();
        for (HistoricalShop historicalShop : q.getResultList()) {
            response.add(new HistoricalShop(historicalShop));
        }
        return response;
    }

    public Boolean verifyOverlapHistoricalShop(String companyId, String document, Long initialDate, Integer areaId, Integer zoneId, Integer shopId, Long finalDate) throws ConflictException {
        List<HistoricalShop> historicalShopList = historicalShopByComIniFinDate(companyId, document, initialDate, areaId, zoneId, shopId, finalDate);
        if (historicalShopList.isEmpty()){
            return true;
        }else{
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.COMPANY_ID, companyId);
            reason.put(NameReserved.DOCUMENT, document);
            reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
            reason.put(NameReserved.AREA_ID, areaId.toString());
            reason.put(NameReserved.ZONE_ID, zoneId.toString());
            reason.put(NameReserved.SHOP_ID, shopId.toString());
            reason.put(NameReserved.FINAL_DATE, finalDate.toString());
            throw new ConflictException(NameReserved.SERVER_OVERLAP, reason);
        }
    }

    public Boolean verifyOverlapHistoricalShopWithoutRealInitialDate(String companyId, String document, Long initialDate, Integer areaId, Integer zoneId, Integer shopId, Long finalDate, Long realInitialDate) throws ConflictException {
        List<HistoricalShop> historicalShopList = historicalShopByComIniFinDate(companyId, document, initialDate, areaId, zoneId, shopId, finalDate);
        List<HistoricalShop> historicalShopListFiltered = historicalShopList.stream().filter(item -> !(item.getInitialDate().equals(realInitialDate))).collect(Collectors.toList());
        if (historicalShopListFiltered.isEmpty()){
            return true;
        }else{
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.COMPANY_ID, companyId);
            reason.put(NameReserved.DOCUMENT, document);
            reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
            reason.put(NameReserved.AREA_ID, areaId.toString());
            reason.put(NameReserved.ZONE_ID, zoneId.toString());
            reason.put(NameReserved.SHOP_ID, shopId.toString());
            reason.put(NameReserved.FINAL_DATE, finalDate.toString());
            throw new ConflictException(NameReserved.SERVER_OVERLAP, reason);
        }
    }

    //1- cross
    //2- try
    //3- overlap
    //4- overlap without realInitialDate
    public void verificationOptions(HistoricalShop historicalShop, Long realInitialDate, ArrayList<Integer> option) throws ConflictException {
        for (Integer integer : option) {
            if (integer.equals(1)) {
                Dates.verifyCrossDates(historicalShop.getInitialDate(), historicalShop.getFinalDate());
            } else if (integer.equals(2)) {
                historicalShopByKeyWithTry(new KeyHistoricalShop(historicalShop.getCompanyId(), historicalShop.getDocument(), historicalShop.getInitialDate(), historicalShop.getAreaId(), historicalShop.getZoneId(), historicalShop.getShopId()));
            } else if (integer.equals(3)) {
                verifyOverlapHistoricalShop(historicalShop.getCompanyId(), historicalShop.getDocument(), historicalShop.getInitialDate(), historicalShop.getAreaId(),historicalShop.getZoneId(), historicalShop.getShopId(), historicalShop.getFinalDate());
            } else if (integer.equals(4)) {
                verifyOverlapHistoricalShopWithoutRealInitialDate(historicalShop.getCompanyId(), historicalShop.getDocument(), historicalShop.getInitialDate(), historicalShop.getAreaId(), historicalShop.getZoneId(), historicalShop.getShopId(), historicalShop.getFinalDate(), realInitialDate);
            }

        }
    }

    // default 1,2,3
    public HistoricalShop createHistoricalShop(HistoricalShop historicalShop) throws ConflictException {
        verificationOptions(historicalShop, null, new ArrayList<>(Arrays.asList(1,2,3)));
        try {
            entityManager.persist(historicalShop);
            return historicalShop;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }

    public HistoricalShop updateHistoricalShop(Long initialDate,HistoricalShop historicalShop) throws NotFoundException, ConflictException {
        verificationOptions(historicalShop, initialDate, new ArrayList<>(Arrays.asList(1,2,4)));
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, historicalShop.getCompanyId());
        reason.put(NameReserved.DOCUMENT, historicalShop.getDocument());
        reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
        reason.put(NameReserved.AREA_ID, historicalShop.getAreaId().toString());
        reason.put(NameReserved.ZONE_ID, historicalShop.getZoneId().toString());
        reason.put(NameReserved.SHOP_ID, historicalShop.getShopId().toString());

        HistoricalShop updateHistoricalShop = historicalShopByKey(new KeyHistoricalShop(historicalShop.getCompanyId(), historicalShop.getDocument(), initialDate, historicalShop.getAreaId(), historicalShop.getZoneId(), historicalShop.getShopId()));
        if (updateHistoricalShop == null) {
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        } else {

            if (historicalShop.getFinalDate() != null) updateHistoricalShop.setFinalDate(historicalShop.getFinalDate());

            if ((initialDate.equals(historicalShop.getInitialDate()))){
                try{
                    entityManager.merge(updateHistoricalShop);
                }catch (Exception e){
                    throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
                }
            }else{
                deleteHistoricalShop(updateHistoricalShop.getCompanyId(), updateHistoricalShop.getDocument(), updateHistoricalShop.getInitialDate(), updateHistoricalShop.getAreaId(), updateHistoricalShop.getZoneId(), updateHistoricalShop.getShopId());
                updateHistoricalShop.setInitialDate(historicalShop.getInitialDate());
                createHistoricalShop(updateHistoricalShop);
            }
            return updateHistoricalShop;
        }
    }

    public HistoricalShop deleteHistoricalShop(String companyId, String document, Long initialDate, Integer areaId, Integer zoneId, Integer shopdId) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, companyId);
        reason.put(NameReserved.DOCUMENT, document);
        reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
        reason.put(NameReserved.AREA_ID, areaId.toString());
        reason.put(NameReserved.ZONE_ID, zoneId.toString());
        reason.put(NameReserved.SHOP_ID, shopdId.toString());
        HistoricalShop historicalShop = historicalShopByKey(new KeyHistoricalShop(companyId, document, initialDate, areaId, zoneId, shopdId));
        if (historicalShop != null){
            try{
                entityManager.remove(historicalShop);
                return historicalShop;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_DELETE, reason);
            }
        }else{
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        }
    }

}

