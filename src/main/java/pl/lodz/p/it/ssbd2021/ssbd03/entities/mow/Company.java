package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;
import static pl.lodz.p.it.ssbd2021.ssbd03.entities.mow.Company.NIP_NAME_CONSTRAINT;

@Entity(name = "companies")
@NamedQueries({
        @NamedQuery(name = "Company.findByName", query = "SELECT company FROM companies company WHERE company.name = :name")
})
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nip", "name"}, name = NIP_NAME_CONSTRAINT)
})
@ToString
public class Company extends BaseEntity {
    public static final String NIP_NAME_CONSTRAINT = "companies_name_unique_constraint";
    @Getter
    @Id
    @SequenceGenerator(name = "COMPANY_SEQ_GEN", sequenceName = "companies_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_SEQ_GEN")
    @ToString.Exclude
    private long id;

    @Getter
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "address_id")
    @NotNull(message = CONSTRAINT_NOT_NULL)
    @Valid
    @ToString.Exclude
    private Address address;


    @Getter
    @Setter
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    @Column(name = "name", unique = true)
    private String name;

    @Getter
    @Setter
    @PhoneNumber
    @Column(name = "phone_number")
    private String phoneNumber;

    @Getter
    @Setter
    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    @Column(name = "nip")
    private long NIP;

    public Company(Address address, String name, String phoneNumber, long NIP) {
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.NIP = NIP;
    }

    public Company() {
    }

    @Override
    public Long getIdentifier() {
        return id;
    }
}
