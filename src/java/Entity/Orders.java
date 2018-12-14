/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Saw
 */
@Entity
@Table(name = "ORDERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o")
    , @NamedQuery(name = "Orders.findByOrderid", query = "SELECT o FROM Orders o WHERE o.orderid = :orderid")
    , @NamedQuery(name = "Orders.findByOrderdate", query = "SELECT o FROM Orders o WHERE o.orderdate = :orderdate")
    , @NamedQuery(name = "Orders.findByOrderpickupmethod", query = "SELECT o FROM Orders o WHERE o.orderpickupmethod = :orderpickupmethod")
    , @NamedQuery(name = "Orders.findByOrderpickupdate", query = "SELECT o FROM Orders o WHERE o.orderpickupdate = :orderpickupdate")
    , @NamedQuery(name = "Orders.findByOrderreceivedtime", query = "SELECT o FROM Orders o WHERE o.orderreceivedtime = :orderreceivedtime")
    , @NamedQuery(name = "Orders.findByOrderprice", query = "SELECT o FROM Orders o WHERE o.orderprice = :orderprice")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ORDERID")
    private Integer orderid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDERDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderdate;
    @Size(max = 50)
    @Column(name = "ORDERPICKUPMETHOD")
    private String orderpickupmethod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ORDERPICKUPDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderpickupdate;
    @Column(name = "ORDERRECEIVEDTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderreceivedtime;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ORDERPRICE")
    private Double orderprice;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "orderid")
    private Payment paymentid;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "orderid")
    private List<Orderlist> orderlistList;
    @JoinColumn(name = "ACCOUNTID", referencedColumnName = "ACCOUNTID")
    @ManyToOne
    private Account accountid;
    @JoinColumn(name = "ADDRESSID", referencedColumnName = "ADDRESSID")
    @ManyToOne
    private Address addressid;

    public Orders() {
    }

    public Orders(Integer orderid) {
        this.orderid = orderid;
    }

    public Orders(Integer orderid, Date orderdate, Date orderpickupdate) {
        this.orderid = orderid;
        this.orderdate = orderdate;
        this.orderpickupdate = orderpickupdate;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrderpickupmethod() {
        return orderpickupmethod;
    }

    public void setOrderpickupmethod(String orderpickupmethod) {
        this.orderpickupmethod = orderpickupmethod;
    }

    public Date getOrderpickupdate() {
        return orderpickupdate;
    }

    public void setOrderpickupdate(Date orderpickupdate) {
        this.orderpickupdate = orderpickupdate;
    }

    public Date getOrderreceivedtime() {
        return orderreceivedtime;
    }

    public void setOrderreceivedtime(Date orderreceivedtime) {
        this.orderreceivedtime = orderreceivedtime;
    }

    public Double getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(Double orderprice) {
        this.orderprice = orderprice;
    }

    @XmlTransient
    public Payment getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(Payment paymentid) {
        this.paymentid = paymentid;
    }

    @XmlTransient
    public List<Orderlist> getOrderlistList() {
        return orderlistList;
    }

    public void setOrderlistList(List<Orderlist> orderlistList) {
        this.orderlistList = orderlistList;
    }

    public Account getAccountid() {
        return accountid;
    }

    public void setAccountid(Account accountid) {
        this.accountid = accountid;
    }

    public Address getAddressid() {
        return addressid;
    }

    public void setAddressid(Address addressid) {
        this.addressid = addressid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderid != null ? orderid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.orderid == null && other.orderid != null) || (this.orderid != null && !this.orderid.equals(other.orderid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Orders[ orderid=" + orderid + " ]";
    }
    
}
