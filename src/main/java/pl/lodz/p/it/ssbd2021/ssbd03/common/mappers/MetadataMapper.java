package pl.lodz.p.it.ssbd2021.ssbd03.common.mappers;

import pl.lodz.p.it.ssbd2021.ssbd03.common.dto.MetadataDto;
import pl.lodz.p.it.ssbd2021.ssbd03.entities.common.BaseEntity;

public class MetadataMapper {
    private MetadataMapper() {
    }

    public static MetadataDto toMetadataDto(BaseEntity entity) {
        return new MetadataDto(entity.getCreationDateTime(), entity.getLastAlterDateTime(),
                entity.getCreatedBy().getLogin(), entity.getAlteredBy().getLogin(),
                entity.getAlterType().getName(), entity.getVersion());
    }
}
