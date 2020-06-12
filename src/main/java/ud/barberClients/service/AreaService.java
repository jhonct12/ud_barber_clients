package ud.barberClients.service;

import ud.barberClients.model.Area;
import ud.barberClients.model.keys.KeyArea;
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
public class AreaService {

    @PersistenceContext
    EntityManager entityManager;

    public AreaService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public Area areaByKey(KeyArea keyArea) {
        Area area = entityManager.find(Area.class, keyArea);
        if (area == null){
            return null;
        }else{
            return new Area(area);
        }
    }

    public Area maximumAreaId(String companyId){
        TypedQuery<Area> q = entityManager.createNamedQuery(Area.FIND_BY_COMPANY_ID, Area.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setMaxResults(1);

        Area area = q.getSingleResult();
        if (area == null){
            return null;
        }else{
            return new Area(area);
        }
    }

    public List<Area> areaByCompany(String companyId) {
        TypedQuery<Area> q = entityManager.createNamedQuery(Area.FIND_BY_COMPANY_ID, Area.class)
                .setParameter(NameReserved.COMPANY_ID, companyId);

        List<Area> response = new ArrayList<>();
        for (Area area : q.getResultList()) {
            response.add(new Area(area));
        }
        return response;
    }

    public Boolean verifySameCodeOrDescription(String companyId, String codeArea, String descriptionArea) throws ConflictException {
        List<Area> verifyData = areaByCompany(companyId).stream().filter(item ->
                item.getCodeArea().equals(codeArea) ||
                        item.getDescriptionArea().equals(descriptionArea)
        ).collect(Collectors.toList());

        if (verifyData.isEmpty()){
            return true;
        }else{
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, NameReserved.DATA);
            throw new ConflictException(NameReserved.SERVER_KEY_RESERVED, reason);
        }
    }

    public Area createArea(Area area) throws ConflictException {
        verifySameCodeOrDescription(area.getCompanyId(), area.getCodeArea(), area.getDescriptionArea());
        Area auxArea = maximumAreaId(area.getCompanyId());
        Integer newAreaId = auxArea == null ? 1 : auxArea.getAreaId() + 1;
        area.setAreaId(newAreaId);
        try {
            entityManager.persist(area);
            return area;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }

    public Area updateArea(String codeArea,Area area) throws NotFoundException, ConflictException {
        verifySameCodeOrDescription(area.getCompanyId(), area.getCodeArea(), area.getDescriptionArea());
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, area.getCompanyId());
        reason.put(NameReserved.AREA_ID, area.getAreaId().toString());

        Area updateArea = areaByKey(new KeyArea(area.getCompanyId(), area.getAreaId()));
        if (updateArea == null) {
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        } else {
            if (area.getDescriptionArea() != null) updateArea.setDescriptionArea(area.getDescriptionArea());
            if ((codeArea.equals(area.getCodeArea()))){
                try{
                    entityManager.merge(updateArea);
                }catch (Exception e){
                    throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
                }
            }else{
                deleteArea(area.getCompanyId(), area.getAreaId());
                updateArea.setCodeArea(area.getCodeArea());
                createArea(updateArea);
            }
            return updateArea;
        }
    }

    public Area deleteArea(String companyId, Integer areaId) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        reason.put(NameReserved.COMPANY_ID, companyId);
        reason.put(NameReserved.AREA_ID, areaId.toString());

        Area area = areaByKey(new KeyArea(companyId, areaId));
        if (area != null){
            try{
                entityManager.remove(area);
                return area;
            }catch (Exception e){
                throw new ConflictException(NameReserved.SERVER_DELETE, reason);
            }
        }else{
            throw new NotFoundException(NameReserved.SERVER_KEY, reason);
        }
    }
}
