/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saw
 */
@Entity
@Table(name = "STOCK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s")
    , @NamedQuery(name = "Stock.findByStockid", query = "SELECT s FROM Stock s WHERE s.stockid = :stockid")
    , @NamedQuery(name = "Stock.findByStockquantity", query = "SELECT s FROM Stock s WHERE s.stockquantity = :stockquantity")})
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STOCKID")
    private Integer stockid;
    @Column(name = "STOCKQUANTITY")
    private Integer stockquantity;
    @JoinColumn(name = "PRODUCTID", referencedColumnName = "PRODUCTID")
    @OneToOne
    private Product productid;

    public Stock() {
    }

    public Stock(Integer stockid) {
        this.stockid = stockid;
    }

    public Stock(int stockid, int stockQuantity, Product product) {
        this.stockid = stockid;
        this.stockquantity = stockQuantity;
        this.productid = product;
    }

    public Integer getStockid() {
        return stockid;
    }

    public void setStockid(Integer stockid) {
        this.stockid = stockid;
    }

    public Integer getStockquantity() {
        return stockquantity;
    }

    public void setStockquantity(Integer stockquantity) {
        this.stockquantity = stockquantity;
    }

    public Product getProductid() {
        return productid;
    }

    public void setProductid(Product productid) {
        this.productid = productid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stockid != null ? stockid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stock)) {
            return false;
        }
        Stock other = (Stock) object;
        if ((this.stockid == null && other.stockid != null) || (this.stockid != null && !this.stockid.equals(other.stockid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Stock[ stockid=" + stockid + " ]";
    }

}
