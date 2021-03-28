/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2021.ssbd03.mok.model.temp;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aradiuk
 */
@Entity
@Table(name = "accounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accounts.findAll", query = "SELECT a FROM Accounts a"),
    @NamedQuery(name = "Accounts.findById", query = "SELECT a FROM Accounts a WHERE a.id = :id"),
    @NamedQuery(name = "Accounts.findByFirstName", query = "SELECT a FROM Accounts a WHERE a.firstName = :firstName"),
    @NamedQuery(name = "Accounts.findBySecondName", query = "SELECT a FROM Accounts a WHERE a.secondName = :secondName"),
    @NamedQuery(name = "Accounts.findByLogin", query = "SELECT a FROM Accounts a WHERE a.login = :login"),
    @NamedQuery(name = "Accounts.findByEmail", query = "SELECT a FROM Accounts a WHERE a.email = :email"),
    @NamedQuery(name = "Accounts.findByPasswordHash", query = "SELECT a FROM Accounts a WHERE a.passwordHash = :passwordHash"),
    @NamedQuery(name = "Accounts.findByConfirmed", query = "SELECT a FROM Accounts a WHERE a.confirmed = :confirmed"),
    @NamedQuery(name = "Accounts.findByActive", query = "SELECT a FROM Accounts a WHERE a.active = :active"),
    @NamedQuery(name = "Accounts.findByLastIncorrectAuthenticationDatetime", query = "SELECT a FROM Accounts a WHERE a.lastIncorrectAuthenticationDatetime = :lastIncorrectAuthenticationDatetime"),
    @NamedQuery(name = "Accounts.findByLastIncorrectAuthenticationLogicalAddress", query = "SELECT a FROM Accounts a WHERE a.lastIncorrectAuthenticationLogicalAddress = :lastIncorrectAuthenticationLogicalAddress"),
    @NamedQuery(name = "Accounts.findByLastCorrectAuthenticationDatetime", query = "SELECT a FROM Accounts a WHERE a.lastCorrectAuthenticationDatetime = :lastCorrectAuthenticationDatetime"),
    @NamedQuery(name = "Accounts.findByLastCorrectAuthenticationLogicalAddress", query = "SELECT a FROM Accounts a WHERE a.lastCorrectAuthenticationLogicalAddress = :lastCorrectAuthenticationLogicalAddress"),
    @NamedQuery(name = "Accounts.findByVersion", query = "SELECT a FROM Accounts a WHERE a.version = :version"),
    @NamedQuery(name = "Accounts.findByCreationDatetime", query = "SELECT a FROM Accounts a WHERE a.creationDatetime = :creationDatetime"),
    @NamedQuery(name = "Accounts.findByLastAlterDatetime", query = "SELECT a FROM Accounts a WHERE a.lastAlterDatetime = :lastAlterDatetime"),
    @NamedQuery(name = "Accounts.findByAlteredById", query = "SELECT a FROM Accounts a WHERE a.alteredById = :alteredById")})
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Size(max = 2147483647)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 2147483647)
    @Column(name = "second_name")
    private String secondName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "login")
    private String login;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "password_hash")
    private String passwordHash;
    @Basic(optional = false)
    @NotNull
    @Column(name = "confirmed")
    private boolean confirmed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @Column(name = "last_incorrect_authentication_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastIncorrectAuthenticationDatetime;
    @Size(max = 2147483647)
    @Column(name = "last_incorrect_authentication_logical_address")
    private String lastIncorrectAuthenticationLogicalAddress;
    @Column(name = "last_correct_authentication_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastCorrectAuthenticationDatetime;
    @Size(max = 2147483647)
    @Column(name = "last_correct_authentication_logical_address")
    private String lastCorrectAuthenticationLogicalAddress;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountId")
    private Collection<AccessLevels> accessLevelsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdById")
    private Collection<Addresses> addressesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdById")
    private Collection<Clients> clientsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdById")
    private Collection<BusinessWorkers> businessWorkersCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdById")
    private Collection<Accounts> accountsCollection;
    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Accounts createdById;
    @JoinColumn(name = "alter_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AlterTypes alterTypeId;
    @JoinColumn(name = "language_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LanguageTypes languageTypeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdById")
    private Collection<Administrators> administratorsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdById")
    private Collection<Moderators> moderatorsCollection;

    public Accounts() {
    }

    public Accounts(Long id) {
        this.id = id;
    }

    public Accounts(Long id, String login, String email, String passwordHash, boolean confirmed, boolean active, long version, Date creationDatetime, Date lastAlterDatetime, long alteredById) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.passwordHash = passwordHash;
        this.confirmed = confirmed;
        this.active = active;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getLastIncorrectAuthenticationDatetime() {
        return lastIncorrectAuthenticationDatetime;
    }

    public void setLastIncorrectAuthenticationDatetime(Date lastIncorrectAuthenticationDatetime) {
        this.lastIncorrectAuthenticationDatetime = lastIncorrectAuthenticationDatetime;
    }

    public String getLastIncorrectAuthenticationLogicalAddress() {
        return lastIncorrectAuthenticationLogicalAddress;
    }

    public void setLastIncorrectAuthenticationLogicalAddress(String lastIncorrectAuthenticationLogicalAddress) {
        this.lastIncorrectAuthenticationLogicalAddress = lastIncorrectAuthenticationLogicalAddress;
    }

    public Date getLastCorrectAuthenticationDatetime() {
        return lastCorrectAuthenticationDatetime;
    }

    public void setLastCorrectAuthenticationDatetime(Date lastCorrectAuthenticationDatetime) {
        this.lastCorrectAuthenticationDatetime = lastCorrectAuthenticationDatetime;
    }

    public String getLastCorrectAuthenticationLogicalAddress() {
        return lastCorrectAuthenticationLogicalAddress;
    }

    public void setLastCorrectAuthenticationLogicalAddress(String lastCorrectAuthenticationLogicalAddress) {
        this.lastCorrectAuthenticationLogicalAddress = lastCorrectAuthenticationLogicalAddress;
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

    @XmlTransient
    public Collection<AccessLevels> getAccessLevelsCollection() {
        return accessLevelsCollection;
    }

    public void setAccessLevelsCollection(Collection<AccessLevels> accessLevelsCollection) {
        this.accessLevelsCollection = accessLevelsCollection;
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

    public Accounts getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Accounts createdById) {
        this.createdById = createdById;
    }

    public AlterTypes getAlterTypeId() {
        return alterTypeId;
    }

    public void setAlterTypeId(AlterTypes alterTypeId) {
        this.alterTypeId = alterTypeId;
    }

    public LanguageTypes getLanguageTypeId() {
        return languageTypeId;
    }

    public void setLanguageTypeId(LanguageTypes languageTypeId) {
        this.languageTypeId = languageTypeId;
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
        if (!(object instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2021.ssbd03.mok.model.temp.Accounts[ id=" + id + " ]";
    }
    
}
