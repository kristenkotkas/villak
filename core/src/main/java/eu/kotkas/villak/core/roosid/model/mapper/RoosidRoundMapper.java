package eu.kotkas.villak.core.roosid.model.mapper;

import eu.kotkas.villak.core.roosid.model.Round;
import eu.kotkas.villak.core.roosid.model.dto.RoundDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {RoosidAnswerMapper.class}
)
public interface RoosidRoundMapper {

    RoosidRoundMapper MAPPER = Mappers.getMapper(RoosidRoundMapper.class);

    RoundDto convert(Round answer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "score", ignore = true)
    @Mapping(target = "active", ignore = true)
    Round invert(RoundDto answerDto);

}
