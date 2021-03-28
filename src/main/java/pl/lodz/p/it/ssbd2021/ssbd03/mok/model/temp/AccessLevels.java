/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.temp;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aradiuk
 */
@Entity
@Table(name = "access_levels")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessLevels.findAll", query = "SELECT a FROM AccessLevels a"),
    @NamedQuery(name = "AccessLevels.findById", query = "SELECT a FROM AccessLevels a WHERE a.id = :id"),
    @NamedQuery(name = "AccessLevels.findByAccessLevel", query = "SELECT a FROM AccessLevels a WHERE a.accessLevel = :accessLevel"),
    @NamedQuery(name = "AccessLevels.findByActive", query = "SELECT a FROM AccessLevels a WHERE a.active = :active")})
public class AccessLevels implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "access_level")
    private String accessLevel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Accounts accountId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "accessLevels")
    private Clients clients;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "accessLevels")
    private BusinessWorkers businessWorkers;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "accessLevels")
    private Administrators administrators;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "accessLevels")
    private Moderators moderators;

    public AccessLevels() {
    }

    public AccessLevels(Long id) {
        this.id = id;
    }

    public AccessLevels(Long id, String accessLevel, boolean active) {
        this.id = id;
        this.accessLevel = accessLevel;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Accounts getAccountId() {
        return accountId;
    }

    public void setAccountId(Accounts accountId) {
        this.accountId = accountId;
    }

    public Clients getClients() {
        return clients;
    }

    public void setClients(Clients clients) {
        this.clients = clients;
    }

    public BusinessWorkers getBusinessWorkers() {
        return businessWorkers;
    }

    public void setBusinessWorkers(BusinessWorkers businessWorkers) {
        this.businessWorkers = businessWorkers;
    }

    public Administrators getAdministrators() {
        return administrators;
    }

    public void setAdministrators(Administrators administrators) {
        this.administrators = administrators;
    }

    public Moderators getModerators() {
        return moderators;
    }

    public void setModerators(Moderators moderators) {
        this.moderators = moderators;
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
        if (!(object instanceof AccessLevels)) {
            return false;
        }
        AccessLevels other = (AccessLevels) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd03.mok.model.temp.AccessLevels[ id=" + id + " ]";
    }
    
}
