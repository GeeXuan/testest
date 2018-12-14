package Entity;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class PromotionService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public PromotionService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addPromotion(Promotion promotion) {
        mgr.persist(promotion);
    }

    public Promotion findPromotionByCode(int code) {
        Promotion promotion = mgr.find(Promotion.class, code);
        return promotion;
    }

    public void deletePromotion(int code) {
        Promotion promotion = findPromotionByCode(code);
        if (promotion != null) {
            mgr.remove(promotion);
        }
    }

    public List<Promotion> findAll() {
        List promotionList = mgr.createNamedQuery("Promotion.findAll").getResultList();
        return promotionList;
    }

    public void updatePromotion(Promotion promotion) {
        mgr.merge(promotion);
    }
}
