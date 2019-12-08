package eu.kotkas.villak.core.model.mapper;

import eu.kotkas.villak.core.model.Category;
import eu.kotkas.villak.core.model.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Kristen Kotkas
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

  CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

  CategoryDto convert(Category category);

  Category invert(CategoryDto categoryDto);
}
