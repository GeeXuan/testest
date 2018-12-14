package Entity;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class ProductService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public ProductService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addProduct(Product product) {
        mgr.persist(product);
    }

    public Product findProductByCode(int code) {
        Product product = mgr.find(Product.class, code);
        return product;
    }

    public void deleteProduct(int code) {
        Product product = findProductByCode(code);
        if (product != null) {
            mgr.remove(product);
        }
    }

    public List<Product> findAll() {
        List productList = mgr.createNamedQuery("Product.findAll").getResultList();
        return productList;
    }

    public void updateProduct(Product product) {
        mgr.merge(product);
    }
}
