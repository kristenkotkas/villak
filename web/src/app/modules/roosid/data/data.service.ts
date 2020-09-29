import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {WebsocketService} from './websocket.service';
import {Game} from "../model/game";

@Injectable()
export class DataService {

  private data: BehaviorSubject<Game> = new BehaviorSubject(new Game([], [], []));
  currentData = this.data.asObservable();

  constructor(private ws: WebsocketService) {
    ws.listen((game: Game) => {
      this.data.next(game);
    });
  }
/*
  private static getTestGame(): Game {
    return {
      teams: [{
        id: 0,
        name: 'Arendajad',
        score: 0,
        crossCount: 0
      }, {
        id: 0,
        name: 'Testijad',
        score: 0,
        crossCount: 0
      }],
      rounds: [
        {
          id: 0,
          question: 'Nimeta midagi hääd',
          multiplayer: 1,
          score: 0,
          slots: 6,
          isActive: true,
          answers: [
            {
              id: 1,
              amount: 20,
              answer: 'Pizza',
              state: AnswerState.CLOSED
            },
            {
              id: 2,
              amount: 15,
              answer: 'Komm',
              state: AnswerState.CLOSED
            },
            {
              id: 3,
              amount: 10,
              answer: 'Küpsis',
              state: AnswerState.CLOSED
            },
            {
              id: 4,
              amount: 8,
              answer: 'Sushi',
              state: AnswerState.CLOSED
            },
            {
              id: 5,
              amount: 7,
              answer: 'Veatu kood',
              state: AnswerState.CLOSED
            },
            {
              id: 6,
              amount: 2,
              answer: 'Midagi muud',
              state: AnswerState.CLOSED
            }
          ]
        },
        {
          id: 1,
          question: 'Nimeta parim pähkel',
          multiplayer: 2,
          score: 0,
          slots: 6,
          isActive: false,
          answers: [
            {
              id: 1,
              amount: 25,
              answer: 'Pistaatsia',
              state: AnswerState.CLOSED
            },
            {
              id: 2,
              amount: 21,
              answer: 'Sarapuu',
              state: AnswerState.CLOSED
            },
            {
              id: 3,
              amount: 15,
              answer: 'Kreeka',
              state: AnswerState.CLOSED
            },
            {
              id: 4,
              amount: 9,
              answer: 'Mandel',
              state: AnswerState.CLOSED
            },
            {
              id: 5,
              amount: 5,
              answer: 'meh?',
              state: AnswerState.CLOSED
            }
          ]
        }
      ]
    };
  }*/
}
