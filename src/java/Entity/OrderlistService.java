package Entity;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class OrderlistService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public OrderlistService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addOrderlist(Orderlist orderlist) {
        mgr.persist(orderlist);
    }

    public Orderlist findOrderlistByCode(int code) {
        Orderlist orderlist = mgr.find(Orderlist.class, code);
        return orderlist;
    }

    public void deleteOrderlist(int code) {
        Orderlist orderlist = findOrderlistByCode(code);
        if (orderlist != null) {
            mgr.remove(orderlist);
        }
    }

    public List<Orderlist> findAll() {
        List orderlistList = mgr.createNamedQuery("Orderlist.findAll").getResultList();
        return orderlistList;
    }

    public void updateOrderlist(Orderlist orderlist) {
        mgr.merge(orderlist);
    }
}
