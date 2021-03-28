/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.temp;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aradiuk
 */
@Entity
@Table(name = "alter_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlterTypes.findAll", query = "SELECT a FROM AlterTypes a"),
    @NamedQuery(name = "AlterTypes.findById", query = "SELECT a FROM AlterTypes a WHERE a.id = :id"),
    @NamedQuery(name = "AlterTypes.findByAlterTypeName", query = "SELECT a FROM AlterTypes a WHERE a.alterTypeName = :alterTypeName")})
public class AlterTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "alter_type_name")
    private String alterTypeName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alterTypeId")
    private Collection<Addresses> addressesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alterTypeId")
    private Collection<Clients> clientsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alterTypeId")
    private Collection<BusinessWorkers> businessWorkersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alterTypeId")
    private Collection<Accounts> accountsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alterTypeId")
    private Collection<Administrators> administratorsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "alterTypeId")
    private Collection<Moderators> moderatorsCollection;

    public AlterTypes() {
    }

    public AlterTypes(Long id) {
        this.id = id;
    }

    public AlterTypes(Long id, String alterTypeName) {
        this.id = id;
        this.alterTypeName = alterTypeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlterTypeName() {
        return alterTypeName;
    }

    public void setAlterTypeName(String alterTypeName) {
        this.alterTypeName = alterTypeName;
    }

    @XmlTransient
    public Collection<Addresses> getAddressesCollection() {
        return addressesCollection;
    }

    public void setAddressesCollection(Collection<Addresses> addressesCollection) {
        this.addressesCollection = addressesCollection;
    }

    @XmlTransient
    public Collection<Clients> getClientsCollection() {
        return clientsCollection;
    }

    public void setClientsCollection(Collection<Clients> clientsCollection) {
        this.clientsCollection = clientsCollection;
    }

    @XmlTransient
    public Collection<BusinessWorkers> getBusinessWorkersCollection() {
        return businessWorkersCollection;
    }

    public void setBusinessWorkersCollection(Collection<BusinessWorkers> businessWorkersCollection) {
        this.businessWorkersCollection = businessWorkersCollection;
    }

    @XmlTransient
    public Collection<Accounts> getAccountsCollection() {
        return accountsCollection;
    }

    public void setAccountsCollection(Collection<Accounts> accountsCollection) {
        this.accountsCollection = accountsCollection;
    }

    @XmlTransient
    public Collection<Administrators> getAdministratorsCollection() {
        return administratorsCollection;
    }

    public void setAdministratorsCollection(Collection<Administrators> administratorsCollection) {
        this.administratorsCollection = administratorsCollection;
    }

    @XmlTransient
    public Collection<Moderators> getModeratorsCollection() {
        return moderatorsCollection;
    }

    public void setModeratorsCollection(Collection<Moderators> moderatorsCollection) {
        this.moderatorsCollection = moderatorsCollection;
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
        if (!(object instanceof AlterTypes)) {
            return false;
        }
        AlterTypes other = (AlterTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd03.mok.model.temp.AlterTypes[ id=" + id + " ]";
    }
    
}
