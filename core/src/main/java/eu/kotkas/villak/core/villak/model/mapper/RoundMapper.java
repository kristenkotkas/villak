package eu.kotkas.villak.core.villak.model.mapper;

import eu.kotkas.villak.core.villak.model.Round;
import eu.kotkas.villak.core.villak.model.dto.RoundDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Kristen Kotkas
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoundMapper {

  RoundMapper MAPPER = Mappers.getMapper(RoundMapper.class);

  RoundDto convert(Round round);

  Round invert(RoundDto roundDto);
}
