package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.validators.PhoneNumber;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity(name = "companies")
@NamedQueries({
        @NamedQuery(name = "Company.findByName", query = "SELECT company FROM companies company WHERE company.name = :name")
})
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nip", "name"},name = "companies_name_unique_constraint")
})
public class Company extends BaseEntity {
    @Getter
    @Id
    @SequenceGenerator(name = "COMPANY_SEQ_GEN", sequenceName = "companies_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @NotNull
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;


    @Getter
    @Setter
    @NotEmpty
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
    private Long NIP;

    public Company(Address address, String name, String phoneNumber, Long NIP) {
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.NIP = NIP;
    }

    public Company() {
    }
}
