package eu.kotkas.villak.core.villak.model.mapper;

import eu.kotkas.villak.core.villak.model.Category;
import eu.kotkas.villak.core.villak.model.Question;
import eu.kotkas.villak.core.villak.model.dto.CategoryDto;
import eu.kotkas.villak.core.villak.model.dto.QuestionDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-28T21:46:38+0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto convert(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setName( category.getName() );
        categoryDto.setQuestions( questionListToQuestionDtoList( category.getQuestions() ) );

        return categoryDto;
    }

    @Override
    public Category invert(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setName( categoryDto.getName() );
        category.setQuestions( questionDtoListToQuestionList( categoryDto.getQuestions() ) );

        return category;
    }

    protected QuestionDto questionToQuestionDto(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionDto questionDto = new QuestionDto();

        questionDto.setPictureUri( question.getPictureUri() );
        questionDto.setSoundUri( question.getSoundUri() );
        questionDto.setAmount( question.getAmount() );
        questionDto.setQuestion( question.getQuestion() );
        questionDto.setAnswer( question.getAnswer() );
        questionDto.setSilver( question.isSilver() );

        return questionDto;
    }

    protected List<QuestionDto> questionListToQuestionDtoList(List<Question> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionDto> list1 = new ArrayList<QuestionDto>( list.size() );
        for ( Question question : list ) {
            list1.add( questionToQuestionDto( question ) );
        }

        return list1;
    }

    protected Question questionDtoToQuestion(QuestionDto questionDto) {
        if ( questionDto == null ) {
            return null;
        }

        Question question = new Question();

        if ( questionDto.getAmount() != null ) {
            question.setAmount( questionDto.getAmount() );
        }
        if ( questionDto.getSilver() != null ) {
            question.setSilver( questionDto.getSilver() );
        }
        question.setQuestion( questionDto.getQuestion() );
        question.setAnswer( questionDto.getAnswer() );
        question.setPictureUri( questionDto.getPictureUri() );
        question.setSoundUri( questionDto.getSoundUri() );

        return question;
    }

    protected List<Question> questionDtoListToQuestionList(List<QuestionDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Question> list1 = new ArrayList<Question>( list.size() );
        for ( QuestionDto questionDto : list ) {
            list1.add( questionDtoToQuestion( questionDto ) );
        }

        return list1;
    }
}
