package eu.kotkas.villak.core.villak.model.mapper;

import eu.kotkas.villak.core.villak.model.Category;
import eu.kotkas.villak.core.villak.model.Game;
import eu.kotkas.villak.core.villak.model.Question;
import eu.kotkas.villak.core.villak.model.Round;
import eu.kotkas.villak.core.villak.model.Team;
import eu.kotkas.villak.core.villak.model.dto.CategoryDto;
import eu.kotkas.villak.core.villak.model.dto.GameDto;
import eu.kotkas.villak.core.villak.model.dto.QuestionDto;
import eu.kotkas.villak.core.villak.model.dto.RoundDto;
import eu.kotkas.villak.core.villak.model.dto.TeamDto;
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
public class GameMapperImpl implements GameMapper {

    @Override
    public GameDto convert(Game game) {
        if ( game == null ) {
            return null;
        }

        GameDto gameDto = new GameDto();

        gameDto.setTeams( teamListToTeamDtoList( game.getTeams() ) );
        gameDto.setRounds( roundListToRoundDtoList( game.getRounds() ) );

        return gameDto;
    }

    @Override
    public Game invert(GameDto gameDto) {
        if ( gameDto == null ) {
            return null;
        }

        Game game = new Game();

        game.setTeams( teamDtoListToTeamList( gameDto.getTeams() ) );
        game.setRounds( roundDtoListToRoundList( gameDto.getRounds() ) );

        return game;
    }

    protected TeamDto teamToTeamDto(Team team) {
        if ( team == null ) {
            return null;
        }

        TeamDto teamDto = new TeamDto();

        teamDto.setName( team.getName() );

        return teamDto;
    }

    protected List<TeamDto> teamListToTeamDtoList(List<Team> list) {
        if ( list == null ) {
            return null;
        }

        List<TeamDto> list1 = new ArrayList<TeamDto>( list.size() );
        for ( Team team : list ) {
            list1.add( teamToTeamDto( team ) );
        }

        return list1;
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

    protected CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setName( category.getName() );
        categoryDto.setQuestions( questionListToQuestionDtoList( category.getQuestions() ) );

        return categoryDto;
    }

    protected List<CategoryDto> categoryListToCategoryDtoList(List<Category> list) {
        if ( list == null ) {
            return null;
        }

        List<CategoryDto> list1 = new ArrayList<CategoryDto>( list.size() );
        for ( Category category : list ) {
            list1.add( categoryToCategoryDto( category ) );
        }

        return list1;
    }

    protected RoundDto roundToRoundDto(Round round) {
        if ( round == null ) {
            return null;
        }

        RoundDto roundDto = new RoundDto();

        roundDto.setCategories( categoryListToCategoryDtoList( round.getCategories() ) );

        return roundDto;
    }

    protected List<RoundDto> roundListToRoundDtoList(List<Round> list) {
        if ( list == null ) {
            return null;
        }

        List<RoundDto> list1 = new ArrayList<RoundDto>( list.size() );
        for ( Round round : list ) {
            list1.add( roundToRoundDto( round ) );
        }

        return list1;
    }

    protected Team teamDtoToTeam(TeamDto teamDto) {
        if ( teamDto == null ) {
            return null;
        }

        Team team = new Team();

        team.setName( teamDto.getName() );

        return team;
    }

    protected List<Team> teamDtoListToTeamList(List<TeamDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Team> list1 = new ArrayList<Team>( list.size() );
        for ( TeamDto teamDto : list ) {
            list1.add( teamDtoToTeam( teamDto ) );
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

    protected Category categoryDtoToCategory(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category category = new Category();

        category.setName( categoryDto.getName() );
        category.setQuestions( questionDtoListToQuestionList( categoryDto.getQuestions() ) );

        return category;
    }

    protected List<Category> categoryDtoListToCategoryList(List<CategoryDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Category> list1 = new ArrayList<Category>( list.size() );
        for ( CategoryDto categoryDto : list ) {
            list1.add( categoryDtoToCategory( categoryDto ) );
        }

        return list1;
    }

    protected Round roundDtoToRound(RoundDto roundDto) {
        if ( roundDto == null ) {
            return null;
        }

        Round round = new Round();

        round.setCategories( categoryDtoListToCategoryList( roundDto.getCategories() ) );

        return round;
    }

    protected List<Round> roundDtoListToRoundList(List<RoundDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Round> list1 = new ArrayList<Round>( list.size() );
        for ( RoundDto roundDto : list ) {
            list1.add( roundDtoToRound( roundDto ) );
        }

        return list1;
    }
}
