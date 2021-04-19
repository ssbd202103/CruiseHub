package pl.lodz.p.it.ssbd2021.ssbd03.mow.model;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.common.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "cruise_pictures")
public class CruisePicture extends BaseEntity {


    @Getter
    @Id
    @SequenceGenerator(name = "ACCOUNT_SEQ_GEN", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @NotNull
    @Column(name = "img")
    private Byte[] img;

    @Getter
    @Setter
    @NotNull
    @Column(name = "img_name")
    private String imgName;
}