package ud.barberClients.service;

import ud.barberClients.model.HistoricalContract;
import ud.barberClients.model.keys.KeyHistoricalWithoutSequence;
import ud.barberClients.utils.exceptions.ConflictException;
import ud.barberClients.utils.exceptions.NotFoundException;
import ud.barberClients.utils.nameReserved.NameReserved;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class HistoricalContractService {
    @PersistenceContext
    EntityManager entityManager;

    public HistoricalContractService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public HistoricalContract historicalContractByKey(KeyHistoricalWithoutSequence keyUserSession) {
        return entityManager.find(HistoricalContract.class, keyUserSession);
    }

    public List<HistoricalContract> historicalContractByComIniFinDate(String companyId, String document, Long initialDate, Long finalDate){
        TypedQuery<HistoricalContract> q = entityManager.createNamedQuery(HistoricalContract.FIND_BY_COM_INI_FIN_DATE, HistoricalContract.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setParameter(NameReserved.DOCUMENT, document)
                .setParameter(NameReserved.INITIAL_DATE, initialDate)
                .setParameter(NameReserved.FINAL_DATE, finalDate);

        List<HistoricalContract> response = new ArrayList<>();
        for (HistoricalContract historicalContract : q.getResultList()) {
            response.add(new HistoricalContract(historicalContract));
        }
        return response;
    }

    public Boolean verifyOverlapHistoricalContract(String companyId, String document, Long initialDate, Long finalDate) throws ConflictException {
        List<HistoricalContract> historicalContractList = historicalContractByComIniFinDate(companyId, document, initialDate, finalDate);

        if (historicalContractList.isEmpty()){
            return true;
        }else{
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.COMPANY_ID, companyId);
            reason.put(NameReserved.DOCUMENT, document);
            reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
            reason.put(NameReserved.FINAL_DATE, finalDate.toString());
            throw new ConflictException(NameReserved.SERVER_OVERLAP, reason);
        }
    }

    public HistoricalContract createHistoricalContract(HistoricalContract historicalContract) throws ConflictException {
        try {
            entityManager.persist(historicalContract);
            return historicalContract;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }

    public HistoricalContract updateHistoricalContract(Long initialDate,HistoricalContract historicalContract) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, historicalContract.getCompanyId());
        reason.put(NameReserved.DOCUMENT, historicalContract.getDocument());
        reason.put(NameReserved.INITIAL_DATE, initialDate.toString());

        HistoricalContract updateHistoricalContract = historicalContractByKey(new KeyHistoricalWithoutSequence(historicalContract.getCompanyId(), historicalContract.getDocument(), initialDate));
        if (updateHistoricalContract == null) {
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        } else {
            if (historicalContract.getFinalDate() != null) updateHistoricalContract.setFinalDate(historicalContract.getFinalDate());
            if (historicalContract.getContractType() != null) updateHistoricalContract.setContractType(historicalContract.getContractType());
            if (historicalContract.getRetirementType() != null) updateHistoricalContract.setRetirementType(historicalContract.getRetirementType());
            if ((initialDate.equals(historicalContract.getInitialDate()))){
                try{
                    entityManager.merge(updateHistoricalContract);
                }catch (Exception e){
                    throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
                }
            }else{
                deleteHistoricalContract(updateHistoricalContract.getCompanyId(), updateHistoricalContract.getDocument(), updateHistoricalContract.getInitialDate());
                updateHistoricalContract.setInitialDate(historicalContract.getInitialDate());
                createHistoricalContract(updateHistoricalContract);
            }
            return updateHistoricalContract;
        }
    }

    public HistoricalContract deleteHistoricalContract(String companyId, String document, Long initialDate ) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, companyId);
        reason.put(NameReserved.DOCUMENT, document);
        reason.put(NameReserved.INITIAL_DATE, initialDate.toString());
        HistoricalContract historicalContract = historicalContractByKey(new KeyHistoricalWithoutSequence(companyId, document, initialDate));
        if (historicalContract != null){
            try{
                entityManager.remove(historicalContract);
                return historicalContract;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_DELETE, reason);
            }
        }else{
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        }
    }
}
