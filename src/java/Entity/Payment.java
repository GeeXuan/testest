/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saw
 */
@Entity
@Table(name = "PAYMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p")
    , @NamedQuery(name = "Payment.findByPaymentid", query = "SELECT p FROM Payment p WHERE p.paymentid = :paymentid")
    , @NamedQuery(name = "Payment.findByPaymentmethod", query = "SELECT p FROM Payment p WHERE p.paymentmethod = :paymentmethod")
    , @NamedQuery(name = "Payment.findByPaymentstatus", query = "SELECT p FROM Payment p WHERE p.paymentstatus = :paymentstatus")
    , @NamedQuery(name = "Payment.findByPaymemtdate", query = "SELECT p FROM Payment p WHERE p.paymemtdate = :paymemtdate")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PAYMENTID")
    private Integer paymentid;
    @Size(max = 10)
    @Column(name = "PAYMENTMETHOD")
    private String paymentmethod;
    @Column(name = "PAYMENTSTATUS")
    private Integer paymentstatus;
    @Basic(optional = false)
    @Column(name = "PAYMEMTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymemtdate;
    @JoinColumn(name = "ORDERID", referencedColumnName = "ORDERID")
    @OneToOne
    private Orders orderid;

    public Payment() {
    }

    public Payment(Integer paymentid) {
        this.paymentid = paymentid;
    }

    public Payment(Integer paymentid, Date paymemtdate) {
        this.paymentid = paymentid;
        this.paymemtdate = paymemtdate;
    }

    public Integer getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(Integer paymentid) {
        this.paymentid = paymentid;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public Integer getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(Integer paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public Date getPaymemtdate() {
        return paymemtdate;
    }

    public void setPaymemtdate(Date paymemtdate) {
        this.paymemtdate = paymemtdate;
    }

    public Orders getOrderid() {
        return orderid;
    }

    public void setOrderid(Orders orderid) {
        this.orderid = orderid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentid != null ? paymentid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.paymentid == null && other.paymentid != null) || (this.paymentid != null && !this.paymentid.equals(other.paymentid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Payment[ paymentid=" + paymentid + " ]";
    }
    
}
