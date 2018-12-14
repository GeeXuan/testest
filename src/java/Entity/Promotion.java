/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Saw
 */
@Entity
@Table(name = "PROMOTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promotion.findAll", query = "SELECT p FROM Promotion p")
    , @NamedQuery(name = "Promotion.findByPromotionid", query = "SELECT p FROM Promotion p WHERE p.promotionid = :promotionid")
    , @NamedQuery(name = "Promotion.findByPromotionname", query = "SELECT p FROM Promotion p WHERE p.promotionname = :promotionname")
    , @NamedQuery(name = "Promotion.findByPromotiontype", query = "SELECT p FROM Promotion p WHERE p.promotiontype = :promotiontype")})
public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PROMOTIONID")
    private Integer promotionid;
    @Size(max = 50)
    @Column(name = "PROMOTIONNAME")
    private String promotionname;
    @Size(max = 50)
    @Column(name = "PROMOTIONTYPE")
    private String promotiontype;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "promotionid")
    private List<Promotiondetails> promotiondetailsList;

    public Promotion() {
    }

    public Promotion(Integer promotionid) {
        this.promotionid = promotionid;
    }

    public Promotion(String promoName, String promoType) {
        this.promotionname = promoName;
        this.promotiontype = promoType;
    }

    public Integer getPromotionid() {
        return promotionid;
    }

    public void setPromotionid(Integer promotionid) {
        this.promotionid = promotionid;
    }

    public String getPromotionname() {
        return promotionname;
    }

    public void setPromotionname(String promotionname) {
        this.promotionname = promotionname;
    }

    public String getPromotiontype() {
        return promotiontype;
    }

    public void setPromotiontype(String promotiontype) {
        this.promotiontype = promotiontype;
    }

    @XmlTransient
    public List<Promotiondetails> getPromotiondetailsList() {
        return promotiondetailsList;
    }

    public void setPromotiondetailsList(List<Promotiondetails> promotiondetailsList) {
        this.promotiondetailsList = promotiondetailsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promotionid != null ? promotionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promotion)) {
            return false;
        }
        Promotion other = (Promotion) object;
        if ((this.promotionid == null && other.promotionid != null) || (this.promotionid != null && !this.promotionid.equals(other.promotionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Promotion[ promotionid=" + promotionid + " ]";
    }

}
