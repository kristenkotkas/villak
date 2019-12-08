import {Category} from './model/category';
import {Game} from './model/game';
import {Question} from './model/question';
import {Round} from './model/round';
import {Team} from './model/team';

export class GameProvider {
  // todo: kristen - see vist üldse maha lasta

  static createGame(): Game {
    const teams: Team[] = [
      this.getTeam(0, 'First team', 0),
    ];
    const rounds: Round[] = [
      this.getRound(0, [
        this.getCategory(0, 'Category', [
          this.getQuestion(0, 50, false, 'Hint', 'Answer'),
        ])
      ])
    ];
    return this.getGame(teams, rounds);
  }

  private static getGame(teams: Team[], rounds: Round[]): Game {
    return new Game(teams, rounds);
  }

  private static getTeam(id: number, name: string, score: number): Team {
    return new Team(id, name, score);
  }

  private static getQuestion(id: number, amount: number, isSilver: boolean, question: string, answer: string,
                             pictureUri?: string, sound?: string): Question {
    return new Question(id, amount, isSilver, question, answer, pictureUri, sound);
  }

  private static getCategory(id: number, name: string, questions: Question[]): Category {
    return new Category(id, name, questions);
  }

  private static getRound(id: number, categories: Category[]): Round {
    return new Round(id, categories);
  }

}
