package pl.lodz.p.it.ssbd2021.ssbd03.mow.model;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.common.BaseEntity;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.entities.Address;
import pl.lodz.p.it.ssbd2021.ssbd03.mok.model.entities.accesslevels.BusinessWorker;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity(name = "companies")
public class Company extends BaseEntity {
    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
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
    @Column(name = "name")
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

    @Getter
    @NotNull
    @ManyToMany
    @JoinTable(name = "company_workers")
    private List<BusinessWorker> workers;

}
