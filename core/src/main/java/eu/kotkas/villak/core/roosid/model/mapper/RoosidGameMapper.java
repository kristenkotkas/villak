package eu.kotkas.villak.core.roosid.model.mapper;

import eu.kotkas.villak.core.roosid.model.Game;
import eu.kotkas.villak.core.roosid.model.dto.GameDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {RoosidTeamMapper.class, RoosidRoundMapper.class}
)
public interface RoosidGameMapper {

    RoosidGameMapper MAPPER = Mappers.getMapper(RoosidGameMapper.class);

    GameDto convert(Game answer);

    @Mapping(target = "latestMessages", ignore = true)
    Game invert(GameDto answerDto);

}
