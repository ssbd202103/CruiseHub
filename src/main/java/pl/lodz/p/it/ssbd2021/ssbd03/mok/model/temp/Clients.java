/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.temp;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author aradiuk
 */
@Entity
@Table(name = "clients")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clients.findAll", query = "SELECT c FROM Clients c"),
    @NamedQuery(name = "Clients.findById", query = "SELECT c FROM Clients c WHERE c.id = :id"),
    @NamedQuery(name = "Clients.findByPhoneNumber", query = "SELECT c FROM Clients c WHERE c.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Clients.findByVersion", query = "SELECT c FROM Clients c WHERE c.version = :version"),
    @NamedQuery(name = "Clients.findByCreationDatetime", query = "SELECT c FROM Clients c WHERE c.creationDatetime = :creationDatetime"),
    @NamedQuery(name = "Clients.findByLastAlterDatetime", query = "SELECT c FROM Clients c WHERE c.lastAlterDatetime = :lastAlterDatetime"),
    @NamedQuery(name = "Clients.findByAlteredById", query = "SELECT c FROM Clients c WHERE c.alteredById = :alteredById")})
public class Clients implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 2147483647)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @Basic(optional = false)
    @NotNull
    @Column(name = "creation_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDatetime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_alter_date_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAlterDatetime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "altered_by_id")
    private long alteredById;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private AccessLevels accessLevels;
    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Accounts createdById;
    @JoinColumn(name = "home_address_id", referencedColumnName = "id")
    @ManyToOne
    private Addresses homeAddressId;
    @JoinColumn(name = "alter_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AlterTypes alterTypeId;

    public Clients() {
    }

    public Clients(Long id) {
        this.id = id;
    }

    public Clients(Long id, long version, Date creationDatetime, Date lastAlterDatetime, long alteredById) {
        this.id = id;
        this.version = version;
        this.creationDatetime = creationDatetime;
        this.lastAlterDatetime = lastAlterDatetime;
        this.alteredById = alteredById;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Date getCreationDatetime() {
        return creationDatetime;
    }

    public void setCreationDatetime(Date creationDatetime) {
        this.creationDatetime = creationDatetime;
    }

    public Date getLastAlterDatetime() {
        return lastAlterDatetime;
    }

    public void setLastAlterDatetime(Date lastAlterDatetime) {
        this.lastAlterDatetime = lastAlterDatetime;
    }

    public long getAlteredById() {
        return alteredById;
    }

    public void setAlteredById(long alteredById) {
        this.alteredById = alteredById;
    }

    public AccessLevels getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(AccessLevels accessLevels) {
        this.accessLevels = accessLevels;
    }

    public Accounts getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Accounts createdById) {
        this.createdById = createdById;
    }

    public Addresses getHomeAddressId() {
        return homeAddressId;
    }

    public void setHomeAddressId(Addresses homeAddressId) {
        this.homeAddressId = homeAddressId;
    }

    public AlterTypes getAlterTypeId() {
        return alterTypeId;
    }

    public void setAlterTypeId(AlterTypes alterTypeId) {
        this.alterTypeId = alterTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clients)) {
            return false;
        }
        Clients other = (Clients) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd03.mok.model.temp.Clients[ id=" + id + " ]";
    }
    
}
