package eu.kotkas.villak.core.villak.service;

import eu.kotkas.villak.core.villak.model.*;
import eu.kotkas.villak.core.villak.model.dto.*;
import eu.kotkas.villak.core.villak.model.mapper.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static eu.kotkas.villak.core.villak.service.TestHelper.*;
import static eu.kotkas.villak.core.villak.service.TestHelper.getRoundDto;

public class MapperTest {

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
