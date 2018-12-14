package Entity;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class PaymentService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public PaymentService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addPayment(Payment payment) {
        mgr.persist(payment);
    }

    public Payment findPaymentByCode(int code) {
        Payment payment = mgr.find(Payment.class, code);
        return payment;
    }

    public void deletePayment(int code) {
        Payment payment = findPaymentByCode(code);
        if (payment != null) {
            mgr.remove(payment);
        }
    }

    public List<Payment> findAll() {
        List paymentList = mgr.createNamedQuery("Payment.findAll").getResultList();
        return paymentList;
    }

    public void updatePayment(Payment payment) {
        mgr.merge(payment);
    }
}
