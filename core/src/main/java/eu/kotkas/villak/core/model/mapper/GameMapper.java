package eu.kotkas.villak.core.model.mapper;

import eu.kotkas.villak.core.model.Game;
import eu.kotkas.villak.core.model.dto.GameDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Kristen Kotkas
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameMapper {

  GameMapper MAPPER = Mappers.getMapper(GameMapper.class);

  GameDto convert(Game game);

  Game invert(GameDto gameDto);
}
