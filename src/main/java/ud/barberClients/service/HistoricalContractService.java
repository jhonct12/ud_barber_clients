package ud.barberClients.service;

import ud.barberClients.model.HistoricalContract;
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
public class HistoricalContractService {
    @PersistenceContext
    EntityManager entityManager;

    public HistoricalContractService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public HistoricalContract historicalContractByKey(KeyHistoricalWithoutSequence historicalWithoutSequence) {
        HistoricalContract historicalContract = entityManager.find(HistoricalContract.class, historicalWithoutSequence);
        if (historicalContract == null){
            return null;
        }else{
            return new HistoricalContract(historicalContract);
        }
    }

    public Boolean historicalContractByKeyWithTry(KeyHistoricalWithoutSequence historicalWithoutSequence) throws ConflictException {
        HistoricalContract historicalContract = entityManager.find(HistoricalContract.class, historicalWithoutSequence);
        if (historicalContract != null){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.COMPANY_ID, historicalWithoutSequence.getCompanyId());
            reason.put(NameReserved.DOCUMENT, historicalWithoutSequence.getDocument());
            reason.put(NameReserved.INITIAL_DATE, historicalWithoutSequence.getInitialDate().toString());
            throw new ConflictException(NameReserved.CROSS_DATES, reason);
        }else{
            return true;
        }
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

    public Boolean verifyOverlapHistoricalContractWithoutRealInitialDate(String companyId, String document, Long initialDate, Long finalDate, Long realInitialDate) throws ConflictException {
        List<HistoricalContract> historicalContractList = historicalContractByComIniFinDate(companyId, document, initialDate, finalDate);

        List<HistoricalContract> historicalContractListFiltered = historicalContractList.stream().filter(item -> !(item.getInitialDate().equals(realInitialDate))).collect(Collectors.toList());

        if (historicalContractListFiltered.isEmpty()){
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

    //1- cross
    //2- try
    //3- overlap
    //4- overlap without realInitialDate
    public void verificationOptions(HistoricalContract historicalContract, Long realInitialDate, ArrayList<Integer> option) throws ConflictException {
        for (Integer integer : option) {
            if (integer.equals(1)){
                Dates.verifyCrossDates(historicalContract.getInitialDate(), historicalContract.getFinalDate());
            }else if(integer.equals(2)){
                historicalContractByKeyWithTry(new KeyHistoricalWithoutSequence(historicalContract.getCompanyId(), historicalContract.getDocument(), historicalContract.getInitialDate()));
            }else if (integer.equals(3)){
                verifyOverlapHistoricalContract(historicalContract.getCompanyId(), historicalContract.getDocument(), historicalContract.getInitialDate(), historicalContract.getFinalDate());
            }else if (integer.equals(4)){
                verifyOverlapHistoricalContractWithoutRealInitialDate(historicalContract.getCompanyId(), historicalContract.getDocument(), historicalContract.getInitialDate(), historicalContract.getFinalDate(), realInitialDate);
            }
        }
    }

    // default 1,2,3
    public HistoricalContract createHistoricalContract(HistoricalContract historicalContract, ArrayList<Integer> options) throws ConflictException {
        if (!options.isEmpty()){
            verificationOptions(historicalContract, null, options);
        }
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
        verificationOptions(historicalContract, initialDate, new ArrayList<>(Arrays.asList(1,2,4)));

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
                createHistoricalContract(updateHistoricalContract, new ArrayList<>());
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
