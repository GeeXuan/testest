package Entity;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class OrdersService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public OrdersService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addOrders(Orders orders) {
        mgr.persist(orders);
    }

    public Orders findOrdersByCode(int code) {
        Orders orders = mgr.find(Orders.class, code);
        return orders;
    }

    public void deleteOrders(int code) {
        Orders orders = findOrdersByCode(code);
        if (orders != null) {
            mgr.remove(orders);
        }
    }

    public List<Orders> findAll() {
        List ordersList = mgr.createNamedQuery("Orders.findAll").getResultList();
        return ordersList;
    }

    public void updateOrders(Orders orders) {
        mgr.merge(orders);
    }
}
