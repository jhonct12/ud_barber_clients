package ud.barberClients.service;

import ud.barberClients.model.Company;
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
public class CompanyService {

    @PersistenceContext
    EntityManager entityManager;

    public CompanyService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public Company companyById(String companyId) {
        Company company = entityManager.find(Company.class, companyId);
        if (company == null){
            return null;
        }else{
            return new Company(company);
        }
    }

    public Boolean companyByIdTry(String companyId) throws ConflictException {
        Company company = entityManager.find(Company.class, companyId);
        if (company != null){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, NameReserved.DATA);
            throw new ConflictException(NameReserved.SERVER_KEY, reason);
        }else{
            return true;
        }
    }

    public Company createCompany(Company company) throws ConflictException {
        companyByIdTry(company.getCompanyId());
        try {
            entityManager.persist(company);
            return company;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }

    public Company updateCompany(Company company) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, company.getCompanyId());

        Company updateCompany = companyById(company.getCompanyId());
        if (updateCompany == null) {
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        } else {
            if (company.getName() != null) updateCompany.setName(company.getName());
            if (company.getDescription() != null) updateCompany.setDescription(company.getDescription());
            try{
                entityManager.merge(updateCompany);
                return updateCompany;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
            }
        }
    }

    public Company deleteCompany(String companyId) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, companyId);

        Company company = companyById(companyId);
        if (company != null){
            try{
                entityManager.remove(company);
                return company;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_DELETE, reason);
            }
        }else{
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        }
    }
}
