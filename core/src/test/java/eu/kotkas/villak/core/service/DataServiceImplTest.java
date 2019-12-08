package eu.kotkas.villak.core.service;

import eu.kotkas.villak.core.dao.DataDao;
import eu.kotkas.villak.core.model.*;
import eu.kotkas.villak.core.model.dto.*;
import eu.kotkas.villak.core.model.enums.Action;
import eu.kotkas.villak.core.model.enums.NameState;
import eu.kotkas.villak.core.model.enums.QuestionState;
import eu.kotkas.villak.core.model.mapper.*;
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

    Game gameSt2 = dataService.reduce(game, getSingleMessage(Action.OPEN, 1L, null));

    assertQuestions(gameSt2, 1L, Question::getState, QuestionState.OPEN);
    assertQuestions(gameSt2, 2L, Question::getState, QuestionState.CLOSE);
    assertQuestions(gameSt2, 3L, Question::getState, QuestionState.CLOSE);

    Game gameSt3 = dataService.reduce(gameSt2, getSingleMessage(Action.CLOSE, 1L, null));

    assertQuestions(gameSt3, 1L, Question::getState, QuestionState.CLOSE);
    assertQuestions(gameSt3, 2L, Question::getState, QuestionState.CLOSE);
    assertQuestions(gameSt3, 3L, Question::getState, QuestionState.CLOSE);

    Game gameSt4 = dataService.reduce(gameSt3, getSingleMessage(Action.ANSWERED, 2L, null));

    assertQuestions(gameSt4, 1L, Question::getState, QuestionState.CLOSE);
    assertQuestions(gameSt4, 2L, Question::getState, QuestionState.ANSWERED);
    assertQuestions(gameSt4, 3L, Question::getState, QuestionState.CLOSE);

    Game gameSt5 = dataService.reduce(gameSt4, getSingleMessage(Action.SILVER, 3L, null));

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
    Game gameSt2 = dataService.reduce(game, getSingleMessage(Action.SCORE, 0, 100));
    assertTeam(gameSt2, 0, Team::getScore, 100);
    assertTeam(gameSt2, 1, Team::getScore, 0);
    assertTeam(gameSt2, 2, Team::getScore, 0);

    // score is reduced
    Game gameSt3 = dataService.reduce(gameSt2, getSingleMessage(Action.SCORE, 1, -150));
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
    Game gameSt2 = dataService.reduce(game, getSingleMessage(Action.CATEGORY_SHOW, 1, null));
    assertCategory(gameSt2, 0, Category::getNameState, NameState.CATEGORY_HIDE);
    assertCategory(gameSt2, 1, Category::getNameState, NameState.CATEGORY_SHOW);

    // hide category
    Game gameSt3 = dataService.reduce(gameSt2, getSingleMessage(Action.CATEGORY_HIDE, 1, null));
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
    Game gameSt1 = dataService.reduce(game, getSingleMessage(Action.ACTIVE_ROUND, 0, null));
    assertRound(gameSt1, 0, Round::isActive, true);
    assertRound(gameSt1, 1, Round::isActive, false);
    assertRound(gameSt1, 2, Round::isActive, false);

    // second round to active
    Game gameSt2 = dataService.reduce(gameSt1, getSingleMessage(Action.ACTIVE_ROUND, 1, null));
    assertRound(gameSt2, 0, Round::isActive, false);
    assertRound(gameSt2, 1, Round::isActive, true);
    assertRound(gameSt2, 2, Round::isActive, false);

    // second round to active
    Game gameSt3 = dataService.reduce(gameSt2, getSingleMessage(Action.ACTIVE_ROUND, 2, null));
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
    Game gameSt2 = dataService.reduce(game, getSingleMessage(Action.PRESS_BUTTON, 0, 123));
    assertTeam(gameSt2, 0, Team::isHavePressed, true);
    assertTeam(gameSt2, 1, Team::isHavePressed, false);
    assertTeam(gameSt2, 2, Team::isHavePressed, false);

    assertTeam(gameSt2, 0, Team::getTimePressed, 123);
    assertTeam(gameSt2, 1, Team::getTimePressed, 0);
    assertTeam(gameSt2, 2, Team::getTimePressed, 0);

    // second pressed
    Game gameSt3 = dataService.reduce(gameSt2, getSingleMessage(Action.PRESS_BUTTON, 2, 64));
    assertTeam(gameSt3, 0, Team::isHavePressed, true);
    assertTeam(gameSt3, 1, Team::isHavePressed, false);
    assertTeam(gameSt3, 2, Team::isHavePressed, true);

    assertTeam(gameSt3, 0, Team::getTimePressed, 123);
    assertTeam(gameSt3, 1, Team::getTimePressed, 0);
    assertTeam(gameSt3, 2, Team::getTimePressed, 64);

    // reset button
    Game gameSt4 = dataService.reduce(gameSt3, getSingleMessage(Action.RESET_BUTTON, -1, null));
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
    Game gameSt2 = dataService.reduce(game, getSingleMessage(Action.SHOW_QUICKEST, -1, null));

    assertTeam(gameSt2, 0, Team::isQuickest, false);
    assertTeam(gameSt2, 1, Team::isQuickest, false);
    assertTeam(gameSt2, 2, Team::isQuickest, false);
  }

  @Test
  void reduce_showQuickest_onePressedOthersZero() {
    Game gameSt2 = dataService.reduce(game, getSingleMessage(Action.PRESS_BUTTON, 1, 123));
    Game gameSt3 = dataService.reduce(gameSt2, getSingleMessage(Action.SHOW_QUICKEST, -1, null));

    assertTeam(gameSt3, 0, Team::isQuickest, false);
    assertTeam(gameSt3, 1, Team::isQuickest, true);
    assertTeam(gameSt3, 2, Team::isQuickest, false);
  }

  @Test
  void reduce_showQuickest_quickestUniqueAllPressed() {
    Game gameSt2 = dataService.reduce(game, getSingleMessage(Action.PRESS_BUTTON, 2, 100));
    Game gameSt3 = dataService.reduce(gameSt2, getSingleMessage(Action.PRESS_BUTTON, 0, 101));
    Game gameSt4 = dataService.reduce(gameSt3, getSingleMessage(Action.PRESS_BUTTON, 1, 102));
    Game gameSt5 = dataService.reduce(gameSt4, getSingleMessage(Action.SHOW_QUICKEST, -1, null));

    assertTeam(gameSt5, 0, Team::isQuickest, false);
    assertTeam(gameSt5, 1, Team::isQuickest, false);
    assertTeam(gameSt5, 2, Team::isQuickest, true);

    // reset button
    Game gameSt6 = dataService.reduce(gameSt5, getSingleMessage(Action.RESET_BUTTON, -1, null));

    assertTeam(gameSt6, 0, Team::isQuickest, false);
    assertTeam(gameSt6, 1, Team::isQuickest, false);
    assertTeam(gameSt6, 2, Team::isQuickest, false);
  }

  @Test
  void reduce_closeAllButtons() {
    Game gameSt2 = dataService.reduce(game, getSingleMessage(Action.PRESS_BUTTON, 2, 100));
    Game gameSt3 = dataService.reduce(gameSt2, getSingleMessage(Action.PRESS_BUTTON, 0, 101));
    Game gameSt4 = dataService.reduce(gameSt3, getSingleMessage(Action.PRESS_BUTTON, 1, 102));

    Game gameSt5 = dataService.reduce(gameSt4, getSingleMessage(Action.CLOSE_BUTTON, -1, null));

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

    Game gameSt2 = dataService.reduce(game, getSingleMessage(Action.TOGGLE_WINNER, 1, null));
    assertTeam(gameSt2, 0, Team::isWinner, false);
    assertTeam(gameSt2, 1, Team::isWinner, true);
    assertTeam(gameSt2, 2, Team::isWinner, false);

    Game gameSt3 = dataService.reduce(gameSt2, getSingleMessage(Action.TOGGLE_WINNER, 2, null));
    assertTeam(gameSt3, 0, Team::isWinner, false);
    assertTeam(gameSt3, 1, Team::isWinner, true);
    assertTeam(gameSt3, 2, Team::isWinner, true);

    Game gameSt4 = dataService.reduce(gameSt3, getSingleMessage(Action.TOGGLE_WINNER, 2, null));
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
