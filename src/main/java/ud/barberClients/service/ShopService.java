package ud.barberClients.service;

import ud.barberClients.model.Shop;
import ud.barberClients.model.keys.KeyShop;
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
public class ShopService {
    @PersistenceContext
    EntityManager entityManager;

    public ShopService() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("con").createEntityManager();
        }
    }

    public Shop shopByKey(KeyShop keyShop) {
        Shop shop =  entityManager.find(Shop.class, keyShop);
        if (shop == null){
            return null;
        }else{
            return new Shop(shop);
        }
    }


    public List<Shop> shopByCompanyIdAreaIdZoneIdShopId(String companyId, Integer areaId, Integer zoneId, Integer shopId) {
        TypedQuery<Shop> q = entityManager.createNamedQuery(Shop.FIND_BY_COMPANY_AREA_ID_ZONE_ID_SHOP_ID, Shop.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setParameter(NameReserved.AREA_ID, areaId)
                .setParameter(NameReserved.ZONE_ID, zoneId)
                .setParameter(NameReserved.SHOP_ID, shopId);

        List<Shop> response = new ArrayList<>();
        for (Shop shop : q.getResultList()) {
            response.add(new Shop(shop));
        }
        return response;
    }

    public Shop maxShopSequence(String companyId, Integer areaId, Integer zoneId, Integer shopId) {
        TypedQuery<Shop> q = entityManager.createNamedQuery(Shop.FIND_BY_COMPANY_AREA_ID_ZONE_ID_SHOP_ID, Shop.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setParameter(NameReserved.AREA_ID, areaId)
                .setParameter(NameReserved.ZONE_ID, zoneId)
                .setParameter(NameReserved.SHOP_ID, shopId)
                .setMaxResults(1);

        Shop shop = q.getSingleResult();

        if (shop == null){
            return null;
        }else{
            return new Shop(shop);
        }
    }

    public Shop maxShopId(String companyId, Integer areaId, Integer zoneId) {
        TypedQuery<Shop> q = entityManager.createNamedQuery(Shop.FIND_BY_COMPANY_AREA_ID_ZONE_ID, Shop.class)
                .setParameter(NameReserved.COMPANY_ID, companyId)
                .setParameter(NameReserved.AREA_ID, areaId)
                .setParameter(NameReserved.ZONE_ID, zoneId)
                .setMaxResults(1);

        Shop shop = q.getSingleResult();

        if (shop == null){
            return null;
        }else{
            return new Shop(shop);
        }
    }

    public Boolean verifySameCodeOrDescription(String companyId, Integer areaId, Integer zoneId, Integer shopId, String codeShop, String descriptionShop) throws ConflictException {
        List<Shop> verifyData = shopByCompanyIdAreaIdZoneIdShopId(companyId, areaId, zoneId, shopId).stream().filter( item ->
                item.getCodeShop().equals(codeShop) ||
                        item.getDescriptionShop().equals(descriptionShop)
        ).collect(Collectors.toList());

        if (verifyData.isEmpty()){
            return true;
        }else{
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, NameReserved.DATA);
            throw new ConflictException(NameReserved.SERVER_KEY_RESERVED, reason);
        }
    }

    public Shop createShop(Shop shop) throws ConflictException {
        verifySameCodeOrDescription(shop.getCompanyId(), shop.getAreaId(), shop.getZoneId(), shop.getShopId(), shop.getCodeShop(), shop.getDescriptionShop());
        Shop auxShop = maxShopSequence(shop.getCompanyId(), shop.getAreaId(), shop.getZoneId(), shop.getShopId());
        Integer newSequence = auxShop == null ? 1 : auxShop.getSequenceNumber() + 1;
        Shop auxShopTwo = maxShopId(shop.getCompanyId(), shop.getAreaId(), shop.getZoneId());
        Integer newShopId = auxShopTwo == null ? 1 : auxShopTwo.getShopId() + 1;
        shop.setSequenceNumber(newSequence);
        shop.setShopId(newShopId);
        try{
            entityManager.persist(shop);
            return shop;
        }catch (Exception e){
            Map<String, String> reason = new HashMap<>();
            reason.put(NameReserved.SERVER, "data");
            throw new ConflictException(NameReserved.SERVER_CREATE, reason);
        }
    }

    public Shop updateShop(Shop shop) throws ConflictException {
        verifySameCodeOrDescription(shop.getCompanyId(), shop.getAreaId(), shop.getZoneId(), shop.getShopId(), shop.getCodeShop(), shop.getDescriptionShop());
        Shop updateShop = shopByKey(new KeyShop(shop.getCompanyId(), shop.getAreaId(), shop.getZoneId(), shop.getShopId(), shop.getSequenceNumber()));
        if (updateShop == null) {
            return null;
        } else {
            if (shop.getCodeShop() != null) updateShop.setCodeShop(shop.getCodeShop());
            if (shop.getDescriptionShop() != null) updateShop.setDescriptionShop(shop.getDescriptionShop());
            if (shop.getGpsLatitude() != null) updateShop.setGpsLatitude(shop.getGpsLatitude());
            if (shop.getGpgLongitude() != null) updateShop.setGpgLongitude(shop.getGpgLongitude());
            if (shop.getGpsError() != null) updateShop.setGpsError(shop.getGpsError());
            if (shop.getShopParentId() != null) updateShop.setShopParentId(shop.getShopParentId());
            try {
                verifySameCodeOrDescription(shop.getCompanyId(), shop.getAreaId(), shop.getZoneId(), shop.getShopId(), shop.getCodeShop(), shop.getDescriptionShop());
                entityManager.merge(updateShop);
                return updateShop;
            }catch (Exception e){
                Map<String, String> reason = new HashMap<>();
                reason.put(NameReserved.SERVER, NameReserved.DATA);
                throw new ConflictException(NameReserved.SERVER_UPDATE, reason);
            }
        }
    }

    public Shop deleteShop(KeyShop keyShop) throws NotFoundException, ConflictException {
        Map<String, String> reason = new HashMap<>();
        Shop deleteShop = shopByKey(keyShop);
        if (deleteShop == null){
            reason.put(NameReserved.COMPANY_ID, keyShop.getCompanyId());
            reason.put(NameReserved.AREA_ID,  keyShop.getAreaId().toString());
            reason.put(NameReserved.ZONE_ID,  keyShop.getZoneId().toString());
            reason.put(NameReserved.SHOP_ID,  keyShop.getShopId().toString());
            reason.put(NameReserved.SEQUENCE_NUMBER,  keyShop.getSequenceNumber().toString());
            throw new NotFoundException(NameReserved.SERVER_FIND, reason);
        }else{
            try{
                entityManager.remove(deleteShop);
                return deleteShop;
            }catch (Exception e){
                reason.put(NameReserved.SERVER,  NameReserved.DATA);
                throw new ConflictException(NameReserved.SERVER_DELETE, reason);
            }
        }
    }
}

