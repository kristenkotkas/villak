package eu.kotkas.villak.core.service;

import eu.kotkas.villak.core.model.Category;
import eu.kotkas.villak.core.model.Game;
import eu.kotkas.villak.core.model.Question;
import eu.kotkas.villak.core.model.Round;
import eu.kotkas.villak.core.model.Team;
import eu.kotkas.villak.core.model.dto.CategoryDto;
import eu.kotkas.villak.core.model.dto.QuestionDto;
import eu.kotkas.villak.core.model.dto.RoundDto;
import eu.kotkas.villak.core.model.dto.TeamDto;
import eu.kotkas.villak.core.model.enums.NameState;
import eu.kotkas.villak.core.model.enums.QuestionState;

import java.util.Arrays;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Kristen Kotkas
 */
public class TestHelper {

  static final String TEST_STRING = "TEST_STRING";
  static final int TEST_INT = -1;
  static final boolean TEST_BOOELAN = true;

  static void assertRound(Round round) {
    assertFalse(round.isActive());
    round.getCategories().forEach(TestHelper::assertCategory);
  }

  static void assertQuestion(Question question) {
    assertEquals(question.getAmount(), TEST_INT);
    assertTrue(question.isSilver());
    assertEquals(question.getQuestion(), TEST_STRING);
    assertEquals(question.getAnswer(), TEST_STRING);
    assertEquals(question.getState(), QuestionState.CLOSE);
    assertEquals(question.getPictureUri(), TEST_STRING);
    assertEquals(question.getSoundUri(), TEST_STRING);
  }

  static void assertCategory(Category category) {
    assertEquals(category.getName(), TEST_STRING);
    assertEquals(category.getNameState(), NameState.CATEGORY_HIDE);
    assertEquals(category.getQuestions().size(), 2);
    category.getQuestions().forEach(TestHelper::assertQuestion);
  }


  static void assertQuestions(Game game, long questionId,
                              Function<Question, Object> actualMapper, Object expected) {
    game.getRounds()
      .forEach(round -> round.getCategories()
        .forEach(category -> category.getQuestions().stream()
          .filter(question -> question.getId() == questionId)
          .forEach(question -> assertEquals(expected, actualMapper.apply(question)))));
  }

  static void assertTeam(Team team) {
    assertEquals(team.getName(), TEST_STRING);
    assertEquals(team.getScore(), 0);
    assertFalse(team.isHavePressed());
    assertEquals(team.getTimePressed(), 0);
    assertFalse(team.isQuickest());
    assertFalse(team.isWinner());
  }

  static void assertTeam(Game game, long teamId,
                         Function<Team, Object> actualMapper, Object expected) {
    game.getTeams().stream()
      .filter(team -> team.getId() == teamId)
      .forEach(team -> assertEquals(expected, actualMapper.apply(team)));
  }

  static void assertCategory(Game game, long categoryId,
                             Function<Category, Object> actualMapper, Object expected) {
    game.getRounds()
      .forEach(round -> round.getCategories().stream()
        .filter(category -> category.getId() == categoryId)
        .forEach(category -> assertEquals(expected, actualMapper.apply(category))));
  }

  static void assertRound(Game game, long roundId,
                          Function<Round, Object> actualMapper, Object expected) {
    game.getRounds().stream()
      .filter(round -> round.getId() == roundId)
      .forEach(round -> assertEquals(expected, actualMapper.apply(round)));
  }


  static QuestionDto getQuestionDto() {
    return QuestionDto.builder()
      .amount(TEST_INT)
      .question(TEST_STRING)
      .answer(TEST_STRING)
      .pictureUri(TEST_STRING)
      .soundUri(TEST_STRING)
      .silver(TEST_BOOELAN)
      .build();
  }

  static TeamDto getTeamDto() {
    return TeamDto.builder()
      .name(TEST_STRING)
      .build();
  }

  static CategoryDto getCategoryDto() {
    return CategoryDto.builder()
      .name(TEST_STRING)
      .questions(Arrays.asList(
        getQuestionDto(),
        getQuestionDto()
      )).build();
  }

  static RoundDto getRoundDto() {
    return RoundDto.builder()
      .categories(Arrays.asList(
        getCategoryDto(),
        getCategoryDto()
      )).build();
  }

}
