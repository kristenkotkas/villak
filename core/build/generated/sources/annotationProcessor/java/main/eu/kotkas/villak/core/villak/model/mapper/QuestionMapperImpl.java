package eu.kotkas.villak.core.villak.model.mapper;

import eu.kotkas.villak.core.villak.model.Question;
import eu.kotkas.villak.core.villak.model.dto.QuestionDto;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-29T19:25:56+0300",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public QuestionDto convert(Question question) {
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

    @Override
    public Question invert(QuestionDto questionDto) {
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
}
