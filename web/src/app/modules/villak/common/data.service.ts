import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {Game} from '../game-data/model/game';
import {WebsocketService} from './websocket.service';

@Injectable()
export class DataService {

  private data: BehaviorSubject<Game> = new BehaviorSubject(new Game([], []));
  currentData = this.data.asObservable();

  constructor(private ws: WebsocketService) {
    ws.listen((game: Game) => {
      this.data.next(game);
      if (game.settings.shouldRefresh) {
        window.location.reload();
      }
    });
  }
}
