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
@Table(name = "language_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LanguageTypes.findAll", query = "SELECT l FROM LanguageTypes l"),
    @NamedQuery(name = "LanguageTypes.findById", query = "SELECT l FROM LanguageTypes l WHERE l.id = :id"),
    @NamedQuery(name = "LanguageTypes.findByLanguageTypeValue", query = "SELECT l FROM LanguageTypes l WHERE l.languageTypeValue = :languageTypeValue")})
public class LanguageTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "language_type_value")
    private String languageTypeValue;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "languageTypeId")
    private Collection<Accounts> accountsCollection;

    public LanguageTypes() {
    }

    public LanguageTypes(Long id) {
        this.id = id;
    }

    public LanguageTypes(Long id, String languageTypeValue) {
        this.id = id;
        this.languageTypeValue = languageTypeValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguageTypeValue() {
        return languageTypeValue;
    }

    public void setLanguageTypeValue(String languageTypeValue) {
        this.languageTypeValue = languageTypeValue;
    }

    @XmlTransient
    public Collection<Accounts> getAccountsCollection() {
        return accountsCollection;
    }

    public void setAccountsCollection(Collection<Accounts> accountsCollection) {
        this.accountsCollection = accountsCollection;
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
        if (!(object instanceof LanguageTypes)) {
            return false;
        }
        LanguageTypes other = (LanguageTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd03.mok.model.temp.LanguageTypes[ id=" + id + " ]";
    }
    
}
