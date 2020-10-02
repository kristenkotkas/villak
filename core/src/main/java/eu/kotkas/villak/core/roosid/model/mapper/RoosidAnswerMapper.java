package eu.kotkas.villak.core.roosid.model.mapper;

import eu.kotkas.villak.core.roosid.model.Answer;
import eu.kotkas.villak.core.roosid.model.dto.AnswerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RoosidAnswerMapper {

    RoosidAnswerMapper MAPPER = Mappers.getMapper(RoosidAnswerMapper.class);

    AnswerDto convert(Answer answer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "state", ignore = true)
    Answer invert(AnswerDto answerDto);

}
