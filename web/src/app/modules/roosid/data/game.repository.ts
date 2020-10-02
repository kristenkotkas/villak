import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Util} from "../../common/util";
import {Game} from "../model/game";

@Injectable({providedIn: 'root'})
export class GameRepository {

  constructor(private http: HttpClient) {
  }

  getGame(): Promise<Game> {
    return this.http.get<Game>(`${Util.getApiUrl()}/api/roosid`).pipe().toPromise();
  }

  createGame(game: Game): Promise<any> {
    return this.http.post(`${Util.getApiUrl()}/api/roosid`, game).pipe().toPromise();
  }

}
