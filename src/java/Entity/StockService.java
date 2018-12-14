package Entity;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.*;

public class StockService {

    @PersistenceContext
    EntityManager mgr;
    @Resource
    Query query;

    public StockService(EntityManager mgr) {
        this.mgr = mgr;
    }

    public void addStock(Stock stock) {
        mgr.persist(stock);
    }

    public Stock findStockByCode(int code) {
        Stock stock = mgr.find(Stock.class, code);
        return stock;
    }

    public void deleteStock(int code) {
        Stock stock = findStockByCode(code);
        if (stock != null) {
            mgr.remove(stock);
        }
    }

    public List<Stock> findAll() {
        List stockList = mgr.createNamedQuery("Stock.findAll").getResultList();
        return stockList;
    }

    public void updateStock(Stock stock) {
        mgr.merge(stock);
    }
}
