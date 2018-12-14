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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saw
 */
@Entity
@Table(name = "PROMOTIONDETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promotiondetails.findAll", query = "SELECT p FROM Promotiondetails p")
    , @NamedQuery(name = "Promotiondetails.findByPromotiondetailsid", query = "SELECT p FROM Promotiondetails p WHERE p.promotiondetailsid = :promotiondetailsid")
    , @NamedQuery(name = "Promotiondetails.findByPromoprice", query = "SELECT p FROM Promotiondetails p WHERE p.promoprice = :promoprice")})
public class Promotiondetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PROMOTIONDETAILSID")
    private Integer promotiondetailsid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PROMOPRICE")
    private Double promoprice;
    @JoinColumn(name = "PRODUCTID", referencedColumnName = "PRODUCTID")
    @ManyToOne
    private Product productid;
    @JoinColumn(name = "PROMOTIONID", referencedColumnName = "PROMOTIONID")
    @ManyToOne
    private Promotion promotionid;

    public Promotiondetails() {
    }

    public Promotiondetails(Integer promotiondetailsid) {
        this.promotiondetailsid = promotiondetailsid;
    }

    public Integer getPromotiondetailsid() {
        return promotiondetailsid;
    }

    public void setPromotiondetailsid(Integer promotiondetailsid) {
        this.promotiondetailsid = promotiondetailsid;
    }

    public Double getPromoprice() {
        return promoprice;
    }

    public void setPromoprice(Double promoprice) {
        this.promoprice = promoprice;
    }

    public Product getProductid() {
        return productid;
    }

    public void setProductid(Product productid) {
        this.productid = productid;
    }

    public Promotion getPromotionid() {
        return promotionid;
    }

    public void setPromotionid(Promotion promotionid) {
        this.promotionid = promotionid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promotiondetailsid != null ? promotiondetailsid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promotiondetails)) {
            return false;
        }
        Promotiondetails other = (Promotiondetails) object;
        if ((this.promotiondetailsid == null && other.promotiondetailsid != null) || (this.promotiondetailsid != null && !this.promotiondetailsid.equals(other.promotiondetailsid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Promotiondetails[ promotiondetailsid=" + promotiondetailsid + " ]";
    }
    
}
