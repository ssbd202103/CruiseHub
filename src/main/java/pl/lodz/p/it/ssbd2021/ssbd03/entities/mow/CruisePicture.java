package pl.lodz.p.it.ssbd2021.ssbd03.entities.mow;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_EMPTY;
import static pl.lodz.p.it.ssbd2021.ssbd03.common.I18n.CONSTRAINT_NOT_NULL;

@Entity(name = "cruise_pictures")
public class CruisePicture extends BaseEntity {

    @Getter
    @Id
    @SequenceGenerator(name = "CRUISE_PICTURE_SEQ_GEN", sequenceName = "cruise_pictures_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRUISE_PICTURE_SEQ_GEN")
    @Column(name = "id")
    private long id;

    @Getter
    @Setter
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    @Column(name = "img")
    private Byte[] img;

    @Getter
    @Setter
    @NotEmpty(message = CONSTRAINT_NOT_EMPTY)
    @Column(name = "img_name")
    private String imgName;

    public CruisePicture(@NotNull(message = CONSTRAINT_NOT_NULL) Byte[] img, @NotNull(message = CONSTRAINT_NOT_NULL) String imgName) {
        this.img = img;
        this.imgName = imgName;
    }

    public CruisePicture() {
    }
}