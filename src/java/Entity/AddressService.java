package Entity;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class AddressService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public AddressService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addAddress(Address address) {
        mgr.persist(address);
    }

    public Address findAddressByCode(int code) {
        Address address = mgr.find(Address.class, code);
        return address;
    }

    public void deleteAddress(int code) {
        Address address = findAddressByCode(code);
        if (address != null) {
            mgr.remove(address);
        }
    }

    public List<Address> findAll() {
        List addressList = mgr.createNamedQuery("Address.findAll").getResultList();
        return addressList;
    }

    public void updateAddress(Address address) {
        mgr.merge(address);
    }
}
