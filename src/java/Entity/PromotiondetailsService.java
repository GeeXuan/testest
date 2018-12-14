package Entity;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class PromotiondetailsService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public PromotiondetailsService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addPromotiondetails(Promotiondetails promotiondetails) {
        mgr.persist(promotiondetails);
    }

    public Promotiondetails findPromotiondetailsByCode(int code) {
        Promotiondetails promotiondetails = mgr.find(Promotiondetails.class, code);
        return promotiondetails;
    }

    public void deletePromotiondetails(int code) {
        Promotiondetails promotiondetails = findPromotiondetailsByCode(code);
        if (promotiondetails != null) {
            mgr.remove(promotiondetails);
        }
    }

    public List<Promotiondetails> findAll() {
        List promotiondetailsList = mgr.createNamedQuery("Promotiondetails.findAll").getResultList();
        return promotiondetailsList;
    }

    public void updatePromotiondetails(Promotiondetails promotiondetails) {
        mgr.merge(promotiondetails);
    }
}
