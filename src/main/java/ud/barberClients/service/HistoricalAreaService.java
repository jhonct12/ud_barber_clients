package ud.barberClients.service;

import ud.barberClients.model.HistoricalArea;
import ud.barberClients.model.HistoricalContract;
import ud.barberClients.model.keys.KeyHistoricalArea;
import ud.barberClients.model.keys.KeyHistoricalWithoutSequence;
import ud.barberClients.utils.dates.Dates;
import ud.barberClients.utils.exceptions.ConflictException;
import ud.barberClients.utils.exceptions.NotFoundException;
import ud.barberClients.utils.nameReserved.NameReserved;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class HistoricalAreaService {

    @PersistenceContext
    EntityManager entityManager;

    public HistoricalAreaService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public HistoricalArea historicalAreaByKey(KeyHistoricalArea keyHistoricalArea) {
        HistoricalArea historicalArea = entityManager.find(HistoricalArea.class, keyHistoricalArea);
        if (historicalArea == null){
            return null;
        }else{
            return new HistoricalArea(historicalArea);
        }
    }

    public Boolean historicalAreaByKeyWithTry(KeyHistoricalArea keyHistoricalArea) throws ConflictException {
        HistoricalArea historicalArea = entityManager.find(HistoricalArea.class, keyHistoricalArea);
        if (historicalArea != null){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.COMPANY_ID, keyHistoricalArea.getCompanyId());
            reason.put(NameReserved.DOCUMENT, keyHistoricalArea.getDocument());
            reason.put(NameReserved.INITIAL_DATE, keyHistoricalArea.getInitialDate().toString());
            reason.put(NameReserved.AREA_ID, keyHistoricalArea.getAreaId().toString());
            throw new ConflictException(NameReserved.CROSS_DATES, reason);
        }else{
            return true;
        }
    }

    public List<HistoricalArea> historicalAreaByComIniFinDate(String companyId, String document, Long initialDate, Integer areaId, Long finalDate){
        TypedQuery<HistoricalArea> q = entityManager.createNamedQuery(HistoricalArea.FIND_BY_COM_INI_FIN_DATE, HistoricalArea.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setParameter(NameReserved.DOCUMENT, document)
                .setParameter(NameReserved.INITIAL_DATE, initialDate)
                .setParameter(NameReserved.AREA_ID, areaId)
                .setParameter(NameReserved.FINAL_DATE, finalDate);

        List<HistoricalArea> response = new ArrayList<>();
        for (HistoricalArea historicalArea : q.getResultList()) {
            response.add(new HistoricalArea(historicalArea));
        }
        return response;
    }

    public Boolean verifyOverlapHistoricalArea(String companyId, String document, Long initialDate, Integer areaId, Long finalDate) throws ConflictException {
        List<HistoricalArea> historicalAreaList = historicalAreaByComIniFinDate(companyId, document, initialDate, areaId, finalDate);
        if (historicalAreaList.isEmpty()){
            return true;
        }else{
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.COMPANY_ID, companyId);
            reason.put(NameReserved.DOCUMENT, document);
            reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
            reason.put(NameReserved.AREA_ID, areaId.toString());
            reason.put(NameReserved.FINAL_DATE, finalDate.toString());
            throw new ConflictException(NameReserved.SERVER_OVERLAP, reason);
        }
    }

    public Boolean verifyOverlapHistoricalAreaWithoutRealInitialDate(String companyId, String document, Long initialDate, Integer areaId, Long finalDate, Long realInitialDate) throws ConflictException {
        List<HistoricalArea> historicalAreaList = historicalAreaByComIniFinDate(companyId, document, initialDate, areaId, finalDate);

        List<HistoricalArea> historicalAreaListFiltered = historicalAreaList.stream().filter(item -> !(item.getInitialDate().equals(realInitialDate))).collect(Collectors.toList());
        if (historicalAreaListFiltered.isEmpty()){
            return true;
        }else{
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.COMPANY_ID, companyId);
            reason.put(NameReserved.DOCUMENT, document);
            reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
            reason.put(NameReserved.AREA_ID, areaId.toString());
            reason.put(NameReserved.FINAL_DATE, finalDate.toString());
            throw new ConflictException(NameReserved.SERVER_OVERLAP, reason);
        }
    }

    //1- cross
    //2- try
    //3- overlap
    //4- overlap without realInitialDate
    public void verificationOptions(HistoricalArea historicalArea, Long realInitialDate, ArrayList<Integer> option) throws ConflictException {
        for (Integer integer : option) {
            if (integer.equals(1)) {
                Dates.verifyCrossDates(historicalArea.getInitialDate(), historicalArea.getFinalDate());
            } else if (integer.equals(2)) {
                historicalAreaByKeyWithTry(new KeyHistoricalArea(historicalArea.getCompanyId(), historicalArea.getDocument(), historicalArea.getInitialDate(), historicalArea.getAreaId()));
            } else if (integer.equals(3)) {
                verifyOverlapHistoricalArea(historicalArea.getCompanyId(), historicalArea.getDocument(), historicalArea.getInitialDate(), historicalArea.getAreaId(), historicalArea.getFinalDate());
            } else if (integer.equals(4)) {
                verifyOverlapHistoricalAreaWithoutRealInitialDate(historicalArea.getCompanyId(), historicalArea.getDocument(), historicalArea.getInitialDate(), historicalArea.getAreaId(), historicalArea.getFinalDate(), realInitialDate);
            }

        }
    }

    // default 1,2,3
    public HistoricalArea createHistoricalArea(HistoricalArea historicalArea, ArrayList<Integer> options) throws ConflictException {
        if (!options.isEmpty()){
            verificationOptions(historicalArea, null, options);
        }
        try {
            entityManager.persist(historicalArea);
            return historicalArea;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }

    public HistoricalArea updateHistoricalArea(Long initialDate,HistoricalArea historicalArea) throws NotFoundException, ConflictException {
        verificationOptions(historicalArea, initialDate, new ArrayList<>(Arrays.asList(1,2,4)));

        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, historicalArea.getCompanyId());
        reason.put(NameReserved.DOCUMENT, historicalArea.getDocument());
        reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
        reason.put(NameReserved.AREA_ID, historicalArea.getAreaId().toString());

        HistoricalArea updateHistoricalArea = historicalAreaByKey(new KeyHistoricalArea(historicalArea.getCompanyId(), historicalArea.getDocument(), initialDate, historicalArea.getAreaId()));
        if (updateHistoricalArea == null) {
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        } else {
            if (historicalArea.getFinalDate() != null) updateHistoricalArea.setFinalDate(historicalArea.getFinalDate());
            if ((initialDate.equals(historicalArea.getInitialDate()))){
                try{
                    entityManager.merge(updateHistoricalArea);
                }catch (Exception e){
                    throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
                }
            }else{
                deleteHistoricalArea(updateHistoricalArea.getCompanyId(), updateHistoricalArea.getDocument(), updateHistoricalArea.getInitialDate(), updateHistoricalArea.getAreaId());
                updateHistoricalArea.setInitialDate(historicalArea.getInitialDate());
                createHistoricalArea(updateHistoricalArea, new ArrayList<>());
            }
            return updateHistoricalArea;
        }
    }

    public HistoricalArea deleteHistoricalArea(String companyId, String document, Long initialDate, Integer areaId ) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, companyId);
        reason.put(NameReserved.DOCUMENT, document);
        reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
        reason.put(NameReserved.AREA_ID, areaId.toString());
        HistoricalArea historicalArea = historicalAreaByKey(new KeyHistoricalArea(companyId, document, initialDate, areaId));
        if (historicalArea != null){
            try{
                entityManager.remove(historicalArea);
                return historicalArea;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_DELETE, reason);
            }
        }else{
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        }
    }

}
