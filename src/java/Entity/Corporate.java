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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Saw
 */
@Entity
@Table(name = "CORPORATE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Corporate.findAll", query = "SELECT c FROM Corporate c")
    , @NamedQuery(name = "Corporate.findByCorporateid", query = "SELECT c FROM Corporate c WHERE c.corporateid = :corporateid")
    , @NamedQuery(name = "Corporate.findByCorporatename", query = "SELECT c FROM Corporate c WHERE c.corporatename = :corporatename")
    , @NamedQuery(name = "Corporate.findByCorporatephonenum", query = "SELECT c FROM Corporate c WHERE c.corporatephonenum = :corporatephonenum")
    , @NamedQuery(name = "Corporate.findByCorporateemail", query = "SELECT c FROM Corporate c WHERE c.corporateemail = :corporateemail")
    , @NamedQuery(name = "Corporate.findByAccountlimit", query = "SELECT c FROM Corporate c WHERE c.accountlimit = :accountlimit")
    , @NamedQuery(name = "Corporate.findByLastpayment", query = "SELECT c FROM Corporate c WHERE c.lastpayment = :lastpayment")
    , @NamedQuery(name = "Corporate.findByDebt", query = "SELECT c FROM Corporate c WHERE c.debt = :debt")})
public class Corporate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CORPORATEID")
    private Integer corporateid;
    @Size(max = 50)
    @Column(name = "CORPORATENAME")
    private String corporatename;
    @Size(max = 20)
    @Column(name = "CORPORATEPHONENUM")
    private String corporatephonenum;
    @Size(max = 30)
    @Column(name = "CORPORATEEMAIL")
    private String corporateemail;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ACCOUNTLIMIT")
    private Double accountlimit;
    @Column(name = "LASTPAYMENT")
    @Temporal(TemporalType.DATE)
    private Date lastpayment;
    @Column(name = "DEBT")
    private Double debt;
    @JoinColumn(name = "ACCOUNTID", referencedColumnName = "ACCOUNTID")
    @ManyToOne
    private Account accountid;

    public Corporate() {
    }

    public Corporate(Integer corporateid) {
        this.corporateid = corporateid;
    }

    public Integer getCorporateid() {
        return corporateid;
    }

    public void setCorporateid(Integer corporateid) {
        this.corporateid = corporateid;
    }

    public String getCorporatename() {
        return corporatename;
    }

    public void setCorporatename(String corporatename) {
        this.corporatename = corporatename;
    }

    public String getCorporatephonenum() {
        return corporatephonenum;
    }

    public void setCorporatephonenum(String corporatephonenum) {
        this.corporatephonenum = corporatephonenum;
    }

    public String getCorporateemail() {
        return corporateemail;
    }

    public void setCorporateemail(String corporateemail) {
        this.corporateemail = corporateemail;
    }

    public Double getAccountlimit() {
        return accountlimit;
    }

    public void setAccountlimit(Double accountlimit) {
        this.accountlimit = accountlimit;
    }

    public Date getLastpayment() {
        return lastpayment;
    }

    public void setLastpayment(Date lastpayment) {
        this.lastpayment = lastpayment;
    }

    public Double getDebt() {
        return debt;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public Account getAccountid() {
        return accountid;
    }

    public void setAccountid(Account accountid) {
        this.accountid = accountid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corporateid != null ? corporateid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Corporate)) {
            return false;
        }
        Corporate other = (Corporate) object;
        if ((this.corporateid == null && other.corporateid != null) || (this.corporateid != null && !this.corporateid.equals(other.corporateid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Corporate[ corporateid=" + corporateid + " ]";
    }
    
}
