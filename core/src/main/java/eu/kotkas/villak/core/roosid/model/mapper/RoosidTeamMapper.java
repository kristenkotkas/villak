package eu.kotkas.villak.core.roosid.model.mapper;

import eu.kotkas.villak.core.roosid.model.Team;
import eu.kotkas.villak.core.roosid.model.dto.TeamDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RoosidTeamMapper {

    RoosidTeamMapper MAPPER = Mappers.getMapper(RoosidTeamMapper.class);

    TeamDto convert(Team answer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "score", ignore = true)
    @Mapping(target = "crossCount", ignore = true)
    Team invert(TeamDto answerDto);

}
