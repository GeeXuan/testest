package Entity;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class CorporateService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public CorporateService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addCorporate(Corporate corporate) {
        mgr.persist(corporate);
    }

    public Corporate findCorporateByCode(int code) {
        Corporate corporate = mgr.find(Corporate.class, code);
        return corporate;
    }

    public void deleteCorporate(int code) {
        Corporate corporate = findCorporateByCode(code);
        if (corporate != null) {
            mgr.remove(corporate);
        }
    }

    public List<Corporate> findAll() {
        List corporateList = mgr.createNamedQuery("Corporate.findAll").getResultList();
        return corporateList;
    }

    public void updateCorporate(Corporate corporate) {
        mgr.merge(corporate);
    }
}
