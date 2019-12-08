package eu.kotkas.villak.core.service;

import eu.kotkas.villak.core.dao.DataDao;
import eu.kotkas.villak.core.model.Category;
import eu.kotkas.villak.core.model.Game;
import eu.kotkas.villak.core.model.Message;
import eu.kotkas.villak.core.model.Question;
import eu.kotkas.villak.core.model.Round;
import eu.kotkas.villak.core.model.Team;
import eu.kotkas.villak.core.model.dto.CategoryDto;
import eu.kotkas.villak.core.model.dto.GameDto;
import eu.kotkas.villak.core.model.dto.QuestionDto;
import eu.kotkas.villak.core.model.dto.RoundDto;
import eu.kotkas.villak.core.model.dto.TeamDto;
import eu.kotkas.villak.core.model.enums.Action;
import eu.kotkas.villak.core.model.enums.NameState;
import eu.kotkas.villak.core.model.enums.QuestionState;
import eu.kotkas.villak.core.model.mapper.CategoryMapper;
import eu.kotkas.villak.core.model.mapper.GameMapper;
import eu.kotkas.villak.core.model.mapper.QuestionMapper;
import eu.kotkas.villak.core.model.mapper.RoundMapper;
import eu.kotkas.villak.core.model.mapper.TeamMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static eu.kotkas.villak.core.service.TestHelper.*;

/**
 * @author Kristen Kotkas
 */
class DataServiceImplTest {

  private final DataService dataService = new DataServiceImpl(
    new DataDao()
  );

  private Game game;

  @BeforeEach
  void setUp() {
    game = TestGameProvider.loadGame();
  }

  @Test
  void reduce_questionActions() {
    assertQuestions(game, 1L, Question::getState, QuestionState.CLOSE);
    assertQuestions(game, 2L, Question::getState, QuestionState.CLOSE);
    assertQuestions(game, 3L, Question::getState, QuestionState.CLOSE);

    Game gameSt2 = dataService.reduce(game, new Message(Action.OPEN.name(), 1L, null));

    assertQuestions(gameSt2, 1L, Question::getState, QuestionState.OPEN);
    assertQuestions(gameSt2, 2L, Question::getState, QuestionState.CLOSE);
    assertQuestions(gameSt2, 3L, Question::getState, QuestionState.CLOSE);

    Game gameSt3 = dataService.reduce(gameSt2, new Message(Action.CLOSE.name(), 1L, null));

    assertQuestions(gameSt3, 1L, Question::getState, QuestionState.CLOSE);
    assertQuestions(gameSt3, 2L, Question::getState, QuestionState.CLOSE);
    assertQuestions(gameSt3, 3L, Question::getState, QuestionState.CLOSE);

    Game gameSt4 = dataService.reduce(gameSt3, new Message(Action.ANSWERED.name(), 2L, null));

    assertQuestions(gameSt4, 1L, Question::getState, QuestionState.CLOSE);
    assertQuestions(gameSt4, 2L, Question::getState, QuestionState.ANSWERED);
    assertQuestions(gameSt4, 3L, Question::getState, QuestionState.CLOSE);

    Game gameSt5 = dataService.reduce(gameSt4, new Message(Action.SILVER.name(), 3L, null));

    assertQuestions(gameSt5, 1L, Question::getState, QuestionState.CLOSE);
    assertQuestions(gameSt5, 2L, Question::getState, QuestionState.ANSWERED);
    assertQuestions(gameSt5, 3L, Question::getState, QuestionState.SILVER);
  }

  @Test
  void reduce_teamScore() {
    // initial score = 0
    assertTeam(game, 0, Team::getScore, 0);
    assertTeam(game, 1, Team::getScore, 0);
    assertTeam(game, 2, Team::getScore, 0);

    // score is increased
    Game gameSt2 = dataService.reduce(game, new Message(Action.SCORE.name(), 0, 100));
    assertTeam(gameSt2, 0, Team::getScore, 100);
    assertTeam(gameSt2, 1, Team::getScore, 0);
    assertTeam(gameSt2, 2, Team::getScore, 0);

    // score is reduced
    Game gameSt3 = dataService.reduce(gameSt2, new Message(Action.SCORE.name(), 1, -150));
    assertTeam(gameSt3, 0, Team::getScore, 100);
    assertTeam(gameSt3, 1, Team::getScore, -150);
    assertTeam(gameSt3, 2, Team::getScore, 0);
  }

  @Test
  void reduce_categoryState() {
    // initial nameState = CATEGORY_HIDE
    assertCategory(game, 0, Category::getNameState, NameState.CATEGORY_HIDE);
    assertCategory(game, 1, Category::getNameState, NameState.CATEGORY_HIDE);

    // show category
    Game gameSt2 = dataService.reduce(game, new Message(Action.CATEGORY_SHOW.name(), 1, null));
    assertCategory(gameSt2, 0, Category::getNameState, NameState.CATEGORY_HIDE);
    assertCategory(gameSt2, 1, Category::getNameState, NameState.CATEGORY_SHOW);

    // hide category
    Game gameSt3 = dataService.reduce(gameSt2, new Message(Action.CATEGORY_HIDE.name(), 1, null));
    assertCategory(gameSt3, 0, Category::getNameState, NameState.CATEGORY_HIDE);
    assertCategory(gameSt3, 1, Category::getNameState, NameState.CATEGORY_HIDE);
  }

  @Test
  void reduce_round() {
    // initial active = false
    assertRound(game, 0, Round::isActive, false);
    assertRound(game, 1, Round::isActive, false);
    assertRound(game, 2, Round::isActive, false);

    // first round to active
    Game gameSt1 = dataService.reduce(game, new Message(Action.ACTIVE_ROUND.name(), 0, null));
    assertRound(gameSt1, 0, Round::isActive, true);
    assertRound(gameSt1, 1, Round::isActive, false);
    assertRound(gameSt1, 2, Round::isActive, false);

    // second round to active
    Game gameSt2 = dataService.reduce(gameSt1, new Message(Action.ACTIVE_ROUND.name(), 1, null));
    assertRound(gameSt2, 0, Round::isActive, false);
    assertRound(gameSt2, 1, Round::isActive, true);
    assertRound(gameSt2, 2, Round::isActive, false);

    // second round to active
    Game gameSt3 = dataService.reduce(gameSt2, new Message(Action.ACTIVE_ROUND.name(), 2, null));
    assertRound(gameSt3, 0, Round::isActive, false);
    assertRound(gameSt3, 1, Round::isActive, false);
    assertRound(gameSt3, 2, Round::isActive, true);
  }

  @Test
  void reduce_teamInitialButton() {
    // initial pressed = false
    assertTeam(game, 0, Team::isHavePressed, false);
    assertTeam(game, 1, Team::isHavePressed, false);
    assertTeam(game, 2, Team::isHavePressed, false);

    // initial timePressed = 0
    assertTeam(game, 0, Team::getTimePressed, 0);
    assertTeam(game, 1, Team::getTimePressed, 0);
    assertTeam(game, 2, Team::getTimePressed, 0);

    // initial quickest = false
    assertTeam(game, 0, Team::isQuickest, false);
    assertTeam(game, 1, Team::isQuickest, false);
    assertTeam(game, 2, Team::isQuickest, false);
  }

  @Test
  void reduce_team_pressButton_Reset() {
    // first pressed
    Game gameSt2 = dataService.reduce(game, new Message(Action.PRESS_BUTTON.name(), 0, 123));
    assertTeam(gameSt2, 0, Team::isHavePressed, true);
    assertTeam(gameSt2, 1, Team::isHavePressed, false);
    assertTeam(gameSt2, 2, Team::isHavePressed, false);

    assertTeam(gameSt2, 0, Team::getTimePressed, 123);
    assertTeam(gameSt2, 1, Team::getTimePressed, 0);
    assertTeam(gameSt2, 2, Team::getTimePressed, 0);

    // second pressed
    Game gameSt3 = dataService.reduce(gameSt2, new Message(Action.PRESS_BUTTON.name(), 2, 64));
    assertTeam(gameSt3, 0, Team::isHavePressed, true);
    assertTeam(gameSt3, 1, Team::isHavePressed, false);
    assertTeam(gameSt3, 2, Team::isHavePressed, true);

    assertTeam(gameSt3, 0, Team::getTimePressed, 123);
    assertTeam(gameSt3, 1, Team::getTimePressed, 0);
    assertTeam(gameSt3, 2, Team::getTimePressed, 64);

    // reset button
    Game gameSt4 = dataService.reduce(gameSt3, new Message(Action.RESET_BUTTON.name(), -1, null));
    assertTeam(gameSt4, 0, Team::isHavePressed, false);
    assertTeam(gameSt4, 1, Team::isHavePressed, false);
    assertTeam(gameSt4, 2, Team::isHavePressed, false);

    assertTeam(gameSt4, 0, Team::getTimePressed, 0);
    assertTeam(gameSt4, 1, Team::getTimePressed, 0);
    assertTeam(gameSt4, 2, Team::getTimePressed, 0);

    assertTeam(gameSt4, 0, Team::isQuickest, false);
    assertTeam(gameSt4, 1, Team::isQuickest, false);
    assertTeam(gameSt4, 2, Team::isQuickest, false);
  }

  @Test
  void reduce_showQuickest_allZero() {
    Game gameSt2 = dataService.reduce(game, new Message(Action.SHOW_QUICKEST.name(), -1, null));

    assertTeam(gameSt2, 0, Team::isQuickest, false);
    assertTeam(gameSt2, 1, Team::isQuickest, false);
    assertTeam(gameSt2, 2, Team::isQuickest, false);
  }

  @Test
  void reduce_showQuickest_onePressedOthersZero() {
    Game gameSt2 = dataService.reduce(game, new Message(Action.PRESS_BUTTON.name(), 1, 123));
    Game gameSt3 = dataService.reduce(gameSt2, new Message(Action.SHOW_QUICKEST.name(), -1, null));

    assertTeam(gameSt3, 0, Team::isQuickest, false);
    assertTeam(gameSt3, 1, Team::isQuickest, true);
    assertTeam(gameSt3, 2, Team::isQuickest, false);
  }

  @Test
  void reduce_showQuickest_quickestUniqueAllPressed() {
    Game gameSt2 = dataService.reduce(game, new Message(Action.PRESS_BUTTON.name(), 2, 100));
    Game gameSt3 = dataService.reduce(gameSt2, new Message(Action.PRESS_BUTTON.name(), 0, 101));
    Game gameSt4 = dataService.reduce(gameSt3, new Message(Action.PRESS_BUTTON.name(), 1, 102));
    Game gameSt5 = dataService.reduce(gameSt4, new Message(Action.SHOW_QUICKEST.name(), -1, null));

    assertTeam(gameSt5, 0, Team::isQuickest, false);
    assertTeam(gameSt5, 1, Team::isQuickest, false);
    assertTeam(gameSt5, 2, Team::isQuickest, true);

    // reset button
    Game gameSt6 = dataService.reduce(gameSt5, new Message(Action.RESET_BUTTON.name(), -1, null));

    assertTeam(gameSt6, 0, Team::isQuickest, false);
    assertTeam(gameSt6, 1, Team::isQuickest, false);
    assertTeam(gameSt6, 2, Team::isQuickest, false);
  }

  @Test
  void reduce_closeAllButtons() {
    Game gameSt2 = dataService.reduce(game, new Message(Action.PRESS_BUTTON.name(), 2, 100));
    Game gameSt3 = dataService.reduce(gameSt2, new Message(Action.PRESS_BUTTON.name(), 0, 101));
    Game gameSt4 = dataService.reduce(gameSt3, new Message(Action.PRESS_BUTTON.name(), 1, 102));

    Game gameSt5 = dataService.reduce(gameSt4, new Message(Action.CLOSE_BUTTON.name(), -1, null));

    assertTeam(gameSt5, 0, Team::isHavePressed, true);
    assertTeam(gameSt5, 1, Team::isHavePressed, true);
    assertTeam(gameSt5, 2, Team::isHavePressed, true);

    assertTeam(gameSt5, 0, Team::isQuickest, false);
    assertTeam(gameSt5, 1, Team::isQuickest, false);
    assertTeam(gameSt5, 2, Team::isQuickest, false);

    assertTeam(gameSt5, 0, Team::getTimePressed, 0);
    assertTeam(gameSt5, 1, Team::getTimePressed, 0);
    assertTeam(gameSt5, 2, Team::getTimePressed, 0);
  }

  @Test
  void reduce_toggleWinner() {
    // initial isWinner = false
    assertTeam(game, 0, Team::isWinner, false);
    assertTeam(game, 1, Team::isWinner, false);
    assertTeam(game, 2, Team::isWinner, false);

    Game gameSt2 = dataService.reduce(game, new Message(Action.TOGGLE_WINNER.name(), 1, -1));
    assertTeam(gameSt2, 0, Team::isWinner, false);
    assertTeam(gameSt2, 1, Team::isWinner, true);
    assertTeam(gameSt2, 2, Team::isWinner, false);

    Game gameSt3 = dataService.reduce(gameSt2, new Message(Action.TOGGLE_WINNER.name(), 2, -1));
    assertTeam(gameSt3, 0, Team::isWinner, false);
    assertTeam(gameSt3, 1, Team::isWinner, true);
    assertTeam(gameSt3, 2, Team::isWinner, true);

    Game gameSt4 = dataService.reduce(gameSt3, new Message(Action.TOGGLE_WINNER.name(), 2, -1));
    assertTeam(gameSt4, 0, Team::isWinner, false);
    assertTeam(gameSt4, 1, Team::isWinner, true);
    assertTeam(gameSt4, 2, Team::isWinner, false);

  }

  @Test
  void mapper_Team() {
    TeamDto teamDto = getTeamDto();
    Team team = TeamMapper.MAPPER.invert(teamDto);
    assertTeam(team);
  }

  @Test
  void mapper_Question() {
    QuestionDto questionDto = getQuestionDto();
    Question question = QuestionMapper.MAPPER.invert(questionDto);
    assertQuestion(question);
  }

  @Test
  void mapper_Category() {
    CategoryDto categoryDto = getCategoryDto();
    Category category = CategoryMapper.MAPPER.invert(categoryDto);
    assertCategory(category);
  }

  @Test
  void mapper_Round() {
    RoundDto roundDto = getRoundDto();
    Round round = RoundMapper.MAPPER.invert(roundDto);
    assertRound(round);
  }

  @Test
  void mapper_Game() {
    GameDto gameDto = GameDto.builder()
      .teams(Arrays.asList(
        getTeamDto(),
        getTeamDto()
      ))
      .rounds(Arrays.asList(
        getRoundDto(),
        getRoundDto()
      ))
      .build();

    Game game = GameMapper.MAPPER.invert(gameDto);

    game.getTeams().forEach(TestHelper::assertTeam);
    game.getRounds().forEach(TestHelper::assertRound);
  }

}
