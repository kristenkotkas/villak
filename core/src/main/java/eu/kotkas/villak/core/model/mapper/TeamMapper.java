package eu.kotkas.villak.core.model.mapper;

import eu.kotkas.villak.core.model.Team;
import eu.kotkas.villak.core.model.dto.TeamDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Kristen Kotkas
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeamMapper {

  TeamMapper MAPPER = Mappers.getMapper(TeamMapper.class);

  TeamDto convert(Team team);

  Team invert(TeamDto teamDto);
}
