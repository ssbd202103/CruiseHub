package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.mok.Address;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "companies")
@NamedQueries({
        @NamedQuery(name = "Company.findByName", query = "SELECT company FROM companies company WHERE company.name = :name")
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
    @NotNull
    @Column(name = "name", unique = true)
    private String name;

    @Getter
    @Setter
    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @Getter
    @Setter
    @NotNull
    @Column(name = "nip")
    private Long NIP;

//    @Getter
//    @NotNull
//    @ManyToMany
//    @JoinTable(name = "scompany_workers",
//            joinColumns = @JoinColumn(name = "company_id"),
//            inverseJoinColumns = @JoinColumn(name = "business_worker_id")
//    )
//    private List<BusinessWorker> workers = new ArrayList<>();

    public Company(Long id, @NotNull Address address, @NotNull String name, @NotNull String phoneNumber, @NotNull Long NIP) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.NIP = NIP;
    }

    public Company() {
    }
}
