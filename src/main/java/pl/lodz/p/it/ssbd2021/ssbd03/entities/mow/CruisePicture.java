package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;


import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "cruise_pictures")
public class CruisePicture extends BaseEntity {


    @Getter
    @Id
    @SequenceGenerator(name = "CRUISE_PICTURE_SEQ_GEN", sequenceName = "cruise_pictures_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRUISE_PICTURE_SEQ_GEN")
    @Column(name = "id")
    private Long id;

    @Getter
    @Setter
    @Column(name = "img")
    private Byte[] img;

    @Getter
    @Setter
    @NotNull
    @Column(name = "img_name")
    private String imgName;

    public CruisePicture(Long id, @NotNull Byte[] img, @NotNull String imgName) {
        this.id = id;
        this.img = img;
        this.imgName = imgName;
    }

    public CruisePicture() {
    }
}