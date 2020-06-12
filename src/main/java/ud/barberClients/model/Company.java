package ud.barberClients.model;


import ud.barberClients.model.keys.KeyCompanySettings;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {

    private String companyId;

    private String name;
    private String description;

    public Company(){
    }

    public Company(String companyId, String name, String description){
        this.companyId = companyId;
        this.name = name;
        this.description = description;
    }

    public Company(Company company){
        this.companyId = company.getCompanyId();
        this.name = company.getName();
        this.description = company.getDescription();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
