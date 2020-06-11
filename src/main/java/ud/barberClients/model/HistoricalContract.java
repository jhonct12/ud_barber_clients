package ud.barberClients.model;

import ud.barberClients.model.keys.KeyHistoricalWithoutSequence;

import javax.persistence.*;

@Entity
@IdClass(KeyHistoricalWithoutSequence.class)
@Table(name = "historicalContract")
@NamedQueries({
        @NamedQuery(name = HistoricalContract.FIND_ALL, query = "SELECT h FROM HistoricalContract h"),
        @NamedQuery(name = HistoricalContract.FIND_BY_COM_INI_FIN_DATE, query = "SELECT h FROM HistoricalContract h where h.companyId = :companyId and h.document = :document and h.initialDate <= :finalDate and h.finalDate >= :initialDate")
})
public class HistoricalContract {

    public static final String FIND_ALL = "HistoricalContract.findAll";
    public static final String FIND_BY_COM_INI_FIN_DATE = "HistoricalContract.findByComIniFinDate";

    @Id
    private String companyId;
    @Id
    private String document;
    @Id
    private Long initialDate;

    private Long finalDate;
    private String contractType;
    private String retirementType;

    public HistoricalContract() {
    }

    public HistoricalContract(String companyId, String document, Long initialDate, Long finalDate, String contractType, String retirementType) {
        this.companyId = companyId;
        this.document = document;
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.contractType = contractType;
        this.retirementType = retirementType;
    }

    public HistoricalContract(HistoricalContract historicalContract) {
        this.companyId = historicalContract.getCompanyId();
        this.document = historicalContract.getDocument();
        this.initialDate =historicalContract.getInitialDate();
        this.finalDate = historicalContract.getFinalDate();
        this.contractType = historicalContract.contractType;
        this.retirementType = historicalContract.getRetirementType();
    }


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Long getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Long initialDate) {
        this.initialDate = initialDate;
    }

    public Long getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Long finalDate) {
        this.finalDate = finalDate;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getRetirementType() {
        return retirementType;
    }

    public void setRetirementType(String retirementType) {
        this.retirementType = retirementType;
    }
}
