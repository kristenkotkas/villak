import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {GameProvider} from '../game-data/game-provider';
import {Game} from '../game-data/model/game';
import {WebsocketService} from './websocket.service';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  private data: BehaviorSubject<Game> = new BehaviorSubject(GameProvider.createGame());
  currentData = this.data.asObservable();

  constructor(private ws: WebsocketService) {
    ws.listen((game: Game) => {
      this.data.next(game);
    });
  }
}
