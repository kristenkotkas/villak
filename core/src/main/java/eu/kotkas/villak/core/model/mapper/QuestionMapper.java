package eu.kotkas.villak.core.model.mapper;

import eu.kotkas.villak.core.model.Question;
import eu.kotkas.villak.core.model.dto.QuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Kristen Kotkas
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {

  QuestionMapper MAPPER = Mappers.getMapper(QuestionMapper.class);

  QuestionDto convert(Question question);

  Question invert(QuestionDto questionDto);
}
