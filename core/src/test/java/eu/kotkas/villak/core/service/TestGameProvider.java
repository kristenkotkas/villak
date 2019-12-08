package eu.kotkas.villak.core.service;

import eu.kotkas.villak.core.model.Category;
import eu.kotkas.villak.core.model.Game;
import eu.kotkas.villak.core.model.Question;
import eu.kotkas.villak.core.model.Round;
import eu.kotkas.villak.core.model.Team;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Kristen Kotkas
 */
class TestGameProvider {

  static Game loadGame() {
    return Game.builder()
      .teams(Arrays.asList(
        Team.builder().id(0).name("Team 1").build(),
        Team.builder().id(1).name("Team 2").build(),
        Team.builder().id(2).name("Team 3").build()
      ))
      .rounds(Arrays.asList(
        Round.builder().id(0).categories(Arrays.asList(
          Category.builder().id(0).name("Category 1").questions(Arrays.asList(
            Question.builder().id(0).amount(0).question("Quest 1").answer("Answer 1").build(),
            Question.builder().id(1).amount(0).question("Quest 2").answer("Answer 2").build(),
            Question.builder().id(2).amount(0).question("Quest 3").answer("Answer 3").build()
          )).build(),
          Category.builder().id(1).name("Category 2").questions(Arrays.asList(
            Question.builder().id(3).amount(0).question("Quest 4").answer("Answer 4").build(),
            Question.builder().id(4).amount(0).question("Quest 5").answer("Answer 5").build(),
            Question.builder().id(5).amount(0).question("Quest 6").answer("Answer 6").build()
          )).build()
        )).build(),
        Round.builder().id(1).categories(Arrays.asList(
          Category.builder().id(2).name("Category 3").questions(Arrays.asList(
            Question.builder().id(6).amount(0).question("Quest 7").answer("Answer 7").build(),
            Question.builder().id(7).amount(0).question("Quest 8").answer("Answer 8").build(),
            Question.builder().id(8).amount(0).question("Quest 9").answer("Answer 9").build()
          )).build(),
          Category.builder().id(3).name("Category 4").questions(Arrays.asList(
            Question.builder().id(9).amount(0).question("Quest 10").answer("Answer 10").build(),
            Question.builder().id(10).amount(0).question("Quest 11").answer("Answer 11").build(),
            Question.builder().id(11).amount(0).question("Quest 12").answer("Answer 12").build()
          )).build()
        )).build(),
        Round.builder().id(2).categories(Collections.singletonList(
          Category.builder().id(4).name("Category 3").questions(Arrays.asList(
            Question.builder().id(12).amount(0).question("Quest 7").answer("Answer 7").build(),
            Question.builder().id(13).amount(0).question("Quest 8").answer("Answer 8").build(),
            Question.builder().id(14).amount(0).question("Quest 9").answer("Answer 9").build()
          )).build()
        )).build()
      )).build();
  }

}
